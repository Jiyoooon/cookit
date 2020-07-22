package com.ssafy.cooking.controller;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.Filter;
import com.ssafy.cooking.dto.Recipe;

import io.swagger.annotations.ApiOperation;


//http://localhost:8080/swagger-ui.html#/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/recipe")
public class RecipeController {
	private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    
	
	//레시피 전체 목록
    @ApiOperation(value = "모든 레시피 목록 가져오기", notes = "둘러보기 페이지에서 모든 레시피 목록을 가져온다.(최신순)")
   	@GetMapping("all")
   	public ResponseEntity<List<Recipe>> getAllRecipes() throws Exception {
    	"ddd".indexOf(0);
   		return new ResponseEntity<List<Recipe>>(new LinkedList<Recipe>(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "내 레시피 목록 가져오기", notes = "내 블로그 페이지에서 내가 작성한 모든 레시피 목록을 가져온다.(최신순)")
   	@GetMapping("all/{user_id}")
   	public ResponseEntity<List<Recipe>> getMyRecipes(@PathVariable("user_id") int user_id) throws Exception {
   		return new ResponseEntity<List<Recipe>>(new LinkedList<Recipe>(), HttpStatus.OK);
   	}
    
    
    
    //레시피 검색
    @ApiOperation(value = "제목으로 레시피 목록 가져오기", notes = "요리 제목과 필터링 정보로 레시피 목록을 가져온다.\n "
			+ "title  : 요리 제목\n"
			+ "filter : 검색 시 추가한 재료 필터링 정보(대분류, 중분류, 소분류 각각 0개 이상씩 설정 가능하며 띄어쓰기로 구분한 String 형태로 입력)")
	@GetMapping("title")
   	public ResponseEntity<List<Recipe>> getRecipesByName(@RequestBody String title
   														, @RequestBody Filter filter) throws Exception {
   		return new ResponseEntity<List<Recipe>>(new LinkedList<Recipe>(), HttpStatus.OK);
   	}
    
    
    @ApiOperation(value = "카테고리별 레시피 목록 가져오기", notes = "카테고리 id와 필터링 정보로 레시피 목록을 가져온다.\n "
													+ "category  : 카테고리 id\n"
													+ "filter : 검색 시 추가한 재료 필터링 정보(대분류, 중분류, 소분류 각각 0개 이상씩 설정 가능하며 띄어쓰기로 구분한 String 형태로 입력)")
	@GetMapping("category")
	public ResponseEntity<List<Recipe>> getRecipesByCategory(@RequestBody int category_id
															, @RequestBody Filter filter) throws Exception {
	return new ResponseEntity<List<Recipe>>(new LinkedList<Recipe>(), HttpStatus.OK);
	}
    
    
    
    //레시피 상세 정보
    @ApiOperation(value = "id로 레시피 가져오기", notes = "레시피 카드를 눌렀을 때 해당 레시피의 상세 정보를 가져온다.(레시피 정보, 재료 정보, 조리 순서 정보)")
   	@GetMapping("{id}")
   	public ResponseEntity<Recipe> getRecipeById(@PathVariable("id") int recipe_id) throws Exception {
   		return new ResponseEntity<Recipe>(new Recipe(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "레시피 수정하기", notes = "레시피 id값으로 레시피 상세 정보를 수정한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
   	@PutMapping("{id}")
   	public ResponseEntity<Recipe> modifyRecipe(@PathVariable("id") int recipe_id) throws Exception {
   		return new ResponseEntity<Recipe>(new Recipe(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "레시피 삭제하기", notes = "레시피 id값으로 레시피를 삭제한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
   	@DeleteMapping("{id}")
   	public ResponseEntity<Recipe> removeRecipe(@PathVariable("id") int recipe_id) throws Exception {
   		return new ResponseEntity<Recipe>(new Recipe(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "레시피 생성하기", notes = "새로운 레시피를 생성한다.(레시피 정보, 재료 정보, 조리 순서 정보)")
   	@PostMapping("{id}")
   	public ResponseEntity<Recipe> makeRecipe(@RequestBody Recipe recipe) throws Exception {
   		return new ResponseEntity<Recipe>(new Recipe(), HttpStatus.OK);
   	}
    
    
    
    
    //레시피별 댓글 정보
    @ApiOperation(value = "레시피 댓글 가져오기", notes = "레시피 id값으로 레시피 댓글 정보를 가져온다.")
   	@GetMapping("{recipe_id}/comments")
   	public ResponseEntity<List<Comment>> getCommets(@PathVariable("recipe_id") int recipe_id) throws Exception {
   		return new ResponseEntity<List<Comment>>(new LinkedList<Comment>(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "레시피 댓글 수정하기", notes = "댓글 id값으로 댓글 정보를 수정한다.")
   	@PutMapping("{recipe_id}/comments/{comment_id}")
   	public ResponseEntity<Comment> modifyComment(@PathVariable("recipe_id") int recipe_id
   												, @PathVariable("comment_id") int comment_id) throws Exception {
   		return new ResponseEntity<Comment>(new Comment(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "레시피 댓글 삭제하기", notes = "댓글 id값으로 댓글 정보를 삭제한다.")
   	@DeleteMapping("{recipe_id}/comments/{comment_id}")
   	public ResponseEntity<Comment> removeComment(@PathVariable("recipe_id") int recipe_id
												, @PathVariable("comment_id") int comment_id) throws Exception {
   		return new ResponseEntity<Comment>(new Comment(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "레시피 댓글 생성하기", notes = "새로운 댓글을 생성한다.")
   	@PostMapping("{recipe_id}/comments")
   	public ResponseEntity<Comment> removeComment(@PathVariable("recipe_id") int recipe_id
   												, @RequestBody Comment comment) throws Exception {
   		return new ResponseEntity<Comment>(new Comment(), HttpStatus.OK);
   	}
    
    

}