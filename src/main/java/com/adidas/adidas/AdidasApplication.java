package com.adidas.adidas;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the application
 */
@SpringBootApplication
public class AdidasApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(AdidasApplication.class, args);
	}
	//TODO
	//functional tests
	//Security Improvement
	//Solve "LinkedHashMap" bug
	//JPA test
}