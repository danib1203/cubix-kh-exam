package hu.webuni.shippingservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShipmentDetailsDto {

    private Long id;
    private String shippingAddress;
    private String pickUpAddress;
    private List<ShipmentItemsDto> items;
    private Long orderId;
}
