package com.app.restaurant.model;

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
public class IngredientVO {
	private Long ingredientId;
	private String ingredientName;
	private String isActive;
	
}
