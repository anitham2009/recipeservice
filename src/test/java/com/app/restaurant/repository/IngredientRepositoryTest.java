package com.app.restaurant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.restaurant.entity.Ingredient;
import com.app.restaurant.ingredient.repository.IIngredientRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IngredientRepositoryTest {
	
	@Autowired
	IIngredientRepository ingredientRepository;
	
	  @Test
	  public void testRecipeId() {
	    List<Ingredient> ingredientList = ingredientRepository.findAllByIsActive("Y");
	    assertThat(ingredientList).isNotNull();
	  }

}
