package com.app.restaurant.recipe.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.restaurant.RecipeConstants;
import com.app.restaurant.entity.Ingredient;
import com.app.restaurant.entity.Recipe;
import com.app.restaurant.entity.RecipeIngredient;
import com.app.restaurant.entity.RecipeInstruction;
import com.app.restaurant.exception.RecipeExistsException;
import com.app.restaurant.exception.RecipeNotFoundException;
import com.app.restaurant.ingredient.service.intf.IIngredientService;
import com.app.restaurant.model.IngredientVO;
import com.app.restaurant.model.RecipeIngredientInstructionVO;
import com.app.restaurant.model.RecipeInstructionVO;
import com.app.restaurant.model.RecipeResponse;
import com.app.restaurant.model.RecipeVO;
import com.app.restaurant.recipe.repostiory.IRecipeRepository;
import com.app.restaurant.recipe.service.intf.IRecipeService;

/**
 * This class handles operations for create recipe,update recipe,delete
 * recipe,get all recipes, get recipes with given input condition.
 * <p>
 * Create/Update/Delete operation methods check data existence before proceeding
 * relevant operations and return error response message based upon operation.
 * Get operation methods converts the database result into response model before
 * returning the value and return response message with record size.
 * 
 * @author Anitha Manoharan
 *
 */
