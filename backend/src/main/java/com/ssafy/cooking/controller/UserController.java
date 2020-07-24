package com.ssafy.cooking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.ssafy.cooking.service.JwtService;
import com.ssafy.cooking.service.UserService;
import com.ssafy.cooking.util.SHA256;

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
	private JavaMailSender mailSender;	// 메일 서비스
	@Autowired
	private JwtService jwtService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	//이메일 중복 체크 
	@ApiOperation(value = "이메일 중복 체크", notes = "fail : 중복되는 이메일 있음 | success : 중복되는 이메일 없음")
	@GetMapping("/dup/email/{email}")
	public ResponseEntity<HashMap<String, Object>> signupEmailCheck(@PathVariable("email") String email) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(userService.isDupEmail(email)) {//이미 존재하는 계정
			map.put("result", "fail");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}else {
			map.put("result", "success");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}
	}
	
	//이메일 인증
	@ApiOperation(value = "인증키 발송 요청")
	@GetMapping("/verification/send/{email}")
	public ResponseEntity<HashMap<String, Object>> signupSendCheckEmail(@PathVariable("email") String email, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String random = new TempKey().getKey(30, false);  // 인증키 생성
		
		String title = "요리조리 회원가입 인증 코드입니다.";
		String content = "\n\n안녕하세요 회원님, 저희 홈페이지를 찾아주셔서 감사합니다.\n\n 인증코드 : " + random; // 내용
            
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(email); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목
            messageHelper.setText(content); // 메일 내용
            
            mailSender.send(message);

            session.setAttribute("random", random);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", "fail");
            return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.SERVICE_UNAVAILABLE);
        }
		
        map.put("result", "success");
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "인증키 확인 요청")
	@GetMapping("/verification/check/{random}")
	public ResponseEntity<HashMap<String, Object>> signupVerification(@PathVariable("random") String random, HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		
        if(random.equals(session.getAttribute("random"))){
        	map.put("result", "success");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
        } else {
        	map.put("result", "fail");
            return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
        }
        
	}
	
	//닉네임 중복체크
	@ApiOperation(value = "닉네임 중복 체크", notes = "fail : 중복되는 닉네임 있음 | success : 중복되는 닉네임 없음")
	@GetMapping("/dup/nickname/{nickname}")
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
    	
    	String namePt = "^[a-zA-Z0-9가-힣]{4,12}$";
    	String pwPt = "^[0-9a-zA-Z~`!@#$%\\\\^&*()-]{8,12}$";//특수,대소문자,숫자 포함 8자리 이상
    	String emailPt = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    	
    	map.put("result", "fail");
    	if(user.getNickname() == null || user.getNickname() == "") {
    		map.put("cause", "닉네임 입력 필수");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	if(!user.getNickname().matches(namePt)) {
    		map.put("cause", "닉네임 형식 오류");
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
    	if(user.getIntro().length() > 100) {
    		map.put("cause", "소개글 글자 수 초과");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}

    	
    	user.setPassword(SHA256.testSHA256(user.getPassword()));
    	
    	map.put("result", "success");
    	if(userService.signup(user) > 0) {
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
	}
    
    
    @ApiOperation(value = "로그인")
   	@PostMapping("/login")
   	public ResponseEntity<HashMap<String, Object>> signinUser(@RequestBody User cuser
   								, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	HttpStatus status = null;
    	
    	String email = cuser.getEmail();
    	String password = cuser.getPassword();
    	
    	try {
    		User user = userService.signin(email, SHA256.testSHA256(password));
    		if (user == null) {
	    		map.put("result", "fail");
	    		map.put("cause", "db에서 데이터 찾을 수 없음");
			} else {//로그인 성공
				String token = jwtService.create(Integer.toString(user.getUser_id()));
				map.put("result", "success");
				response.setHeader("jwt-auth-token", token);
			}
    		status = HttpStatus.ACCEPTED;
    	}catch(RuntimeException e) {
    		map.put("result", "fail");
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    
    @ApiOperation(value = "로그아웃")///token
    @GetMapping("/logout")
   	public ResponseEntity<HashMap<String, Object>> signoutUser(HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	String token = request.getHeader("jwt-auth-token");
		if(token != null && token.length() > 0) {
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				try {
					//access token을 blacklist로
					redisTemplate.opsForValue().set(token, true);
					redisTemplate.expire(token, 365, TimeUnit.DAYS);//1년..
					
					result = "success";
				}catch(Exception e){
					result = "fail";
					map.put("cause", "서버 오류");
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		}else {
			result = "fail";
			map.put("cause", "토큰 먼저 받아오세요");
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    
    @ApiOperation(value = "회원탈퇴")///token
   	@DeleteMapping()
   	public ResponseEntity<HashMap<String, Object>> deleteUser(HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	String token = request.getHeader("jwt-auth-token");
		if(token != null && token.length() > 0) {
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				String uid = (String)claims.get("uid");
				try {
					userService.delete(uid);
					result = "success";
				}catch(Exception e){
					result = "fail";
					map.put("cause", "서버 오류");
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		}else {
			result = "fail";
			map.put("cause", "토큰 먼저 받아오세요");
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    
    @ApiOperation(value = "회원정보 가져오기")///token
   	@GetMapping()
   	public ResponseEntity<HashMap<String, Object>> getUser(HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	String token = request.getHeader("jwt-auth-token");
		if(token != null && token.length() > 0) {
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				String uid = (String)claims.get("uid");
				try {
					result = "success";
					map.put("data", userService.getUser(uid));
				}catch(Exception e){
					result = "fail";
					map.put("cause", "서버 오류");
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		}else {
			result = "fail";
			map.put("cause", "토큰 먼저 받아오세요");
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}

    
    @ApiOperation(value = "회원정보 수정하기")///token
   	@PutMapping()
   	public ResponseEntity<HashMap<String, Object>> reviseUser(@RequestBody User user, HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	String token = request.getHeader("jwt-auth-token");
		if(token != null && token.length() > 0) {
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				user.setUser_id((int)claims.get("uid"));
				
				try {
					userService.reviseUser(user);
					result = "success";
				}catch(Exception e){
					result = "fail";
					map.put("cause", "서버 오류");
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		}else {
			result = "fail";
			map.put("cause", "토큰 먼저 받아오세요");
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    @ApiOperation(value = "팔로워 가져오기")
   	@GetMapping("/follwers/{id}")
   	public ResponseEntity<List<User>> getFollowers(@PathVariable("id") String uid) throws Exception {
   		return new ResponseEntity<List<User>>(userService.getFollowers(uid), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "내가 쓴 댓글 가져오기")
   	@GetMapping("/comments/{id}")
   	public ResponseEntity<List<Comment>> getCommnets(@PathVariable("id") String uid) throws Exception {
   		return new ResponseEntity<List<Comment>>(userService.getCommnets(uid), HttpStatus.OK);
   	}
    
    
    //내 필터링 정보 가져오기, 추가하기, 삭제하기
}