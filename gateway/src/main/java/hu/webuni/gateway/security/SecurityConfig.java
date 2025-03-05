package hu.webuni.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(auth -> auth
                        .pathMatchers("/login").permitAll()
                        .pathMatchers("/oauth2/**").permitAll()
                        .pathMatchers("/login/oauth2/**").permitAll()
                        .pathMatchers("/user/**").permitAll()
                        .pathMatchers("/order/**").authenticated()
                        .pathMatchers(HttpMethod.GET, "/catalog/**").permitAll()

                        .pathMatchers(HttpMethod.POST, "/catalog/**").hasAuthority("admin")
                        .pathMatchers(HttpMethod.PUT, "/catalog/**").hasAuthority("admin")
                        .pathMatchers(HttpMethod.DELETE, "/catalog/**").hasAuthority("admin")

                        .anyExchange().denyAll()
                )

                .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
