package hu.webuni.shippingservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "shippingDetails", schema = "Shipping")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shippingAddress;
    private String pickUpAddress;
    @OneToMany(mappedBy = "shipment", cascade = CascadeType.ALL)
    private List<Items> items;
    private Long orderId;
}
