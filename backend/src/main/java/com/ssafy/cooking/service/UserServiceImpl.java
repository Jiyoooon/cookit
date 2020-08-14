package com.ssafy.cooking.service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
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
import com.ssafy.cooking.dto.Filter;
import com.ssafy.cooking.dto.SNS;
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
	@Transactional
	public User getUser(String uid, String baseUrl) {
		User user = userDao.getUser(uid);
		user.setSns_list(userDao.getAllLinkedSNS(uid));
		String fileFullPath = "/var/lib/tomcat8/webapps/images/profile/"+user.getProfile_image();
		
		if(!new File(fileFullPath).exists()) {
			user.setImage_name(null);
			user.setImage_url(baseUrl+"/images/profile/default_image.png");
		}else {
			user.setImage_url(baseUrl+"/images/profile/"+user.getProfile_image());
		}
		
		return user;
	}
	
	@Override
	@Transactional
	public int reviseUser(MultipartFile profile, User user) throws IOException {
		String imageName = user.getImage_name();
		User oriUser = userDao.getUser(Integer.toString(user.getUser_id()));
		
		try {
			//profile != null => 프로필 수정
			if(profile != null) {
				removeProfile(Integer.toString(user.getUser_id()));
				writeProfile(profile, user.getUser_id(), user);
			}
			//user.getImage_name() == null => 프로필 내림
			else if(imageName == null || imageName.trim().equals("")) {
				removeProfile(Integer.toString(user.getUser_id()));
				user.setProfile_image("");
				user.setImage_name("");
			}
			//수정 X
			else{
				user.setProfile_image(oriUser.getProfile_image());
				user.setImage_name(oriUser.getImage_name());
			}
		}catch(IOException e) {
			throw new IOException();
		}
		
		if(userDao.reviseUser(user) > 0) {
			List<SNS> snsList = user.getSns_list();
			userDao.deleteLinkedSNS(Integer.toString(user.getUser_id()), null);
			for(SNS sns : snsList) {
				if(sns.getSns_name() != null && sns.getSns_name() != "") {
					userDao.insertLinkedSNS(Integer.toString(user.getUser_id()), sns.getSns_name(), sns.getSns_url());
				}
			}
			return 1;
		}else return 0;
	}

	@Override
	public List<User> getFollowers(String uid, String baseUrl) {
		List<User> users = userDao.getFollowers(uid);
		for(User user : users) {
			String imageName = user.getProfile_image();
			if(imageName != null && !imageName.equals("")) {
				user.setImage_url(baseUrl+"/images/profile/"+user.getProfile_image());
			}
		}
		return users;
	}

	@Override
	public List<User> getFollowings(String uid, String baseUrl) {
		List<User> users = userDao.getFollowings(uid);
		for(User user : users) {
			String imageName = user.getProfile_image();
			if(imageName != null && !imageName.equals("")) {
				user.setImage_url(baseUrl+"/images/profile/"+user.getProfile_image());
			}
		}
		return users;
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
		
		if(fileName == null || fileName.equals("") || fileName.equals("default_image.png")) {//프로필 이미지 원래 없거나 기본이미지
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
		String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		
		String fileName = "profile_image_"+timestamp+extension;//파일명
		String fileFullPath = filePath+separator+fileName;
		try {
//    			파일 저장
			profile.transferTo(new File(fileFullPath));
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

	@Override
	public List<Filter> getFilterings(String uid) {
		return userDao.getFilterings(uid);
	}

	@Override
	public int removeFiltering(String filterId) {
		return userDao.deleteFiltering(filterId);
	}

	@Override
	public int addFiltering(Filter filter) {
		return userDao.insertFiltering(filter);
	}

	@Override
	public int plusBlogHits(String uid) {
		return userDao.plusBlogHits(uid);
	}

	@Override
	public int follow(String from_user, String to_user) {
		return userDao.follow(from_user, to_user);
	}

	@Override
	public int unfollow(String from_user, String to_user) {
		return userDao.unfollow(from_user, to_user);
	}
	
	@Override
	public List<SNS> getLinkedSNS(String uid) {
		return userDao.getAllLinkedSNS(uid);
	}

	@Override
	public int addLinkedSNS(String uid, String name, String url) {
		return userDao.insertLinkedSNS(uid, name, url);
	}


	@Override
	public int removeLinkedSNS(String uid, String name) {
		return userDao.deleteLinkedSNS(uid, name);
	}
}
