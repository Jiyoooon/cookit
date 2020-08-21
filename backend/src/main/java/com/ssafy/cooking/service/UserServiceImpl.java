package com.ssafy.cooking.service;

import java.io.File;
import java.io.IOException;
<<<<<<< HEAD
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
=======
import java.sql.Timestamp;
>>>>>>> develop
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	public int signup(User user) {
		int userId = userDao.selectNextUserId();
		return userDao.signup(user) > 0 ? userId : -1;
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

//	
//	public Resource loadFileAsResource(String fileName) throws MalformedURLException {
//		Resource rs = null;
//		String filePath = 
//		try {
//			File path = new File(fileName);
//		}catch(MalformedURLException e) {
//			throw new MalformedURLException();
//		}
//		return null;
//	}
	
	
	
	//////////////////////////////////////////////////////
	@Override
	@Transactional
	public User getUser(String uid, String baseUrl) {
		User user = userDao.getUser(uid);
		
		String fileFullPath = "/var/lib/tomcat8/webapps/images/profile/"+user.getProfile_image();
		
		if(!new File(fileFullPath).exists()) {
			user.setImage_name(null);
			user.setImage_url(baseUrl+"/images/profile/default_image.png");
		}else {
			user.setImage_url(baseUrl+"/images/profile/"+user.getProfile_image());
		}
		
		List<SNS> existSNS = userDao.getAllSNS();
		List<SNS> linkedSNS = userDao.getAllLinkedSNS(uid);
		
		int len = linkedSNS.size();
		for(int i = 0, idx = 0; i < existSNS.size() && idx < len; i++) {
			String curr = existSNS.get(i).getSns_name();
			String u = linkedSNS.get(idx).getSns_name();
			
			if(curr.equals(u)) {
				existSNS.get(i).setSns_url(linkedSNS.get(idx++).getSns_url());
			}
		}
		
		user.setSns_list(existSNS);
		return user;
	}
	
<<<<<<< HEAD
	
	@Override
	@Transactional
	public HashMap<String, Object> getUserResource(String uid) throws IOException {
		String separator = File.separator;
		String filePath = "C:\\SSAFY\\commonpjt\\profile";
//		String filePath = "/app/images/profile";
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		
		Resource rs = null;
		HttpHeaders header = new HttpHeaders();
		User user = userDao.getUser(uid);
		String oriName = user.getProfile_image();
		map.put("user", user);
		
		
		//프로필 사진 없으면 user정보만 return
		if(oriName == null || oriName.equals("")) {
			map.put("resource", null);
			return map;
		}
		
		String fileName = filePath + separator + user.getProfile_image();
		File target = new File(fileName);
		
		if(target.exists()) {
			try {
				String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
				if(mimeType == null) mimeType = "octet-stream";
				
				rs = new UrlResource(target.toURI());
				
				header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+rs.getFilename()+"\"");
				header.setContentType(MediaType.parseMediaType(mimeType));
				
				map.put("resource", rs);
				map.put("header", header);
				
				return map;
			}catch(IOException e) {
				e.printStackTrace();
				logger.warn("서버 폴더에서 이미지 호출 실패");
				map.put("resource", null);
				return map;
			}
		}else {
			logger.warn("profile_image값은 있는데 서버 폴더에서 이미지 찾지 못함");
			map.put("resource", null);
			return map;
		}
		
	}

=======
>>>>>>> develop
	@Override
	@Transactional
	public int reviseUser(MultipartFile profile, User user) throws IOException {
		try {
<<<<<<< HEAD
			//새로 입력받은 프로필 이미지가 비어있으면 서버에서 삭제 후 profile_image = null로 갱신
			if(profile == null) {
				removeProfile(Integer.toString(user.getUser_id()));
				user.setProfile_image("");
=======
			//profile != null => 프로필 수정
			if(profile != null) {
				removeProfile(Integer.toString(user.getUser_id()));
				writeProfile(profile, user.getUser_id(), user);
			}
			//user.getImage_name() == null => 프로필 내림
			else if(imageName == null || imageName.trim().equals("")) {
				removeProfile(Integer.toString(user.getUser_id()));
	    		user.setProfile_image("default_image.png");
	    		user.setImage_name("default_image.png");
>>>>>>> develop
			}
			//새로 입력받은 프로필 이미지가 null이 아니면 본래 저장소에 새로 저장
			else {
				writeProfile(profile, user.getUser_id(), user);
			}
		}catch(IOException e) {
			throw new IOException();
		}
		
		if(userDao.reviseUser(user) > 0) {
			List<SNS> snsList = user.getSns_list();
			userDao.deleteLinkedSNS(Integer.toString(user.getUser_id()));
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

	public boolean removeProfile(String uid) throws IOException{
		String separator = File.separator;
		String filePath = "C:\\SSAFY\\commonpjt\\profile";
//		String filePath = "/app/images/profile";
		
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
		String filePath = "C:\\SSAFY\\commonpjt\\profile";
//    	String filePath = "/app/images/profile";
		String oriName = profile.getOriginalFilename();
		String extension = oriName.substring(oriName.lastIndexOf("."));//확장자
		String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		
		String fileName = "profile_image_"+timestamp+extension;//파일명
		String fileFullPath = filePath+separator+fileName;
		try {
//    			파일 저장
			profile.transferTo(new File(fileFullPath));
			user.setProfile_image(fileName);
			
//    			ImageIO.write(profile, extension, new File(fileFullPath));
			
			
//    			Runtime.getRuntime().exec("chmod 777 " + fileFullPath);
//    			File newFile = new File(fileFullPath);
//    			newFile.setExecutable(true, false); 
//    			newFile.setReadable(true, false); 
//    			newFile.setWritable(true, false);
			
//    			new File(fileFullPath).createNewFile();
			return true;
		}catch(IOException e) {
			logger.info("Error save profile file, filepath : "+fileFullPath);
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int signup2(MultipartFile profile, User user) throws IOException {
    	
        if(userDao.isDupEmail(user.getEmail()) > 0) {//중복된 이메일
        	return -1;
        }
//    	File dir = new File(filePath); //파일 저장 경로 확인, 없으면 만든다.
//    	if (!dir.exists()) {
//    		dir.mkdirs();
//    	}
		int nextId = userDao.selectNextUserId();
    	if(profile != null) {
    		try{
    			if(!writeProfile(profile, nextId, user)) {
//    				System.out.println("파일 저장 실패,,");
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
}
