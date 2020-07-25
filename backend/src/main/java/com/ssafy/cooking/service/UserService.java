package com.ssafy.cooking.service;

import java.util.List;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.User;

public interface UserService {
	//이메일 중복 체크
	public boolean isDupEmail(String email);
	public boolean isDupNickname(String nickname);
	
	public User signin(String email, String password);
	public int signup(User user);
	public int delete(String uid);
	public User getUser(String uid);
	public int reviseUser(User user);
	public List<User> getFollowers(String uid);
	public List<Comment> getCommnets(String uid);
	public boolean checkPassword(String uid, String password);
	public void modifyPassword(String uid, String password, String email) throws Exception;
	public boolean updatePassword(String string, String password);
}
