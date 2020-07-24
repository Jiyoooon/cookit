package com.ssafy.cooking.service;

import java.util.Map;

public interface JwtService {
	//로그인 성공 시 사용자 정보를 기반으로 JWTToken 생성해 반환
	public String create(final String uid);
	//클라이언트로부터 전달받은 토큰이 재대로 생성된것인지 확인하고, 문제있으면 Runtime 에러 
	public boolean checkValid(final String jwt);
	//jwt토큰을 분석해 필요한 정보를 반환
	public Map<String, Object> get(final String jwt);
}
