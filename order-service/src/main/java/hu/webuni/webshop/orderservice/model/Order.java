package hu.webuni.webshop.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Orders", schema = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String shippingAddress;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Version
    private int version;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;


    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        DECLINED,
        DELIVERED,
        SHIPMENT_FAILED
    }

}
