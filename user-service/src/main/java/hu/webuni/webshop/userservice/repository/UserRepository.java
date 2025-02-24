package hu.webuni.webshop.userservice.repository;

import java.util.Optional;

import hu.webuni.webshop.userservice.model.WebshopUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<WebshopUser, String> {

    Optional<WebshopUser> findByFacebookId(String facebookId);

    Optional<WebshopUser> findByUsername(String username);
}