package com.ssafy.cooking.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.EmailConfirm;
import com.ssafy.cooking.dto.User;

public interface UserService {
	//이메일 중복 체크
	public boolean isDupEmail(String email);
	public boolean isDupNickname(String nickname);
	
	public User signin(String email, String password);
	public int delete(String uid) throws IOException;
	public int reviseUser(MultipartFile profile, User user) throws IOException;
	public List<User> getFollowers(String uid);
	public List<Comment> getCommnets(String uid);
	public boolean checkPassword(String uid, String password);
	public void sendTmpPasswordEmail(String password, String email) throws Exception;
	public boolean isConfirmedEmail(String email);
	public int addEmailConfirm(String email, String code);
	public boolean checkConfirmCode(EmailConfirm emailConfirm);
	public void removeConfirmCode(String email);
	public int signup(MultipartFile profile, User user) throws IOException;
	public User getUser(String uid);
}
