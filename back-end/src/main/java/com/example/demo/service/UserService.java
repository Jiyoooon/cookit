package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.Comment;
import com.example.demo.dto.User;

public interface UserService {
	public User signin();
	public User signup();
	public User delete();
	public User getUser();
	public User reviseUser();
	public List<User> getFollowers();
	public List<Comment> getCommnets();
}
