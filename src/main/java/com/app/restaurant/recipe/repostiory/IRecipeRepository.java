package com.app.restaurant.recipe.repostiory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.restaurant.entity.Recipe;
/**
 * Recipe repository extends JpaRepostiory<Recipe, Long>
 * @author Anitha Manoharan
 *
 */
@Repository
public interface IRecipeRepository extends JpaRepository<Recipe, Long> {
	//Get recipe detail by given recipe id.
	Recipe findByRecipeId(Long recipeId);

	List<Recipe> findByIsActive(String string);
	@Query(value="select distinct ri.* from recipe ri inner join recipe_ingredient rii on ri.recipe_id=rii.recipe_id  inner join ingredient ii on rii.ingredient_id=ii.ingredient_id inner join recipe_instruction rins on ri.recipe_id=rins.recipe_id  where  (:isVeg is null or lower(ri.is_veg) = :isVeg)  and (:includeIngredient is null or lower(ii.ingredient_name) like :includeIngredient%) and ri.recipe_id not in ( Select  ring.recipe_id from recipe_ingredient ring where ring.ingredient_id in ( select  ing.ingredient_id  from ingredient ing where lower(ing.ingredient_name)  like :excludeIngredient% )) and  (:noOfServings is null or ri.no_of_servings >= :noOfServings) and (:instructions is null or (lower(rins.cooking_method) like %:instructions% or lower(rins.cooking_steps) like %:instructions%  or lower(rins.additional_info) like %:instructions%))",nativeQuery = true)
	List<Recipe>  findByRecipeDetail(@Param("isVeg") String isVeg, @Param("noOfServings") Integer noOfServings, @Param("includeIngredient") String includeIngredient, @Param("excludeIngredient") String excludeIngredient,
			@Param("instructions") String instruction);
	//Get recipe detail of given recipe name (case insensitive search)
	Recipe findByRecipeNameIgnoreCase(String recipeName);

}
