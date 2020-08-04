package com.ssafy.cooking.dto;

public class Filter {
	private int filtering_id;
	private String filtering_name;
	private int filtering_user_id;
	
	private String hate_large;
	private String hate_medium;
	private String hate_small;
	private String like_large;
	private String like_medium;
	private String like_small;
	private String create_date;
	
	public int getFiltering_id() {
		return filtering_id;
	}
	public void setFiltering_id(int filtering_id) {
		this.filtering_id = filtering_id;
	}
	public String getFiltering_name() {
		return filtering_name;
	}
	public void setFiltering_name(String filtering_name) {
		this.filtering_name = filtering_name;
	}
	public int getFiltering_user_id() {
		return filtering_user_id;
	}
	public void setFiltering_user_id(int filtering_user_id) {
		this.filtering_user_id = filtering_user_id;
	}
	public String getHate_large() {
		return hate_large;
	}
	public void setHate_large(String hate_large) {
		this.hate_large = hate_large;
	}
	public String getHate_medium() {
		return hate_medium;
	}
	public void setHate_medium(String hate_medium) {
		this.hate_medium = hate_medium;
	}
	public String getHate_small() {
		return hate_small;
	}
	public void setHate_small(String hate_small) {
		this.hate_small = hate_small;
	}
	public String getLike_large() {
		return like_large;
	}
	public void setLike_large(String like_large) {
		this.like_large = like_large;
	}
	public String getLike_medium() {
		return like_medium;
	}
	public void setLike_medium(String like_medium) {
		this.like_medium = like_medium;
	}
	public String getLike_small() {
		return like_small;
	}
	public void setLike_small(String like_small) {
		this.like_small = like_small;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	@Override
	public String toString() {
		return "Filter [filtering_id=" + filtering_id + ", filtering_name=" + filtering_name + ", filtering_user_id="
				+ filtering_user_id + ", hate_large=" + hate_large + ", hate_medium=" + hate_medium + ", hate_small="
				+ hate_small + ", like_large=" + like_large + ", like_medium=" + like_medium + ", like_small="
				+ like_small + ", create_date=" + create_date + "]";
	}
	
}
