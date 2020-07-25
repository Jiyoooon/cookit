package com.ssafy.cooking.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.util.StringUtils;

import com.ssafy.cooking.service.JwtService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {
	Logger logger = LoggerFactory.getLogger("io.ojw.mall.interceptor.JwtInterceptor");
	private static final String TOKEN = "jwt-auth-token";
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader(TOKEN);
		
		logger.debug("JwtInterceptor > preHandle > token: " + token);
		
		if (StringUtils.equals(request.getMethod(), "OPTIONS")) {
			logger.debug("if request options method is options, return true");
			
			return true;
		}
		
		if(token != null && jwtService.checkValid(token)){
			return true;
		}else{
			throw new Exception();
		}
	}
	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		System.out.println(request.getMethod()+" : "+request.getServletPath());
//		//option 요청은 바로 통과
//		if(request.getMethod().equals("OPTION")) {
//			return true;
//		}else {
//			//request의 parameter에서 auth_token으로 넘어온 부분을 찾음
//			//String token = request.getParameter("auth-token");
//			String token = request.getHeader("jwt-auth-token");
//			if(token != null && token.length() > 0) {
//				jwtService.checkValid(token);
//				return true;
//			}else {
//				throw new RuntimeException("인증 토큰이 없습니다.");
//			}
//		}
//	}
	
	
	
}
