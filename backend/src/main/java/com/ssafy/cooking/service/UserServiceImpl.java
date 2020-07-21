package com.ssafy.cooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cooking.dao.UserDao;
import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public User signin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int signup(User user) {
		return userDao.signup(user);
	}

	@Override
	public User delete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User reviseUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getFollowers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> getCommnets() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean isDupEmail(String email) {
		return userDao.isDupEmail(email) > 0 ? true : false;
	}

	@Override
	public boolean isDupNickname(String nickname) {
		return userDao.isDupNickname(nickname) > 0 ? true : false;
	}

}
