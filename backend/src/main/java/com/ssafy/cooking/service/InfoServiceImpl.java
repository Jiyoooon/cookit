package com.ssafy.cooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cooking.dao.InfoDao;
import com.ssafy.cooking.dto.Category;
import com.ssafy.cooking.dto.Filter;

@Service
public class InfoServiceImpl implements InfoService{
	
	@Autowired
	private InfoDao infoDao;
	
	@Override
	public List<Category> searchCategories() {
		return infoDao.selectCategories();
	}

	@Override
	public Filter searchIngredients(String name) {
		return infoDao.selectIngredients(name);
	}
	
}
