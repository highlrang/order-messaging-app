package com.myproject.externalclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAutoConfiguration(exclude = {
	DataSourceAutoConfiguration.class, 
	DataSourceTransactionManagerAutoConfiguration.class, 
	HibernateJpaAutoConfiguration.class
})
@EnableFeignClients
@SpringBootApplication
public class ExternalClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExternalClientApplication.class, args);
	}

}
