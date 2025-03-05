package hu.webuni.webshop.userservice.controller;

import hu.webuni.webshop.userservice.dto.RegisterDto;
import hu.webuni.webshop.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody final RegisterDto registerDto) {
        String email = registerDto.getEmail();
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();
        try {
            userService.register(email, username, password);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Registration failed for user: " + username);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
