package com.example.QuizApp.model;

import com.example.QuizApp.security.ApplicationUserRole;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.mongodb.core.index.Indexed;
import java.util.Collection;

@Data
@Document
@ToString
public class User implements UserDetails {

    @Id
    private String Id;
    @Indexed(unique = true)
    private String username;
    private String password;


    private boolean active = true;
    private ApplicationUserRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return role.getGrantedAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }


    public ApplicationUserRole getRole() {
        return role;
    }
}