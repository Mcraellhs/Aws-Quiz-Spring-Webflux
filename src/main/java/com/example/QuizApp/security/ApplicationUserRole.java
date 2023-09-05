package com.example.QuizApp.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.QuizApp.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {
    ADMIN(Sets.newHashSet(CONTENT_READ, CONTENT_WRITE, USER_READ,USER_WRITE)),
    USER(Sets.newHashSet());

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> collect =getPermissions().stream()
                .map(permissions-> new SimpleGrantedAuthority(permissions.getPermission()))
                .collect(Collectors.toSet());
        collect.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return collect;
    }

}