package hu.webuni.webshop.userservice.security;

import hu.webuni.webshop.userservice.model.WebshopUser;
import hu.webuni.webshop.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class WebshopUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebshopUser webshopUser = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(username, webshopUser.getPassword(),
                webshopUser.getRoles().stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }

}
