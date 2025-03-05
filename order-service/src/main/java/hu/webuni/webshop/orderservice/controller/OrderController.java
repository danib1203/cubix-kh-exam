package hu.webuni.webshop.orderservice.controller;

import hu.webuni.webshop.orderservice.dto.OrderDto;
import hu.webuni.webshop.orderservice.mapper.OrderMapper;
import hu.webuni.webshop.orderservice.model.Order;
import hu.webuni.webshop.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final JmsTemplate jmsTemplate;

    @PostMapping("/create")
    public OrderDto createOrder(@AuthenticationPrincipal Jwt jwt, @RequestBody OrderDto orderDto) {
        Order incomingOrder = orderMapper.dtoToOrder(orderDto);
        Order newOrder = orderService.createOrder(incomingOrder, jwt);
        return orderMapper.orderToDto(newOrder);
    }

    @GetMapping("/search/{username}")
    public List<OrderDto> searchOrders(@PathVariable String username) {
        List<Order> orders = orderService.getOrdersByUsername(username);
        return orderMapper.ordersToDtos(orders);
    }

    @PutMapping("/changeStatus/{orderId}")
    public OrderDto changeStatus(@RequestParam int statusCode, @PathVariable Long orderId) {
        return orderMapper.orderToDto(orderService.changeStatus(statusCode, orderId));
    }


}
