package com.ssafy.cooking.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.cooking.dao.RecipeDao;
import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.CookingStep;
import com.ssafy.cooking.dto.Food_Ingredient;
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.RecipeDetail;
import com.ssafy.cooking.util.Timer;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeDao recipeDao;

	@Override
	public List<Recipe> getRecipes(Integer p, Integer id, String user, String query, Integer category, String filter) {
		int start = 0;
		int end = Integer.MAX_VALUE;
		if (p != null) {
			start = p;
			end = 20;
		}
		return recipeDao.getRecipes(start, end, id, user, query, category, filter);
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
			recipeDetail.setMain_image("http://i3a201.p.ssafy.io:8080/images/recipe/" + imageName + "jpg");
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
								.setStep_image("http://i3a201.p.ssafy.io:8080/images/recipe/" + stepImageName + "jpg");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		if (recipeDetail.getCookingStep() != null)
			recipeDao.addCookingsteps(recipe_id, recipeDetail.getCookingStep());
		if (recipeDetail.getIngredients() != null)
			recipeDao.addIngredients(recipe_id, recipeDetail.getIngredients());

		return recipe_id;
	}

	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {
//		String saveDir = "/var/lib/tomcat8/webapps/images/recipe/";
		String saveDir = "C:/SSAFY/image/";
		FileOutputStream fos = new FileOutputStream(saveDir + saveFileName + ".jpg");
		fos.write(multipartFile.getBytes());
		fos.close();
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
	public List<Food_Ingredient> getAllIngredients() {
		return recipeDao.getAllIngredients();
	}
}
