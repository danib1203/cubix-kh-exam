package hu.webuni.webshop.orderservice.mapper;

import hu.webuni.webshop.orderservice.wsclient.ShipmentDetailsDto;
import hu.webuni.webshop.orderservice.wsclient.ShipmentItemsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentDetailsMapper {

    ShipmentDetailsDto dtoDetailsToWsclienDetailsDto(hu.webuni.webshop.orderservice.dto.ShipmentDetailsDto shipmentDetailsDto);

    ShipmentItemsDto dtoItemsToWsclientItemsDto(hu.webuni.webshop.orderservice.dto.ShipmentItemsDto itemsDto);
}
