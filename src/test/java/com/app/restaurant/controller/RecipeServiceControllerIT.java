package com.app.restaurant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.restaurant.RecipeConstants;
import com.app.restaurant.recipe.repostiory.IRecipeRepository;
import com.app.restaurant.recipe.service.intf.IRecipeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value="classpath:application-test.properties")
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class RecipeServiceControllerIT {
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	IRecipeService recipeService;
	
	@Mock
	IRecipeRepository recipeRepository;
	
	@Mock
	RecipeController recipeController;

	public static final String BASE_FILE_PATH = "src/test/resources";
	public static final String CONTEXT_PATH = "/recipeservice";
	public static final String BASE_PATH = "/recipeservice/recipes";
	
	@DisplayName("Create French Fries recipe")
	@Test
	@Order(1)
	public void testCreateRecipe() throws Exception {
		String recipeInput = readJSONFile(BASE_FILE_PATH +"/createrecipe.json");
		RequestBuilder request = MockMvcRequestBuilders.post(BASE_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(recipeInput)
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
	 assertNotNull(result.getResponse());
	}
	
	@DisplayName("Update French Fries recipe")
	@Test
	@Order(2)
	public void testUpdateRecipe() throws Exception {
		String recipeInput = readJSONFile(BASE_FILE_PATH+ "/updaterecipe.json");
		RequestBuilder request = MockMvcRequestBuilders.put(BASE_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(recipeInput)
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andExpect(status().is2xxSuccessful()).andReturn();
	 assertNotNull(result.getResponse());
	}

	@DisplayName("Get All available recipies")
	@Test
	@Order(3)
	public void testgetAllRecipe() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_PATH)
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
	 assertNotNull(result.getResponse());
	 String expectedJSON = readJSONFile(BASE_FILE_PATH+"/availablerecipes.json");
	JSONAssert.assertEquals(expectedJSON, result.getResponse().getContentAsString(), false);	
	}
	
	@DisplayName("Get all vegetarian recipes")
	@Test
	@Order(4)
	public void testAllVegRecipe() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_PATH +"/getRecipes").param("isVeg", "Y")
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
	 assertNotNull(result.getResponse());
		
	}
	
	@DisplayName("Recipes that can serve 4 persons and have potato as ingredient")
	@Test
	@Order(5)
	public void testRecipeByCond1() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_PATH+"/getRecipes").param("isVeg", "Y").param("includeIngredient", "potato")
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
	 assertNotNull(result.getResponse());
		
	}
	
	@DisplayName("Recipes without salmon as an ingredient that has oven in the instruction")
	@Test
	@Order(6)
	public void testRecipeByCond2() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_PATH+"/getRecipes").param("excludeIngredient", "salmon").param("instruction", "oven")
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
	 assertNotNull(result.getResponse());
		
	}
	
	@DisplayName("Get All Non veg recipes")
	@Test
	@Order(7)
	public void testRecipeByCond3() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_PATH+"/getRecipes").param("isVeg", "N")
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
	 assertNotNull(result.getResponse());
		
	}
	
	@DisplayName("Get All Recipes if no condition")
	@Test
	@Order(8)
	public void testRecipeByNoCond() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get(BASE_PATH+"/getRecipes")
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
	 assertNotNull(result.getResponse());
		
	}
	@DisplayName("Recipe exists")
	@Test
	@Order(9)
	public void testCreateDuplicateRecipe() throws Exception {
		String recipeInput = readJSONFile(BASE_FILE_PATH+ "/createrecipe.json");
		RequestBuilder request = MockMvcRequestBuilders.post(BASE_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(recipeInput)
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		 mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
	}
	
	@Order(10)
	@DisplayName("Delete French fries")
	@Test
	public void testDeleteRecipe() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete(BASE_PATH+"/3")
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(request).andReturn();
		
	 assertEquals(RecipeConstants.DELETED_MSG,result.getResponse().getContentAsString());
	}

	@Order(11)
	@DisplayName("throws Recipe does not exists exception")
	@Test
	public void testRecipeNotExists() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete(BASE_PATH+"/3")
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
	}
	
	@Order(12)
	@DisplayName("throws exception when gives Invalid input")
	@Test
	public void testInvalidInputParam() throws Exception {
		String recipeInput = readJSONFile(BASE_FILE_PATH+"/createinvalidinputrecipe.json");
		RequestBuilder request = MockMvcRequestBuilders.post(BASE_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(recipeInput)
				.contextPath(CONTEXT_PATH).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(request).andExpect(status().is4xxClientError()).andReturn();
	}

	@Order(12)
	@DisplayName("throws internal server error")
	@Test
	public void testInternalServerError() throws Exception {
		String recipeInput = readJSONFile(BASE_FILE_PATH+"/recipesError.json");
		
		RequestBuilder request = MockMvcRequestBuilders.post(BASE_PATH).contentType(MediaType.APPLICATION_JSON)
				.content(recipeInput)
				.contextPath(CONTEXT_PATH);
		mockMvc.perform(request).andExpect(status().is5xxServerError()).andReturn();
	}
	/**
	 * Read json file.
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	String readJSONFile(String filePath) throws IOException {
		File file = new File(filePath);
		String content = FileUtils.readFileToString(file, "UTF-8");
		return content;
	}
	
	
	
	
		
}
