package com.example.QuizApp.security;

import com.example.QuizApp.jwt.AuthConverter;
import com.example.QuizApp.jwt.AuthManager;
import com.example.QuizApp.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


import java.util.Arrays;

import static com.example.QuizApp.security.ApplicationUserPermission.CONTENT_WRITE;
import static com.example.QuizApp.security.ApplicationUserRole.ADMIN;
import static com.example.QuizApp.security.ApplicationUserRole.USER;


//@EnableWebFluxSecurity
@Configuration
public class ApplicationSecurityConfig {
    @Autowired
    public UserRepository userRepository;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, AuthConverter jwtAuthConverter, AuthManager jwtAuthManager){

        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(jwtAuthManager);
        jwtFilter.setServerAuthenticationConverter(jwtAuthConverter);

        return http .csrf().disable()
                .authorizeExchange()

                .pathMatchers(HttpMethod.GET,"/api/practice-set/**").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/practice-set/**").hasRole(ADMIN.name())
                .pathMatchers(HttpMethod.PUT,"/api/practice-set/**").hasRole(ADMIN.name())

                .pathMatchers(HttpMethod.GET,"/api/question/**").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/question/**").hasRole(ADMIN.name())

                .pathMatchers(HttpMethod.GET,"/api/quiz").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/quiz").permitAll()

                .pathMatchers(HttpMethod.POST,"/api/login").permitAll()
                .pathMatchers(HttpMethod.POST,"/api/register").permitAll()


                .anyExchange()
                .authenticated()
                .and()
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHORIZATION)
                .formLogin().disable()
                .httpBasic().disable()
                .build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return (username) -> userRepository.findByUsername(username).cast(UserDetails.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
