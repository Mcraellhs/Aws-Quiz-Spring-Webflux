package com.example.QuizApp.controller;

import com.example.QuizApp.Helpers.ServiceResponse;
import com.example.QuizApp.dtos.UserDto;
import com.example.QuizApp.jwt.JwtService;
import com.example.QuizApp.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/api/login")
    public Mono<ResponseEntity<ServiceResponse<String>>> loginUser(@RequestBody UserDto user){

        Mono returnData= userRepository.findByUsername(user.getUsername())
                .map(x->{
                    if (passwordEncoder.matches(user.getPassword(),x.getPassword())) {
                        return new ResponseEntity<>(new ServiceResponse(jwtService.generate(x.getUsername(),x.getRole()),x.getRole()),HttpStatus.OK);
                    }
                    return new ResponseEntity<>(new ServiceResponse("Bad credentials",null),HttpStatus.BAD_REQUEST);
                });
        return returnData.defaultIfEmpty(new ResponseEntity<>(new ServiceResponse("Not found",null),HttpStatus.NOT_FOUND));


    }

}
