package hu.webuni.shippingservice.xmlws;

import jakarta.xml.ws.WebFault;

@WebFault(name = "ShipmentFault")
public class ShipmentException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String faultMessage;

    public ShipmentException(String message) {
        super(message);
        this.faultMessage = message;
    }

    public String getFaultMessage() {
        return faultMessage;
    }
}
