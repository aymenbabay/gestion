package com.meta.store.base.AppConfig;


import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class WebConfig {


	  
	@Bean
	  public AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl();
	}
	

	   
	
}
