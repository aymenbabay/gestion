package com.meta.store.base.AppConfig;


import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.repository.AppUserRepository;
import com.meta.store.base.security.service.AppUserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableCaching
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class WebConfig {


	private final AppUserRepository appUserRepository;

	  
	@Bean
	public AuditorAware<String> auditorAware(JwtAuthenticationFilter jwtAuthenticationFilter) {
	    return new AuditorAwareImpl(jwtAuthenticationFilter);
	}

	

	  @Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		  return config.getAuthenticationManager();
	  }
	  
	  @Bean
		public UserDetailsService userDetailsService() {
			return username -> appUserRepository.findByUserName(username)
					.orElseThrow(() -> new RecordNotFoundException("user Not Found"));
		}
		
		  @Bean
		  public AuthenticationProvider authenticationProvider() {
			  DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			  authProvider.setUserDetailsService(userDetailsService());
			  authProvider.setPasswordEncoder(passwordEncoder());
			  return authProvider;
		  } 

		
		@Bean
		public PasswordEncoder passwordEncoder() {
			// TODO Auto-generated method stub
			return new BCryptPasswordEncoder();
		}
	
}
