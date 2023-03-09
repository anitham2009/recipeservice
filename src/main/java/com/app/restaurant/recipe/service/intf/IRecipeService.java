package com.app.restaurant.recipe.service.intf;

import com.app.restaurant.model.RecipeVO;
import com.app.restaurant.model.RecipeResponse;

public interface IRecipeService {

	void createRecipe(RecipeVO recipeData);

	void updateRecipe(RecipeVO recipeData);

	void deleteRecipe(Long recipeId);

	RecipeResponse getAllRecipes();

	RecipeResponse getRecipes(String isVeg, Integer noOfServings, String includeIngredient,
			String excludeIngredient, String instruction);
}
