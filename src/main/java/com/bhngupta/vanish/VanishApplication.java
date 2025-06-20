package com.bhngupta.vanish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableScheduling
@SpringBootApplication
public class VanishApplication {

	public static void main(String[] args) {
		SpringApplication.run(VanishApplication.class, args);
	}

}
