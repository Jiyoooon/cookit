package com.ssafy.cooking.dto;

public class Ingredient {
	private Integer recipe_ingredient_id;
	private Integer recipe_id;
	private Integer food_ingredient_small_id;
	private String food_ingredient_small_name;
	private String name;
	private Integer is_essential;
	private String quantity;
	private String create_date;
	private String update_date;
	
	public Integer getRecipe_ingredient_id() {
		return recipe_ingredient_id;
	}
	public void setRecipe_ingredient_id(Integer recipe_ingredient_id) {
		this.recipe_ingredient_id = recipe_ingredient_id;
	}
	public Integer getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(Integer recipe_id) {
		this.recipe_id = recipe_id;
	}
	public Integer getFood_ingredient_small_id() {
		return food_ingredient_small_id;
	}
	public void setFood_ingredient_small_id(Integer food_ingredient_small_id) {
		this.food_ingredient_small_id = food_ingredient_small_id;
	}
	public String getFood_ingredient_small_name() {
		return food_ingredient_small_name;
	}
	public void setFood_ingredient_small_name(String food_ingredient_small_name) {
		this.food_ingredient_small_name = food_ingredient_small_name;
	}
	public Integer getIs_essential() {
		return is_essential;
	}
	public void setIs_essential(Integer is_essential) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Ingredient [recipe_ingredient_id=" + recipe_ingredient_id + ", recipe_id=" + recipe_id
				+ ", food_ingredient_small_id=" + food_ingredient_small_id + ", food_ingredient_small_name="
				+ food_ingredient_small_name + ", name=" + name + ", is_essential=" + is_essential + ", quantity="
				+ quantity + ", create_date=" + create_date + ", update_date=" + update_date + "]";
	}
}
