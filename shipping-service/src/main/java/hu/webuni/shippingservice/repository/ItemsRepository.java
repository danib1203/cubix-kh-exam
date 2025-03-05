package hu.webuni.shippingservice.repository;

import hu.webuni.shippingservice.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepository extends JpaRepository<Items, Long> {
}
