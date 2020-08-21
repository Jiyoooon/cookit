package com.ssafy.cooking.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.EmailConfirm;
import com.ssafy.cooking.dto.Filter;
import com.ssafy.cooking.dto.SNS;
import com.ssafy.cooking.dto.User;

public interface UserService {
	//이메일 중복 체크
	public boolean isDupEmail(String email);
	public boolean isDupNickname(String nickname);
	
	public User signin(String email, String password);
	public int signup(User user);
	public int delete(String uid) throws IOException;
	public int reviseUser(MultipartFile profile, User user) throws IOException;
	public List<User> getFollowers(String uid, String baseUrl);
	public List<User> getFollowings(String uid, String baseUrl);
	public List<Comment> getCommnets(String uid);
	public boolean checkPassword(String uid, String password);
	public void sendTmpPasswordEmail(String password, String email) throws Exception;
	public boolean updatePassword(String string, String password);
	public boolean isConfirmedEmail(String email);
	public int addEmailConfirm(String email, String code);
	public boolean checkConfirmCode(EmailConfirm emailConfirm);
	public void removeConfirmCode(String email);
<<<<<<< HEAD
	public int signup2(MultipartFile profile, User user) throws IOException;
	public HashMap<String, Object> getUserResource(String uid) throws IOException;
	public User getUser(String uid);
=======
	public int signup(MultipartFile profile, User user) throws IOException;
	public User getUser(String uid, String baseUrl);
	public List<Filter> getFilterings(String uid);
	public int removeFiltering(String filterId);
	public int addFiltering(Filter filter);
	public int plusBlogHits(String uid);
	
	public int follow(String from_user, String to_user);
	public int unfollow(String from_user, String to_user);
	
	public List<SNS> getLinkedSNS(String uid);
	public int addLinkedSNS(String uid, String name, String url);
>>>>>>> develop
}
