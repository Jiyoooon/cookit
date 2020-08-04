package com.ssafy.cooking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.cooking.interceptor.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**")
	                    .allowedOrigins("*")
	                    .allowedMethods("*")
	                    .allowCredentials(false)
	                    .exposedHeaders("token")
	                    .maxAge(3600);
	            
	            registry.addMapping("/*/token/**")
	            		.allowedHeaders("Authorization");
	        }
	        
	        @Override
	        public void addInterceptors(InterceptorRegistry registry) {
	        	registry.addInterceptor(jwtInterceptor())
	        			.addPathPatterns("/*/token/**");
	        }
	    };
	}
	
	@Bean
	public JwtInterceptor jwtInterceptor() {
		return new JwtInterceptor();
	}
	
	
}
