package hu.webuni.webshop.userservice.service;

import hu.webuni.webshop.userservice.model.WebshopUser;
import hu.webuni.webshop.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    //   private final OAuth2AuthorizedClientService authClientService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
        public void registerNewUserIfNeeded(OAuth2AuthenticationToken authenticationToken) {
            String userId = authenticationToken.getName();

            OAuth2User oauth2User = authenticationToken.getPrincipal();
            System.out.println("FB id from oauth2User:" + oauth2User.getName());
            Object email = oauth2User.getAttribute("email");
            System.out.println("email:" + email);
            System.out.println("full name:" + oauth2User.getAttribute("name"));

            String authorizedClientRegistrationId = authenticationToken.getAuthorizedClientRegistrationId(); //facebook, google, stb.

            OAuth2AuthorizedClient client = authClientService.loadAuthorizedClient(authorizedClientRegistrationId, userId);
            System.out.println("access token:" + client.getAccessToken().getTokenValue());

            Optional<WebshopUser> optionalExistingUser = userRepository.findByFacebookId(userId);
            if (optionalExistingUser.isEmpty()) {
                WebshopUser newUser = new WebshopUser(email.toString(), "", email.toString(), Set.of("user"));
                newUser.setFacebookId(userId);
                userRepository.save(newUser);
            }
        }
    */
    public void register(String email, String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            System.out.println("User already exists");
        } else {
            System.out.println("User created:" + userRepository.save(new WebshopUser(username, passwordEncoder.encode(password), email, Set.of("user"))));
        }

    }
}