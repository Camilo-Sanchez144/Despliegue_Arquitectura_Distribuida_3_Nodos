package com.biblioteca.gestionbiblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class GestionbibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionbibliotecaApplication.class, args);
	}

}
