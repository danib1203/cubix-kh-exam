package hu.webuni.webshop.catalogservice.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import hu.webuni.webshop.catalogservice.model.HistoryData;
import hu.webuni.webshop.catalogservice.model.Product;
import hu.webuni.webshop.catalogservice.model.QProduct;
import hu.webuni.webshop.catalogservice.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    @PersistenceContext
    private EntityManager em;


    @Transactional
    public Product create(Product product) {
        if (Objects.isNull(product)) {
            return null;
        }

        if (product.getId() != null && productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product with id " + product.getId() + " already " + "exists");
        }
        return save(product);
    }

    @Transactional
    @CachePut(value = "productPriceHistoryCache", key = "#productId")
    public Product update(Product product) {
        if (Objects.isNull(product)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (findById(product.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Product with id " + product.getId() + " not found");
        }
        return save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    public Iterable<Product> searchProducts(String name, String categoryName,
                                            Long minPrice, Long maxPrice, Pageable pageable) {
        QProduct qProduct = QProduct.product;

        Predicate predicate = qProduct.id.isNotNull();

        if (minPrice != null) {
            predicate = ExpressionUtils.allOf(predicate, qProduct.priceInHuf.goe(minPrice));
        }

        if (maxPrice != null) {
            predicate = ExpressionUtils.allOf(predicate, qProduct.priceInHuf.loe(maxPrice));
        }

        if (name != null && !name.isEmpty()) {
            predicate = ExpressionUtils.allOf(predicate, qProduct.name.containsIgnoreCase(name));
        }

        if (categoryName != null && !categoryName.isEmpty()) {
            predicate = ExpressionUtils.allOf(predicate, qProduct.category.name.startsWithIgnoreCase(categoryName));
        }

        System.out.println("Generated Predicate: " + predicate);
        return productRepository.findAll(predicate, pageable);
    }

    @Transactional
    @CacheEvict(value = "productPriceHistoryCache", key = "#productId")
    public void delete(long productId) {
        productRepository.deleteById(productId);
    }

    private Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings({"unchecked"})
    @Cacheable(value = "productPriceHistoryCache", key = "#productId")
    public List<HistoryData<Product>> getProductPriceHistory(Long productId) {
        return AuditReaderFactory.get(em)
                .createQuery()
                .forRevisionsOfEntity(Product.class, false, true)
                .add(AuditEntity.id().eq(productId))
                .getResultList()
                .stream()
                .map(o -> {
                    Object[] objArray = (Object[]) o;
                    Product product = (Product) objArray[0];
                    DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) objArray[1];
                    RevisionType revisionType = (RevisionType) objArray[2];

                    return new HistoryData<>(
                            product,
                            revisionType,
                            defaultRevisionEntity.getId(),
                            defaultRevisionEntity.getRevisionDate()
                    );
                })
                .toList();
    }

}
