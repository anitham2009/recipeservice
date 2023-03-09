package com.app.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.annotations.ApiOperation;

@ApiOperation("Recipe Service API")
@SpringBootApplication
@ComponentScan(basePackages = { "com.app.restaurant" })
@EntityScan("com.app.restaurant.entity") 
public class RecipeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeServiceApplication.class, args);
	}
	
	 
	
}
