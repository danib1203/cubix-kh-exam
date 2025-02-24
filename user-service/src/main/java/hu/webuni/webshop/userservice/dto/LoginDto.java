package hu.webuni.webshop.userservice.dto;

import lombok.Data;

@Data
public class LoginDto {

    private String username;
    private String password;
}