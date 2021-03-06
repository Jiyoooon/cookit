package com.ssafy.cooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cooking.dto.Category;
import com.ssafy.cooking.service.InfoService;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html#/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api")
public class InfoController {

	@Autowired
	private InfoService infoService;
	
    @ApiOperation(value = "카테고리 목록 조회")
   	@GetMapping("category")
   	public ResponseEntity<List<Category>> getCategories() throws Exception {
    	System.out.println("카테고리목록조회");
   		return new ResponseEntity<List<Category>>(infoService.searchCategories(), HttpStatus.OK);
   	}
    
}
