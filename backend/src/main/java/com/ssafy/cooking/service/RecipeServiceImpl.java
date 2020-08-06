package com.ssafy.cooking.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.cooking.dao.RecipeDao;
import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Filter;
import com.ssafy.cooking.dto.FoodIngredient;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.RecipeDetail;
import com.ssafy.cooking.util.Timer;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeDao recipeDao;

	@Override
	public List<Recipe> getRecipes(Integer p, Integer id, String user, String query, Integer category, String filter, String baseUrl) {
		int start = 0;
		int end = Integer.MAX_VALUE;
		if (p != null) {
			start = p;
			end = 20;
		}
		List<Recipe> recipes = recipeDao.getRecipes(start, end, id, user, query, category, filter);
		for (Recipe r : recipes) {
			r.setRecipe_user_profileImage(baseUrl + "/images/profile/"+r.getRecipe_user_profileImage());
		}
		return recipes;
	}

	@Override
	public void upHits(int recipe_id) {
		recipeDao.upHits(recipe_id);
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

	@Override
	public int addRecipe(RecipeDetail recipeDetail) {
		String imageName;
		if (recipeDetail.getRecipe_user() != null && recipeDetail.getRecipe_user() > 0)
			imageName = recipeDetail.getRecipe_user() + Long.toString(System.currentTimeMillis());
		else
			imageName = Long.toString(System.currentTimeMillis());
		
		if (recipeDetail.getMain_image_file() != null && !recipeDetail.getMain_image_file().isEmpty()) {
			try {
				writeFile(recipeDetail.getMain_image_file(), imageName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			recipeDetail.setMain_image("http://i3a201.p.ssafy.io:8080/images/recipe/" + imageName + ".jpg");
		} else {
			recipeDetail.setMain_image("http://i3a201.p.ssafy.io:8080/images/recipe/default.jpg");
		}

		recipeDao.addRecipe(recipeDetail);
		int recipe_id = recipeDetail.getRecipe_id();

		if (recipeDetail.getCookingStep() != null) {
			for (int i = 0; i < recipeDetail.getCookingStep().size(); i++) {
				CookingStep step = recipeDetail.getCookingStep().get(i);
				step.setTime(Timer.getTimer(step.getDescription()));
				if (step.getStep_image_file() != null) {
					try {
						String stepImageName = imageName + Integer.toString(i);
						writeFile(step.getStep_image_file(), stepImageName);
						recipeDetail.getCookingStep().get(i)
								.setStep_image("http://i3a201.p.ssafy.io:8080/images/recipe/" + stepImageName + ".jpg");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			recipeDao.addCookingsteps(recipe_id, recipeDetail.getCookingStep());
		}
		
		if (recipeDetail.getIngredients() != null)
			recipeDao.addIngredients(recipe_id, recipeDetail.getIngredients());

		return recipe_id;
	}

	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		String saveDir = "/var/lib/tomcat8/webapps/images/recipe/";
		FileOutputStream fos = new FileOutputStream(saveDir + saveFileName + ".jpg");
		fos.write(multipartFile.getBytes());
		fos.close();
	}
	
	@Override
	public int reviseRecipe(RecipeDetail recipeData) {
		if (recipeData.getMain_image_file() != null && !recipeData.getMain_image_file().isEmpty()) {
			try {
				String saveFileName = recipeData.getMain_image().substring(44, recipeData.getMain_image().length() - 4);
				writeFile(recipeData.getMain_image_file(), saveFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		recipeDao.reviseRecipe(recipeData);
		int recipe_id = recipeData.getRecipe_id();
		if (recipeData.getCookingStep() != null) {
			List<CookingStep> reviseCookSteps = new LinkedList<>();
			List<CookingStep> addCookSteps = new LinkedList<>();
			for (int i = 0; i < recipeData.getCookingStep().size(); i++) {
				CookingStep step = recipeData.getCookingStep().get(i);
				if(step.getCooking_steps_id() != null) {
					reviseCookSteps.add(step);
				} else {
					addCookSteps.add(step);
				}
				step.setTime(Timer.getTimer(step.getDescription()));
				if (step.getStep_image_file() != null) {
					try {
						String imageName = recipeData.getRecipe_user() + Long.toString(System.currentTimeMillis());
						String stepImageName = imageName + Integer.toString(i);
						writeFile(step.getStep_image_file(), stepImageName);
						recipeData.getCookingStep().get(i)
								.setStep_image("http://i3a201.p.ssafy.io:8080/images/recipe/" + stepImageName + ".jpg");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			recipeDao.reviseCookingsteps(recipe_id, reviseCookSteps);
			recipeDao.addCookingsteps(recipe_id, addCookSteps);
		}
		
		if (recipeData.getIngredients() != null) {
			List<Ingredient> reviseIngres = new LinkedList<>();
			List<Ingredient> addIngres = new LinkedList<>();
			for (int i = 0; i < recipeData.getIngredients().size(); i++) {
				if(recipeData.getIngredients().get(i).getRecipe_ingredient_id() != null) {
					reviseIngres.add(recipeData.getIngredients().get(i));
				} else {
					addIngres.add(recipeData.getIngredients().get(i));
				}
			}
			recipeDao.reviseIngredients(recipe_id, reviseIngres);
			recipeDao.addIngredients(recipe_id, addIngres);
		}
		
		return recipe_id;
	}

	@Override
	public int deleteRecipe(int recipe_id, int uid) {
		return recipeDao.deleteRecipe(recipe_id, uid);
	}

	@Override
	public int deleteComment(int comment_id, int uid) {
		return recipeDao.deleteComment(comment_id, uid);
	}

	@Override
	public int modifyComment(Comment comment, int uid) {
		return recipeDao.modifyComment(comment, uid);
	}

	@Override
	public List<FoodIngredient> getAllIngredients() {
		List<FoodIngredient> ingredientList = new ArrayList<FoodIngredient>();
		ingredientList.addAll(recipeDao.getSmallIngredients());
		ingredientList.addAll(recipeDao.getMediumIngredients());
		ingredientList.addAll(recipeDao.getLargeIngredients());

		return ingredientList;
	}
	
	@Override
	public String[] getSmallIngredients() {
		return recipeDao.getSmallIngredientsArray();
	}
	
	@Override
	public List<Recipe> getRecipes2(Integer p, Integer id, String user, String query, Integer category, Filter filter) {
		if (p == null) {
			p = 0;
		}
		
		List<String> hate_large = null;
		List<String> hate_medium = null;
		List<String> hate_small = null;
		List<String> like_large = null;
		List<String> like_medium = null;
		List<String> like_small = null;

		if(filter.getHate_large() 	!= null) hate_large = Arrays.asList(filter.getHate_large().trim().split(","));
		if(filter.getHate_medium() 	!= null) hate_medium = Arrays.asList(filter.getHate_medium().trim().split(","));
		if(filter.getHate_small() 	!= null) hate_small = Arrays.asList(filter.getHate_small().trim().split(","));
		if(filter.getLike_large() 	!= null) like_large = Arrays.asList(filter.getLike_large().trim().split(","));
		if(filter.getLike_medium()	!= null) like_medium = Arrays.asList(filter.getLike_medium().trim().split(","));
		if(filter.getLike_small() 	!= null) like_small = Arrays.asList(filter.getLike_small().trim().split(","));
		
//		if(filter.getHate_large() 	!= null) System.out.println(hate_large+", "+hate_large.size());
//		if(filter.getHate_medium() != null) System.out.println(hate_medium+", "+hate_medium.size());
//		if(filter.getHate_small() != null) System.out.println(hate_small+", "+hate_small.size());
//		if(filter.getLike_large() != null) System.out.println(like_large+", "+like_large.size());
//		if(filter.getLike_medium() != null) System.out.println(like_medium+", "+like_medium.size());
//		if(filter.getLike_small() != null) System.out.println(like_small+", "+like_small.size());
		return recipeDao.getRecipes2(p, 20, id, user, query, category, 
									hate_large, hate_medium, hate_small, like_large, like_medium, like_small);
	}

	@Override
	public int setLike(int recipe_id, int uid) {
		if(recipeDao.deleteLike(recipe_id, uid) > 0) {
			return 1;
		} else {
			recipeDao.setLike(recipe_id, uid);
			return 0;
		}
	}

	
	
}
