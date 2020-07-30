package com.ssafy.cooking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.RecipeDetail;
import com.ssafy.cooking.service.JwtService;
import com.ssafy.cooking.service.RecipeService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html#/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/recipe")
public class RecipeController {
	private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

	@Autowired
	private RecipeService recipeservice;
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "해당 레시피 목록 가져오기", notes = "레시피 목록을 가져온다.(각 항목은 필요시만 입력)\n" + "p  : 시작 번호\n" + "id : 레시피 아이디\n"
			+ "user : 유저 아이디\n" + "query : 검색어(요리명)\n" + "category : 해당 카테고리 id\n"
			+ "filter : 검색 시 추가한 재료 필터링 정보(대분류, 중분류, 소분류 각각 0개 이상씩 설정 가능하며 띄어쓰기로 구분한 String 형태로 입력)")
	@GetMapping("/recipes")
	public ResponseEntity<List<Recipe>> getUserRecipes(@RequestParam(value = "p", required = false) Integer p,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "user", required = false) Integer user,
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "filter", required = false) String filter) throws Exception {
		return new ResponseEntity<List<Recipe>>(recipeservice.getRecipes(p, id, user, query, category, filter),
				HttpStatus.OK);
	}

	@ApiOperation(value = "레시피 생성하기", notes = "레시피 추가한다.")
	@PostMapping("/save")
	public ResponseEntity<HashMap<String, Object>> addRecipe(RecipeDetail recipeDetail, HttpServletRequest request)
			throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization");

		if (token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if (jwtService.checkValid(token)) {// 토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				int uid = (int) claims.get("uid");
				try {
					if (recipeDetail.getRecipe() == null) {
						result = "fail";
						map.put("cause", "레시피 정보 없음");
					} else {
						recipeDetail.getRecipe().setRecipe_user(uid);
						if (recipeDetail.getRecipe().getCategory_id() == null) {
							recipeDetail.getRecipe().setCategory_id(8);
						}
						recipeservice.addRecipe(recipeDetail);
					}
					result = "success";
				} catch (Exception e) {
					result = "fail";
					map.put("cause", "서버 오류");
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			} else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		} else {
			result = "fail";
			map.put("cause", "로그인 필요");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	@ApiOperation(value = "레시피 생성하기(로그인 필요없는 테스트버전)", notes = "레시피 추가한다.")
	@PostMapping("/save2")
	public ResponseEntity<HashMap<String, Object>> addRecipe2(RecipeDetail recipeData) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;	

//		System.out.println(recipeData.toString());
		recipeData.getRecipe().setRecipe_user(3);
		if (recipeData.getRecipe().getCategory_id() == null) {
			recipeData.getRecipe().setCategory_id(8);
		}
		recipeservice.addRecipe(recipeData);

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}
	
	@ApiOperation(value = "레시피 생성하기(로그인 필요없는 테스트버전)", notes = "레시피 추가한다.")
	@PostMapping("/save3")
	public ResponseEntity<HashMap<String, Object>> addRecipe3(RecipeDetail recipeData) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;
		
		System.out.println(recipeData.getRecipe());

//		System.out.println(recipeData.toString());
		recipeData.getRecipe().setRecipe_user(3);
		if (recipeData.getRecipe().getCategory_id() == null) {
			recipeData.getRecipe().setCategory_id(8);
		}
		recipeservice.addRecipe(recipeData);

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	// 레시피 상세 정보
	@ApiOperation(value = "id로 레시피 상세정보 가져오기", notes = "레시피 카드를 눌렀을 때 해당 레시피의 상세 정보를 가져온다.(레시피 정보, 재료 정보, 조리 순서 정보)")
	@GetMapping("{id}")
	public ResponseEntity<RecipeDetail> getRecipeById(@PathVariable("id") int recipe_id) throws Exception {
		RecipeDetail recipeDetail = new RecipeDetail();
		recipeDetail.setRecipe(recipeservice.getRecipes(null, recipe_id, null, null, null, null).get(0));
		recipeDetail.setIngredients(recipeservice.getIngredients(recipe_id));
		recipeDetail.setCookingStep(recipeservice.getCookingSteps(recipe_id));
		recipeservice.upHits(recipe_id);
		return new ResponseEntity<RecipeDetail>(recipeDetail, HttpStatus.OK);
	}

//    @ApiOperation(value = "레시피 수정하기", notes = "레시피 id값으로 레시피 상세 정보를 수정한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
//   	@PutMapping("{id}")
//   	public ResponseEntity<Recipe> modifyRecipe(@PathVariable("id") int recipe_id) throws Exception {
//   		return new ResponseEntity<Recipe>(new Recipe(), HttpStatus.OK);
//   	}
//    
	@ApiOperation(value = "레시피 삭제하기", notes = "레시피 id값으로 레시피를 삭제한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
	@DeleteMapping("{id}")
	public ResponseEntity<HashMap<String, Object>> removeRecipe(@PathVariable("id") int recipe_id,
			HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization");

		if (token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if (jwtService.checkValid(token)) {// 토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				int uid = (int) claims.get("uid");
				if (recipeservice.deleteRecipe(recipe_id, uid) > 0) {
					result = "success";
				} else {
					result = "fail";
					map.put("cause", "삭제 실패");
				}
			} else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		} else {
			result = "fail";
			map.put("cause", "로그인 필요");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	// 레시피별 댓글 정보
	@ApiOperation(value = "레시피 댓글 가져오기", notes = "레시피 id값으로 레시피 댓글 정보를 가져온다.")
	@GetMapping("{recipe_id}/comments")
	public ResponseEntity<List<Comment>> getCommets(@PathVariable("recipe_id") int recipe_id) throws Exception {
		return new ResponseEntity<List<Comment>>(recipeservice.getComments(recipe_id), HttpStatus.OK);
	}

	@ApiOperation(value = "레시피 댓글 수정하기", notes = "댓글 id값으로 댓글 정보를 수정한다.")
	@PutMapping("{recipe_id}/comments/{comment_id}")
	public ResponseEntity<HashMap<String, Object>> modifyComment(@RequestBody Comment comment,
			HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization");

		if (token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if (jwtService.checkValid(token)) {// 토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				int uid = (int) claims.get("uid");
				if (recipeservice.modifyComment(comment, uid) > 0) {
					result = "success";
				} else {
					result = "fail";
					map.put("cause", "수정 실패");
				}
			} else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		} else {
			result = "fail";
			map.put("cause", "로그인 필요");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	@ApiOperation(value = "레시피 댓글 삭제하기", notes = "댓글 id값으로 댓글 정보를 삭제한다.")
	@DeleteMapping("{recipe_id}/comments/{comment_id}")
	public ResponseEntity<HashMap<String, Object>> removeComment(@PathVariable("recipe_id") int recipe_id,
			@PathVariable("comment_id") int comment_id, HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization");

		if (token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if (jwtService.checkValid(token)) {// 토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				int uid = (int) claims.get("uid");
				if (recipeservice.deleteComment(comment_id, uid) > 0) {
					result = "success";
				} else {
					result = "fail";
					map.put("cause", "삭제 실패");
				}
			} else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		} else {
			result = "fail";
			map.put("cause", "로그인 필요");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	@ApiOperation(value = "레시피 댓글 생성하기", notes = "새로운 댓글을 생성한다.")
	@PostMapping("{recipe_id}/comments")
	public ResponseEntity<HashMap<String, Object>> addComment(@PathVariable("recipe_id") int recipe_id,
			@RequestBody Comment comment, HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization");

		if (token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if (jwtService.checkValid(token)) {// 토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				int uid = (int) claims.get("uid");
				comment.setComment_user_id(uid);
				if (recipeservice.addCommnet(recipe_id, comment) > 0) {
					result = "success";
				} else {
					result = "fail";
					map.put("cause", "로그인 필요1");
				}
			} else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		} else {
			result = "fail";
			map.put("cause", "로그인 필요");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}
}