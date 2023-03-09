package com.app.restaurant.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RecipeResponse {
	private String responseMessage;
	private Integer recordSize;
	private List<RecipeIngredientInstructionVO> recipeList;
	
}
