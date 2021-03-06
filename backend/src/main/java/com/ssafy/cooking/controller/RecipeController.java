package com.ssafy.cooking.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.Filter;
<<<<<<< HEAD
=======
import com.ssafy.cooking.dto.FoodIngredient;
>>>>>>> develop
import com.ssafy.cooking.dto.Recipe;
import com.ssafy.cooking.dto.RecipeDetail;
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
<<<<<<< HEAD
	
//	//레시피 전체 목록
//    @ApiOperation(value = "모든 레시피 목록 가져오기", notes = "둘러보기 페이지에서 모든 레시피 목록을 가져온다.(최신순)")
//   	@GetMapping("all")
//   	public ResponseEntity<List<Recipe>> getAllRecipes() throws Exception {
//   		return new ResponseEntity<List<Recipe>>(recipeservice.getAll(), HttpStatus.OK);
//   	}
    
    @ApiOperation(value = "해당 레시피 목록 가져오기", notes = "레시피 목록을 가져온다.(각 항목은 필요시만 입력)"
			+ "p  : 시작 번호\n"
			+ "id : 레시피 아이디\n"
			+ "user : 유저 아이디\n"
			+ "query : 검색어(요리명)\n"
			+ "category : 해당 카테고리 id\n"
			+ "filter : 검색 시 추가한 재료 필터링 정보(대분류, 중분류, 소분류 각각 0개 이상씩 설정 가능하며 띄어쓰기로 구분한 String 형태로 입력)")
   	@GetMapping("/recipes")
   	public ResponseEntity<List<Recipe>> getUserRecipes(@RequestParam(value = "p", required = false) Integer p, 
   			@RequestParam(value = "id", required = false) Integer id, 
   			@RequestParam(value = "user", required = false) Integer user, 
   			@RequestParam(value = "query", required = false) String query, 
   			@RequestParam(value = "category", required = false) Integer category, 
   			@RequestParam(value = "filter", required = false) String filter) throws Exception {
   		return new ResponseEntity<List<Recipe>>(recipeservice.getRecipes(p, id, user, query, category, filter), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "레시피 추가하기", notes = "레시피 추가한다.")
   	@PostMapping("/recipes/save")
   	public ResponseEntity<HashMap<String, Object>> addRecipe(@RequestBody RecipeDetail recipeDetail) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	recipeservice.addRecipe(recipeDetail);
   		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
   	}
    
//    @ApiOperation(value = "해당 레시피 목록 가져오기", notes = "해당 유저 레시피 목록을 가져온다.(최신순)")
//   	@GetMapping("all/{user_id}")
//   	public ResponseEntity<List<Recipe>> getRecipes(@PathVariable("user_id") int user_id) throws Exception {
//   		return new ResponseEntity<List<Recipe>>(new LinkedList<Recipe>(), HttpStatus.OK);
//   	}
//    
    
    
    
    //레시피 검색
