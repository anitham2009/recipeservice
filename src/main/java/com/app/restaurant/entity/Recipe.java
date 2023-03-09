package com.app.restaurant.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recipeId;
	private String recipeName;
	private String isVeg;
	private Double noOfServings;
	@OneToOne(mappedBy="recipe",cascade = CascadeType.ALL, orphanRemoval = true)
	private RecipeInstruction recipeInstruction;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	@OneToMany(mappedBy= "recipe", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<RecipeIngredient> recipeIngredientList;
	
	
}
