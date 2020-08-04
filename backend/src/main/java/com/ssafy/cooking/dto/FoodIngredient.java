package com.ssafy.cooking.dto;

public class FoodIngredient {
	private String name;
	private String kind;
	private Integer id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "FoodIngredient [name=" + name + ", kind=" + kind + ", id=" + id + "]";
	}
	
	
}
