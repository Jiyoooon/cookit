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
	
	public User signin();
	public User delete();
	public User getUser();
	public User reviseUser();
	public List<User> getFollowers();
	public List<Comment> getCommnets();
}
