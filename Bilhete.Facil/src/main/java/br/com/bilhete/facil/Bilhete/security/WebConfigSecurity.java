package br.com.bilhete.facil.Bilhete.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
/*https://www.projetojavaweb.com/certificado-aluno/plataforma-curso/aulaatual/473061072/idcurso/1/idvideoaula/698 */
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//injetar a implemtação de Service
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
				
		/*
		 * auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance
		 * ()) .withUser("eder")
		 * .password("$2a$10$YVfCUEg2l8hUZsHG5rt6oeJM6L/IK/IszDq11b7C67R.ik3J/GR/2")
		 * .roles("ADMIN");
		 */
		 
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/bootstrap-5.1.3-dist/**");
	}
}



/*REFERENCIAS EXTRAS 
https://spring.io/guides/gs/securing-web/
*/