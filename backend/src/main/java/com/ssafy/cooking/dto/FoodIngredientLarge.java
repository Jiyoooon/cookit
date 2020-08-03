package com.ssafy.cooking.dto;

public class FoodIngredientLarge {
	private Integer food_ingredient_large_id;
	private String large;
	public Integer getFood_ingredient_large_id() {
		return food_ingredient_large_id;
	}
	public void setFood_ingredient_large_id(Integer food_ingredient_large_id) {
		this.food_ingredient_large_id = food_ingredient_large_id;
	}
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	@Override
	public String toString() {
		return "FoodIngredientLarge [food_ingredient_large_id=" + food_ingredient_large_id + ", large=" + large + "]";
	}
	

}
