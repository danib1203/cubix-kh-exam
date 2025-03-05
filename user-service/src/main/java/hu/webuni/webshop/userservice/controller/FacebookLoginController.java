package hu.webuni.webshop.userservice.controller;

import hu.webuni.webshop.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class FacebookLoginController {

    private final UserService userService;

    @GetMapping("/api/fbLoginSuccess")
    public ResponseEntity<?> onFacebookLoginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        String jwt = userService.registerNewUserIfNeeded(principal);
        return ResponseEntity.ok(Collections.singletonMap("token", jwt));
    }


}
