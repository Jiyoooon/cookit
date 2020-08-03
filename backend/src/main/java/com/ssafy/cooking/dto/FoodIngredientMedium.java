package com.ssafy.cooking.dto;

public class FoodIngredientMedium {
	private Integer food_ingredient_medium_id;
	private Integer food_ingredient_large_id;
	private String medium;
	public Integer getFood_ingredient_medium_id() {
		return food_ingredient_medium_id;
	}
	public void setFood_ingredient_medium_id(Integer food_ingredient_medium_id) {
		this.food_ingredient_medium_id = food_ingredient_medium_id;
	}
	public Integer getFood_ingredient_large_id() {
		return food_ingredient_large_id;
	}
	public void setFood_ingredient_large_id(Integer food_ingredient_large_id) {
		this.food_ingredient_large_id = food_ingredient_large_id;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	@Override
	public String toString() {
		return "FoodIngredientMedium [food_ingredient_medium_id=" + food_ingredient_medium_id
				+ ", food_ingredient_large_id=" + food_ingredient_large_id + ", medium=" + medium + "]";
	}
	
	
}
