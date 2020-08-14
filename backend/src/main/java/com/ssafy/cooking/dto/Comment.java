package com.ssafy.cooking.dto;

public class Comment {
	private int comment_id;
	private int comment_user_id;
	private String comment_user_name;
	private String comment_user_profileImage;//작성자 사진
	private int recipe_id;
	private String description;
	private String create_date; //작성시간
	private String update_date;
	private String delete_date;
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public int getComment_user_id() {
		return comment_user_id;
	}
	public void setComment_user_id(int comment_user_id) {
		this.comment_user_id = comment_user_id;
	}
	public String getComment_user_name() {
		return comment_user_name;
	}
	public void setComment_user_name(String comment_user_name) {
		this.comment_user_name = comment_user_name;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getComment_user_profileImage() {
		return comment_user_profileImage;
	}
	public void setComment_user_profileImage(String comment_user_profileImage) {
		this.comment_user_profileImage = comment_user_profileImage;
	}
	
	
}
