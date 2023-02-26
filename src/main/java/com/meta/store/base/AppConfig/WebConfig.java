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

//	  @Bean
//	    public DataSource getDataSource() {
//	        return DataSourceBuilder.create()
//	          .driverClassName("org.postgresql.Driver")
//	          .url("jdbc:postgresql://localhost:5432/metaStore")
//	          .username("postgres")
//	          .password("password")
//	          .build();	
//	    }
	  
	@Bean
	  public AuditorAware<String> auditorAware(){
		return new AuditorAwareImpl();
	}
	

	   
	
}
