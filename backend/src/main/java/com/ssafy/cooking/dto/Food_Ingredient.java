package com.ssafy.cooking.dto;

public class Food_Ingredient {
	private Integer food_ingredient_small_id;
	private Integer food_ingredient_medium_id;
	private String small;
	
	public Integer getFood_ingredient_small_id() {
		return food_ingredient_small_id;
	}
	public void setFood_ingredient_small_id(Integer food_ingredient_small_id) {
		this.food_ingredient_small_id = food_ingredient_small_id;
	}
	public Integer getFood_ingredient_medium_id() {
		return food_ingredient_medium_id;
	}
	public void setFood_ingredient_medium_id(Integer food_ingredient_medium_id) {
		this.food_ingredient_medium_id = food_ingredient_medium_id;
	}
	public String getSmall() {
		return small;
	}
	public void setSmall(String small) {
		this.small = small;
	}
}
