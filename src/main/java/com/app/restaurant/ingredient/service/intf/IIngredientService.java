package com.app.restaurant.ingredient.service.intf;

import java.util.List;

import com.app.restaurant.entity.Ingredient;

public interface IIngredientService {

	List<Ingredient> findAllByIsActive();
}
