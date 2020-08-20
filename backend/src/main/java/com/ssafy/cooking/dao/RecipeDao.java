package com.ssafy.cooking.dao;

import java.util.List;

import org.mapstruct.Mapper;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.FoodIngredient;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.RecipeDetail;

@Mapper
public interface RecipeDao {
	
	int addRecipe(Recipe recipe);
	void checkIngredients(Ingredient ingredient);
	int addIngredients(int recipe_id, Ingredient ingredient);
	int addCookingsteps(int recipe_id, List<CookingStep> cookingSteps);
	List<Recipe> getRecipes(int start, int end, Integer id, String user, String[] query, Integer category, Integer order, Integer likeUser, String filter);

	List<Ingredient> getIngredients(int recipe_id);
	List<CookingStep> getCookingSteps(int recipe_id);

	void upHits(int recipe_id);
	int deleteRecipe(int recipe_id, int uid);
	
	List<Comment> getCommnets(int recipe_id);
	int addCommnet(int recipe_id, Comment comment);
	int modifyComment(Comment comment, int uid);
	int deleteComment(int comment_id, int uid);
	List<FoodIngredient> getSmallIngredients();
	List<FoodIngredient> getMediumIngredients();
	List<FoodIngredient> getLargeIngredients();
	List<Recipe> getRecipes2(int start, int end, Integer id, String user, String[]  query, Integer category, Integer order, Integer likeUser
							, List<String> hate_large, List<String> hate_medium, List<String> hate_small
							, List<String> like_large, List<String> like_medium, List<String> like_small);
	String[] getSmallIngredientsArray();
	void updateTime(Integer cooking_steps_id, String time);
	int reviseRecipe(Integer uid, RecipeDetail recipeData);
	void reviseCookingsteps(int recipe_id, CookingStep step);
	void setLike(int recipe_id, int uid);
	int deleteLike(int recipe_id, int uid);
	List<Integer> getLikeList(Integer recipe_id);
	void deleteCookingSteps(int recipe_id, int size);
	void deleteIngredients(int recipe_id);
	CookingStep getCookingStep(int recipe_id, int step_id);
	List<Recipe> getRandom();
}
