package hu.webuni.webshop.orderservice.mapper;


import hu.webuni.webshop.orderservice.dto.OrderItemDto;
import hu.webuni.webshop.orderservice.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem dtoToOrderItem(OrderItemDto orderItemDto);

    OrderItemDto orderItemToDto(OrderItem orderItem);

    List<OrderItemDto> orderItemsToDtos(List<OrderItem> orderItems);

}
