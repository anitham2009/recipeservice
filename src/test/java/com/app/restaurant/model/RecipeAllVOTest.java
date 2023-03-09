package com.app.restaurant.model;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test all classes in com.app.restaurant.model package.
 * 
 * @author Anitha Manoharan
 *
 */
public class RecipeAllVOTest {

	@DisplayName("Test RecipeVO")
	@Test
	public void testRecipeVO() {
		RecipeVO recipe = getRecipes();
		RecipeVO recipeBuilder = recipe.toBuilder().build();
		assertNotNull(recipeBuilder);
		assertNotNull(recipe);
	}

	@DisplayName("Test IngredientVO")
	@Test
	public void testIngredientVO() {
		IngredientVO ingredient = getIngredients();
		IngredientVO ingredientBuilder = ingredient.toBuilder().build();
		assertNotNull(ingredientBuilder);
		assertNotNull(ingredient);
	}

	@DisplayName("RecipeCreateVO")
	@Test
	public void testRecipeCreateVO() {
		RecipeCreateVO recipe = getRecipeCreateValues();
		RecipeCreateVO recipeBuilder = recipe.toBuilder().build();
		assertNotNull(recipeBuilder);
		assertNotNull(recipe);
	}

	@DisplayName("RecipeUpdateVO")
	@Test
	public void testRecipeUpdateVO() {
		RecipeUpdateVO recipe = getRecipeUpdateValues();
		RecipeUpdateVO recipeBuilder = recipe.toBuilder().build();
		assertNotNull(recipeBuilder);
		assertNotNull(recipe);
	}

	@DisplayName("RecipeIngredientInstructionVO")
	@Test
	public void testRecipeIngredientInstructionVO() {
		RecipeIngredientInstructionVO recipe = getRecipeIngInstructionValues();
		RecipeIngredientInstructionVO recipeBuilder = recipe.toBuilder().build();
		assertNotNull(recipeBuilder);
		assertNotNull(recipe);
	}

	@DisplayName("RecipeResponseVO")
	@Test
	public void testRecipeResponseVO() {
		RecipeResponse recipe = getRecipeResponseVal();
		assertNotNull(recipe);
		RecipeResponse recipeBuilder = recipe.toBuilder().build();
		assertNotNull(recipeBuilder);
	}

	@DisplayName("Test ErrorMessage")
	@Test
	public void testErrorMessage() {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setSeverity("ERROR");
		errorMessage.setCode("400");
		errorMessage.setMessage("Request body has errors");
		errorMessage.setSource("INPUT VALIDATION");

		List<ErrorMessage.InnerError> innerErrors = new ArrayList<>();
		errorMessage.setInnerErrors(innerErrors);
		ErrorMessage.InnerError innerErrorMessage = new ErrorMessage.InnerError();
		innerErrorMessage.setMessage("must be greater than or equal to 1");
		innerErrorMessage.setSource("noOfServings");
		innerErrors.add(innerErrorMessage);

		assertNotNull(errorMessage);
		assertNotNull(innerErrorMessage);
		assertNotNull(innerErrors);
		assertNotNull(errorMessage.toString());
	}

	/**
	 * Set values for RecipeResponse.
	 * 
	 * @return RecipeResponse
	 */
	private RecipeResponse getRecipeResponseVal() {
		RecipeResponse recipeResponse = new RecipeResponse();
		recipeResponse.setRecordSize(1);
		recipeResponse.setResponseMessage("Success");
		RecipeIngredientInstructionVO recipe = getRecipeIngInstructionValues();
		List<RecipeIngredientInstructionVO> recipeList = new ArrayList<>();
		recipeList.add(recipe);
		recipeResponse.setRecipeList(recipeList);
		return recipeResponse;
	}

	/**
	 * Set values for RecipeIngredientInstructionVO.
	 * 
	 * @return RecipeIngredientInstructionVO
	 */
	private RecipeIngredientInstructionVO getRecipeIngInstructionValues() {
		RecipeIngredientInstructionVO recipe = new RecipeIngredientInstructionVO();
		IngredientVO ingredient = getIngredients();
		List<IngredientVO> ingredientList = new ArrayList<>();
		ingredientList.add(ingredient);
		recipe.setRecipeId(1L);
		recipe.setIsActive("Y");
		recipe.setIsVeg("Y");
		recipe.setNoOfServings(2);
		recipe.setRecipeName("French Fries");
		recipe.setRecipeInstruction(getRecipeInstruction());
		recipe.setIngredientList(ingredientList);
		return recipe;
	}

	/**
	 * Set values for RecipeCreateVO.
	 * 
	 * @return RecipeCreateVO
	 */
	private RecipeCreateVO getRecipeCreateValues() {
		RecipeCreateVO recipe = new RecipeCreateVO();
		Long[] ingredients = { 1L };
		recipe.setIngredientIds(ingredients);
		recipe.setIsActive("Y");
		recipe.setIsVeg("Y");
		recipe.setNoOfServings(2.0);
		recipe.setRecipeName("French Fries");
		recipe.setRecipeInstruction(getRecipeInstruction());
		return recipe;
	}

	/**
	 * Set values for RecipeUpdateVO.
	 * 
	 * @return RecipeUpdateVO
	 */
	private RecipeUpdateVO getRecipeUpdateValues() {
		RecipeUpdateVO recipe = new RecipeUpdateVO();
		Long[] ingredients = { 1L };
		recipe.setRecipeId(1L);
		recipe.setIngredientIds(ingredients);
		recipe.setIsActive("Y");
		recipe.setIsVeg("Y");
		recipe.setNoOfServings(2.0);
		recipe.setRecipeName("French Fries");
		recipe.setRecipeInstruction(getRecipeInstruction());
		return recipe;
	}

	/**
	 * Set values for IngredientVO
	 * 
	 * @return IngredientVO
	 */
	private IngredientVO getIngredients() {

		// Set ingredient.
		IngredientVO ingredient = new IngredientVO();
		ingredient.setIngredientId(1L);
		ingredient.setIngredientName("Potato");
		ingredient.setIsActive("Y");
		return ingredient;

	}

	/**
	 * Set values for RecipeInstructionVO.
	 * 
	 * @return RecipeInstructionVO
	 */
	RecipeInstructionVO getRecipeInstruction() {
		// Set recipe instruction.
		RecipeInstructionVO recipeInstruction = new RecipeInstructionVO();
		recipeInstruction.setCookingMethod("stove top");
		recipeInstruction.setCookingSteps("test");
		recipeInstruction.setAdditionalInfo("test");
		return recipeInstruction;
	}

	/**
	 * Set values for RecipeVO.
	 * 
	 * @return RecipeVO
	 */
	RecipeVO getRecipes() {

		// Set recipe.
		RecipeVO recipe = new RecipeVO();
		recipe.setRecipeId(1L);
		Long[] ingredients = { 1L };
		recipe.setIngredientIds(ingredients);
		recipe.setIsActive("Y");
		recipe.setIsVeg("Y");
		recipe.setNoOfServings(2.0);
		recipe.setRecipeId(1L);
		recipe.setRecipeName("French Fries");

		recipe.setRecipeInstruction(getRecipeInstruction());
		return recipe;
	}
}