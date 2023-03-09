package com.app.restaurant.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="recipe_instruction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RecipeInstruction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long instructionsId;
	 @OneToOne
	 @JoinColumn(name="recipe_id")
	 private Recipe recipe;
	 private String cookingMethod;
	 private String cookingSteps;
	 private String additionalInfo;

}
