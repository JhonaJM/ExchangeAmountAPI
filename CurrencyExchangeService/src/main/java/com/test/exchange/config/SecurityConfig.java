package com.test.exchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
//@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public MapReactiveUserDetailsService userDetailsService (PasswordEncoder encoder){
		
		UserDetails user = User.builder()
				.username("kuku")
				.password(encoder.encode("kuku"))
				.roles("USER")
				.build();
		
		return new MapReactiveUserDetailsService(user);		
	} 
	
	
	 
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityWebFilterChain securityWebFilterChain (ServerHttpSecurity http, AuthConverter jwtAuthConverter,AuthManager jwtAuthManager ){
		
		AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(jwtAuthManager);
		jwtFilter.setServerAuthenticationConverter(jwtAuthConverter);
		return http
				.authorizeExchange(auth -> {
					auth.pathMatchers("/auth/**").permitAll();
					auth.anyExchange().authenticated();
				})
				.addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.httpBasic().disable()
				.formLogin().disable()
				.csrf().disable()
				.build();		
	} 

}
