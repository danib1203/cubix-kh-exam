package hu.webuni.shippingservice.xmlws;

import hu.webuni.shippingservice.dto.ShipmentDetailsDto;
import jakarta.jws.WebService;
import org.springframework.web.bind.annotation.RequestBody;

@WebService
public interface ShippingXmlWs {

    ShipmentDetailsDto createShipment(@RequestBody ShipmentDetailsDto shipmentDetailsDto);
}
