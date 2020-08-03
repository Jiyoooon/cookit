package com.ssafy.cooking.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler{
	
	//token 존재여부, 유효성 체크 exception 처리
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = SecurityException.class)
	public HashMap<String, Object> unAuthorizedExceptionHandler(Exception e){
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("result", "fail");
		map.put("cause", e.getMessage());
		
		return map;
	}
	
//	@ExceptionHandler(Exception.class)
//	protected ResponseEntity<HashMap<String, Object>> defaultException(Exception e, String msg){
//		e.printStackTrace();
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		
//		map.put("result", "fail");
//		map.put("cause", msg);
//		
//		return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.NOT_ACCEPTABLE);
//	}
}
