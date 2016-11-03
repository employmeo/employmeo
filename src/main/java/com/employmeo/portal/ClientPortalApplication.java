package com.employmeo.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.employmeo.portal", "com.employmeo.data"})
public class ClientPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientPortalApplication.class, args);
	}
}
