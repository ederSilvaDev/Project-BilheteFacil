package br.com.bilhete.facil.Bilhete.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
/*
 * https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/
 * 473061072/idcurso/1/idvideoaula/698
 */
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	/*https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/473063134/idcurso/1/idvideoaula/704*/
	
	/*IMPLEMEMTANDO A TELA DE LOGIN CONTROLADO E CUSTOMIZADO
	 * https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/473063415/idcurso/1/idvideoaula/705
	 */
	@Override //Configura as solicitação de de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
				http.csrf().disable() /*DESATIVA AS CONFIGURÇÃO PADRÃO DE MEMORIA*/
				.authorizeRequests() /*PERMITIR RESTRINGIR ACESSO */
				.antMatchers(HttpMethod.GET, "/").permitAll() /*QUALQUER USUARIO ACESSA A PAGINA INICIAL index.html*/
				.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN", "CAIXA", "USER")				
			    .anyRequest().authenticated()
			    .and().formLogin()
			    .loginPage("/login") /*INTERCEPTANDO A TELA DE LOGIN PERSONALIZADO*/
			    .permitAll()/*PERMITE QUALQUER USUARIO*/
			    .and().logout()/*MAPEIA URL DE LOGOUT E INVALIDA USUARIO AUTENTICADO*/
			    .logoutSuccessUrl("/login")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

	}

	@Override /*CRIA AUTENTICAÇÃO DO USUARIO COM BANCO DE DADOS OU EM MEMORIA*/
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// injetar a implemtação de Service
		
		  auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new
		  BCryptPasswordEncoder());
		
		/*
		 * auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance
		 * ()) .withUser("eder") .password("tux") .roles("ADMIN");
		 */

	}

	@Override /*Ignora URL especificas*/
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/bootstrap-5.1.3-dist/**");
		
	}
}

/*
 * REFERENCIAS EXTRAS https://spring.io/guides/gs/securing-web/
 */