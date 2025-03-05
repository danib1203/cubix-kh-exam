package hu.webuni.webshop.orderservice.repository;


import hu.webuni.webshop.orderservice.model.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUsername(final String username);

    @EntityGraph(attributePaths = {"items"})
    @Query("SELECT o FROM Order o")
    List<Order> findAllWithItems();
}
