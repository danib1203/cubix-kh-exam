package hu.webuni.webshop.catalogservice.mapper;

import hu.webuni.webshop.api.model.ProductDto;
import hu.webuni.webshop.catalogservice.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product dtoToProduct(ProductDto productDto);

    ProductDto productToDto(Product product);

    List<ProductDto> productsToDtos(List<Product> products);

    List<ProductDto> productsToDtos(Iterable<Product> all);

}
