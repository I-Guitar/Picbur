package com.joe.Figure_bed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:conf.properties")
public class FigureBedApplication {

	public static void main(String[] args) {
		SpringApplication.run(FigureBedApplication.class, args);
	}

}
