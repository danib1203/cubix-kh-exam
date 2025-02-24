package hu.webuni.webshop.userservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "webshop_user")
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebshopUser {


    @Id
    private String username;
    private String password;
    private String email;
    private String facebookId;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;

    public WebshopUser(String username, String password, Set<String> roles) {
        super();
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public WebshopUser(String username, String password, String email, Set<String> roles) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

}
