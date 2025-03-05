package hu.webuni.webshop.catalogservice.service;

import hu.webuni.webshop.catalogservice.model.Category;
import hu.webuni.webshop.catalogservice.model.Product;
import hu.webuni.webshop.catalogservice.repository.CategoryRepository;
import hu.webuni.webshop.catalogservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InitDbService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final JdbcTemplate jdbcTemplate;


    @Transactional
    public void initDatabase() {
        Category tech = new Category("tech");
        Category clothes = new Category("clothes");

        categoryRepository.save(tech);
        categoryRepository.save(clothes);

        Product laptop = new Product("Gaming laptop", 350000L, tech);
        Product camera = new Product("Camera", 80000L, tech);

        Product tShirt = new Product("T-shirt", 7600L, clothes);

        productRepository.save(laptop);
        productRepository.save(camera);
        productRepository.save(tShirt);

    }


    @Transactional
    public void clearDatabase() {
        jdbcTemplate.update("DELETE FROM catalog.product_aud");
        jdbcTemplate.update("DELETE FROM catalog.category_aud");

        productRepository.deleteAllInBatch();
        categoryRepository.deleteAllInBatch();
    }

}
