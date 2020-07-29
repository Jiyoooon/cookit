package com.ssafy.cooking.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.cooking.dto.Category;
import com.ssafy.cooking.dto.Filter;

@Mapper
public interface InfoDao {

	public List<Category> selectCategories();
	public Filter selectIngredients(String name);

}
