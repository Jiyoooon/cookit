package com.example.demo.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Category;
import com.example.demo.dto.Filter;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html#/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api")
public class InfoController {

    @ApiOperation(value = "카테고리 목록 조회")
   	@GetMapping("category")
   	public ResponseEntity<List<Category>> getCategories() throws Exception {
   		return new ResponseEntity<List<Category>>(new LinkedList<Category>(), HttpStatus.OK);
   	}
    
    
    //식재료 자동완성
    @ApiOperation(value = "레시피 작성 중 식재료 자동 완성", notes = "유저가 입력한 재료명을 포함한 식재료 검색")
   	@GetMapping("ingredient/{name}")
   	public ResponseEntity<Filter> getIngredient(@PathVariable("name") String name) throws Exception {
   		return new ResponseEntity<Filter>(new Filter(), HttpStatus.OK);
   	}
}
