package hu.webuni.shippingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Items", schema = "Shipping")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "shipping_details_id")
    private Shipment shipment;
}
