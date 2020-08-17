package com.ssafy.cooking.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
	public List<Recipe> getRecipes(Integer p, Integer id, String user, String query, Integer category, Integer order, Integer likeUser, String filter, String baseUrl) {
		int start = 0;
		int end = Integer.MAX_VALUE;
		if (p != null) {
			start = p;
			end = 20;
		}
		
		String[] q = null;
		if(query != null)
			q = query.split("#|@|&| |-|/|!|%|^|(|)|_|=|~|`");
		
		List<Recipe> recipes = recipeDao.getRecipes(start, end, id, user, q, category, order, likeUser, filter);
		for (Recipe r : recipes) {
			if(r.getRecipe_user_profileImage() == null || r.getRecipe_user_profileImage() == "" 
					|| (!new File("/var/lib/tomcat8/webapps/images/profile/"+r.getRecipe_user_profileImage()).exists())) {
				r.setRecipe_user_profileImage(baseUrl + "/images/profile/default_image.png");
			}
			else {
				r.setRecipe_user_profileImage(baseUrl + "/images/profile/"+r.getRecipe_user_profileImage());
			}
			r.setLike(recipeDao.getLikeList(r.getRecipe_id()));
			r.setTag(r.getTagString().split(","));
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
		List<CookingStep> cookingSteps = recipeDao.getCookingSteps(recipe_id);
		for (CookingStep cs : cookingSteps) {
			if(cs.getDescription() != null) {
				cs.setTime(Timer.getTimer(cs.getDescription()));
			}
		}
		return cookingSteps;
	}

	@Override
	public List<Comment> getComments(int recipe_id, String baseUrl) {
		List<Comment> comments = recipeDao.getCommnets(recipe_id);
		for (Comment r : comments) {
			if (r.getComment_user_profileImage() == null || r.getComment_user_profileImage() == ""
					|| (!new File("/var/lib/tomcat8/webapps/images/profile/" + r.getComment_user_profileImage())
							.exists())) {
				r.setComment_user_profileImage(baseUrl + "/images/profile/default_image.png");
			} else {
				r.setComment_user_profileImage(baseUrl + "/images/profile/" + r.getComment_user_profileImage());
			}
		}
		return comments;
	}

	@Override
	public int addCommnet(int recipe_id, Comment comment) {
		return recipeDao.addCommnet(recipe_id, comment);
	}

	@Override
	public int addRecipe(RecipeDetail recipeDetail, String baseUrl) {
		String imageName;
		if (recipeDetail.getRecipe_user() != null && recipeDetail.getRecipe_user() > 0)
			imageName = recipeDetail.getRecipe_user() + Long.toString(System.currentTimeMillis());
		else
			imageName = Long.toString(System.currentTimeMillis());
		
		if(recipeDetail.getCooking_time() == null || recipeDetail.getCooking_time() == 0)
			recipeDetail.setCooking_time(1);
		if(recipeDetail.getLevel() == null || recipeDetail.getLevel() == 0)
			recipeDetail.setLevel(1);
		if(recipeDetail.getServings() == null || recipeDetail.getServings() == 0)
			recipeDetail.setServings(1);

		if (recipeDetail.getCategory_id() == null || recipeDetail.getCategory_id() == 0)
			recipeDetail.setCategory_id(8);
		
		recipeDetail.setTagString(Arrays.toString(recipeDetail.getTag()).substring(1, Arrays.toString(recipeDetail.getTag()).length()-1));
		
		if (recipeDetail.getMain_image_file() != null && !recipeDetail.getMain_image_file().isEmpty()) {
			try {
				writeFile(recipeDetail.getMain_image_file(), imageName, baseUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
			recipeDetail.setMain_image(baseUrl + "/images/recipe/" + imageName + ".jpg");
		} else {
			recipeDetail.setMain_image(baseUrl + "/images/recipe/default.jpg");
		}

		recipeDao.addRecipe(recipeDetail);
		int recipe_id = recipeDetail.getRecipe_id();

		if (recipeDetail.getCookingStep() != null) {
			for (int i = 0; i < recipeDetail.getCookingStep().size(); i++) {
				CookingStep step = recipeDetail.getCookingStep().get(i);
				
				if (step.getStep_image_file() != null) {
					try {
						String stepImageName = imageName + Integer.toString(i);
						writeFile(step.getStep_image_file(), stepImageName, baseUrl);
						recipeDetail.getCookingStep().get(i)
								.setStep_image(baseUrl + "/images/recipe/" + stepImageName + ".jpg");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			recipeDao.addCookingsteps(recipe_id, recipeDetail.getCookingStep());
		}
		
		if (recipeDetail.getIngredients() != null) {
			recipeDao.checkIngredients(recipe_id, recipeDetail.getIngredients());
			recipeDao.addIngredients(recipe_id, recipeDetail.getIngredients());
		}

		return recipe_id;
	}

	private void writeFile(MultipartFile multipartFile, String saveFileName, String baseUrl) throws IOException {
		String saveDir = "/var/lib/tomcat8/webapps/images/recipe/";
		FileOutputStream fos = new FileOutputStream(saveDir + saveFileName + ".jpg");
		fos.write(multipartFile.getBytes());
		fos.close();
	}
	
	@Override
	public int reviseRecipe(Integer uid, RecipeDetail recipeData, String baseUrl) {
		String imageName;
		if (recipeData.getRecipe_user() != null && recipeData.getRecipe_user() > 0)
			imageName = recipeData.getRecipe_user() + Long.toString(System.currentTimeMillis());
		else
			imageName = Long.toString(System.currentTimeMillis());
		
		if (recipeData.getMain_image_file() != null && !recipeData.getMain_image_file().isEmpty()) {
			try {
				writeFile(recipeData.getMain_image_file(), imageName, baseUrl);
			} catch (IOException e) {
				e.printStackTrace();
			}
			recipeData.setMain_image(baseUrl + "/images/recipe/" + imageName + ".jpg");
		}
		
		recipeData.setTagString(Arrays.toString(recipeData.getTag()).substring(1, Arrays.toString(recipeData.getTag()).length()-1));
		
		int result = recipeDao.reviseRecipe(uid, recipeData);
		if(result > 0) {
			int recipe_id = recipeData.getRecipe_id();
			
			if (recipeData.getCookingStep() != null) {
				for (int i = 0; i < recipeData.getCookingStep().size(); i++) {
					CookingStep step = recipeData.getCookingStep().get(i);

					if (step.getStep_image_file() != null) {
						try {
							String stepImageName = imageName + Integer.toString(i);
							writeFile(step.getStep_image_file(), stepImageName, baseUrl);
							recipeData.getCookingStep().get(i)
									.setStep_image(baseUrl + "/images/recipe/" + stepImageName + ".jpg");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				for (CookingStep s : recipeData.getCookingStep()) {
					recipeDao.reviseCookingsteps(recipe_id, s);
				}
				recipeDao.deleteCookingSteps(recipe_id, recipeData.getCookingStep().size());
				
			}
			
			if (recipeData.getIngredients() != null) {
				recipeDao.deleteIngredients(recipe_id);
				recipeDao.addIngredients(recipe_id, recipeData.getIngredients());
			}
		}
		
		return result;
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
	public List<Recipe> getRecipes2(Integer p, Integer id, String user, String query, Integer category, Integer order, Integer likeUser, Filter filter, String baseUrl) {
		int start = 0;
		int end = Integer.MAX_VALUE;
		if (p != null) {
			start = p;
			end = 20;
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
		
		String[] q = null;
		if(query != null)
			q = query.split("#|@|&| |-|/|!|%|^|(|)|_|=|~|`");
		
		List<Recipe> recipes = recipeDao.getRecipes2(start, end, id, user, q, category, order, likeUser,
				hate_large, hate_medium, hate_small, like_large, like_medium, like_small);
		for (Recipe r : recipes) {
			if(r.getRecipe_user_profileImage() == null || r.getRecipe_user_profileImage() == "" 
					|| (!new File("/var/lib/tomcat8/webapps/images/profile/"+r.getRecipe_user_profileImage()).exists())) {
				r.setRecipe_user_profileImage(baseUrl + "/images/profile/default_image.png");
			}
			else {
				r.setRecipe_user_profileImage(baseUrl + "/images/profile/"+r.getRecipe_user_profileImage());
			}
			r.setLike(recipeDao.getLikeList(r.getRecipe_id()));
			r.setTag(r.getTagString().split(","));
		}
		return recipes;
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
