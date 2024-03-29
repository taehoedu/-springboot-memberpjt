package com.office.memberpjt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		log.info("addCorsMappings()");
		
		registry.addMapping("/**")
		.allowedOrigins("http://14.42.124.125:3000")
		.allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
		.allowCredentials(true)
		.maxAge(3000);
		
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/memberpjt/**")
			    .addResourceLocations("file:///c://memberpjt/upload/");
		
	}
	
}
