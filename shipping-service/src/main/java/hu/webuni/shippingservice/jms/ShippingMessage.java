package hu.webuni.shippingservice.jms;

import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

public class ShippingMessage {

    private boolean isSuccess;
    private OffsetDateTime timestamp;
    private Long orderId;

    public ShippingMessage() {
    }

    public ShippingMessage(boolean isSuccess, OffsetDateTime timestamp, Long orderId) {
        this.isSuccess = isSuccess;
        this.timestamp = timestamp;
        this.orderId = orderId;
    }

    public ShippingMessage(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
