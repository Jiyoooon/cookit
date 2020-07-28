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
import com.ssafy.cooking.dto.Ingredient;
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.RecipeDetail;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	private RecipeDao recipeDao;

	@Override
	public List<Recipe> getRecipes(Integer p, Integer id, Integer user, String query, Integer category, String filter) {
		if (p == null) {
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

	@Override
	public int addRecipe(RecipeDetail recipeDetail) {
		String imageName;
		if (recipeDetail.getRecipe().getRecipe_user_name() != null)
			imageName = recipeDetail.getRecipe().getRecipe_user_name() + Long.toString(System.currentTimeMillis());
		else
			imageName = Long.toString(System.currentTimeMillis());

		System.out.println("000000000000");
		System.out.println(recipeDetail.getRecipe().getMain_image_file());
		if (!recipeDetail.getRecipe().getMain_image_file().isEmpty()) {
			System.out.println("cccc");
			try {
				writeFile(recipeDetail.getRecipe().getMain_image_file(), imageName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int recipe_id = recipeDao.addRecipe(recipeDetail.getRecipe());

		System.out.println(imageName);

		if (recipeDetail.getCookingStep() != null) {
			System.out.println("fifi");
			for (int i = 0; i < recipeDetail.getCookingStep().size(); i++) {
				CookingStep step = recipeDetail.getCookingStep().get(i);

				System.out.println(i);
				if (step.getStep_image_file() != null) {
					try {
						System.out.println("fff");
						String stepImageName = imageName + Integer.toString(i);
						writeFile(step.getStep_image_file(), stepImageName);
						recipeDetail.getCookingStep().get(i).setStep_image(stepImageName);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} 
		
		
		
		recipeDao.addCookingsteps(recipe_id, recipeDetail.getCookingStep());
		recipeDao.addIngredients(recipe_id, recipeDetail.getIngredients());

		return 0;
	}

	private boolean writeFile(MultipartFile multipartFile, String saveFileName) throws IOException {
		boolean result = false;
		String saveDir = "C:/SSAFY/image";
		System.out.println(saveFileName);

		byte[] data = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream(saveDir + saveFileName);
		fos.write(data);
		fos.close();

		return result;
	}
}
