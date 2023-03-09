package com.app.restaurant.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class RecipeInstructionVO {
	@NotNull(message = "Cooking Method is mandatory")
	@Size(min = 4,max=50, message = "Cooking Method should have 4 characters")
	private String cookingMethod;
	@NotNull(message = "Cooking Steps is mandatory")
	@Size(min = 4, max= 2000, message = "Cooking Steps should have 4 characters")
	private String cookingSteps;
	private String additionalInfo;
}
