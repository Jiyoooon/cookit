package com.ssafy.cooking.service;

import java.util.List;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;

public interface RecipeService {
	public List<Recipe> getRecipes(Integer p, Integer id, Integer user, String query, Integer category, String filter);

	public List<Ingredient> getIngredients(int recipe_id);
	public List<CookingStep> getCookingSteps(int recipe_id);

	public List<Comment> getComments(int recipe_id);

	public int addCommnet(int recipe_id, Comment comment);
}
