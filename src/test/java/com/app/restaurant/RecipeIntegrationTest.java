package com.app.restaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.restaurant.model.RecipeCreateVO;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(Cucumber.class)
//@CucumberOptions(features = "classpath:features/" , glue = {"com.app.restaurant.it.glue"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecipeServiceApplication.class, webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecipeIntegrationTest {

	@Autowired
	TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	private static final String LOCALHOST= "http://localhost:";
	private String getRootURL() {
		 return LOCALHOST+ port;
	}
	public static final String BASE_FILE_PATH = "src/test/resources";
	public static final String CONTEXT_PATH = "/recipeservice";
	public static final String BASE_PATH = "/recipeservice/recipes";
	
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
	
	@DisplayName("Create French Fries recipe")
	@Test
	@Order(1)
	public void testCreateRecipe() throws Exception {
		String recipeInput = readJSONFile(BASE_FILE_PATH +"/createrecipe.json");
		ObjectMapper mapper = new ObjectMapper();
		RecipeCreateVO  recipeCreate = mapper.readValue(recipeInput, RecipeCreateVO.class);
		 ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootURL() + BASE_PATH, recipeCreate, String.class);
		 
		
	 assertNotNull(postResponse.getBody());
  assertTrue(postResponse.getStatusCode().is2xxSuccessful());
	}
}
