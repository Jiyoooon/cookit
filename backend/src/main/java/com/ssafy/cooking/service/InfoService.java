package com.ssafy.cooking.service;

import java.util.List;

import com.ssafy.cooking.dto.Category;
import com.ssafy.cooking.dto.Filter;

public interface InfoService {
	public List<Category> searchCategories();
	public Filter searchIngredients(String name);
}
