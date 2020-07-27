package com.ssafy.cooking.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.cooking.controller.UserController;
import com.ssafy.cooking.dao.UserDao;
import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.EmailConfirm;
import com.ssafy.cooking.dto.User;
import com.ssafy.cooking.util.SHA256;

@Service 
public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDao userDao;
	@Autowired
	private JavaMailSender mailSender;	// 메일 서비스
	
	@Override
	public User signin(String email, String password) {
		return userDao.signin(email, password);
	}

	@Override
	@Transactional
	public int signup(User user) {
		int userId = userDao.selectNextUserId();
		return userDao.signup(user) > 0 ? userId : -1;
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
	public void sendTmpPasswordEmail(String password, String email) throws Exception {
		String title = "요리조리 임시 비밀번호 발급";
		String content = "\n\n안녕하세요 회원님, 임시 비밀번호로 로그인 후 반드시 수정해주세욥!!.\n\n 인증코드 : " + password; // 내용
            
		try{
			userDao.updatePasswordByEmail(email, SHA256.testSHA256(password));
			
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

	@Override
	@Transactional
	public int signup2(MultipartFile profile, User user) throws IOException {
    	String filePath = "C:\\SSAFY\\commonpjt\\profile";
    	int nextId = -1;
//    	File dir = new File(filePath); //파일 저장 경로 확인, 없으면 만든다.
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
        
    	if(profile != null) {
    		String[] filename = profile.getOriginalFilename().split("\\.");
    		String extension = filename[filename.length-1];
    		
    		nextId = userDao.selectNextUserId();
    		String fileName = "profile_image_"+nextId+"."+extension;//파일명
    		String fileFullPath = filePath+"\\"+fileName;
    		try {
    			//파일 저장
    			profile.transferTo(new File(fileName));
    			user.setProfile_image(fileName);
    		}catch(Exception e) {
    			logger.info("Error save profile file, filepath : "+fileFullPath);
    			e.printStackTrace();
    		}
    	}
    	
    	user.setPassword(SHA256.testSHA256(user.getPassword()));
    	return userDao.signup(user) > 0 ? nextId : -1;
		
	}

}
