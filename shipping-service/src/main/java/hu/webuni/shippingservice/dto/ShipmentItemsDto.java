package hu.webuni.shippingservice.dto;

import lombok.Data;

@Data
public class ShipmentItemsDto {

    private Long id;
    private String name;
    private int quantity;
    private ShipmentDetailsDto shippingDetails;
}
