package hu.webuni.webshop.catalogservice.controller;

import hu.webuni.webshop.api.ProductControllerApi;
import hu.webuni.webshop.api.model.HistoryDataProductDto;
import hu.webuni.webshop.api.model.ProductDto;
import hu.webuni.webshop.catalogservice.mapper.HistoryDataMapper;
import hu.webuni.webshop.catalogservice.mapper.ProductMapper;
import hu.webuni.webshop.catalogservice.model.HistoryData;
import hu.webuni.webshop.catalogservice.model.Product;
import hu.webuni.webshop.catalogservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerApi {


    private final NativeWebRequest webRequest;
    private final HistoryDataMapper historyDataMapper;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(webRequest);
    }

    @Override
    public ResponseEntity<ProductDto> findById(Long id) {
        Product product = productService.findById(id).orElse(null);
        if (Objects.isNull(product)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(productMapper.productToDto(product));
    }


    @Override
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto) {
        Product product = productService.create(productMapper.dtoToProduct(productDto));
        return ResponseEntity.ok(productMapper.productToDto(product));
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ProductDto> updateProduct(Long id, ProductDto productDto) {
        Product product = productMapper.dtoToProduct(productDto);
        product.setId(id);
        try {
            ProductDto savedProductDto = productMapper.productToDto(productService.update(product));
            return ResponseEntity.ok(savedProductDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public ResponseEntity<List<ProductDto>> searchProducts(String name, String categoryName,
                                                           Long minPrice, Long maxPrice,
                                                           Integer page, Integer size,
                                                           String sort) {

        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 20, Sort.by(sort != null && !sort.isEmpty() ? sort : "id").ascending());

        return ResponseEntity.ok(productMapper.productsToDtos(
                productService.searchProducts(
                        name, categoryName, minPrice,
                        maxPrice, pageable)));

    }

    @Override
    public ResponseEntity<List<HistoryDataProductDto>> getProductPriceHistoryById(Long id) {
        List<HistoryData<Product>> productPriceHistory = productService.getProductPriceHistory(id);

        List<HistoryDataProductDto> productDtosWithHistory = new ArrayList<>();
        productPriceHistory.forEach(hd ->
                productDtosWithHistory.add(
                        historyDataMapper.productPriceHistoryDataToDto(hd)));

        return ResponseEntity.ok(productDtosWithHistory);
    }
}
