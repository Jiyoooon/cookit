package com.ssafy.cooking.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.EmailConfirm;
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
	public void sendTmpPasswordEmail(String password, String email) throws Exception;
	public boolean updatePassword(String string, String password);
	public boolean isConfirmedEmail(String email);
	public int addEmailConfirm(String email, String code);
	public boolean checkConfirmCode(EmailConfirm emailConfirm);
	public void removeConfirmCode(String email);
	public int signup2(MultipartFile profile, User user) throws IOException;
}
