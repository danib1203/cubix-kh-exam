package hu.webuni.webshop.catalogservice.mapper;

import hu.webuni.webshop.api.model.HistoryDataProductDto;
import hu.webuni.webshop.api.model.ProductDto;
import hu.webuni.webshop.catalogservice.model.HistoryData;
import hu.webuni.webshop.catalogservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryDataMapper {


    HistoryDataProductDto productPriceHistoryDataToDto(HistoryData<Product> hd);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "priceInHuf")
    @Mapping(target = "category", ignore = true)
    ProductDto productToDto(Product product);


}
