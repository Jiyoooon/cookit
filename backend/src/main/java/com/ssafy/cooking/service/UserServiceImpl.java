package com.ssafy.cooking.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.mail.internet.MimeMessage;

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
	public int delete(String uid) throws IOException {
		//이미지 폴더에서 삭제
		try{
			removeProfile(uid);
		}catch(IOException e) {
			throw new IOException();
		}
		return userDao.delete(uid);
	}
	
	
	@Override
	public User getUser(String uid) {
		return userDao.getUser(uid);
	}
	

	@Override
	@Transactional
	public int reviseUser(MultipartFile profile, User user) throws IOException {
		String imageName = user.getImage_name();
		User oriUser = userDao.getUser(Integer.toString(user.getUser_id()));
		
		if(profile == null) System.out.println("프로필 null!!");
		if(imageName != null) {
			System.out.println("이미지 이름 : "+imageName+", "+imageName.equals("null")+", "+imageName.length()+", "+imageName.trim().equals(""));
		}
		
		
		try {
			//profile != null => 프로필 수정
			if(profile != null) {
				System.out.println("프로필 수정!");
				writeProfile(profile, user.getUser_id(), user);
			}
			//user.getImage_name() == null => 프로필 내림
			else if(imageName == null || imageName.trim().equals("")) {
				System.out.println("프로필 내림!");
				removeProfile(Integer.toString(user.getUser_id()));
				user.setProfile_image("default_image.png");
				user.setImage_name("default_image.png");
			}
			//수정 X
			else{
				System.out.println("프로필 수정하지 X");
				user.setProfile_image(oriUser.getProfile_image());
				user.setImage_name(oriUser.getImage_name());
			}
		}catch(IOException e) {
			throw new IOException();
		}
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
		String content = "\n\n안녕하세요 회원님, 임시 비밀번호로 로그인 후 반드시 수정해주세요!!"
						+ "\n\n새 비밀번호 : " + password; // 내용
            
		try{
			userDao.updatePasswordByEmail(email, SHA256.testSHA256(password));
			
			MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(email); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목
            messageHelper.setText(content); // 메일 내용
            
            mailSender.send(message);
		}catch(Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		
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

	public boolean removeProfile(String uid) throws IOException{
		String separator = File.separator;
//		String filePath = "C:\\SSAFY\\commonpjt\\profile";
		String filePath = "/var/lib/tomcat8/webapps/images/profile";
		
		User user = userDao.getUser(uid);
		String fileName = user.getProfile_image();
		
		if(fileName == null || fileName.equals("")) {//프로필 이미지 원래 없었음
			return true;
		}
		
		String fileFullPath = filePath + separator + fileName;
		try {
			File file = new File(fileFullPath);
			if(file.exists()) file.delete();
		
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	public boolean writeProfile(MultipartFile profile, int userId, User user) {
		String separator = File.separator;
//		String filePath = "C:\\SSAFY\\commonpjt\\profile";
    	String filePath = "/var/lib/tomcat8/webapps/images/profile";
		String oriName = profile.getOriginalFilename();
		user.setImage_name(oriName);
		
		String extension = oriName.substring(oriName.lastIndexOf("."));//확장자
		
		String fileName = "profile_image_"+userId+extension;//파일명
		String fileFullPath = filePath+separator+fileName;
		try {
//    			파일 저장
			profile.transferTo(new File(fileFullPath));//window
			user.setProfile_image(fileName);
			
			return true;
		}catch(IOException e) {
			logger.info("Error save profile file, filepath : "+fileFullPath);
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int signup(MultipartFile profile, User user) throws IOException {
    	
        if(userDao.isDupEmail(user.getEmail()) > 0) {//중복된 이메일
        	return -1;
        }
        
		int nextId = userDao.selectNextUserId();
    	if(profile != null) {
    		try{
    			if(!writeProfile(profile, nextId, user)) {
    				throw new IOException();
    			}
    		}catch(IOException e) {
    			e.printStackTrace();
    		}
    	}else {
    		user.setProfile_image("default_image.png");
    		user.setImage_name("default_image.png");
    	}
    	
    	user.setPassword(SHA256.testSHA256(user.getPassword()));
    	return userDao.signup(user) > 0 ? nextId : -1;
		
	}

	

}
