package hu.webuni.webshop.userservice.service;

import hu.webuni.webshop.userservice.model.WebshopUser;
import hu.webuni.webshop.userservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class InitDbService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void deleteDb() {
        userRepository.deleteAll();
    }


    @Transactional
    public void addInitData() {
        createUsersIfNeeded();
    }

    @Transactional
    public void createUsersIfNeeded() {
        if (!userRepository.existsById("admin")) {
            userRepository.save(new WebshopUser("admin", passwordEncoder.encode("pass"), Set.of("admin", "user")));
        }
    }
}
