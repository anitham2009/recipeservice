package com.app.restaurant.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.restaurant.entity.Ingredient;
import com.app.restaurant.entity.Recipe;
import com.app.restaurant.entity.RecipeIngredient;
import com.app.restaurant.entity.RecipeInstruction;
import com.app.restaurant.exception.RecipeExistsException;
import com.app.restaurant.exception.RecipeNotFoundException;
import com.app.restaurant.ingredient.repository.IIngredientRepository;
import com.app.restaurant.ingredient.service.impl.IngredientServiceImpl;
import com.app.restaurant.model.RecipeInstructionVO;
import com.app.restaurant.model.RecipeResponse;
import com.app.restaurant.model.RecipeVO;
import com.app.restaurant.recipe.repostiory.IRecipeRepository;
import com.app.restaurant.recipe.service.impl.RecipeServiceImpl;

/**
 * Test recipe service methods
 * 
 * @author Anitha Manoharan.
 *
 */
@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

	@InjectMocks
	private RecipeServiceImpl recipeService;

	@Mock
	private IRecipeRepository recipeRepository;

	@Mock
	private IngredientServiceImpl ingredientService;

	@Mock
	IIngredientRepository ingredientRepository;

	@Spy
	RecipeInstructionVO recipeInstruction = new RecipeInstructionVO();
	@Spy
	Recipe recipes = new Recipe();

	@DisplayName("Test create recipe")
	@Test
	public void testCreateSuccess() {
		when(ingredientService.findAllByIsActive()).thenReturn(getIngredientsList());
		doReturn(getRecipes()).when(recipeRepository).saveAndFlush(Mockito.any());
		assertDoesNotThrow(() -> recipeService.createRecipe(getRecipeInput()));
	}

	@DisplayName("Test RecipeExistsException while create recipe")
	@Test
	public void testCreateFailure() {
		when(recipeRepository.findByRecipeNameIgnoreCase(any())).thenReturn(getRecipes());
		assertThrows(RecipeExistsException.class, () -> recipeService.createRecipe(getRecipeInput()));
	}

	@DisplayName("Test update recipe")
	@Test
	public void testUpdateSuccess() {
		when(recipeRepository.findByRecipeId(any())).thenReturn(getRecipes());
		doReturn(getRecipes()).when(recipeRepository).saveAndFlush(Mockito.any());
		assertDoesNotThrow(() -> recipeService.updateRecipe(getRecipeInput()));
	}

	@DisplayName("Test RecipeNotFoundException while update recipe")
	@Test
	public void testUpdateFailure() {
		assertThrows(RecipeNotFoundException.class, () -> recipeService.updateRecipe(getRecipeInput()));
	}

	@DisplayName("Test get all recipes")
	@Test
	public void testGetAllRecipe() {
		List<Recipe> recipeList = new ArrayList<>();
		Ingredient ingredient = Ingredient.builder().ingredientId(1L).ingredientName("Potato").isActive("Y").build();

		List<RecipeIngredient> recipeIngredientInfoList = new ArrayList<>();
		// Set value for recipe instruction.
		RecipeInstruction recipeInstructions = RecipeInstruction.builder().cookingMethod("test").cookingSteps("test")
				.additionalInfo("test").build();
		// Set value for recipe.
		Recipe recipe = Recipe.builder().isActive("Y").isVeg("Y").noOfServings(2.0).recipeId(1L)
				.recipeName("French Fries").recipeInstruction(recipeInstructions).build();
		// Set value for recipe ingredient.
		RecipeIngredient recipeIngredient = RecipeIngredient.builder().ingredients(ingredient).recipe(recipe).build();
		recipeIngredientInfoList.add(recipeIngredient);
		recipe = recipe.toBuilder().recipeIngredientList(recipeIngredientInfoList).build();
		recipeList.add(recipe);

		when(recipeRepository.findByIsActive(any())).thenReturn(recipeList);
		assertDoesNotThrow(() -> recipeService.getAllRecipes());
		RecipeResponse recipeResponse = recipeService.getAllRecipes();
		assertNotNull(recipeResponse);
		assertEquals(1, recipeResponse.getRecordSize());
	}

	@DisplayName("Test Null recipes while call getAllRecipes")
	@Test
	public void testGetAllRecipeNull() {
		when(recipeRepository.findByIsActive(any())).thenReturn(null);
		assertDoesNotThrow(() -> recipeService.getAllRecipes());
		RecipeResponse recipeResponse = recipeService.getAllRecipes();
		assertNotNull(recipeResponse);
		assertEquals(0, recipeResponse.getRecordSize());
	}

	@DisplayName("Test Empty recipes while call getAllRecipes")
	@Test
	public void testGetAllRecipeEmpty() {
		List<Recipe> recipe = new ArrayList<>();
		when(recipeRepository.findByIsActive(any())).thenReturn(recipe);
		assertDoesNotThrow(() -> recipeService.getAllRecipes());
		RecipeResponse recipeResponse = recipeService.getAllRecipes();
		assertNotNull(recipeResponse);
		assertEquals(0, recipeResponse.getRecordSize());
	}

	@DisplayName("Test get recipes by given input condition")
	@Test
	public void testGetRecipeByCond() {
		List<Recipe> recipeList = new ArrayList<>();
		Ingredient ingredient = Ingredient.builder().ingredientId(1L).ingredientName("Potato").isActive("Y").build();

		List<RecipeIngredient> recipeIngredientInfoList = new ArrayList<>();
		// Set value for recipe instruction.
		RecipeInstruction recipeInstructions = RecipeInstruction.builder().cookingMethod("test").cookingSteps("test")
				.additionalInfo("test").build();
		// Set recipe value.
		Recipe recipe = Recipe.builder().isActive("Y").isVeg("Y").noOfServings(2.0).recipeId(1L)
				.recipeName("French Fries").recipeInstruction(recipeInstructions).build();
		// Set recipe ingredient.
		RecipeIngredient recipeIngredient = RecipeIngredient.builder().ingredients(ingredient).recipe(recipe).build();
		recipeIngredientInfoList.add(recipeIngredient);
		recipe = recipe.toBuilder().recipeIngredientList(recipeIngredientInfoList).build();
		// Add recipe into list.
		recipeList.add(recipe);
		when(recipeRepository.findByRecipeDetail(any(), any(), any(), any(), any())).thenReturn(recipeList);
		assertDoesNotThrow(() -> recipeService.getRecipes("Y", null, null, null, null));
		RecipeResponse recipeResponse = recipeService.getRecipes("Y", null, null, null, null);
		assertNotNull(recipeResponse);
		assertEquals(1, recipeResponse.getRecordSize());
	}

	@DisplayName("Test get recipes by given input condition returns null")
	@Test
	public void testGetRecipeByCondNull() {
		when(recipeRepository.findByRecipeDetail(any(), any(), any(), any(), any())).thenReturn(null);
		assertDoesNotThrow(() -> recipeService.getRecipes("Y", null, null, null, null));
		RecipeResponse recipeResponse = recipeService.getRecipes("Y", null, null, null, null);
		assertNotNull(recipeResponse);
		assertEquals(0, recipeResponse.getRecordSize());
	}

	@DisplayName("Test delete recipe")
	@Test
	public void testDeleteSuccess() {
		when(recipeRepository.findByRecipeId(any())).thenReturn(getRecipes());
		doNothing().when(recipeRepository).delete(Mockito.any());
		assertDoesNotThrow(() -> recipeService.deleteRecipe(3L));
	}

	@DisplayName("Test throw RecipeNotFoundException while delete recipe")
	@Test
	public void testDeleteFailure() {
		when(recipeRepository.findByRecipeId(any())).thenReturn(null);
		assertThrows(RecipeNotFoundException.class, () -> recipeService.deleteRecipe(3L));
	}

	/**
	 * Get ingredient list.
	 * 
	 * @return
	 */
	List<Ingredient> getIngredientsList() {
		List<Ingredient> ingredientsList = new ArrayList<>();
		Ingredient ingredients = Ingredient.builder().ingredientId(1L).ingredientName("Potato").build();
		ingredientsList.add(ingredients);
		return ingredientsList;
	}

	/**
	 * Get recipes.
	 * 
	 * @return
	 */
	Recipe getRecipes() {
		// Set recipe.
		Recipe recipes = Recipe.builder().isActive("Y").isVeg("Y").noOfServings(2.0).recipeId(1L)
				.recipeName("French Fries").recipeIngredientList(null).build();
		// Set recipe instruction.
		RecipeInstruction recipeInstruction = RecipeInstruction.builder().cookingMethod("stove top")
				.cookingSteps("test").additionalInfo("test").build();
		// Set ingredient.
		Ingredient ingredients = Ingredient.builder().ingredientId(1L).ingredientName("Potato").isActive("Y").build();
		List<RecipeIngredient> recipeIngredientList = new ArrayList<>();
		RecipeIngredient recipeIngredients = RecipeIngredient.builder().ingredients(ingredients).recipe(recipes)
				.build();
		recipeIngredientList.add(recipeIngredients);
		recipes.setRecipeIngredientList(recipeIngredientList);
		recipes.setRecipeInstruction(recipeInstruction);

		return recipes;
	}

	/**
	 * Get recipe input.
	 * 
	 * @return
	 */
	RecipeVO getRecipeInput() {
		// Set recipe instruction.
		RecipeInstructionVO recipeInstruction = RecipeInstructionVO.builder().cookingMethod("stove top")
				.cookingSteps("test").additionalInfo("test").build();
		// Set ingredient.
		Long[] ingredientList = { 1L };
		// Set recipe.
		RecipeVO recipe = RecipeVO.builder().isActive("Y").isVeg("Y").noOfServings(2.0).recipeId(1L)
				.recipeName("French Fries").recipeInstruction(recipeInstruction).ingredientIds(ingredientList).build();
		return recipe;
	}
}
