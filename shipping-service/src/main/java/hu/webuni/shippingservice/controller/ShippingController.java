package hu.webuni.shippingservice.controller;

import hu.webuni.shippingservice.dto.ShipmentDetailsDto;
import hu.webuni.shippingservice.mapper.ShipmentMapper;
import hu.webuni.shippingservice.model.Shipment;
import hu.webuni.shippingservice.service.ShippingService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("/api/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;
    @Autowired
    private ShipmentMapper shipmentMapper;

    @PostMapping("/create")
    ShipmentDetailsDto createShipment(@RequestBody ShipmentDetailsDto shipmentDetailsDto) {
        Shipment shipment = shipmentMapper.dtoToShipment(shipmentDetailsDto);
        Shipment createdShipment = shippingService.createShipment(shipment);
        return shipmentMapper.shipmentToDto(createdShipment);
    }
}
