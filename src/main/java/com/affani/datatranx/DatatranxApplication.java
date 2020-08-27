package com.affani.datatranx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* Initializing spring boot application.
*
* @author  Hamza AFFANI
* @version 1.0
* 
* Don't forget to add that configuration annotation that enables Spring's annotation-driven transaction management capability
*  @EnableTransactionManagement, it's not necessary for this project since we're lucky to have extra support by SpringBoot for trans.
*  management.
*/

@SpringBootApplication

public class DatatranxApplication {

	public static void main(String[] args) {

		SpringApplication.run(DatatranxApplication.class, args);
	}
}
