package hu.webuni.webshop.orderservice.service;
import hu.webuni.webshop.orderservice.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "catalog", url = "http://localhost:8080/catalog", configuration = FeignClientConfig.class)
public interface CatalogServiceClient {
    @GetMapping("/product/{id}")
    ProductDto getProductById(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
