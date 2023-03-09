package com.app.restaurant.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.restaurant.entity.Recipe;
import com.app.restaurant.recipe.repostiory.IRecipeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecipeRepositoryTest {
	
	@Autowired
	IRecipeRepository recipeRepository;
	
	@DisplayName("Find recipe by id")
	  @Test
	  public void testRecipeId() {
	    Recipe recipes = recipeRepository.findByRecipeId(1L);
	    assertThat(recipes).isNotNull();
	  }
	@DisplayName("Find all active recipes")
	  @Test
	  public void testRecipeIsActive() {
	    List<Recipe> recipes = recipeRepository.findByIsActive("Y");
	    assertThat(recipes).isNotNull();
	    assertEquals(2, recipes.size());
	   
	  }
	@DisplayName("Find recipe by name")
	  @Test
	  public void testRecipeByName() {
	    Recipe recipes = recipeRepository.findByRecipeNameIgnoreCase("Cheese Omlete");

	    assertThat(recipes).isNotNull();
	  }
	@DisplayName("Find recipe by given input condition")
	  @Test
	  public void testRecipeByCond() {
	   List<Recipe> recipes = recipeRepository.findByRecipeDetail("N",null,null,null,null);
	    assertThat(recipes).isNotNull();
	  }
}
