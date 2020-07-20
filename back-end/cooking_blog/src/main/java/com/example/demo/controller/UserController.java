package com.example.demo.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Comment;
import com.example.demo.dto.User;
import com.example.demo.service.UserService;

import io.swagger.annotations.ApiOperation;


//http://localhost:8080/swagger-ui.html#/
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/auth")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

    @ApiOperation(value = "회원가입")
	@PostMapping("/new")
	public ResponseEntity<User> signupUser() throws Exception {
		return new ResponseEntity<User>(userService.signup(), HttpStatus.OK);
	}
    
    @ApiOperation(value = "로그인")
   	@PostMapping("/login")
   	public ResponseEntity<User> signinUser() throws Exception {
   		return new ResponseEntity<User>(userService.signin(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "회원탈퇴")
   	@DeleteMapping
   	public ResponseEntity<User> deleteUser() throws Exception {
   		return new ResponseEntity<User>(userService.delete(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "회원정보 가져오기")
   	@GetMapping
   	public ResponseEntity<User> getUser() throws Exception {
   		return new ResponseEntity<User>(userService.getUser(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "회원정보 수정하기")
   	@PostMapping
   	public ResponseEntity<User> reviseUser() throws Exception {
   		return new ResponseEntity<User>(userService.reviseUser(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "팔로워 가져오기")
   	@GetMapping("/follwer")
   	public ResponseEntity<List<User>> getFollowers() throws Exception {
   		return new ResponseEntity<List<User>>(userService.getFollowers(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "내가 쓴 댓글 가져오기")
   	@GetMapping("/comment")
   	public ResponseEntity<List<Comment>> getCommnets() throws Exception {
   		return new ResponseEntity<List<Comment>>(userService.getCommnets(), HttpStatus.OK);
   	}
}