package hu.webuni.webshop.userservice.service;

import hu.webuni.webshop.userservice.model.WebshopUser;
import hu.webuni.webshop.userservice.repository.UserRepository;
import hu.webuni.webshop.userservice.security.JwtService;
import hu.webuni.webshop.userservice.security.WebshopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final OAuth2AuthorizedClientService authClientService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final WebshopUserDetailsService webshopUserDetailsService;


    public String registerNewUserIfNeeded(OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        WebshopUser user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    WebshopUser newUser = new WebshopUser(email, email, name, Set.of("user"));
                    return userRepository.save(newUser);
                });

        return jwtService.createJwtToken(webshopUserDetailsService.loadUserByUsername(user.getEmail()));
    }

    public void register(String email, String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            System.out.println("User already exists");
            throw new RuntimeException("User already exists");
        } else
            System.out.println("User created:" + userRepository.save(new WebshopUser(username, passwordEncoder.encode(password), email, Set.of("user"))));

    }
}