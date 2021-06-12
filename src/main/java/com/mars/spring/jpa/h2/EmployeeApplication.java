package com.mars.spring.jpa.h2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/mars");
		SpringApplication.run(EmployeeApplication.class, args);
	}

}
