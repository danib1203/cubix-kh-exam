package hu.webuni.shippingservice.repository;

import hu.webuni.shippingservice.model.Shipment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShippingDetailsRepository extends JpaRepository<Shipment, Long> {

    @EntityGraph(attributePaths = {"items"})
    @Query("SELECT s FROM Shipment s")
    List<Shipment> findAllWithItems();
}


