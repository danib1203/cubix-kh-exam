package hu.webuni.webshop.orderservice.service;

import feign.FeignException;
import hu.webuni.webshop.orderservice.dto.ProductDto;
import hu.webuni.webshop.orderservice.dto.ShipmentDetailsDto;
import hu.webuni.webshop.orderservice.dto.ShipmentItemsDto;
import hu.webuni.webshop.orderservice.model.Order;
import hu.webuni.webshop.orderservice.model.OrderItem;
import hu.webuni.webshop.orderservice.repository.OrderItemRepository;
import hu.webuni.webshop.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CatalogServiceClient catalogServiceClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShippingServiceClient shippingServiceClient;

    @Transactional
    public Order createOrder(Order order, Jwt jwt) {
        if (Objects.isNull(order)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        if (order.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order items cannot be empty");
        }
        String shippingAddress = order.getShippingAddress();
        Order newOrder = new Order();
        newOrder.setUsername(jwt.getClaim("sub"));
        newOrder.setStatus(Order.OrderStatus.PENDING);
        newOrder = orderRepository.save(newOrder);
        newOrder.setShippingAddress(shippingAddress);
        String jwtToken = jwt.getTokenValue();
        List<OrderItem> verifiedProducts = verifyProductsById(order.getItems(), jwtToken, newOrder);

        newOrder.setItems(verifiedProducts);

        return orderRepository.save(newOrder);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected List<OrderItem> verifyProductsById(List<OrderItem> orderItems, String jwt, Order order) {
        List<OrderItem> verifiedItems = new ArrayList<>();

        for (OrderItem orderItem : orderItems) {
            ProductDto product = catalogServiceClient.getProductById(orderItem.getProductId(), jwt);

            if (orderItem.getId() == null || orderItem.getId() <= 0) {
                orderItem.setId(null);
                orderItem = orderItemRepository.save(orderItem);
            }

            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductPriceInHuf(product.getPriceInHuf());

            orderItem.setOrder(order);

            if (orderItem.getId() == null) {
                orderItem = orderItemRepository.save(orderItem);
            } else {
                OrderItem finalOrderItem = orderItem;
                orderItem = orderItemRepository.findById(orderItem.getId())
                        .map(existingItem -> {
                            existingItem.setProductId(finalOrderItem.getProductId());
                            existingItem.setProductName(finalOrderItem.getProductName());
                            existingItem.setProductPriceInHuf(finalOrderItem.getProductPriceInHuf());
                            existingItem.setOrder(order);
                            return orderItemRepository.save(existingItem);
                        })
                        .orElseGet(() -> orderItemRepository.saveAndFlush(finalOrderItem));
            }

            verifiedItems.add(orderItem);
        }
        return verifiedItems;
    }


    @Transactional
    public List<Order> getOrdersByUsername(String username) {
        List<Order> orders = orderRepository.findByUsername(username);
        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        orders = orderRepository.findAllWithItems();
        return orders;
    }


    @Transactional
    public void updateOrderStatusAfterShipmentMessage(boolean isSuccess, long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!isSuccess) {
            order.setStatus(Order.OrderStatus.SHIPMENT_FAILED);
        } else {
            order.setStatus(Order.OrderStatus.DELIVERED);
        }
    }

    @Transactional
    public Order changeStatus(int statusCode, Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Order with id " + orderId + " not found");
        }

        Order order = optionalOrder.get();
        if (order.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order items cannot be empty");
        }
        order.getItems().get(0);

        switch (statusCode) {
            case 1:
                order.setStatus(Order.OrderStatus.CONFIRMED);
                ShipmentDetailsDto shipmentDetailsDto = createShipmentDtos(order);
                try {
                    shippingServiceClient.createShipment(shipmentDetailsDto);
                } catch (FeignException.NotAcceptable e){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Shipment failed");
                }
            case 2:
                order.setStatus(Order.OrderStatus.DECLINED);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Invalid status code. Only 1 (CONFIRMED) and 2 (DECLINED) are allowed)");
        }
        return orderRepository.save(order);
    }

    private ShipmentDetailsDto createShipmentDtos(Order order) {
        List<ShipmentItemsDto> itemsDto = new ArrayList<>();

        order.getItems().forEach(item -> {
            ShipmentItemsDto itemDto = new ShipmentItemsDto();
            itemDto.setQuantity(item.getQuantity());
            itemDto.setName(item.getProductName());
            itemsDto.add(itemDto);
        });

        ShipmentDetailsDto detailsDto = new ShipmentDetailsDto();
        detailsDto.setShippingAddress(order.getShippingAddress());
        detailsDto.setPickUpAddress("1026, Bp. Fix-pickup-address 23");
        detailsDto.setItems(itemsDto);
        detailsDto.setOrderId(order.getId());
        return detailsDto;
    }

}
