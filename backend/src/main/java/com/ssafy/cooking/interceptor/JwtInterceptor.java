package com.ssafy.cooking.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ssafy.cooking.service.JwtService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LoggerFactory.getLogger("io.ojw.mall.interceptor.JwtInterceptor");
	private static final String TOKEN = HttpHeaders.AUTHORIZATION;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods","*");
	    response.setHeader("Access-Control-Max-Age", "3600");
	    response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
		//option 요청은 바로 통과
//	    System.out.println(request.getMethod()+", "+request.getMethod().equals("OPTIONS"));
//	    System.out.println(request.getParameter("password"));
//	    System.out.println(request.getHeader(HttpHeaders.CONTENT_TYPE));
//	    System.out.println("들어온 ㅋ토큰 : "+request.getHeader(HttpHeaders.AUTHORIZATION));
	    
		if(request.getMethod().equals("OPTIONS")) {
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
