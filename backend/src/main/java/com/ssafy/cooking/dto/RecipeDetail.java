package com.ssafy.cooking.dto;

import java.io.Serializable;
import java.util.List;

public class RecipeDetail implements Serializable {
	private Recipe recipe;
	private List<Ingredient> ingredients;
	private List<CookingStep> cookingSteps;

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<CookingStep> getCookingSteps() {
		return cookingSteps;
	}

	public void setCookingSteps(List<CookingStep> cookingSteps) {
		this.cookingSteps = cookingSteps;
	}
}
