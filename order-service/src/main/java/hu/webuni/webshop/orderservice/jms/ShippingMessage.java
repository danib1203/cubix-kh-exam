package hu.webuni.webshop.orderservice.jms;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class ShippingMessage {

    private boolean isSuccess;
    private OffsetDateTime timestamp;
    private Long orderId;
}
