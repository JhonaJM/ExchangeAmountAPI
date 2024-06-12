package com.test.exchange.config;

import java.util.Collection;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import com.test.exchange.services.JWTService;

import reactor.core.publisher.Mono;

@Configuration
public class AuthManager  implements ReactiveAuthenticationManager{

	final JWTService jwtService;
	final ReactiveUserDetailsService users;
	
	
	public AuthManager(JWTService jwtService, ReactiveUserDetailsService users) {
		this.jwtService = jwtService;
		this.users = users;
	}


	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.justOrEmpty(
			authentication
			)
			.cast(BearerToken.class)
			.flatMap(auth -> {
				String userName = jwtService.getUserName(auth.getCredentials());
				Mono<UserDetails> foundUser = users.findByUsername(userName).defaultIfEmpty(new UserDetails() {

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
				
				return foundUser.map(u->{
					if(jwtService.validate(u,auth.getCredentials())) {
						return new UsernamePasswordAuthenticationToken(u.getUsername(),u.getPassword(),u.getAuthorities());
					}
					
					throw new IllegalArgumentException("Ibalid token");
				});
			});
	}

}
