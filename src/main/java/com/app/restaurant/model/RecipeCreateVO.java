package com.app.restaurant.model;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class RecipeCreateVO {
	@NotNull(message = "Ingredients is mandatory")
	@NotEmpty
	private Long[] ingredientIds;
	@NotNull(message = "Recipe Name is mandatory")
	@Size(min = 2, max=250, message = "Recipe name atleast have 2 characters")
	private String recipeName;
	@ApiModelProperty(example = "Y")
	@NotNull(message = "isVeg value is mandatory")
	@Size(min = 1,max=1, message = "Value should be Y or N")
	private String isVeg;
	@NotNull(message = "No of servings value is mandatory")
	@DecimalMin(value = "1")
	private Double noOfServings;
	@ApiModelProperty(example = "Y")
	@NotNull(message = "isActive value is mandatory")
	@Size(min = 1,max=1, message = "Value should be Y or N")
	private String isActive;
	@Valid
	@NotNull(message= "Recipe Instruction is mandatory")
	private RecipeInstructionVO recipeInstruction;

}

