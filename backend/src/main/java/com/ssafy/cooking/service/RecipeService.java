package com.ssafy.cooking.service;

import java.util.List;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Filter;
import com.ssafy.cooking.dto.FoodIngredient;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.RecipeDetail;

public interface RecipeService {
	public List<Recipe> getRecipes(Integer p, Integer id, String user, String query, Integer category, Integer order, String filter, String baseUrl);

	public List<Ingredient> getIngredients(int recipe_id);
	public List<CookingStep> getCookingSteps(int recipe_id);
	public void upHits(int recipe_id);
	public List<Comment> getComments(int recipe_id);

	public int addCommnet(int recipe_id, Comment comment);

	public int addRecipe(RecipeDetail recipeDetail, String baseUrl);

	public int deleteRecipe(int recipe_id, int uid);

	public int deleteComment(int comment_id, int uid);

	public int modifyComment(Comment comment, int uid);

	public List<FoodIngredient> getAllIngredients();

	public List<Recipe> getRecipes2(Integer p, Integer id, String user, String query, Integer category, Integer order, Filter filter, String baseUrl);

	public String[] getSmallIngredients();

	public int reviseRecipe(RecipeDetail recipeData, String baseUrl);

	public int setLike(int recipe_id, int uid);
}
