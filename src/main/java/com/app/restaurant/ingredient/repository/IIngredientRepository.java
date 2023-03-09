package com.app.restaurant.ingredient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.restaurant.entity.Ingredient;

/**
 * Ingredient repository
 * @author Anitha Manoharan
 *
 */
@Repository
public interface IIngredientRepository extends JpaRepository<Ingredient, Long>{
	
	List<Ingredient> findAllByIsActive(String isActive);

}
