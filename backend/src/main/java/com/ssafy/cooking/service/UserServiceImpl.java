package com.ssafy.cooking.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cooking.dao.UserDao;
import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.EmailConfirm;
import com.ssafy.cooking.dto.User;
import com.ssafy.cooking.util.SHA256;

@Service 
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	@Autowired
	private JavaMailSender mailSender;	// 메일 서비스
	
	@Override
	public User signin(String email, String password) {
		return userDao.signin(email, password);
	}

	@Override
	public int signup(User user) {
		return userDao.signup(user);
	}

	@Override
	public int delete(String uid) {
		return userDao.delete(uid);
	}

	@Override
	public User getUser(String uid) {
		return userDao.getUser(uid);
	}

	@Override
	public int reviseUser(User user) {
		return userDao.reviseUser(user);
	}

	@Override
	public List<User> getFollowers(String uid) {
		return userDao.getFollowers(uid);
	}

	@Override
	public List<Comment> getCommnets(String uid) {
		return userDao.getCommnets(uid);
	}


	@Override
	public boolean isDupEmail(String email) {
		return userDao.isDupEmail(email) > 0 ? true : false;
	}

	@Override
	public boolean isDupNickname(String nickname) {
		return userDao.isDupNickname(nickname) > 0 ? true : false;
	}

	@Override
	public boolean checkPassword(String uid, String password) {
		return userDao.checkPassword(uid, password) > 0 ? true : false;
		
	}

	@Override
	@Transactional
	public void modifyPassword(String uid, String password, String email) throws Exception {
		String title = "요리조리 임시 비밀번호 발급";
		String content = "\n\n안녕하세요 회원님, 임시 비밀번호로 로그인 후 반드시 수정해주세욥!!.\n\n 인증코드 : " + password; // 내용
            
		try{
			userDao.updatePassword(uid, SHA256.testSHA256(password));
			
			MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(email); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목
            messageHelper.setText(content); // 메일 내용
            
            mailSender.send(message);
//            System.out.println("메시지 보냄");
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		
	}

	@Override
	public boolean updatePassword(String uid, String password) {
		return userDao.updatePassword(uid, password) > 0 ? true : false;
	}

	@Override
	public boolean isConfirmedEmail(String email) {
		return userDao.isConfirmedEmail(email) > 0 ? true : false;
	}

	@Override
	public int addEmailConfirm(String email, String code) {
		return userDao.insertEmailConfirm(email, code);
		
	}

	@Override
	public boolean checkConfirmCode(EmailConfirm emailConfirm) {
		return userDao.checkConfirmCode(emailConfirm) > 0 ? true : false;
	}

	@Override
	public void removeConfirmCode(String email) {
		userDao.deleteConfirmCode(email);
	}

}
