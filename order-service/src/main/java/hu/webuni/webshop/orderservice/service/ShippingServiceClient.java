package hu.webuni.webshop.orderservice.service;

import hu.webuni.webshop.orderservice.dto.ShipmentDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipping-service", url = "http://localhost:8090/services", configuration = FeignClientConfig.class)
public interface ShippingServiceClient {

    // http://localhost:8090/services/shipping
    // http://localhost:8090/api/shipping/create

    @GetMapping("/shipping")
    ShipmentDetailsDto createShipment(@RequestBody ShipmentDetailsDto shipmentDetailsDto);
}
