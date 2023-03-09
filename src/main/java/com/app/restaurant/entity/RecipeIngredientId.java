package com.app.restaurant.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeIngredientId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Ingredient ingredients;
	
	private Recipe recipe;

	
	

	
}
