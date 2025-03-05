package hu.webuni.webshop.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitDbService {
    private final JdbcTemplate jdbcTemplate;

    public void createOrderSchema() {
        jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS orders");
    }
}
