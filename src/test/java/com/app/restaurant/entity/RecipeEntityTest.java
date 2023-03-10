package com.app.restaurant.entity;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test all classes in com.app.restaurant.entity package
 * 
 * @author Anitha Manoharan
 *
 */
public class RecipeEntityTest {

	@DisplayName("Test all entity classes")
	@Test
	public void testRecipeResponse() {

		// Recipe
		Recipe recipe = getRecipes();
		assertNotNull(recipe.toString());
		assertNotNull(recipe.getCreatedBy());
		assertNotNull(recipe.getUpdatedBy());
		assertNotNull(recipe.getUpdatedDate());
		assertNotNull(recipe.getCreatedDate());
		assertNotNull(recipe.getRecipeIngredientList().get(0).getIngredients());
		assertNotNull(recipe.getRecipeIngredientList().get(0).getRecipe());

		// Ingredient
		Ingredient ingredient = recipe.getRecipeIngredientList().get(0).getIngredients();
		// Ingredient Builder
		Ingredient ingredients = ingredient.toBuilder().createdBy("System").createdDate(new Date()).updatedBy("System")
				.updatedDate(new Date()).recipeIngredientInfo(ingredient.getRecipeIngredientInfo()).build();
		assertNotNull(ingredients);
		assertNotNull(ingredient.getCreatedBy());
		assertNotNull(ingredient.getUpdatedBy());
		assertNotNull(ingredient.getUpdatedDate());
		assertNotNull(ingredient.getCreatedDate());

		// RecipeIngredientId
		RecipeIngredientId recipeIngredientId = new RecipeIngredientId();
		recipeIngredientId.setIngredients(ingredient);
		recipeIngredientId.setRecipe(recipe);
		assertNotNull(recipeIngredientId.getIngredients());
		assertNotNull(recipeIngredientId.getRecipe());

		// RecipeIngredientId Builder
		RecipeIngredientId recipeIngredientIdBuilder = RecipeIngredientId.builder().recipe(recipe)
				.ingredients(ingredient).build();
		assertNotNull(recipeIngredientIdBuilder);

		assertNotNull(recipe.getRecipeIngredientList().get(0).getIngredients().getUpdatedBy());
		assertNotNull(recipe.getRecipeIngredientList());
		assertNotNull(recipe.getRecipeInstruction());

		// RecipeInstruction
		RecipeInstruction recipeInstructions = recipe.getRecipeInstruction();
		RecipeInstruction recipeInstructionBuilder = recipeInstructions.toBuilder().instructionsId(1L).build();
		assertNotNull(recipeInstructionBuilder);
		assertNotNull(recipeInstructions.getRecipe());
		assertNotNull(recipeInstructions.getInstructionsId());

	}

	/**
	 * Set values for Recipe, Ingredient, RecipeIngredient, RecipeInstruction, class
	 * 
	 * @return Recipe
	 */
	Recipe getRecipes() {

		// Set values for Ingredient.
		Ingredient ingredient = new Ingredient();
		ingredient.setIngredientId(1L);
		ingredient.setIngredientName("Potato");
		ingredient.setIsActive("Y");
		ingredient.setCreatedBy("System");
		ingredient.setUpdatedBy("System");
		ingredient.setCreatedDate(new Date());
		ingredient.setUpdatedDate(new Date());

		// Set values for RecipeIngredient.
		RecipeIngredient recipeIngredient = new RecipeIngredient();
		recipeIngredient.setIngredients(ingredient);

		// Set recipe RecipeInstruction.
		RecipeInstruction recipeInstruction = new RecipeInstruction();
		recipeInstruction.setInstructionsId(1L);
		recipeInstruction.setCookingMethod("stove top");
		recipeInstruction.setCookingSteps("test");
		recipeInstruction.setAdditionalInfo("test");

		// Set values for Recipe.
		Recipe recipe = new Recipe();
		recipe.setRecipeId(1L);
		recipe.setIsActive("Y");
		recipe.setIsVeg("Y");
		recipe.setNoOfServings(2.0);
		recipe.setRecipeId(1L);
		recipe.setRecipeName("French Fries");
		recipe.setCreatedBy("System");
		recipe.setUpdatedBy("System");
		recipe.setCreatedDate(new Date());
		recipe.setUpdatedDate(new Date());

		// Set Recipe into RecipeIngredient.
		recipeIngredient.setRecipe(recipe);
		// Set Recipe into RecipeInstruction.
		recipeInstruction.setRecipe(recipe);

		// Set RecipeIngredient into Recipe
		List<RecipeIngredient> recipeIngredientList = new ArrayList<>();
		recipeIngredientList.add(recipeIngredient);
		ingredient.setRecipeIngredientInfo(recipeIngredientList);
		recipe.setRecipeIngredientList(recipeIngredientList);

		// Set RecipeInstruction into Recipe
		recipe.setRecipeInstruction(recipeInstruction);
		return recipe;
	}

}
