package hu.webuni.webshop.userservice.service;

import hu.webuni.webshop.userservice.model.WebshopUser;
import hu.webuni.webshop.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class InitDbService {

    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void deleteDb() {

    }

    @Transactional
    public void deleteAudTables() {
        jdbcTemplate.update("DELETE FROM address_aud");
    }

    @Transactional
    public void deleteAuditData() {
        jdbcTemplate.update("DELETE FROM address_aud");
    }


    @Transactional
    public void addInitData() {
//        createUsersIfNeeded();
        userRepository.save(new WebshopUser("admin", passwordEncoder.encode("pass"), Set.of("admin", "user")));

    }

    @Transactional
    public void createUsersIfNeeded() {
        if(!userRepository.existsById("admin")) {
            userRepository.save(new WebshopUser("admin", passwordEncoder.encode("pass"), Set.of("admin", "user")));
        }
    }
}
