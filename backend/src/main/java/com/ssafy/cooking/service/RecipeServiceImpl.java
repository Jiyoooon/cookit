package com.ssafy.cooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cooking.dao.RecipeDao;
import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;

@Service
public class RecipeServiceImpl implements RecipeService{

	@Autowired
	private RecipeDao recipeDao;

	@Override
	public List<Recipe> getRecipes(Integer p, Integer id, Integer user, String query, Integer category, String filter) {
		if(p == null) {
			p = 0;
		}
		return recipeDao.getRecipes(p, p + 20, id, user, query, category, filter);
	}

	@Override
	public List<Ingredient> getIngredients(int recipe_id) {
		return recipeDao.getIngredients(recipe_id);
	}

	@Override
	public List<CookingStep> getCookingSteps(int recipe_id) {
		return recipeDao.getCookingSteps(recipe_id);
	}

	@Override
	public List<Comment> getComments(int recipe_id) {
		return recipeDao.getCommnets(recipe_id);
	}

	@Override
	public int addCommnet(int recipe_id, Comment comment) {
		return recipeDao.addCommnet(recipe_id, comment);
	}
}
