package com.ssafy.cooking.dao;

import java.util.List;

import org.mapstruct.Mapper;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.User;

@Mapper
public interface UserDao {
	public int isDupEmail(String email);
	public int isDupNickname(String nickname);
	public int signup(User user);
	
	public User signin(String email, String password);
	public int delete(String uid);
	public User getUser(String uid);
	public int reviseUser(User user);
	public List<User> getFollowers(String uid);
	public List<Comment> getCommnets(String uid);
	public int checkPassword(String uid, String password);
	public int updatePassword(String uid, String password);
}
