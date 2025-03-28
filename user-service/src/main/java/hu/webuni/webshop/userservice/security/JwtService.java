package hu.webuni.webshop.userservice.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {

    @Value("${user.security.secret}")
    private String secret;

    @Value("${user.security.issuer}")
    private String issuer;

    private static final String AUTH = "auth";
    private Algorithm alg;

    @PostConstruct
    public void init() {
        alg = Algorithm.HMAC256(secret);
    }

    public String createJwtToken(UserDetails principal) {
        return JWT.create()
                .withSubject(principal.getUsername())
                .withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20)))
                .withIssuer(issuer)
                .sign(alg);
    }


    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJwt = JWT.require(alg)
                .withIssuer(issuer)
                .build()
                .verify(jwtToken);

        return new User(decodedJwt.getSubject(), "dummy",
                decodedJwt.getClaim(AUTH).asList(String.class)
                        .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }

}