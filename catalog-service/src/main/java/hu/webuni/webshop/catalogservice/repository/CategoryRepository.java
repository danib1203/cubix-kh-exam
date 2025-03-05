package hu.webuni.webshop.catalogservice.repository;

import hu.webuni.webshop.catalogservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(final String name);
    boolean existsByName(final String name);
}
