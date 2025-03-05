package hu.webuni.webshop.orderservice.dto;

import lombok.Data;

@Data
public class ShipmentItemsDto {

    private Long id;
    private String name;
    private int quantity;
    private ShipmentDetailsDto shippingDetails;
}
