package com.example.QuizApp.jwt;

import com.example.QuizApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthManager implements ReactiveAuthenticationManager {

    @Autowired
    JwtService jwtService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .cast(BearerToken.class)
                .flatMap(auth->{
                    String userName = jwtService.getUserName(auth.getCredentials());
                    return userDetailsService.findByUsername(userName).map(x->{
                        if (jwtService.validate(x,auth.getCredentials())){
                            return new UsernamePasswordAuthenticationToken(x.getUsername(),x.getPassword(),x.getAuthorities());
                        }
                        throw new IllegalArgumentException("invalid token");
                    });

                });
    }

}