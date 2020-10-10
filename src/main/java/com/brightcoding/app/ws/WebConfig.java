package com.brightcoding.app.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration //il faut spécifier que cette classe est une classe de configuration
public class WebConfig implements WebMvcConfigurer {
	public void addCorsMappings(CorsRegistry registry) {
		/*
		registry
		.addMapping("/users")
		.allowedMethods("GET","PUT","POST")
		.allowedOrigins("http://localhost:4200"); //ce client peut déclencher les methodes post,get et put de controlleur users.
		
		*/
		
		
		
		registry
		.addMapping("/**")
		.allowedMethods("*")
		.allowedOrigins("*"); //n'importe client peut déclencher n'importe méthode de n'importe controlleur
		
		
		
	}
}
