package com.ssafy.cooking.dao;

import java.util.List;

import org.mapstruct.Mapper;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.User;

@Mapper
public interface RecipeDao {
	List<Recipe> getRecipes(int start, int end, Integer id, Integer user, String query, Integer category, String filter);

	List<Ingredient> getIngredients(int recipe_id);
	List<CookingStep> getCookingSteps(int recipe_id);

	List<Comment> getCommnets(int recipe_id);

	int addCommnet(int recipe_id, Comment comment);
}
