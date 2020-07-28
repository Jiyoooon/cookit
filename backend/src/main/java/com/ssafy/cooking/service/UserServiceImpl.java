package com.ssafy.cooking.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
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
	
	@Override
	@Transactional
	public ResponseEntity<HashMap<String, Object>> getUser(String uid) throws IOException {
		HashMap<String, Object> map = new HashMap<>();
		String separator = File.separator;
		String filePath = "C:\\SSAFY\\commonpjt\\profile";
//		String filePath = "/app/images/profile";
		Resource rs = null;
		
		
		User user = userDao.getUser(uid);
		String oriName = user.getProfile_image();
		System.out.println(user);
		System.out.println(oriName);
		map.put("result", "success");
		map.put("user", user);
		
		if(oriName == null || oriName.equals("")) {//프로필 사진 없으면 user정보만 return
			return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.OK);
		}
		
		String fileName = filePath + separator + user.getProfile_image();
		File target = new File(fileName);
		
		if(target.exists()) {
			try {
				HttpHeaders header = new HttpHeaders();
				String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
				if(mimeType == null) mimeType = "octet-stream";
				
				rs = new UrlResource(target.toURI());
				
				
				header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+rs.getFilename()+"\"");
				header.setContentType(MediaType.parseMediaType(mimeType));
				System.out.println(rs);
				System.out.println(header.getAccessControlAllowOrigin());
				
				
				
				map.put("resource", rs);
				return new ResponseEntity<HashMap<String,Object>>(map, header, HttpStatus.OK);
			}catch(IOException e) {
				e.printStackTrace();
				throw new IOException();
			}
		}
		return new ResponseEntity<HashMap<String,Object>>(map, HttpStatus.OK);
		
	}

	@Override
	@Transactional
	public int reviseUser(MultipartFile profile, User user) throws IOException {
		try {
			//새로 입력받은 프로필 이미지가 비어있으면 서버에서 삭제 후 profile_image = null로 갱신
			if(profile == null) {
				removeProfile(Integer.toString(user.getUser_id()));
				user.setProfile_image("");
			}
			//새로 입력받은 프로필 이미지가 null이 아니면 본래 저장소에 새로 저장
			else {
				writeProfile(profile, user.getUser_id(), user);
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
//		String filePath = "C:\\SSAFY\\commonpjt\\profile";
		String filePath = "/app/images/profile";
		
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
    	String filePath = "/app/images/profile";
		String oriName = profile.getOriginalFilename();
		String extension = oriName.substring(oriName.lastIndexOf("."));//확장자
		
		String fileName = "profile_image_"+userId+extension;//파일명
		String fileFullPath = filePath+separator+fileName;
		try {
//    			파일 저장
			profile.transferTo(new File(fileFullPath));//window
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
    	}
    	
    	user.setPassword(SHA256.testSHA256(user.getPassword()));
    	return userDao.signup(user) > 0 ? nextId : -1;
		
	}

}
