package com.ssafy.cooking.service;

import java.util.List;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.RecipeDetail;

public interface RecipeService {
	public List<Recipe> getRecipes(Integer p, Integer id, Integer user, String query, Integer category, String filter);

	public List<Ingredient> getIngredients(int recipe_id);
	public List<CookingStep> getCookingSteps(int recipe_id);
	public void upHits(int recipe_id);
	public List<Comment> getComments(int recipe_id);

	public int addCommnet(int recipe_id, Comment comment);

	public int addRecipe(RecipeDetail recipeDetail);

	public int deleteRecipe(int recipe_id, int uid);

	public int deleteComment(int comment_id, int uid);

	public int modifyComment(Comment comment, int uid);
}
