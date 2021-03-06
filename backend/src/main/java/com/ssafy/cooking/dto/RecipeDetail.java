package com.ssafy.cooking.dto;

<<<<<<< HEAD
import java.util.List;

public class RecipeDetail {
	private Recipe recipe;
=======
import java.util.Arrays;
import java.util.List;

public class RecipeDetail extends Recipe {
>>>>>>> develop
	private List<Ingredient> ingredients;
	private List<CookingStep> cookingStep;
	
	public RecipeDetail() {
	}
	
	public RecipeDetail(Recipe recipe) {
		setRecipe_id(recipe.getRecipe_id());
		setTitle(recipe.getTitle());
		setDescription(recipe.getDescription());
		setRecipe_user(recipe.getRecipe_user());
		setRecipe_user_name(recipe.getRecipe_user_name());
		setRecipe_user_profileImage(recipe.getRecipe_user_profileImage());
		setCategory_id(recipe.getCategory_id());
		setMain_image(recipe.getMain_image());
		setServings(recipe.getServings());
		setCooking_time(recipe.getCooking_time());
		setLevel(recipe.getLevel());
		setHits(recipe.getHits());
		setCreate_date(recipe.getCreate_date());
		setUpdate_date(recipe.getUpdate_date());
		setDelete_date(recipe.getDelete_date());
		setLike(recipe.getLike());
		setTag(recipe.getTag());
	
//		if(recipe.getTag().length() > 2) {
//			setTagString((recipe.getTag().split(",")));
//			String tag = "[ ";
//			for (String t : getTagString()) {
//				tag += '"' + t + '"' + ',' + ' ';
//			}
//		}
//		
//		
//		setTag(Arrays.toString(getTagString()));
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<CookingStep> getCookingStep() {
		return cookingStep;
	}

	public void setCookingStep(List<CookingStep> cookingStep) {
		this.cookingStep = cookingStep;
	}
}
