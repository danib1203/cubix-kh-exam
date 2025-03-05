package hu.webuni.shippingservice.service;

import hu.webuni.shippingservice.jms.ShippingMessage;
import hu.webuni.shippingservice.model.Items;
import hu.webuni.shippingservice.model.Shipment;
import hu.webuni.shippingservice.repository.ItemsRepository;
import hu.webuni.shippingservice.repository.ShippingDetailsRepository;
import hu.webuni.shippingservice.xmlws.ShipmentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Random;

@Service
public class ShippingService {

    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private ShippingDetailsRepository shippingDetailsRepository;
    @Autowired
    private JmsTemplate jmsTemplate;
    private final Random random = new Random();


    @Transactional
    public Shipment createShipment(Shipment shipment) throws ShipmentException {
        if (Objects.isNull(shipment)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shipment cannot be null");
        }

        if (shipment.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No items specified in shipment");
        }

        boolean isSuccess = random.nextBoolean();
        sendMessage(shipment.getOrderId(), isSuccess);
        if (!isSuccess) {
            throw new ShipmentException("Shipment failed");
        }
        Shipment savedShipment = shippingDetailsRepository.save(shipment);

        for (Items item : savedShipment.getItems()) {
            item.setShipment(savedShipment);
        }
        return shippingDetailsRepository.save(savedShipment);
    }

    @Async
    protected void sendMessage(Long orderId, Boolean isSuccess) {
        ShippingMessage message = new ShippingMessage();

        message.setSuccess(isSuccess);
        message.setOrderId(orderId);
        message.setTimestamp(OffsetDateTime.now());

        this.jmsTemplate.setDeliveryDelay(10000);
        this.jmsTemplate.convertAndSend("shippingstatus", message);
    }


}
