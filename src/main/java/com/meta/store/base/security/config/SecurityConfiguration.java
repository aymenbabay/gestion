package com.meta.store.base.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.meta.store.base.AppConfig.WebConfig;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthFilter;

	private final AuthenticationProvider authenticationProvider;
	
	private final LogoutHandler logoutHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChaine (HttpSecurity http) throws Exception {
		
		http
		.csrf()
		.disable()
		.authorizeHttpRequests()
		.requestMatchers("/api/auth/**")
			.permitAll()
		.anyRequest()
			.authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
		.logout()
		.logoutUrl("/api/auth/logout")
		.addLogoutHandler(logoutHandler)
		.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());
		return http.build();
	}
	
	
	

	
}
