package com.app.restaurant.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ingredient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ingredientId;
	private String ingredientName;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	@OneToMany(mappedBy="ingredients", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RecipeIngredient> recipeIngredientInfo;

	
	
	
}
