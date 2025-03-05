package hu.webuni.shippingservice.xmlws;

import hu.webuni.shippingservice.dto.ShipmentDetailsDto;
import hu.webuni.shippingservice.mapper.ShipmentMapper;
import hu.webuni.shippingservice.model.Shipment;
import hu.webuni.shippingservice.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingXmlWsImpl implements ShippingXmlWs {

    @Autowired
    private ShippingService shippingService;
    @Autowired
    private ShipmentMapper shipmentMapper;

    @Override
    public ShipmentDetailsDto createShipment(ShipmentDetailsDto shipmentDetailsDto) throws ShipmentException {
        Shipment shipment = shipmentMapper.dtoToShipment(shipmentDetailsDto);
        Shipment createdShipment = shippingService.createShipment(shipment);
        return shipmentMapper.shipmentToDto(createdShipment);
    }
}



