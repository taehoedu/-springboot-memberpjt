package com.office.memberpjt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean PasswordEncoder passwordEncoder() {
		log.info("passwordEncoder()");
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean SecurityFilterChain filterChainForAdmin(HttpSecurity http) throws Exception {
		log.info("filterChainForAdmin()");
		
		http
		.cors(cors -> cors.disable())
		.csrf(csrf -> csrf.disable());
		
		http
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/**")
				.permitAll());
		
		return http.build();
		
	}
	
}
