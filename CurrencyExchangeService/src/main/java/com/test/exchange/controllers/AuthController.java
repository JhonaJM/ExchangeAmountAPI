package com.test.exchange.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.exchange.dto.User;
import com.test.exchange.models.LoginRq;
import com.test.exchange.models.LoginRs;
import com.test.exchange.services.JWTService;
import com.test.exchange.services.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	final ReactiveUserDetailsService users;
	final JWTService jwtService;
	final PasswordEncoder encoder;

	public AuthController(ReactiveUserDetailsService users, JWTService jwtService, PasswordEncoder encoder) {
		this.users = users;
		this.jwtService = jwtService;
		this.encoder = encoder;
	}

	 @Autowired
     private UserService userService;
	 
	@PostMapping("/register")
	public Mono<ResponseEntity<User>> auth(@RequestBody User user) {
		User userCreated = userService.createUser(user);
		return Mono.just(ResponseEntity.ok(userCreated));
	}

	@PostMapping("/login")
	public Mono<ResponseEntity<LoginRs>> login(@RequestBody LoginRq loginRq) {

		LoginRs loginRs = new LoginRs();

		Mono<UserDetails> userFounded = users.findByUsername(loginRq.getUsername()).defaultIfEmpty(new UserDetails() {

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getPassword() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getUsername() {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		return userFounded.flatMap(user -> {
			if (user != null) {
				if (encoder.matches(loginRq.getPassword(),user.getPassword())) {
					loginRs.setStatus("success");
					loginRs.setToken(jwtService.generate(user.getUsername()));
					return Mono.just(ResponseEntity.ok(loginRs));
				}
			} 
			loginRs.setStatus("error");
			loginRs.setToken("invalid credentials");
			return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginRs));
		});
		
	}

}
