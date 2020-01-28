package com.ssafy.edu.controller;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.service.JwtService;
import com.ssafy.edu.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberRestController {
	private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    
	@Value("${jwt.salt}")
    private String salt;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MemberService memberservice;

    
    //일반 로그인
    @PostMapping("/member/signin")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody Member member, HttpServletResponse res) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	log.info("=====================signin=====================");
		log.info("로그인 정보 : " + member);
		log.info("================================================");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
//        	SignatureAlgorithm.HS256, salt.getBytes()
        	MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	    digest.reset();
    	    String password = member.getPassword() + salt;
    	    digest.update(password.getBytes("utf8"));
    	    String sha256password = String.format("%064x", new BigInteger(1, digest.digest()));
    	    member.setPassword(sha256password);
			
    	    Member loginUser = memberservice.signin(member);
        	String token = jwtService.create(loginUser);
            
            res.setHeader("login-token", token);
            resultMap.put("status", true);
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            log.error("로그인 실패", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    //토큰 정보획득
    @PostMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest req,
            @RequestBody Member member) {
    	Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            // 사용자에게 전달할 정보이다.
            // 보너스로 토큰에 담긴 정보도 전달해보자. 서버에서 토큰을 사용하는 방법임을 알 수 있다.
            resultMap.putAll(jwtService.get(req.getHeader("login-token")));
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            log.error("정보조회 실패", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
    //아이디 중복체크
    public String search(String email) {
    	if(memberservice.getEmail(email)==null) {
    		log.info("=====================search=====================");
    		log.info("email : " + memberservice.getEmail(email));
    		log.info("================================================");
    		return "Notexist";
    	}
    	else {
    		return "exist";
    	}  
    }
    
    //회원가입
    @PostMapping("/member/signup")
    public int signup(@RequestBody Member member) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	if(search(member.getEmail()).equals("Notexist")) {
    		member.setKeyword(member.getInputkeyword().toString());
    		String keyword = Arrays.toString(member.getInputkeyword()).replace("[", "").replace("]", "").replace(",", " ");
    		member.setKeyword(keyword);
        	MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	    digest.reset();
    	    String password = member.getPassword() + salt;
    	    digest.update(password.getBytes("utf8"));
    	    String sha256password = String.format("%064x", new BigInteger(1, digest.digest()));
    	    member.setPassword(sha256password);
    		
    		log.info("=====================signup=====================");
    		log.info("회원가입 : " + member);
    		log.info("================================================");
    		memberservice.insertMember(member);
    		return 1;
    	}
    	else {
    		return 0;    		
    	}
    }
    
    //소셜로그인 중복체크
    @PostMapping("/member/signupcheck")
    public String socialsignup(@RequestBody Member member) {
		return search(member.getEmail());
    }
    
    //소셜로그인 정보 토큰생성
    @PostMapping("/member/socialtoken")
    public ResponseEntity<Map<String, Object>> socialtoken(@RequestBody Member member,
            HttpServletResponse res) {
		log.info("=====================socialtoken=====================");
		log.info("소셜로그인 정보 : " + member);
		log.info("================================================");

        Map<String, Object> resultMap = new HashMap<>();
        String token = jwtService.create(member);
        res.setHeader("login-token", token);
        resultMap.put("status", true);
        HttpStatus status = HttpStatus.ACCEPTED;
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
    
}