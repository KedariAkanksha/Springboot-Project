package com.itp.flipkart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SecondSpringBootProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondSpringBootProjectApplication.class, args);
	}

}
