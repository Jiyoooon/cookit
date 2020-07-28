package com.ssafy.cooking.dto;

import java.io.Serializable;

public class User implements Serializable{
	private int user_id;//pk
	private String email;
	private String password;
	private String name;
	private String nickname;
	private String intro;

	private boolean start_page;	//내블로그(true), 커뮤니티(false)
	private String profile_image;
	private int hits;

	private String create_date;
	private String update_date;

	private String sns_type;
	private String sns_token_id;
	private String sns_connect_date;
	
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public boolean isStart_page() {
		return start_page;
	}
	public void setStart_page(boolean start_page) {
		this.start_page = start_page;
	}
	public String getProfile_image() {
		return profile_image;
	}
	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
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
	public String getSns_type() {
		return sns_type;
	}
	public void setSns_type(String sns_type) {
		this.sns_type = sns_type;
	}
	public String getSns_token_id() {
		return sns_token_id;
	}
	public void setSns_token_id(String sns_token_id) {
		this.sns_token_id = sns_token_id;
	}
	public String getSns_connect_date() {
		return sns_connect_date;
	}
	public void setSns_connect_date(String sns_connect_date) {
		this.sns_connect_date = sns_connect_date;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", email=" + email + ", password=" + password + ", name="
				+ name + ", nickname=" + nickname + ", intro=" + intro + ", start_page=" + start_page
				+ ", profile_image=" + profile_image + ", hits=" + hits + ", create_date=" + create_date
				+ ", update_date=" + update_date + ", sns_type=" + sns_type
				+ ", sns_token_id=" + sns_token_id + ", sns_connect_date=" + sns_connect_date + "]";
	}
	
	
	
	
}
