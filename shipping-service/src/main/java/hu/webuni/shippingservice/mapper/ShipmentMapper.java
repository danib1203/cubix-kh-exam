package hu.webuni.shippingservice.mapper;


import hu.webuni.shippingservice.dto.ShipmentDetailsDto;
import hu.webuni.shippingservice.dto.ShipmentItemsDto;
import hu.webuni.shippingservice.model.Items;
import hu.webuni.shippingservice.model.Shipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    Shipment dtoToShipment(ShipmentDetailsDto shipmentDetailsDto);

    ShipmentDetailsDto shipmentToDto(Shipment shipment);

    List<ShipmentDetailsDto> shipmentsToDtos(List<ShipmentDetailsDto> shipmentDetailsDtos);

    @Mapping(target = "shippingDetails", ignore = true)
    ShipmentItemsDto shipmentItemToDto(Items item);

}
