package com.app.restaurant.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="recipe_ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(RecipeIngredientId.class)
public class RecipeIngredient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="ingredient_id")
	@JsonIgnore
	private Ingredient ingredients;
	
	@Id
	@ManyToOne
	@JoinColumn(name="recipe_id")
	@JsonIgnore
	private Recipe recipe;

	
	
}
