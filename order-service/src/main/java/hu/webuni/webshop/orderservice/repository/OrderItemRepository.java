package hu.webuni.webshop.orderservice.repository;


import hu.webuni.webshop.orderservice.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
