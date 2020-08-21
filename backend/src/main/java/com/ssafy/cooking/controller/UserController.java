package com.ssafy.cooking.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.cooking.dto.Comment;
import com.ssafy.cooking.dto.EmailConfirm;
import com.ssafy.cooking.dto.Filter;
import com.ssafy.cooking.dto.Login;
import com.ssafy.cooking.dto.SNS;
import com.ssafy.cooking.dto.TempKey;
import com.ssafy.cooking.dto.User;
import com.ssafy.cooking.service.JwtService;
import com.ssafy.cooking.service.UserService;
import com.ssafy.cooking.util.SHA256;

import io.swagger.annotations.ApiOperation;

//http://localhost:8080/swagger-ui.html
//, allowedHeaders = {"Authorization"}, exposedHeaders = {"Authorization", "token"}
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
		
		try {
			if(userService.isDupEmail(email)) {//이미 존재하는 계정
				map.put("result", "fail");
			}else {
				map.put("result", "success");
			}
		}catch(Exception e) {
			e.printStackTrace();
			map.put("reult", "fail");
			map.put("cause", "서버 오류");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
	
	//이메일 인증키 발송
	@ApiOperation(value = "인증키 발송 요청")
	@GetMapping("/verification/send/{email}")
	public ResponseEntity<HashMap<String, Object>> signupSendCheckEmail(@PathVariable("email") String email) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String random = new TempKey().getKey(30, false);  // 인증키 생성

		map.put("result", "fail");
		//email이 중복됐는지 확인
		if(userService.isDupEmail(email)) {
			map.put("cause", "이미 가입한 계정");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}
		
		//인증코드 한번만 보낼 수 있게
		if(userService.isConfirmedEmail(email)) {
			map.put("cause", "인증코드 발송함");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}
		
		userService.removeConfirmCode(email);
		
		String title = "쿠킷 회원가입 인증 코드입니다.";
		String content = "\n\n안녕하세요 회원님, 쿠킷(Cookit)을 찾아주셔서 감사합니다.\n\n 인증코드 : " + random; // 내용
            
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
            messageHelper.setTo(email); // 받는사람 이메일
            messageHelper.setSubject(title); // 메일제목
            messageHelper.setText(content); // 메일 내용
            
            mailSender.send(message);
            userService.addEmailConfirm(email, random);//유효시간 3분
            
        } catch (Exception e) {
            e.printStackTrace();
            map.put("cause", "서버 오류");
            return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.SERVICE_UNAVAILABLE);
        }
		
        map.put("result", "success");
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
	
	//인증키 확인
	@ApiOperation(value = "인증키 확인 요청")
	@PostMapping("/verification/check")
	public ResponseEntity<HashMap<String, Object>> signupVerification(@RequestBody EmailConfirm emailConfirm) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(userService.checkConfirmCode(emailConfirm)) {
			map.put("result", "success");
			userService.removeConfirmCode(emailConfirm.getEmail());
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}else {
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
    	if(user.getIntro() != null && user.getIntro().length() > 100) {
    		map.put("cause", "소개글 글자 수 초과");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	
    	user.setPassword(SHA256.testSHA256(user.getPassword()));
		int uid = userService.signup(user);
		
    	if(uid > 0) {
    		String token = jwtService.create(Integer.toString(uid));
    		map.put("token", token);
    		map.put("result", "success");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
	}

    //profile image 추가 회원가입
	@ApiOperation(value = "회원가입테스트")//, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, headers = "Content-Type= multipart/form-data"
	@PostMapping(value = "/join2")//, consumes = "multipart/form-data"
	public ResponseEntity<HashMap<String, Object>> signupUser2(@RequestPart(required = false, name = "profile") MultipartFile profile
															, @ModelAttribute("user") User user
															, HttpServletResponse response)throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String namePt = "^[a-zA-Z0-9가-힣]{2,12}$";
    	String pwPt = "^[0-9a-zA-Z~`!@#$%\\\\^&*()-]{8,12}$";//특수,대소문자,숫자 포함 8자리 이상
    	String emailPt = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    	
    	map.put("result", "fail");
    	if(user.getNickname() == null || user.getNickname().equals("")) {
    		map.put("cause", "닉네임 입력 필수");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	if(!user.getNickname().matches(namePt)) {
    		map.put("cause", "닉네임 형식 오류");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	
    	if(user.getPassword() == null || user.getPassword().equals("")) {
    		map.put("cause", "비밀번호 입력 필수");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	if(!user.getPassword().matches(pwPt)) {
    		map.put("cause", "비밀번호 형식 오류");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	
    	if(user.getEmail() == null || user.getEmail().equals("")) {
    		map.put("cause", "이메일 입력 필수");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	if(!user.getEmail().matches(emailPt)) {
    		map.put("cause", "이메일 형식 오류");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	
    	if(user.getIntro() != null && user.getIntro().length() > 100) {
    		map.put("cause", "소개글 글자 수 초과");
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
    	if(profile != null) {
    		if(profile.getContentType().indexOf("image") == -1) {
    			map.put("cause", "이미지 파일  형식 오류");
    			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    		}
    	}
    	
<<<<<<< HEAD
    	int uid = userService.signup2(profile, user);
=======
    	response.setHeader("Access-Control-Allow-Headers", "token");//token
    	
    	int uid = userService.signup(profile, user);
>>>>>>> develop
		
    	if(uid > 0) {
    		String token = jwtService.create(Integer.toString(uid));
    		map.put("token", token);////
    		map.put("result", "success");
    		response.addHeader("token", token);
    		
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}else {
    		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    	}
	}
    
	//로그인
    @ApiOperation(value = "로그인")
   	@PostMapping("/login")
   	public ResponseEntity<HashMap<String, Object>> signinUser(@RequestBody Login login
   								, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	HttpStatus status = null;
    	
    	System.out.println("login!!");
//    	response.setHeader("Access-Control-Allow-Origin", "*");
//      response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//      response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "token");//token
    	String email = login.getEmail();
    	String password = login.getPassword();
    	
    	try {
    		User user = userService.signin(email, SHA256.testSHA256(password));
    		if (user == null) {
	    		map.put("result", "fail");
	    		map.put("cause", "db에서 데이터 찾을 수 없음");
			} else {//로그인 성공
				String token = jwtService.create(Integer.toString(user.getUser_id()));
				map.put("result", "success");
				map.put("token", token);
				response.addHeader("token", token);
				
			}
    		status = HttpStatus.ACCEPTED;
    	}catch(RuntimeException e) {
    		map.put("result", "fail");
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    //로그아웃
    @ApiOperation(value = "로그아웃")///token
    @GetMapping("/token/logout")
   	public ResponseEntity<HashMap<String, Object>> signoutUser(HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
<<<<<<< HEAD
    	String token = request.getHeader("Authorization");

    	if(token != null && token.length() > 0) {
			token = token.split(" ")[1];
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
=======
    	String token = request.getHeader("Authorization").split(" ")[1];
		try {
			//access token을 blacklist로
			redisTemplate.opsForValue().set(token, true);
			redisTemplate.expire(token, 365, TimeUnit.DAYS);//1년..
			
			result = "success";
		}catch(Exception e){
>>>>>>> develop
			result = "fail";
			map.put("cause", "서버 오류");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    //회원탈퇴
    @ApiOperation(value = "회원탈퇴")///token
   	@DeleteMapping("/token")
   	public ResponseEntity<HashMap<String, Object>> deleteUser(HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	String token = request.getHeader("Authorization").split(" ")[1];

<<<<<<< HEAD
    	if(token != null && token.length() > 0) {
			token = token.split(" ")[1];
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
=======
		Map<String, Object> claims = jwtService.get(token);
		String uid = (String)claims.get("uid");
		try {
			userService.delete(uid);
			result = "success";
		}catch(Exception e){
>>>>>>> develop
			result = "fail";
			map.put("cause", "서버 오류");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    //회원정보 조회
    @ApiOperation(value = "회원 정보 가져오기")///token
   	@GetMapping("/token")
   	public ResponseEntity<HashMap<String, Object>> getUserInfo(HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	map.put("result", result);
    	
    	String token = request.getHeader("Authorization").split(" ")[1];
    	String baseUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();

<<<<<<< HEAD
    	if(token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				String uid = (String)claims.get("uid");
				try {
					map.put("data", userService.getUser(uid));
					return new ResponseEntity<HashMap<String, Object>>(map, status);
					
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
=======
		Map<String, Object> claims = jwtService.get(token);
		String uid = (String)claims.get("uid");
		try {
			User user = userService.getUser(uid, baseUrl);
			
			user.setPassword("");
			map.put("data", user);
			
			return new ResponseEntity<HashMap<String, Object>>(map, status);
			
		}catch(Exception e){
>>>>>>> develop
			result = "fail";
			map.put("cause", "서버 오류");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
<<<<<<< HEAD
    
    //회원정보 조회
    @ApiOperation(value = "회원 프로필 이미지 가져오기")///token
   	@GetMapping("/image")
   	public ResponseEntity<Resource> getUserImage(HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	Resource rs = null;
    	HttpHeaders header = null;
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	
    	String token = request.getHeader("Authorization");

    	if(token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				String uid = (String)claims.get("uid");
				try {
					result = "success";
					map = userService.getUserResource(uid);
					
					rs = (Resource) map.get("resource");
					header = (HttpHeaders) map.get("header");
					
//					header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+rs.getFilename()+"\"");
//					header.setContentType(MediaType.parseMediaType("octet-stream"));
//					System.out.println(rs);
//					System.out.println(header.getAccessControlAllowOrigin());
					return new ResponseEntity<Resource>(rs, header, status);
					
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
			map.put("cause", "로그인 필요");
		}
		
		map.put("result", result);
		return new ResponseEntity<Resource>(rs, header, status);
   	}
    
  //회원정보 조회
    @ApiOperation(value = "회원정보 가져오기(이미지 바이트로..)")///token MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.APPLICATION_XML_VALUE,
   	@GetMapping(value = "/test")
   	public @ResponseBody User getUser2(HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	String separator = File.separator;
    	String filePath = "C:\\SSAFY\\commonpjt\\profile";
    	User user = null;
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	System.out.println("ddd");
    	String token = request.getHeader("Authorization");

    	if(token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				String uid = (String)claims.get("uid");
				try {
					user = userService.getUser(uid);
					
					System.out.println(user);
					String fileName = user.getProfile_image();
					if(fileName != null && !fileName.equals("")) {
						File file = new File(filePath+separator+fileName);
						byte[] array = Files.readAllBytes(file.toPath());
//						InputStream in = getClass().getResourceAsStream(filePath+separator+fileName);
						
						System.out.println(array); 
						user.setImage(array);
//						user.setImage(IOUtils.toByteArray(in));
//						return array;
					}
//					result = "success";
//					map.put("result", result);
					
					return user;
//					return new ResponseEntity<HashMap<String, Object>>(map, status);
					
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
			map.put("cause", "로그인 필요");
		}
		
		map.put("result", result);
//		return new ResponseEntity<HashMap<String, Object>>(map, status);
		return user;
   	}
=======
   
    //id로 회원정보 조회
    @ApiOperation(value = "id로 회원 정보 가져오기")
   	@GetMapping("/{id}")
   	public ResponseEntity<HashMap<String, Object>> getUserInfoById(HttpServletRequest request, @PathVariable("id") String id) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "fail";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	String baseUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
    	
		try {
			User user = userService.getUser(id, baseUrl);
			if(user == null) {
				map.put("cause", "회원정보 없음");
			}else {
				user.setPassword("");
				result = "success";
				map.put("data", user);
			}
			map.put("result", result);
			return new ResponseEntity<HashMap<String, Object>>(map, status);
			
		}catch(Exception e){
			result = "fail";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			map.put("cause", "서버 오류");
			map.put("result", result);
			return new ResponseEntity<HashMap<String, Object>>(map, status);
		}
			
		
   	}
    
>>>>>>> develop
    //회원정보 수정
    @ApiOperation(value = "회원정보 수정하기")///token
   	@PutMapping("/token")
   	public ResponseEntity<HashMap<String, Object>> reviseUser(@RequestPart(required = false, name = "profile") MultipartFile profile
   															, @ModelAttribute("user") User user
   															, HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	if(profile != null) {
    		if(profile.getContentType().indexOf("image") == -1) {
    			map.put("cause", "이미지 파일  형식 오류");
    			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    		}
    	}
    	
    	String password = user.getPassword();
<<<<<<< HEAD
    	if(password != null && password != "") {//비밀번호 입력했을때만 수정
=======
    	if(password != null && !password.trim().equals("")) {//비밀번호 입력했을때만 수정
>>>>>>> develop
    		user.setPassword(SHA256.testSHA256(password));
    	}
    	
    	String token = request.getHeader("Authorization").split(" ")[1];

<<<<<<< HEAD
    	if(token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				user.setUser_id(Integer.parseInt((String)claims.get("uid")));
				
				try {
					userService.reviseUser(profile, user);
					result = "success";
				}catch(Exception e){
					e.printStackTrace();
					result = "fail";
					map.put("cause", "서버 오류");
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
			}else {
				result = "fail";
				map.put("cause", "토큰 유효하지 않음");
			}
		}else {
=======
		Map<String, Object> claims = jwtService.get(token);
		user.setUser_id(Integer.parseInt((String)claims.get("uid")));
		
		try {
			userService.reviseUser(profile, user);
			result = "success";
		}catch(Exception e){
			e.printStackTrace();
>>>>>>> develop
			result = "fail";
			map.put("cause", "서버 오류");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    @ApiOperation(value = "비밀번호 확인")///token
   	@PostMapping("/token/password")
   	public ResponseEntity<HashMap<String, Object>> checkPassword(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String password = (String)param.get("password");
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	String token = request.getHeader("Authorization").split(" ")[1];

<<<<<<< HEAD
    	if(token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				
				try {
					if(userService.checkPassword((String)claims.get("uid"), SHA256.testSHA256(password))) {
						result = "success";
					}else {
						result = "fail";
						map.put("cause", "비밀번호 오류");
					}
					
				}catch(Exception e){
					result = "fail";
					map.put("cause", "서버 오류");
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}
=======
		Map<String, Object> claims = jwtService.get(token);
		
		try {
			if(userService.checkPassword((String)claims.get("uid"), SHA256.testSHA256(password))) {
				result = "success";
>>>>>>> develop
			}else {
				result = "fail";
				map.put("cause", "비밀번호 오류");
			}
			
		}catch(Exception e){
			result = "fail";
			map.put("cause", "서버 오류");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    @ApiOperation(value = "비밀번호 찾기")
   	@GetMapping("/password/{email}")
   	public ResponseEntity<HashMap<String, Object>> sendEmailForPw(@PathVariable("email") String email, HttpServletRequest request) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(!userService.isDupEmail(email)) {
			map.put("result", "fail");
			map.put("cause", "가입하지 않은 회원");
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		}
		
		String result = "success";
		String tmpPw = new TempKey().getKey(6, false);  // 임시비밀번호
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
		try {
			userService.sendTmpPasswordEmail(tmpPw, email);
			result = "success";
		}catch(Exception e) {
			e.printStackTrace();
            map.put("cause", "서버 오류");
            return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.SERVICE_UNAVAILABLE);
		}
				
        map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    @ApiOperation(value = "비밀번호 수정")///token
   	@PutMapping("/password")
   	public ResponseEntity<HashMap<String, Object>> modifyPassword(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	String password = (String)param.get("password");
    	String result = "success";
    	HttpStatus status = HttpStatus.ACCEPTED;
    	
    	String token = request.getHeader("Authorization");

    	if(token != null && token.length() > 0) {
			token = token.split(" ")[1];
			if(jwtService.checkValid(token)) {//토큰 유효성 체크
				Map<String, Object> claims = jwtService.get(token);
				
				try {
					if(userService.updatePassword((String)claims.get("uid"), SHA256.testSHA256(password))) {
						result = "success";
					}else {
						result = "fail";
						map.put("cause", "db에서 정보를 못 찾음");
					}
					
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
			map.put("cause", "로그인 필요");
		}
		
		map.put("result", result);
		return new ResponseEntity<HashMap<String, Object>>(map, status);
   	}
    
    
    
<<<<<<< HEAD
    //내 필터링 정보 가져오기, 추가하기, 삭제하기
=======
    @ApiOperation(value = "user id로 댓글 가져오기")
   	@GetMapping("/comments/{id}")
   	public ResponseEntity<List<Comment>> getCommnets(@PathVariable("id") String uid) throws Exception {
   		return new ResponseEntity<List<Comment>>(userService.getCommnets(uid), HttpStatus.OK);
   	}
    
    //블로그 전체 조회수 + 1
    @ApiOperation(value = "블로그 조회수 ++", notes = "id번 유저의 전체 블로그 조회수를 1 더한다.")
    @PutMapping("/hits/{id}")
    public ResponseEntity<HashMap<String, Object>> plusBlogHits(HttpServletRequest request, @PathVariable("id") String uid) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	if(userService.plusBlogHits(uid) > 0) {
    		map.put("result", "success");
    	}else map.put("result", "fail");
    	
    	return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
    }
    
    /**필터링 crud*/
    
    //내 필터링 정보 가져오기, 추가하기, 삭제하기
    @ApiOperation(value = "필터링 조회", notes = "내 필터링 정보를 가져온다.")
   	@GetMapping("/token/filtering")
   	public ResponseEntity<List<Filter>> getFilterings(HttpServletRequest request) throws Exception {
    	
    	String token = request.getHeader("Authorization").split(" ")[1];
    	Map<String, Object> claims = jwtService.get(token);
    	
    	
   		return new ResponseEntity<List<Filter>>(userService.getFilterings((String)claims.get("uid")), HttpStatus.OK);
   	}
    
    @ApiOperation(value = "필터링 생성", notes = "새로운 필터링 정보를 저장한다.")
   	@PostMapping("/token/filtering")
   	public ResponseEntity<HashMap<String, Object>> saveFiltering(HttpServletRequest request, Filter filter) throws Exception {
    	String token = request.getHeader("Authorization").split(" ")[1];
    	Map<String, Object> claims = jwtService.get(token);
    	filter.setFiltering_user_id(Integer.parseInt((String)claims.get("uid")));
    	
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	if(userService.addFiltering(filter) > 0) {
    		map.put("result", "success");
    	}else map.put("result", "fail");
    	
   		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
   	}
    
    @ApiOperation(value = "필터링 삭제", notes = "필터링 id로 필터링 정보를 삭제한다.")
   	@DeleteMapping("/token/filtering/{filter_id}")
   	public ResponseEntity<HashMap<String, Object>> deleteFiltering(HttpServletRequest request, @PathVariable("filter_id") String id) throws Exception {
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	if(userService.removeFiltering(id) > 0) {
    		map.put("result", "success");
    	}else map.put("result", "fail");
    	
   		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
   	}
    
    
    
    /**팔로우 정보 crud*/
    //내 팔로워 가져오기
    @ApiOperation(value = "팔로워 유저들 조회", notes = "id번 유저를 팔로우하는 유저들 정보")
 	@GetMapping("/followers/{id}")
 	public ResponseEntity<List<User>> getFollowers(@PathVariable("id") String uid, HttpServletRequest request) throws Exception {
    	String baseUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
 		return new ResponseEntity<List<User>>(userService.getFollowers(uid, baseUrl), HttpStatus.OK);
 	}

    //내 팔로잉 가져오기
    @ApiOperation(value = "팔로잉 유저들 조회", notes = "id번 유저가 팔로잉하는 유저들 정보")
    @GetMapping("/following/{id}")
    public ResponseEntity<List<User>> getFollowings(@PathVariable("id") String uid, HttpServletRequest request) throws Exception {
    	String baseUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
    	return new ResponseEntity<List<User>>(userService.getFollowings(uid, baseUrl), HttpStatus.OK);
    }
    
    //팔로우 하기
    @ApiOperation(value = "팔로우 하기", notes = "내가 id번 유저를 팔로우")
    @PostMapping("/token/follow/{id}")
    public ResponseEntity<HashMap<String, Object>> follow(HttpServletRequest request, @PathVariable("id") String to_user) throws Exception {
    	String token = request.getHeader("Authorization").split(" ")[1];
    	Map<String, Object> claims = jwtService.get(token);
    	
    	String from_user = (String) claims.get("uid");
    	
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	try {
	    	if(userService.follow(from_user, to_user) > 0) {
	    		map.put("result", "success");
	    	}else map.put("result", "fail");
    	}catch(Exception e) {
    		throw new Exception("팔로우 중복 or 없는 유저 팔로우 시도");
    	}
   		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
   	}
    
    
    //팔로우 끊기
    @ApiOperation(value = "언팔로우 하기", notes = "내가 id번 유저를 언팔로우")
    @DeleteMapping("/token/unfollow/{id}")
    public ResponseEntity<HashMap<String, Object>> unfollow(HttpServletRequest request, @PathVariable("id") String to_user) throws Exception {
    	String token = request.getHeader("Authorization").split(" ")[1];
    	Map<String, Object> claims = jwtService.get(token);
    	
    	String from_user = (String) claims.get("uid");
    	
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	if(userService.unfollow(from_user, to_user) > 0) {
    		map.put("result", "success");
    	}else map.put("result", "fail");
    	
   		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
   	}
  
    
    /**SNS계정 crud*/
    
    //sns계정 조회
    @ApiOperation(value = "sns계정 조회", notes = "id번 유저가 연동한 sns계정 정보 조회")
    @GetMapping("/sns/{id}")
    public ResponseEntity<List<SNS>> getLinkedSNS(@PathVariable("id") String uid) throws Exception {
    	return new ResponseEntity<List<SNS>>(userService.getLinkedSNS(uid), HttpStatus.OK);
    }
    
    //sns계정 추가
    @ApiOperation(value = "sns계정 추가", notes = "회원정보에 sns계정 정보를 추가")
    @PostMapping("/token/sns")
    public ResponseEntity<HashMap<String, Object>> saveLinkedSNS(HttpServletRequest request
    														, @RequestParam(value = "sns_name", required = true) String name
    														, @RequestParam(value = "sns_url", required = true) String url) throws Exception {
    	String token = request.getHeader("Authorization").split(" ")[1];
    	Map<String, Object> claims = jwtService.get(token);
    	
    	String uid = (String) claims.get("uid");
    	
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	
    	if(userService.addLinkedSNS(uid, name, url) > 0) {
    		map.put("result", "success");
    	}else map.put("result", "fail");
    	
   		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
   	}
    
//    //sns계정 삭제
//    @ApiOperation(value = "sns계정 삭제", notes = "회원정보에 sns계정 정보를 삭제")
//    @DeleteMapping("/token/sns")
//    public ResponseEntity<HashMap<String, Object>> deleteLinkedSNS(HttpServletRequest request
//    														, @RequestParam(value = "sns_name", required = true) String name) throws Exception {
//    	String token = request.getHeader("Authorization").split(" ")[1];
//    	Map<String, Object> claims = jwtService.get(token);
//    	
//    	String uid = (String) claims.get("uid");
//    	
//    	HashMap<String, Object> map = new HashMap<String, Object>();
//    	
//    	if(userService.removeLinkedSNS(uid, name) > 0) {
//    		map.put("result", "success");
//    	}else map.put("result", "fail");
//    	
//   		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
//   	}
>>>>>>> develop
}