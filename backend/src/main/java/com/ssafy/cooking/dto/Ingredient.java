package com.ssafy.cooking.dto;

public class Ingredient {
	private int recipe_ingredient_id;
	private int recipe_id;
	private int food_ingredient_small_id;
	private String food_ingredient_small_name;
	private int is_essential;
	private String quantity;
	private String create_date;
	private String update_date;
	public int getRecipe_ingredient_id() {
		return recipe_ingredient_id;
	}
	public void setRecipe_ingredient_id(int recipe_ingredient_id) {
		this.recipe_ingredient_id = recipe_ingredient_id;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public int getFood_ingredient_small_id() {
		return food_ingredient_small_id;
	}
	public void setFood_ingredient_small_id(int food_ingredient_small_id) {
		this.food_ingredient_small_id = food_ingredient_small_id;
	}
	public String getFood_ingredient_small_name() {
		return food_ingredient_small_name;
	}
	public void setFood_ingredient_small_name(String food_ingredient_small_name) {
		this.food_ingredient_small_name = food_ingredient_small_name;
	}
	public int getIs_essential() {
		return is_essential;
	}
	public void setIs_essential(int is_essential) {
		this.is_essential = is_essential;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	
	@Override
	public String toString() {
		return "Ingredient [recipe_ingredient_id=" + recipe_ingredient_id + ", recipe_id=" + recipe_id
				+ ", food_ingredient_small_id=" + food_ingredient_small_id + ", food_ingredient_small_name="
				+ food_ingredient_small_name + ", is_essential=" + is_essential + ", quantity=" + quantity
				+ ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}
}
