package com.app.restaurant.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.restaurant.exception.RecipeExistsException;
import com.app.restaurant.exception.RecipeNotFoundException;
import com.app.restaurant.model.ErrorMessage;
import com.app.restaurant.model.ErrorResponse;
import com.app.restaurant.model.RecipeCreateVO;
import com.app.restaurant.model.RecipeResponse;
import com.app.restaurant.model.RecipeUpdateVO;
import com.app.restaurant.model.RecipeVO;
import com.app.restaurant.recipe.service.intf.IRecipeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Handled recipe operations : create, update,delete,get all, get recipe by given conditions.
 * <p>
 * While create/update/delete/get recipe from the database
 * it create/update/delete/get relevant recipe instruction,recipe ingredient information from the database.
 * 
 * @author Anitha Manoharan
 *
 */

@RestController
@RequestMapping("/recipes")
@Validated
@Api(value = "/recipes", tags = "Recipe Resource")
public class RecipeController {
	Logger logger = LoggerFactory.getLogger(RecipeController.class);

	@Autowired
	IRecipeService recipeService;

	/**
	 * Create Recipe with ingredient and instruction.
	 * Check recipe name existence in the database before saving given input details.
	 * If data exists in the database then throw custom exception message
	 * else store the given information in the database.
	 * 
	 * @param input recipe inputs
	 * @return response message
	 */
	@ApiOperation(value = "Create Recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saved Recipe successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Recipe already exists",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content)})
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> createRecipe(@Valid @RequestBody RecipeCreateVO input) {
		logger.debug("Inside createRecipe method", this.getClass());
		RecipeVO recipeData = new RecipeVO();
		BeanUtils.copyProperties(input, recipeData);
		recipeService.createRecipe(recipeData);
		return new ResponseEntity<>("Saved recipe successfully", HttpStatus.CREATED);
	}

	/**
	 * Update recipe detail.
	 * <p>
	 * Check recipe existence using recipe id given in the input body.
	 * If recipe exist in the database then update entity object with input values and save in database
	 * with recipe, recipe ingredient, recipe instruction detail and return "success" response with record size
	 * If recipe is not available in database then throw custom exception message with empty record and record size 0
	 * 
	 * @param input recipe details to update
	 * @return response message
	 */
	@ApiOperation(value = "Update Recipe")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Recipe successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Recipe does not exists",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content)})
	@PutMapping
	public ResponseEntity<String> updateRecipe(@Valid @RequestBody RecipeUpdateVO input) {
		logger.debug("Inside updateRecipe method", this.getClass());
		RecipeVO recipeData = new RecipeVO();
		BeanUtils.copyProperties(input, recipeData);
		recipeService.updateRecipe(recipeData);
		return new ResponseEntity<>("Updated recipe successfully", HttpStatus.OK);
	}

	/**
	 * Delete recipe by given id.
	 * Before deleting recipe, check given recipe existence in the database.
	 * If recipe is there, then delete else throw custom exception with message.
	 * 
	 * @param recipeId recipe id of the recipe
	 * @return response message
	 */
	@ApiOperation(value = "Delete Recipe")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted Recipe successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Recipe does not exists",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content)})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRecipe(@PathVariable(value = "id") Long recipeId) {
		logger.debug("Inside deleteRecipe method", this.getClass());
		recipeService.deleteRecipe(recipeId);

		return new ResponseEntity<>("Deleted recipe successfully", HttpStatus.OK);
	}

	/**
	 * Get all active('Y') recipes from database.
	 * If data exists in the database then set retrieved values of Recipe , RecipeInstruction, RecipeIngredient information
	 *  in the model of type  RecipeResponse. Also RecipeResponse has response message "Success" 
	 * and retrieved record size along with Recipe detail.
	 * If data not exists in the database then set error response message, record size value 0 in
	 * RecipeResponse object.
	 * 
	 * @return all available recipes with ingredient and instruction detail
	 */
	@ApiOperation(value = "Get All Recipes")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe does not exists",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content)})
	@GetMapping
	public ResponseEntity<RecipeResponse> getAllRecipes() {
		logger.debug("Inside getAllRecipes method", this.getClass());
		RecipeResponse recipeResponse = recipeService.getAllRecipes();
		return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
	}

	/**
	 * Get all recipes by given input condition.
	 * Retrieves record from the database that satisfies condition of all non null values given in the input parameter.
	 * If all values in the input parameter is null then it retrieves all recipes from the database.
	 * If no record available for the given input then return empty result with response message and record size 0.
	 * If record is available for the given input then return List of recipes with success response message and its record size.
	 * 
	 * @param isVeg is vegetarian recipe
	 * @param noOfServings recipes that can serve for no of persons
	 * @param includeIngredient recipes which has ingredient included
	 * @param excludeIngredient recipes which excluded given ingredient
	 * @param instruction recipes has instruction
	 * @return recipe details with response message and record size
	 */
	@ApiOperation(value = "Get Recipes by search param")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe does not exists",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
            content = @Content)})
	@GetMapping("/getRecipes")
	public ResponseEntity<RecipeResponse> getRecipes(@RequestParam(name = "isVeg", required = false) String isVeg,
			 @RequestParam(name = "noOfServings", required = false) Integer noOfServings,
			  @RequestParam(name = "includeIngredient", required = false) String includeIngredient,
			  @RequestParam(name = "exculdeIngredient", required = false) String excludeIngredient,
			 @RequestParam(name = "instruction", required = false) String instruction) {
		logger.debug("Inside getRecipes method", this.getClass());
		RecipeResponse recipeResponse = recipeService.getRecipes(isVeg, noOfServings, includeIngredient,
				excludeIngredient, instruction);
		return new ResponseEntity<>(recipeResponse, HttpStatus.OK);
	}

	/**
	 * Handle MethodArgumentNotValidException.
	 * 
	 * @param ex MethodArgumentNotValidException
	 * @return ErrorMessage
	 */

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorMessage handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		ErrorMessage errorResponse = ErrorMessage.builder().code("400").message("Request body has errors")
				.severity("ERROR").source("INPUT VALIDATION").build();
		List<ErrorMessage.InnerError> innerErrors = new ArrayList<>();
		errorResponse.setInnerErrors(innerErrors);
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			ErrorMessage.InnerError innerError = ErrorMessage.InnerError.builder().message(error.getDefaultMessage())
					.source(fieldName).build();
			innerErrors.add(innerError);
		});
		return errorResponse;
	}

	/**
	 * Handle RecipeExistsException.
	 * 
	 * @param ex RecipeExistsException
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(RecipeExistsException.class)
	public ErrorResponse handleDuplicateRecipe(RecipeExistsException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode("409").build();
		return errorResponse;
	}

	/**
	 * Handle not found exception
	 * 
	 * @param ex RecipeNotFoundException
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RecipeNotFoundException.class)
	public ErrorResponse handleRecipeNotRecipe(RecipeNotFoundException ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode("404").build();
		return errorResponse;
	}

	/**
	 * Handles Internal server error.
	 * 
	 * @param ex Exception
	 * @return ErrorResponse
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleInternalServerError(Exception ex) {
		ErrorResponse errorResponse = ErrorResponse.builder().errorMessage(ex.getMessage()).statusCode("500").build();
		return errorResponse;
	}

}
