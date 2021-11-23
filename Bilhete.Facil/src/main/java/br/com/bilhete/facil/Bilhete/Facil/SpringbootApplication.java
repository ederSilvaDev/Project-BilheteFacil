package br.com.bilhete.facil.Bilhete.Facil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "br.com.bilhete.facil.Bilhete.model")
@ComponentScan(basePackages = {"br.com.*"})/*ANOTAÇÃO QUE SCANNEA TODOS OS PACOTES*/
@EnableJpaRepositories(basePackages = {"br.com.bilhete.facil.Bilhete.repository"})
@EnableTransactionManagement
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
