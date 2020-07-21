package com.ssafy.cooking.controller;

import java.util.HashMap;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.TempKey;
import com.ssafy.cooking.dto.User;
import com.ssafy.cooking.service.UserService;

import io.swagger.annotations.ApiOperation;


//http://localhost:8080/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender mailSender;	// 메일 서비스 사용
	
	//이메일 중복 체크
	@ApiOperation(value = "이메일 중복 체크", notes = "fail : 중복되는 이메일 있음 | success : 중복되는 이메일 없음")
	@GetMapping("/mail/{mail}")
	public ResponseEntity<HashMap<String, Object>> signupEmailCheck(@PathVariable("mail") String mail) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(userService.isDupEmail(mail)) {//이미 존재하는 계정
			map.put("result", "fail");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}else {
			map.put("result", "success");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	//이메일 인증
	@ApiOperation(value = "이메일 본인 인증")
	@GetMapping("/verification/{mail}")
	public ResponseEntity<HashMap<String, Object>> signupVerification(@PathVariable("mail") String email) throws Exception {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		String key = new TempKey().getKey(30, false);  // 인증키 생성
		
		String title = "요리조리 회원가입 인증 코드입니다.";
		String content = "\n\n안녕하세요 회원님, 저희 홈페이지를 찾아주셔서 감사합니다.\n\n 인증코드 : " +key; // 내용
            
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                    true, "UTF-8");
 
            messageHelper.setTo(email); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목
            messageHelper.setText(content); // 메일 내용
            
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "fail");
            return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.SERVICE_UNAVAILABLE);
        }
		
        map.put("result", "success");
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
	
	//닉네임 중복체크
	@ApiOperation(value = "닉네임 중복 체크", notes = "fail : 중복되는 닉네임 있음 | success : 중복되는 닉네임 없음")
	@GetMapping("/nickname/{nickname}")
	public ResponseEntity<HashMap<String, Object>> signupNicknameCheck(@PathVariable("nickname") String nickname) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(userService.isDupNickname(nickname)) {//이미 존재하는 닉네임
			map.put("result", "fail");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}else {
			map.put("result", "success");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	//회원가입 요청
    @ApiOperation(value = "회원가입")
	@PostMapping("/join")
	public ResponseEntity<HashMap<String, Object>> signupUser(@RequestBody User user) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String pwPt = "^[0-9a-zA-Z~`!@#$%\\\\^&*()-]{8,12}$";//특수,대소문자,숫자 포함 8자리 이상
    	String emailPt = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    	
    	map.put("result", "fail");
    	if(user.getId() == null || user.getId() == "") {
    		map.put("cause", "아이디 입력 필수");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	if(user.getNickname() == null || user.getNickname() == "") {
    		map.put("cause", "닉네임 입력 필수");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	if(!user.getPassword().matches(pwPt)) {
    		map.put("cause", "비밀번호 형식 오류");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	if(!user.getEmail().matches(emailPt)) {
    		map.put("cause", "이메일 형식 오류");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	
    	map.put("result", "success");
    	if(userService.signup(user) > 0) {
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
	}
    
    @ApiOperation(value = "로그인")
   	@PostMapping("/login")
   	public ResponseEntity<User> signinUser() throws Exception {
   		return new ResponseEntity<User>(userService.signin(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "회원탈퇴")
   	@DeleteMapping("/{id}")
   	public ResponseEntity<User> deleteUser() throws Exception {
   		return new ResponseEntity<User>(userService.delete(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "회원정보 가져오기")
   	@GetMapping("/{id}")
   	public ResponseEntity<User> getUser() throws Exception {
   		return new ResponseEntity<User>(userService.getUser(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "회원정보 수정하기")
   	@PutMapping("/{id}")
   	public ResponseEntity<User> reviseUser() throws Exception {
   		return new ResponseEntity<User>(userService.reviseUser(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "팔로워 가져오기")
   	@GetMapping("/follwers/{id}")
   	public ResponseEntity<List<User>> getFollowers() throws Exception {
   		return new ResponseEntity<List<User>>(userService.getFollowers(), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "내가 쓴 댓글 가져오기")
   	@GetMapping("/comments/{id}")
   	public ResponseEntity<List<Comment>> getCommnets() throws Exception {
   		return new ResponseEntity<List<Comment>>(userService.getCommnets(), HttpStatus.OK);
   	}
    
}