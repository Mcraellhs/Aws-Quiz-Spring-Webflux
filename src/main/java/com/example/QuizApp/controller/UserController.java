package com.example.QuizApp.controller;

import com.example.QuizApp.dtos.UserDto;
import com.example.QuizApp.model.User;
import com.example.QuizApp.repository.UserRepository;
import com.example.QuizApp.security.ApplicationUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @GetMapping
    public Flux<User> getUsers(){
        return userRepository.findAll();
    }

    @PostMapping("/register")
    public Mono<Object> createUser(@RequestBody UserDto user){
        Mono<User> userMatch= userRepository.findByUsername(user.getUsername());

        User newUser= new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder(10).encode(user.getPassword()));
        newUser.setRole(ApplicationUserRole.ADMIN);
        return userMatch.map(up->{
                    throw new RuntimeException("User already exists");
                }).
                switchIfEmpty(Mono.defer(()->{
                    userRepository.save(newUser).subscribe();
                    return Mono.just(new ResponseEntity<>(user,HttpStatus.CREATED));
                }));
    }


}
