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
	//almacenado real de datos
	//Seguridad en consulta de datos
	//resolver problema de Lkn hashmap instead of entities
	//linkeHashMap


	//boo 1 JENkins + loaBalancer + SonarQ
	//
	//
	//
	// extra Docker  docker local (mail y suscribidor) + docker BD +

}
