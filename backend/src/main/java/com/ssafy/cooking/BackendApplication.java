package com.ssafy.cooking;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(value = "com.ssafy.cooking.dao")
public class BackendApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
