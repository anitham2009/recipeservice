package com.app.restaurant.ingredient.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.restaurant.entity.Ingredient;
import com.app.restaurant.ingredient.repository.IIngredientRepository;
import com.app.restaurant.ingredient.service.intf.IIngredientService;

/**
 *  Ingredient service class.
 * @author Anitha Manoharan
 *
 */
@Service
public class IngredientServiceImpl implements IIngredientService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	IIngredientRepository ingredientRepository;

	/**
	 * Get All active ingredients 
	 * @return List of Ingredient
	 */
	@Override
	public List<Ingredient> findAllByIsActive() {
		logger.info("Inside findAllByIsActive method",this.getClass());
		return ingredientRepository.findAllByIsActive("Y");
	}
	

}
