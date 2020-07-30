package com.ssafy.cooking.dto;

import org.springframework.web.multipart.MultipartFile;

public class Recipe  {
	private Integer recipe_id; //레시피id
	private Integer recipe_user;//작성자id
	private String recipe_user_name;//작성자 이름
	private Integer category_id;//카테고리id
	private String title; //제목
	private String description; //설명 
	private String main_image; //대표 이미지 경로
	private Integer servings; //인원
	private Integer cooking_time; //시간
	private Integer level; //시간
	private Integer hits; //조회
	private String tag; //태그
	private String create_date; //작성시간
	private String update_date;
	private String delete_date;
	private MultipartFile main_image_file;
	public Integer getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(Integer recipe_id) {
		this.recipe_id = recipe_id;
	}
	public Integer getRecipe_user() {
		return recipe_user;
	}
	public void setRecipe_user(Integer recipe_user) {
		this.recipe_user = recipe_user;
	}
	public String getRecipe_user_name() {
		return recipe_user_name;
	}
	public void setRecipe_user_name(String recipe_user_name) {
		this.recipe_user_name = recipe_user_name;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMain_image() {
		return main_image;
	}
	public void setMain_image(String main_image) {
		this.main_image = main_image;
	}
	public Integer getServings() {
		return servings;
	}
	public void setServings(Integer servings) {
		this.servings = servings;
	}
	public Integer getCooking_time() {
		return cooking_time;
	}
	public void setCooking_time(Integer cooking_time) {
		this.cooking_time = cooking_time;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getHits() {
		return hits;
	}
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
	public String getDelete_date() {
		return delete_date;
	}
	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
	}
	public MultipartFile getMain_image_file() {
		return main_image_file;
	}
	public void setMain_image_file(MultipartFile main_image_file) {
		this.main_image_file = main_image_file;
	}
	
}