@Service
@Transactional
public class RecipeServiceImpl implements IRecipeService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	IIngredientService ingredientService;

	@Autowired
	IRecipeRepository recipeRepository;

	/**
	 * Create new recipe.
	 * <p>
	 * Before creating new recipe check recipe name existence in the database. If
	 * given input recipe name already exists in the database then throw custom
	 * exception RecipeExistsException.class If given input recipe name is not
	 * available in the database then read inputs from RecipeVO and set it into
	 * Recipe entity object. Recipe entity object has RecipeInstruction object,
	 * RecipeIngredient (which has Recipe, Ingredient) object Save Recipe entity
	 * object into database. It will save values in table of Recipe,
	 * RecipeInstruction, RecipeIngredient object
	 */
	@Override
	public void createRecipe(RecipeVO recipeDetail) {
		logger.info("Inside create recipe method {}", this.getClass());
		// Check recipe name existence before insert new recipe.
		Recipe existingRecipes = findByRecipeName(recipeDetail.getRecipeName());
		if (existingRecipes != null) {
			// if recipe exists then throw exception.
			logger.error("Recipe already exists {}", this.getClass());
			throw new RecipeExistsException(RecipeConstants.RECIPE_EXISTS_ERR_MSG);
		}
		Recipe recipes = formRecipeInput(recipeDetail, null);
		logger.info("Creating recipe");
		recipeRepository.saveAndFlush(recipes);
		logger.info("Recipe created");
	}

	/**
	 * <p>
	 * Form Recipe, RecipeIngredient, RecipeInstruction entity object from given
	 * input while create and update recipe. Get all active Ingredients from
	 * database and filter only ingredients which is available in given input
	 * ingredient of type Long[] and set it into Ingredient entity object. If recipe
	 * already exists in the database set input values in the existing Recipe entity
	 * else set input values in new Recipe object. Set RecipeIngredient object
	 * (which has both Recipe and Ingredient object) in Recipe object Get input
	 * value of recipeInstruction of type RecipeInstructionVO and Set
	 * RecipeInstruction object in Recipe object
	 * 
	 * @param recipeDetail
	 * @param existingRecipe
	 * @return Recipe
	 */
	private Recipe formRecipeInput(RecipeVO recipeDetail, Recipe existingRecipe) {
		logger.info("Get All active ingredients");
		List<Ingredient> ingredientInfoList = findAllActiveIngredients();
		List<Long> ingredientIdList = Arrays.asList(recipeDetail.getIngredientIds());

		// Filter input ingredients from db result.
		List<Ingredient> ingredientInputList = ingredientInfoList.stream()
				.filter(e -> ingredientIdList.contains(e.getIngredientId())).collect(Collectors.toList());

		Recipe recipe = null;
		if (existingRecipe != null) {
			// Set input value into existing recipe.
			recipe = existingRecipe.toBuilder().recipeName(recipeDetail.getRecipeName())
					.isActive(recipeDetail.getIsActive()).isVeg(recipeDetail.getIsVeg())
					.noOfServings(recipeDetail.getNoOfServings()).updatedBy(RecipeConstants.SYSTEM).updatedDate(new Date()).build();
		} else {
			recipe = Recipe.builder().recipeName(recipeDetail.getRecipeName())
			.isActive(recipeDetail.getIsActive()).isVeg(recipeDetail.getIsVeg())
			.noOfServings(recipeDetail.getNoOfServings()).createdBy(RecipeConstants.SYSTEM).createdDate(new Date())
			.updatedBy(RecipeConstants.SYSTEM).updatedDate(new Date()).build();
		}

		Recipe recipeInfo = recipe;
		// Set Recipe ingredient model from input data.
		List<RecipeIngredient> recipeIngredientInfoList = new ArrayList<>();
		recipeIngredientInfoList = ingredientInputList.stream().map(ingredient -> {
			RecipeIngredient recipeIngredientInfo = RecipeIngredient.builder().ingredients(ingredient)
					.recipe(recipeInfo).build();
			return recipeIngredientInfo;

		}).collect(Collectors.toList());

		recipe.setRecipeIngredientList(recipeIngredientInfoList);
		// Set recipeInstruction entity from input.
		recipe.setRecipeInstruction(formRecipeInstructionsInput(recipeDetail.getRecipeInstruction(), recipe));
		return recipe;

	}

	/**
	 * Get All active ingredients from database.
	 * 
	 * @return List<Ingredient>
	 */
	private List<Ingredient> findAllActiveIngredients() {
		return ingredientService.findAllByIsActive();

	}

	/**
	 * Get input value of recipeInstruction of type RecipeInstructionVO and set into
	 * RecipeInstruction object with Recipe object.
	 * 
	 * @param recipeInstruction
	 * @param recipe
	 * @return RecipeInstruction
	 */
	private RecipeInstruction formRecipeInstructionsInput(RecipeInstructionVO recipeInstruction, Recipe recipe) {
		// Build recipeInstruction model object from input data.
		RecipeInstruction recipeInstructions = RecipeInstruction.builder()
				.cookingMethod(recipeInstruction.getCookingMethod()).cookingSteps(recipeInstruction.getCookingSteps())
				.additionalInfo(recipeInstruction.getAdditionalInfo()).recipe(recipe).build();
		return recipeInstructions;
	}

	/**
	 * Update recipe.
	 * <p>
	 * Check given input recipe existence in database by recipeId. If recipe is not
	 * exists in the database then throw custom exception of type
	 * RecipeNotFoundException.class If recipe exists in the database then read
	 * input values from RecipeVO object and set it into Recipe object Recipe entity
	 * object has RecipeInstruction object, RecipeIngredient (which has Recipe,
	 * Ingredient) object Save Recipe entity object into database. It will update
	 * values in table of Recipe, RecipeInstruction, RecipeIngredient object.
	 */
	@Override
	public void updateRecipe(RecipeVO recipeData) {
		logger.info("Inside updateRecipe method {}", this.getClass());
		logger.info("Get recipe by input id.");
		Recipe existingRecipeInfo = findByRecipeId(recipeData.getRecipeId());
		if (existingRecipeInfo != null) {
			// Form recipe input to update.
			Recipe recipeInfo = formRecipeInput(recipeData, existingRecipeInfo);
			logger.info("update recipe.");
			recipeRepository.saveAndFlush(recipeInfo);
			logger.info("updated recipe");
		} else {
			logger.error("Recipe does not exists {}", this.getClass());
			// if data is not there then throw exception.
			throw new RecipeNotFoundException(RecipeConstants.RECIPE_NOT_EXISTS_MSG);
		}
	}

	/**
	 * Get recipe detail by recipeId.
	 * 
	 * @param recipeId
	 * @return Recipe
	 */
	private Recipe findByRecipeId(Long recipeId) {

		return recipeRepository.findByRecipeId(recipeId);
	}

	/**
	 * Get recipe detail by ignoring name case sensitive.
	 * 
	 * @param recipeName
	 * @return Recipe
	 */
	private Recipe findByRecipeName(String recipeName) {

		return recipeRepository.findByRecipeNameIgnoreCase(recipeName);
	}

	/**
	 * Delete recipe by id.
	 * <p>
	 * Get existing Recipe object from database by recipeId. If Recipe is not exists
	 * in the database then throw custom exception of type
	 * RecipeNotFoundException.class If Recipe is exists in the database then delete
	 * Recipe object retrieved from database using recipeId. It will delete records
	 * from tables of RecipeInstruction, RecipeIngredient which has given recipeId
	 * in the input.
	 */
	@Override
	public void deleteRecipe(Long recipeId) {

		Recipe existingRecipeInfo = recipeRepository.findByRecipeId(recipeId);
		if (existingRecipeInfo != null) {
			recipeRepository.delete(existingRecipeInfo);
		} else {
			logger.error("Recipe does not exists {}", this.getClass());
			throw new RecipeNotFoundException(RecipeConstants.RECIPE_NOT_EXISTS_MSG);
		}
	}

	/**
	 * Get all active('Y') recipes from database.
	 * <p>
	 * If data exists in the database then convert retrieved Recipe ,
	 * RecipeInstruction, RecipeIngredient information into response model of type
	 * RecipeResponse. RecipeResponse has response message "Success" and retrieved
	 * record size along with Recipe detail. If data not exists in the database then
	 * set response message "Recipe does not exists", record size value 0 in
	 * RecipeResponse object.
	 * @return RecipeResponse
	 */
	@Override
	public RecipeResponse getAllRecipes() {
		logger.info("Get all recipes {}", this.getClass());
		RecipeResponse recipeResponse = null;
		// Get all active recipes.
		List<Recipe> recipeInfoList = recipeRepository.findByIsActive("Y");

		if (recipeInfoList != null && !recipeInfoList.isEmpty()) {
			// Convert into response model.
			List<RecipeIngredientInstructionVO> recipeResponseList = convertToResponseModel(recipeInfoList);
			recipeResponse = RecipeResponse.builder().responseMessage(RecipeConstants.SUCCESS_MSG)
					.recordSize(recipeResponseList.size()).recipeList(recipeResponseList).build();
		} else {
			logger.error("Recipe does not exists {}", this.getClass());
			// Set response message for empty response.
			recipeResponse = RecipeResponse.builder().recipeList(new ArrayList<>()).recordSize(0)
					.responseMessage(RecipeConstants.RECIPE_NOT_EXISTS_MSG).build();
		}

		return recipeResponse;
	}

	/**
	 * Convert entity object to response model.
	 * <p>
	 * Read retrieved List<Recipe> type data convert it into
	 * RecipeIngredientInstructionVO object which has details of recipe and its
	 * ingredient and instruction details. Recipe Ingredient detail is in
	 * IngredientVO object. Recipe Instruction detail is in RecipeInstructionVO
	 * object. Recipe detail is in RecipeIngredientInstructionVO. Finally add all
	 * details into List of type RecipeIngredientInstructionVO.
	 * 
	 * @param recipeInfoList
	 * @return List<RecipeIngredientInstructionVO>
	 */
	private List<RecipeIngredientInstructionVO> convertToResponseModel(List<Recipe> recipeInfoList) {
		// Form response object of Recipe, recipe instruction, ingredients object from
		// db result.
		List<RecipeIngredientInstructionVO> recipeResponseList = recipeInfoList.stream().map(recipeInfo -> {
			// Set recipe information into response model of type
			// RecipeIngredientInstructionVO from retrieved Recipe entity object.
			RecipeIngredientInstructionVO recipeResponse = RecipeIngredientInstructionVO.builder()
					.recipeId(recipeInfo.getRecipeId()).recipeName(recipeInfo.getRecipeName())
					.isActive(recipeInfo.getIsActive()).isVeg(recipeInfo.getIsVeg())
					.noOfServings(recipeInfo.getNoOfServings().intValue())
					// Set recipeInstruction value for response of type RecipeInstructionVO from
					// retrieved RecipeInstructions entity object
					.recipeInstruction(Collections.singletonList(recipeInfo.getRecipeInstruction()).stream()
							.map(recipeInstructions -> {
								RecipeInstructionVO recipeInstruction = RecipeInstructionVO.builder()
										.cookingMethod(recipeInstructions.getCookingMethod())
										.cookingSteps(recipeInstructions.getCookingSteps())
										.additionalInfo(recipeInstructions.getAdditionalInfo()).build();
								return recipeInstruction;
							}).collect(Collectors.toList()).get(0))
					// Set ingredientList value of type IngredientVO for response from retrieved
					// RecipeIngredient entity object
					.ingredientList(recipeInfo.getRecipeIngredientList().stream().map(ingredientInfo -> {
						IngredientVO ingredientResponse = IngredientVO.builder()
								.ingredientId(ingredientInfo.getIngredients().getIngredientId())
								.ingredientName(ingredientInfo.getIngredients().getIngredientName())
								.isActive(ingredientInfo.getIngredients().getIsActive()).build();
						return ingredientResponse;
					}).collect(Collectors.toList())).build();
			return recipeResponse;
			// Add RecipeIngredientInstructionVO object into list.
		}).collect(Collectors.toList());
		return recipeResponseList;
	}

	/**
	 * Get recipes by given condition.
	 * 
	 * <p>
	 * For example:
	 *  Scenario 1: If given input value for parameter isVeg = 'Y' then
	 * it retrieves all vegetarian recipes from database.
	 *  Scenario 2: If given input
	 * value for parameter isVeg = 'Y' and noOfServings 4 and includeIngredient =
	 * 'Potato' then it retrieves all recipes that can serve noOfServings >= 4 which
	 * has Potato as ingredient. 
	 * Scenario 3: If given input value for parameter
	 * excludeIngredient = 'Salmon' and instruction = 'oven' then it retrieves all
	 * recipes without "Salmon" as an ingredient that has "oven" in the
	 * instructions. 
	 * Scenario 4: If given input value for parameter isVeg = 'Y' and
	 * noOfServings 4 and includeIngredient = 'Potato' and excludeIngredient =
	 * 'Salmon' and instruction = 'stove top' then it retrieves all vegetarian
	 * recipes which has 'Potato' as ingredient and 'Salmon' not included in the
	 * ingredient that has 'stove top' in the instruction which serves >= 4 persons.
	 * Scenario 5: If no input is given then retrieves all the recipes. Converts all
	 * string inputs to lower case before sending into repository layer for case
	 * insensitive search. If data is available in database for given condition then
	 * convert result into response model of type RecipeIngredientInstructionVO
	 * which has recipe and its ingredient and instruction detail, then set response
	 * message "success" with record size. If data is not available in the database
	 * then return empty list object of type RecipeIngredientInstructionVO and
	 * response message "Recipe does not exists" with record size 0;
	 * 
	 * @return RecipeResponse
	 */
	@Override
	public RecipeResponse getRecipes(String isVeg, Integer noOfServings, String includeIngredient,
			String excludeIngredient, String instruction) {
		logger.info("Inside getRecipes method {}", this.getClass());
		RecipeResponse recipeResponse = null;
		logger.info("Get recipe by input condition");
		List<Recipe> recipeInfoList = recipeRepository.findByRecipeDetail(StringUtils.lowerCase(isVeg), noOfServings,
				StringUtils.lowerCase(includeIngredient), StringUtils.lowerCase(excludeIngredient),
				StringUtils.lowerCase(instruction));
		if (recipeInfoList != null && !recipeInfoList.isEmpty()) {
			// Convert db result into response model.
			List<RecipeIngredientInstructionVO> recipes = convertToResponseModel(recipeInfoList);
			// Set response message.
			recipeResponse = RecipeResponse.builder().recipeList(recipes).responseMessage(RecipeConstants.SUCCESS_MSG)
					.recordSize(recipes.size()).build();
		} else {
			logger.error("Recipe does not exists {}", this.getClass());
			// Set response message if no data is available.
			recipeResponse = RecipeResponse.builder().recipeList(new ArrayList<>())
					.responseMessage(RecipeConstants.RECIPE_NOT_EXISTS_MSG).recordSize(0).build();
		}
		return recipeResponse;
	}

}
