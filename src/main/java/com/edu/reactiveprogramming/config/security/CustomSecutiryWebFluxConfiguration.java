package com.edu.reactiveprogramming.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class CustomSecutiryWebFluxConfiguration {
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
		serverHttpSecurity
				.authorizeExchange(exchanges -> exchanges
						.pathMatchers("/car/**").hasAuthority("ROLE_USER")
						.pathMatchers("/inventory/**").hasAuthority("ROLE_ADMIN")
						.pathMatchers("/sale/**").hasAuthority("ROLE_USER")
						.pathMatchers("/user/**").permitAll()
						.pathMatchers("/opaque/**").permitAll()
						.pathMatchers("/bearerToken/**").permitAll())
				.httpBasic(Customizer.withDefaults())
				.csrf(ServerHttpSecurity.CsrfSpec::disable);
		serverHttpSecurity
				.authorizeExchange(exchanges -> exchanges
						.pathMatchers("/sqs/**").authenticated()
						.pathMatchers("/kafka/**").authenticated())
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
				.httpBasic(Customizer.withDefaults())
				.csrf(ServerHttpSecurity.CsrfSpec::disable);
		serverHttpSecurity
				.authenticationManager(authenticationManager());
		return serverHttpSecurity.build();
	}
	
	private ReactiveAuthenticationManager authenticationManager() {
		return new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService());
	}
	
	@Bean
	public MapReactiveUserDetailsService reactiveUserDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails user = User
				.withUsername("user")
				.password(encoder.encode("password"))
				.roles("USER")
				.build();
		UserDetails admin = User
				.withUsername("admin")
				.password(encoder.encode("password"))
				.roles("ADMIN")
				.build();
		return new MapReactiveUserDetailsService(user, admin);
	}
	
	@Bean
	public ReactiveJwtDecoder reactiveJwtDecoder() {
		return new NimbusReactiveJwtDecoder("jwkSetUrl");
	}

}
