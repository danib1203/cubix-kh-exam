package hu.webuni.webshop.orderservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private String username;
    private String shippingAddress;
    private OrderStatus status;
    private List<OrderItemDto> items;
    private int version;

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        DECLINED,
        DELIVERED,
        SHIPMENT_FAILED
    }

}
