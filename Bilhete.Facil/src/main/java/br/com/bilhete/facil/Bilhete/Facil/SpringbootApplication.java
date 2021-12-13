package br.com.bilhete.facil.Bilhete.Facil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EntityScan(basePackages = "br.com.bilhete.facil.Bilhete.model")
@ComponentScan(basePackages = {"br.com.*"})//ANOTAÇÃO QUE SCANNEA TODOS OS PACOTES
@EnableJpaRepositories(basePackages = {"br.com.bilhete.facil.Bilhete.repository"})
@EnableTransactionManagement
@EnableWebMvc 
public class SpringbootApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		 SpringApplication.run(SpringbootApplication.class, args); 
	}
		
		  /*https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/
		  473061615/idcurso/1/idvideoaula/699*/
		  
			
			  @Override public void addViewControllers(ViewControllerRegistry registry) {
			  registry.addViewController("/login").setViewName("/login");
			  registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
			  
			  }
			 

}
