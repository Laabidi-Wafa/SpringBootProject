package com.brightcoding.app.ws.security;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFilter;


import com.brightcoding.app.ws.services.UserService;
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	
		private final UserService userDetailsService;
		private final BCryptPasswordEncoder bCryptPasswordEncoder;
		
		public WebSecurity(UserService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder) {
			this.userDetailsService=userDetailsService;
			this.bCryptPasswordEncoder=bCryptPasswordEncoder;
		}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	

		http
			.cors().and() //activer le cors cad charger des donnees d'autres domaines dans notre cas backend:springboot port 8080 et frontend:angular port:4200
			.csrf().disable() //desactiver le csrf puisque on travaille avec les microservices et on ne dispose pas des formulaires
			.authorizeRequests() 
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL) //authoriser les requettes https post qui dans la route user et on va la garantir tous les privilieges
			.permitAll()
			.anyRequest().authenticated() // apart les requettes http post il faut s'authentifier 
			.and()
			.addFilter(getAuthenticationFilter()) 
			.addFilter(new AuthorizationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}  
	
	protected com.brightcoding.app.ws.security.AuthenticationFilter getAuthenticationFilter() throws Exception {
		final com.brightcoding.app.ws.security.AuthenticationFilter filter = new com.brightcoding.app.ws.security.AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login"); //on peut changer la route 
		return filter;
		
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		
	}

}
