package com.ssafy.cooking.dao;

import java.util.List;

import org.mapstruct.Mapper;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Filter;
import com.ssafy.cooking.dto.FoodIngredientLarge;
import com.ssafy.cooking.dto.FoodIngredientMedium;
import com.ssafy.cooking.dto.FoodIngredientSmall;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;

@Mapper
public interface RecipeDao {
	
	int addRecipe(Recipe recipe);
	int addIngredients(int recipe_id, List<Ingredient> ingredients);
	int addCookingsteps(int recipe_id, List<CookingStep> cookingSteps);
	List<Recipe> getRecipes(int start, int end, Integer id, String user, String query, Integer category, String filter);

	List<Ingredient> getIngredients(int recipe_id);
	List<CookingStep> getCookingSteps(int recipe_id);

	void upHits(int recipe_id);
	int deleteRecipe(int recipe_id, int uid);
	
	List<Comment> getCommnets(int recipe_id);
	int addCommnet(int recipe_id, Comment comment);
	int modifyComment(Comment comment, int uid);
	int deleteComment(int comment_id, int uid);
	List<FoodIngredientSmall> getSmallIngredients();
	List<FoodIngredientMedium> getMediumIngredients();
	List<FoodIngredientLarge> getLargeIngredients();
	List<Recipe> getRecipes2(int start, int end, Integer id, String user, String query, Integer category
							, List<String> hate_large, List<String> hate_medium, List<String> hate_small
							, List<String> like_large, List<String> like_medium, List<String> like_small);
	
}
