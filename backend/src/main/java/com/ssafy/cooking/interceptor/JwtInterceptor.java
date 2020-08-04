package com.ssafy.cooking.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssafy.cooking.service.JwtService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LoggerFactory.getLogger("io.ojw.mall.interceptor.JwtInterceptor");
	private static final String TOKEN = "Authorization";
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//option 요청은 바로 통과
		if(request.getMethod().equals("OPTION")) {
			return true;
		}else {
			String token = request.getHeader(TOKEN);
	    	if(token != null && token.length() > 0 && token.split(" ").length == 2) {
				token = token.split(" ")[1];
				
				if(!jwtService.checkValid(token)) {//토큰 유효성 체크
					throw new SecurityException("토큰 유효하지 않음");
				}
			}else {
				throw new SecurityException("토큰 존재하지 않음");
			}
		}
		return true;
	}
	
	
	
}
