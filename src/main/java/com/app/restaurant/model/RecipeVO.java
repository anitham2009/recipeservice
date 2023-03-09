package com.app.restaurant.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description="All recipe details")
public class RecipeVO {
	private Long recipeId;
	private Long[] ingredientIds;
	private String recipeName;
	@ApiModelProperty(example = "Y")
	private String isVeg;
	private Double noOfServings;
	@ApiModelProperty(example = "Y")
	private String isActive;
	private RecipeInstructionVO recipeInstruction;

}
