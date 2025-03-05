package hu.webuni.webshop.orderservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OrderItem", schema = "Orders")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private Long productPriceInHuf;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;


    public OrderItem(Long productId, String productName, Long productPriceInHuf, Integer quantity, Order order) {
        this.productId = productId;
        this.productName = productName;
        this.productPriceInHuf = productPriceInHuf;
        this.quantity = quantity;
        this.order = order;
    }

}