//    @ApiOperation(value = "제목으로 레시피 목록 가져오기", notes = "요리 제목과 필터링 정보로 레시피 목록을 가져온다.\n "
//			+ "title  : 요리 제목\n"
//			+ "filter : 검색 시 추가한 재료 필터링 정보(대분류, 중분류, 소분류 각각 0개 이상씩 설정 가능하며 띄어쓰기로 구분한 String 형태로 입력)")
//	@GetMapping("title")
//   	public ResponseEntity<List<Recipe>> getRecipesByName(@RequestBody String title
//   														, @RequestBody Filter filter) throws Exception {
//   		return new ResponseEntity<List<Recipe>>(new LinkedList<Recipe>(), HttpStatus.OK);
//   	}
//    
//    
//    @ApiOperation(value = "카테고리별 레시피 목록 가져오기", notes = "카테고리 id와 필터링 정보로 레시피 목록을 가져온다.\n "
//													+ "category  : 카테고리 id\n"
//													+ "filter : 검색 시 추가한 재료 필터링 정보(대분류, 중분류, 소분류 각각 0개 이상씩 설정 가능하며 띄어쓰기로 구분한 String 형태로 입력)")
//	@GetMapping("category")
//	public ResponseEntity<List<Recipe>> getRecipesByCategory(@RequestBody int category_id
//															, @RequestBody Filter filter) throws Exception {
//	return new ResponseEntity<List<Recipe>>(recipeservice.get, HttpStatus.OK);
//	}
//    
    
    
    //레시피 상세 정보
    @ApiOperation(value = "id로 레시피 가져오기", notes = "레시피 카드를 눌렀을 때 해당 레시피의 상세 정보를 가져온다.(레시피 정보, 재료 정보, 조리 순서 정보)")
   	@GetMapping("{id}")
   	public ResponseEntity<RecipeDetail> getRecipeById(@PathVariable("id") int recipe_id) throws Exception {
    	RecipeDetail recipeDetail = new RecipeDetail();
    	recipeDetail.setRecipe(recipeservice.getRecipes(null, recipe_id, null, null, null, null).get(0));
    	recipeDetail.setIngredients(recipeservice.getIngredients(recipe_id));
    	recipeDetail.setCookingStep(recipeservice.getCookingSteps(recipe_id));
   		return new ResponseEntity<RecipeDetail>(recipeDetail, HttpStatus.OK);
   	}
    
//    @ApiOperation(value = "레시피 수정하기", notes = "레시피 id값으로 레시피 상세 정보를 수정한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
//   	@PutMapping("{id}")
//   	public ResponseEntity<Recipe> modifyRecipe(@PathVariable("id") int recipe_id) throws Exception {
//   		return new ResponseEntity<Recipe>(new Recipe(), HttpStatus.OK);
//   	}
//    
//    @ApiOperation(value = "레시피 삭제하기", notes = "레시피 id값으로 레시피를 삭제한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
//   	@DeleteMapping("{id}")
//   	public ResponseEntity<Recipe> removeRecipe(@PathVariable("id") int recipe_id) throws Exception {
//   		return new ResponseEntity<Recipe>(new Recipe(), HttpStatus.OK);
//   	}
//    
//    @ApiOperation(value = "레시피 생성하기", notes = "새로운 레시피를 생성한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
//   	@PostMapping("{id}")
//   	public ResponseEntity<Recipe> makeRecipe(@RequestBody Recipe recipe) throws Exception {
//   		return new ResponseEntity<Recipe>(new Recipe(), HttpStatus.OK);
//   	}
    
    
    //레시피별 댓글 정보
    @ApiOperation(value = "레시피 댓글 가져오기", notes = "레시피 id값으로 레시피 댓글 정보를 가져온다.")
   	@GetMapping("{recipe_id}/comments")
   	public ResponseEntity<List<Comment>> getCommets(@PathVariable("recipe_id") int recipe_id) throws Exception {
   		return new ResponseEntity<List<Comment>>(recipeservice.getComments(recipe_id), HttpStatus.OK);
   	}
    
//    @ApiOperation(value = "레시피 댓글 수정하기", notes = "댓글 id값으로 댓글 정보를 수정한다.")
//   	@PutMapping("{recipe_id}/comments/{comment_id}")
//   	public ResponseEntity<Comment> modifyComment(@PathVariable("recipe_id") int recipe_id
//   												, @PathVariable("comment_id") int comment_id) throws Exception {
//   		return new ResponseEntity<Comment>(new Comment(), HttpStatus.OK);
//   	}
//    
//    @ApiOperation(value = "레시피 댓글 삭제하기", notes = "댓글 id값으로 댓글 정보를 삭제한다.")
//   	@DeleteMapping("{recipe_id}/comments/{comment_id}")
//   	public ResponseEntity<Comment> removeComment(@PathVariable("recipe_id") int recipe_id
//												, @PathVariable("comment_id") int comment_id) throws Exception {
//   		return new ResponseEntity<Comment>(new Comment(), HttpStatus.OK);
//   	}
//    
    @ApiOperation(value = "레시피 댓글 생성하기", notes = "새로운 댓글을 생성한다.")
   	@PostMapping("{recipe_id}/comments")
   	public ResponseEntity<HashMap<String, Object>> addComment(@PathVariable("recipe_id") int recipe_id
   												, @RequestBody Comment comment) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	if(recipeservice.addCommnet(recipe_id, comment) > 0) {
       		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	} else {
       		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
   	}
