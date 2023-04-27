package com.myproject.externalclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ExternalClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExternalClientApplication.class, args);
	}

}
