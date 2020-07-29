package com.ssafy.cooking.dao;

import java.util.List;

import org.mapstruct.Mapper;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;

@Mapper
public interface RecipeDao {
	
	int addRecipe(Recipe recipe);
	int addIngredients(int recipe_id, List<Ingredient> ingredients);
	int addCookingsteps(int recipe_id, List<CookingStep> cookingSteps);
	List<Recipe> getRecipes(int start, int end, Integer id, Integer user, String query, Integer category, String filter);

	List<Ingredient> getIngredients(int recipe_id);
	List<CookingStep> getCookingSteps(int recipe_id);

	void upHits(int recipe_id);
	int deleteRecipe(int recipe_id, int uid);
	
	List<Comment> getCommnets(int recipe_id);
	int addCommnet(int recipe_id, Comment comment);
	int modifyComment(Comment comment, int uid);
	int deleteComment(int comment_id, int uid);
}
