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
public class RecipeIngredientInstructionVO {

	private Long recipeId;
	private String recipeName;
	private String isVeg;
	private Integer noOfServings;
	private String isActive;
	private List<IngredientVO> ingredientList;
	private RecipeInstructionVO recipeInstruction;
}
