package com.app.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.restaurant.controller.RecipeController;
import com.app.restaurant.recipe.service.intf.IRecipeService;



@SpringBootTest
class RecipeServiceApplicationTests {

	@Autowired
	RecipeController recipeController;
	
	@Autowired
	IRecipeService recipeService;
	
	@Test
	void contextLoads() {
		assertThat(recipeController).isNotNull();
		assertThat(recipeService).isNotNull();
		RecipeServiceApplication.main(new String[] {});
		assertTrue(true);
		
	}

}
