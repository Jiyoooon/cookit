package com.ssafy.cooking.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CookingStep {
	private Integer cooking_steps_id;
	private Integer recipe_id;
	private Integer steps;
	private String description;
	private String tip;
	private String step_image;
	private String create_date;
	private String update_date;
	private List<int[]> time;
	private MultipartFile step_image_file;
	public Integer getCooking_steps_id() {
		return cooking_steps_id;
	}
	public void setCooking_steps_id(Integer cooking_steps_id) {
		this.cooking_steps_id = cooking_steps_id;
	}
	public Integer getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(Integer recipe_id) {
		this.recipe_id = recipe_id;
	}
	public Integer getSteps() {
		return steps;
	}
	public void setSteps(Integer steps) {
		this.steps = steps;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getStep_image() {
		return step_image;
	}
	public void setStep_image(String step_image) {
		this.step_image = step_image;
	}
	public MultipartFile getStep_image_file() {
		return step_image_file;
	}
	public void setStep_image_file(MultipartFile step_image_file) {
		this.step_image_file = step_image_file;
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
	public List<int[]> getTime() {
		return time;
	}
	public void setTime(List<int[]> time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "CookingStep [cooking_steps_id=" + cooking_steps_id + ", recipe_id=" + recipe_id + ", steps=" + steps
				+ ", description=" + description + ", tip=" + tip + ", step_image=" + step_image + ", create_date="
				+ create_date + ", update_date=" + update_date + ", time=" + time + ", step_image_file="
				+ step_image_file + "]";
	}
}
