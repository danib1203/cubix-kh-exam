package hu.webuni.webshop.orderservice.mapper;


import hu.webuni.webshop.orderservice.dto.OrderDto;
import hu.webuni.webshop.orderservice.dto.OrderItemDto;
import hu.webuni.webshop.orderservice.model.Order;
import hu.webuni.webshop.orderservice.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order dtoToOrder(OrderDto orderDto);

    @Mapping(target = "items", source = "items")
    OrderDto orderToDto(Order order);

    List<OrderDto> ordersToDtos(List<Order> orders);

    @Mapping(target = "order", ignore = true)
    OrderItemDto orderItemToDto(OrderItem orderItem);

}