=======
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "해당 레시피 목록 가져오기", notes = "레시피 목록을 가져온다.(각 항목은 필요시만 입력)\n" + "p  : 시작 번호\n" + "id : 레시피 아이디\n"
			+ "user : 유저 네임\n" + "query : 검색어(요리명)\n" + "category : 해당 카테고리 id\n" + "order : 1-최신순, 2-조회순, 3-라이크순 \n"
			+ "likeUser : 유저 아아디(int) 해당 유저가 like한 레시피만\n"
			+ "filter : 검색 시 추가한 재료 필터링 정보(대분류, 중분류, 소분류 각각 0개 이상씩 설정 가능하며 띄어쓰기로 구분한 String 형태로 입력)")
	@GetMapping("/recipes")
	public ResponseEntity<List<Recipe>> getUserRecipes(@RequestParam(value = "p", required = false) Integer p,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "order", defaultValue = "1") Integer order,
			@RequestParam(value = "likeUser", required = false) Integer likeUser,
			@RequestParam(value = "filter", required = false) String filter, HttpServletRequest request)
			throws Exception {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		return new ResponseEntity<List<Recipe>>(
				recipeservice.getRecipes(p, id, user, query, category, order, likeUser, filter, baseUrl),
				HttpStatus.OK);
	}

	@ApiOperation(value = "해당 레시피 목록 가져오기2", notes = "레시피 목록을 가져온다.(각 항목은 필요시만 입력)\n" + "p  : 시작 번호\n"
			+ "id : 레시피 아이디\n" + "user : 유저 네임\n" + "query : 검색어(요리명)\n" + "category : 해당 카테고리 id\n"
			+ "order : 1-최신순, 2-조회순, 3-라이크순 \n" + "likeUser : 유저 아아디(int) 해당 유저가 like한 레시피만\n"
			+ "filter : 검색 시 추가한 재료 필터링 정보(대분류, 중분류, 소분류 각각 0개 이상씩 설정 가능하며 띄어쓰기로 구분한 String 형태로 입력)")
	@GetMapping("/recipes2")
	public ResponseEntity<List<Recipe>> getUserRecipes2(@RequestParam(value = "p", required = false) Integer p,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "user", required = false) String user,
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "order", defaultValue = "1") Integer order,
			@RequestParam(value = "likeUser", required = false) Integer likeUser,
			@ModelAttribute(value = "filter") Filter filter, HttpServletRequest request) throws Exception {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//		System.out.println("query = " + query);
//		System.out.println("user = " + user);
		return new ResponseEntity<List<Recipe>>(
				recipeservice.getRecipes2(p, id, user, query, category, order, likeUser, filter, baseUrl),
				HttpStatus.OK);
	}
	
	@GetMapping("/random")
	public ResponseEntity<List<Recipe>> getUserRecipes2(HttpServletRequest request) throws Exception {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//		System.out.println("query = " + query);
//		System.out.println("user = " + user);
		return new ResponseEntity<List<Recipe>>(
				recipeservice.getRandom(baseUrl),
				HttpStatus.OK);
	}

	@ApiOperation(value = "모든 재료 목록 가져오기", notes = "재료 목록을 불러온다")
	@GetMapping("/ingredients")
	public ResponseEntity<List<FoodIngredient>> getIngreidents() throws Exception {

		return new ResponseEntity<List<FoodIngredient>>(recipeservice.getAllIngredients(), HttpStatus.OK);
	}

	@ApiOperation(value = "모든 소분류 재료 배열 가져오기")
	@GetMapping("/ingredients/small")
	public ResponseEntity<String[]> getSmallIngredients() throws Exception {
		return new ResponseEntity<String[]>(recipeservice.getSmallIngredients(), HttpStatus.OK);
	}

	@ApiOperation(value = "레시피 생성하기", notes = "레시피 추가한다.")
	@PostMapping("token/save") //// token
	public ResponseEntity<HashMap<String, Object>> addRecipe(@ModelAttribute("recipeData") RecipeDetail recipeData,
			HttpServletRequest request) throws Exception {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		HashMap<String, Object> map = new HashMap<String, Object>();

		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization").split(" ")[1];

		Map<String, Object> claims = jwtService.get(token);
		int uid = Integer.parseInt((String) claims.get("uid"));
		try {
			if (recipeData == null) {
				result = "fail";
				map.put("cause", "레시피 정보 없음");
			} else if (recipeData.getTitle() == null) {
				map.put("cause", "레시피 title 없음");
				result = "fail";
			} else {
				recipeData.setRecipe_user(uid);
				if (recipeData.getCategory_id() == null || recipeData.getCategory_id() == 0) {
					recipeData.setCategory_id(8);
				}
				map.put("recipe_id", recipeservice.addRecipe(recipeData, baseUrl));
			}

			result = "success";
		} catch (Exception e) {
			result = "fail";
			map.put("cause", "서버 오류");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	@ApiOperation(value = "레시피 생성하기(로그인 필요없는 테스트버전)", notes = "레시피 추가한다.")
	@PostMapping("/save2")
	public ResponseEntity<HashMap<String, Object>> addRecipe2(@ModelAttribute("recipeData") RecipeDetail recipeData,
			HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;
		if (recipeData == null) {
			map.put("cause", "레시피 데이터 없음");
			result = "fail";
		} else if (recipeData.getTitle() == null) {
			map.put("cause", "레시피 title 없음");
			result = "fail";
		} else {
			recipeData.setRecipe_user(3);
			recipeData.setCategory_id(8);
			recipeservice.addRecipe(recipeData, baseUrl);
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	// 레시피 상세 정보
	@ApiOperation(value = "id로 레시피 상세정보 가져오기", notes = "레시피 카드를 눌렀을 때 해당 레시피의 상세 정보를 가져온다.(레시피 정보, 재료 정보, 조리 순서 정보)")
	@GetMapping("{id}")
	public ResponseEntity<RecipeDetail> getRecipeById(@PathVariable("id") int recipe_id, HttpServletRequest request)
			throws Exception {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		List<Recipe> recipes = recipeservice.getRecipes(null, recipe_id, null, null, null, 0, null, null, baseUrl);
		if (recipes.size() > 0) {
			RecipeDetail recipeDetail = new RecipeDetail(recipes.get(0));
			recipeDetail.setIngredients(recipeservice.getIngredients(recipe_id));
			recipeDetail.setCookingStep(recipeservice.getCookingSteps(recipe_id));
//			recipeservice.upHits(recipe_id);
			return new ResponseEntity<RecipeDetail>(recipeDetail, HttpStatus.OK);
		} else {
			return new ResponseEntity<RecipeDetail>(new RecipeDetail(), HttpStatus.OK);
		}
	}
	
	// 레시피 조회수++
	@ApiOperation(value = "id로 레시피 조회수++")
	@GetMapping("hits/{id}")
	public void plusRecipeHits(@PathVariable("id") int recipe_id, HttpServletRequest request)
			throws Exception {
		recipeservice.plusRecipeHit(recipe_id);
	}

	@ApiOperation(value = "레시피 수정하기", notes = "레시피 추가한다.")
	@PutMapping("token/revise") //// token
	public ResponseEntity<HashMap<String, Object>> reviseRecipe(@ModelAttribute("recipeData") RecipeDetail recipeData,
			HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization").split(" ")[1];

		Map<String, Object> claims = jwtService.get(token);
		int uid = Integer.parseInt((String) claims.get("uid"));

		try {
			if (recipeData == null) {
				result = "fail";
				map.put("cause", "레시피 정보 없음");
			} else if (recipeData.getTitle() == null) {
				map.put("cause", "레시피 title 없음");
				result = "fail";
			} else {
				if(recipeservice.reviseRecipe(uid, recipeData, baseUrl) < 1) {
					map.put("cause", "유저 아이디 불일치");
					result = "fail";
				} else {
					result = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
			map.put("cause", "서버 오류");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	@ApiOperation(value = "레시피 삭제하기", notes = "레시피 id값으로 레시피를 삭제한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
	@DeleteMapping("token/{id}") //// token
	public ResponseEntity<HashMap<String, Object>> removeRecipe(@PathVariable("id") int recipe_id,
			HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization").split(" ")[1];

		Map<String, Object> claims = jwtService.get(token);
		int uid = Integer.parseInt((String) claims.get("uid"));
		if (recipeservice.deleteRecipe(recipe_id, uid) > 0) {
			result = "success";
		} else {
			result = "fail";
			map.put("cause", "삭제 실패");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	// 레시피별 댓글 정보
	@ApiOperation(value = "레시피 댓글 가져오기", notes = "레시피 id값으로 레시피 댓글 정보를 가져온다.")
	@GetMapping("{recipe_id}/comments")
	public ResponseEntity<List<Comment>> getCommets(@PathVariable("recipe_id") int recipe_id, HttpServletRequest request) throws Exception {

		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		return new ResponseEntity<List<Comment>>(recipeservice.getComments(recipe_id, baseUrl), HttpStatus.OK);
	}

	@ApiOperation(value = "레시피 댓글 수정하기", notes = "댓글 id값으로 댓글 정보를 수정한다.")
	@PutMapping("token/{recipe_id}/comments/{comment_id}") //// token
	public ResponseEntity<HashMap<String, Object>> modifyComment(@RequestBody Comment comment,
			HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization").split(" ")[1];

		Map<String, Object> claims = jwtService.get(token);
		int uid = Integer.parseInt((String) claims.get("uid"));
		if (recipeservice.modifyComment(comment, uid) > 0) {
			result = "success";
		} else {
			result = "fail";
			map.put("cause", "수정 실패");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	@ApiOperation(value = "레시피 댓글 삭제하기", notes = "댓글 id값으로 댓글 정보를 삭제한다.")
	@DeleteMapping("token/{recipe_id}/comments/{comment_id}") //// token
	public ResponseEntity<HashMap<String, Object>> removeComment(@PathVariable("recipe_id") int recipe_id,
			@PathVariable("comment_id") int comment_id, HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization").split(" ")[1];

		Map<String, Object> claims = jwtService.get(token);
		int uid = Integer.parseInt((String) claims.get("uid"));
		if (recipeservice.deleteComment(comment_id, uid) > 0) {
			result = "success";
		} else {
			result = "fail";
			map.put("cause", "삭제 실패");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	@ApiOperation(value = "레시피 댓글 생성하기", notes = "새로운 댓글을 생성한다.")
	@PostMapping("token/{recipe_id}/comments") //// token
	public ResponseEntity<HashMap<String, Object>> addComment(@PathVariable("recipe_id") int recipe_id,
			@RequestBody Comment comment, HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization").split(" ")[1];

		Map<String, Object> claims = jwtService.get(token);
		int uid = Integer.parseInt((String) claims.get("uid"));
		comment.setComment_user_id(uid);
		if (recipeservice.addCommnet(recipe_id, comment) > 0) {
			result = "success";
		} else {
			result = "fail";
			map.put("cause", "로그인 필요1");
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}

	@ApiOperation(value = "레시피 LIKE", notes = "like")
	@GetMapping("token/{recipe_id}/like") //// token
	public ResponseEntity<HashMap<String, Object>> setLike(@PathVariable("recipe_id") int recipe_id,
			HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String result = "success";
		HttpStatus status = HttpStatus.ACCEPTED;

		String token = request.getHeader("Authorization").split(" ")[1];

		Map<String, Object> claims = jwtService.get(token);
		int uid = Integer.parseInt((String) claims.get("uid"));
		if (recipeservice.setLike(recipe_id, uid) > 0) {
			result = "unlike";
		} else {
			result = "like";
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
	}
>>>>>>> develop
}