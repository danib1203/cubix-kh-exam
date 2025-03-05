package hu.webuni.webshop.catalogservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;


@Data
@Entity
@Audited
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Product", schema = "Catalog")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long priceInHuf;
    @ManyToOne
    private Category category;

    public Product(String name, Long priceInHuf, Category category) {
        this.name = name;
        this.priceInHuf = priceInHuf;
        this.category = category;
    }
}
