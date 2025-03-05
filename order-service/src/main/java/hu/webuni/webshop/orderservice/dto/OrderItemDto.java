package hu.webuni.webshop.orderservice.dto;

import hu.webuni.webshop.orderservice.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderItemDto {

    private Long id;

    private Long productId;
    private String productName;
    private Long productPriceInHuf;
    private Integer quantity;
    private Order order;



}
