package hu.webuni.webshop.orderservice.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Long priceInHuf;

}
