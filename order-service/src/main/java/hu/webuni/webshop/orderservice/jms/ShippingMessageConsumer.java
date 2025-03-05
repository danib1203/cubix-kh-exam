package hu.webuni.webshop.orderservice.jms;

import hu.webuni.webshop.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShippingMessageConsumer {

    private final OrderService orderService;


    @JmsListener(destination = "shippingstatus")
    public void onShippingMessage(ShippingMessage message) {
        System.out.println(message);

        boolean isSuccess = message.isSuccess();
        long orderId = message.getOrderId();
        orderService.updateOrderStatusAfterShipmentMessage(isSuccess, orderId);
    }
}
