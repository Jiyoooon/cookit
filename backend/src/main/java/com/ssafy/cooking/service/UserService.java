package com.ssafy.cooking.service;

import java.util.List;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.User;

public interface UserService {
	//이메일 중복 체크
	public boolean isDupEmail(String email);
	public boolean isDupNickname(String nickname);
	
	public User signin();
	public int signup(User user);
	public User delete();
	public User getUser();
	public User reviseUser();
	public List<User> getFollowers();
	public List<Comment> getCommnets();
}
