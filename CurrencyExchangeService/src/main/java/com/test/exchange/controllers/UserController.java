package com.test.exchange.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.exchange.dto.User;
import com.test.exchange.services.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	 @Autowired
     private UserService userService;
	 
	 
	 @GetMapping("/{id}")
	    public Mono<ResponseEntity<Optional<User>>>  getUserById(@PathVariable Long id) {
	        Optional<User> optionalUser = Optional.ofNullable(userService.getUserById(id));
	        return Mono.just(ResponseEntity.ok(optionalUser));
	    }
	 
	 @GetMapping("/find/{username}")
    public Mono<ResponseEntity<Optional<User>>>  getUserById(@PathVariable String username) {
        Optional<User> optionalUser = Optional.ofNullable(userService.findByUsername(username));
        return Mono.just(ResponseEntity.ok(optionalUser));
    }
	 
}
