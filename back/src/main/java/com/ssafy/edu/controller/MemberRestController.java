package com.ssafy.edu.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.help.AdminManageHelp;
import com.ssafy.edu.help.MemberNewsHelp;
import com.ssafy.edu.help.TempKey;
import com.ssafy.edu.service.EmailService;
import com.ssafy.edu.service.ICommentService;
import com.ssafy.edu.service.INewsService;
import com.ssafy.edu.service.JwtService;
import com.ssafy.edu.service.MemberService;

@RestController
public class MemberRestController {
	private static final Logger log = LoggerFactory.getLogger(JwtService.class);

	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${AWS.IP")
	private String aws_ip;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MemberService memberservice;

	@Autowired
	INewsService newsService;

	@Autowired
	ICommentService commentService;

	@Autowired
	private EmailService emailService;

	// 일반 로그인
	@PostMapping("/member/signin")
	public ResponseEntity<Map<String, Object>> signin(@RequestBody Member member, HttpServletResponse res)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		log.info("MemberRestController Excute ! signin : " + member);
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
			resultMap.put("member_name", loginUser.getName());
			resultMap.put("member_keyword", loginUser.getKeyword());
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			log.error("로그인 실패", e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 토큰 정보획득
	@PostMapping("/api/info")
	public ResponseEntity<Map<String, Object>> getInfo(HttpServletRequest req, @RequestBody Member member) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			// 사용자에게 전달할 정보이다.
			// 보너스로 토큰에 담긴 정보도 전달해보자. 서버에서 토큰을 사용하는 방법임을 알 수 있다.
			resultMap.putAll(jwtService.get(req.getHeader("login-token")));
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			log.error("정보조회 실패", e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 아이디 중복체크
	public String search(String email) {
		
		if (memberservice.getEmail(email) == null) {
			return "Notexist";
		} else {
			return "exist";
		}
	}

	// 회원가입
	@PostMapping("/member/signup")
	public int signup(@RequestBody Member member) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		log.info("MemberRestController Excute ! search : " + memberservice.getEmail(member.getEmail()));
		if (search(member.getEmail()).equals("Notexist")) {
			member.setKeyword(member.getInputkeyword().toString());
			String keyword = Arrays.toString(member.getInputkeyword()).replace("[", "").replace("]", "").replace(",",
					"");
			member.setKeyword(keyword);
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			String password = member.getPassword() + salt;
			digest.update(password.getBytes("utf8"));
			String sha256password = String.format("%064x", new BigInteger(1, digest.digest()));
			member.setPassword(sha256password);

			String certifiedkey = new TempKey().getKey(10, false);
			member.setCertifiedkey(certifiedkey);

			log.info("MemberRestController Excute ! signup : " + member);
			memberservice.insertMember(member);
			return 1;
		} else {
			return 0;
		}
	}

	// 소셜로그인 중복체크
	@PostMapping("/member/signupcheck")
	public String socialsignup(@RequestBody Member member) {
		return search(member.getEmail());
	}

	// 소셜로그인 정보 토큰생성
	@PostMapping("/member/socialtoken")
	public ResponseEntity<Map<String, Object>> socialtoken(@RequestBody Member member, HttpServletResponse res) {
		log.info("\"MemberRestController Excute ! socialtoken " + member);

		Map<String, Object> resultMap = new HashMap<>();
		Member loginUser = new Member();
		System.out.println(member);
		if(memberservice.getEmail(member.getEmail()) != null) {
			loginUser = memberservice.getEmail(member.getEmail());
			member.setMember_id((memberservice.getEmail(member.getEmail())).getMember_id());
			member.setCertifiedkey(memberservice.getEmail(member.getEmail()).getCertifiedkey());
		}

		String token = jwtService.create(member);
		res.setHeader("login-token", token);
		resultMap.put("member_name", loginUser.getName());
		resultMap.put("member_keyword", loginUser.getKeyword());
		
		resultMap.put("status", true);
		System.out.println(resultMap);
		HttpStatus status = HttpStatus.ACCEPTED;
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/api/profile")
	public ResponseEntity<MemberNewsHelp> profile(@RequestBody Member member) {
		log.info("MemberRestController Excute ! profile : " + member.getMember_id());

		MemberNewsHelp result = new MemberNewsHelp();

		result.setMember(memberservice.getMember(member.getMember_id()));
		result.setList(newsService.getMeberNews(member.getMember_id()));
		result.setCount(commentService.getCount(member.getMember_id()));
		
//		System.out.println("log test ==============================================");
//		System.out.println(result.getMember());
//		System.out.println(result.getCount());
//		System.out.println(result.getList().toString());

		if (result.getList().size() < 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<MemberNewsHelp>(result, HttpStatus.OK);
	}

	@PostMapping("/api/profileupdate")
	public ResponseEntity<Member> profileupdate(@RequestBody Member member, HttpServletRequest req) {
		log.info("MemberRestController Excute ! profileupdate : " + member);
		Map<String, Object> resultMap = new HashMap<>();

		String keyword = Arrays.toString(member.getInputkeyword()).replace("[", "").replace("]", "").replace(",","");
		try {
			resultMap.putAll(jwtService.get(req.getHeader("login-token")));
		} catch (RuntimeException e) {
			log.error("정보조회 실패", e.getMessage());
			resultMap.put("message", e.getMessage());
		}
		
		member.setMember_id((int)resultMap.get("member_id"));
		member.setKeyword(keyword);
		memberservice.updatemember(member);
		
		Member m = memberservice.getMember((int)resultMap.get("member_id"));
		if(m == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Member>(m, HttpStatus.OK);
	}

	@GetMapping(value = "/api/sendmail")
	public String sendmail(HttpServletRequest req) throws MessagingException {
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.putAll(jwtService.get(req.getHeader("login-token")));
		} catch (RuntimeException e) {
			log.error("정보조회 실패", e.getMessage());
			resultMap.put("message", e.getMessage());
		}

//		System.out.println(resultMap);
		StringBuffer emailcontent = new StringBuffer();
		emailcontent.append("<!DOCTYPE html>");
		emailcontent.append("<html>");
		emailcontent.append("<head>");
		emailcontent.append("</head>");
		emailcontent.append("<body>");
		emailcontent.append("<h1>[New Space 이메일 인증]</h1>");
		emailcontent.append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>");
		emailcontent.append("<a href='http://192.168.31.84:8080" +"/member/");
		emailcontent.append(resultMap.get("member_certifiedkey"));
		emailcontent.append("/" + resultMap.get("member_email"));
		emailcontent.append("'>이메일 인증 확인</a>");
		emailcontent.append("</body>");
		emailcontent.append("</html>");
		emailService.sendMail((String)resultMap.get("member_email"), "[New Space 이메일 인증]", emailcontent.toString());

		return "emailsent";
	}
	
	@PostMapping("/member/adminManage")
	public ResponseEntity<AdminManageHelp> adminManage(HttpServletRequest req) {
		log.info("Administrator Page Access ! " + req.getLocalAddr()+":" + req.getLocalPort() + "\t" + new Date() );

		List<Member> list = null;
		List<Boolean> checkCrawling = null;
		AdminManageHelp amh = new AdminManageHelp();
		
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.putAll(jwtService.get(req.getHeader("login-token")));
		} catch (RuntimeException e) {
			log.error("정보조회 실패", e.getMessage());
			resultMap.put("message", e.getMessage());
		}
		int auth = (int) resultMap.get("auth");
		System.out.println(auth);
		
		if(auth != 1) {
			System.out.println("check error");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}else {
			System.out.println("check");
			list = memberservice.getNormalMember();
			checkCrawling =  newsService.getServerStatus();
			
			amh.setMemberList(list);
			amh.setCheckCrawling(checkCrawling);
			
			return new ResponseEntity<AdminManageHelp>(amh, HttpStatus.OK);
		}
	}
}