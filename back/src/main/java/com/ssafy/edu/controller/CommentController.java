package com.ssafy.edu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.CommentDTO;
import com.ssafy.edu.dto.ServerLog;
import com.ssafy.edu.service.ICommentService;
import com.ssafy.edu.service.ILogService;
import com.ssafy.edu.service.JwtService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api")
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	ICommentService commentService;
	
	@Autowired
	ILogService logService;

	@Autowired
	private JwtService jwtService;

	@RequestMapping(value = "/comment/{news_id}", method = RequestMethod.GET)
	public ResponseEntity getComment(@PathVariable int news_id) throws Exception {
		logger.info("CommentController Excute ! getComment :" + news_id + "\t" + new Date());

		List<CommentDTO> comments = commentService.getComment(news_id);

		if (comments == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<CommentDTO>>(comments, HttpStatus.OK);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public void addComment(@RequestBody CommentDTO comment, HttpServletRequest req) throws Exception {
		
		logger.info("CommentController Excute ! addComment :" + comment.toString() + "\t" + new Date());

		Map<String, Object> resultMap = new HashMap<>();

		resultMap = jwtService.get(req.getHeader("login-token"));

		int member_id = (int) resultMap.get("member_id");

		String member_name = (String) resultMap.get("member_name");
	      
        comment.setMember_id(member_id);
        comment.setMember_name(member_name);
        
        if(comment.getMember_id() != 0) {
        	commentService.addComment(comment);
        	ServerLog sl = new ServerLog();
			sl.setMember_id(member_id);
			sl.setLog_content("Comment Added Content : " + comment.getComment_text() + " from " + member_id + " news : " + comment.getNews_id());
			logService.insertLog(sl);
        }else {
        	logger.error("CommentControler Error ! member_id load faild"); 	
        }
	}

	@RequestMapping(value = "/comment/{comment_id}", method = RequestMethod.DELETE)
	public void deleteComment(@PathVariable int comment_id, HttpServletRequest req) throws Exception {
		
		logger.info("CommentController Excute ! deleteComment :" + comment_id + "\t" + new Date());
		
		CommentDTO comment = commentService.getCommentOne(comment_id);
		
		Map<String, Object> resultMap = new HashMap<>();

		resultMap = jwtService.get(req.getHeader("login-token"));

		int member_id = (int) resultMap.get("member_id");
		
		if(member_id != comment.getMember_id()) {
			logger.error("CommentController Error ! deleteComment :" + comment.getComment_id() + "\t" + new Date());
		}else {
			logger.info("CommentController Delete ! comment_id : " + comment.getComment_id() + "\t" + new Date());
			commentService.deleteComment(comment.getComment_id());
			ServerLog sl = new ServerLog();
			sl.setMember_id(member_id);
			sl.setLog_content("Comment Delete Content : " + comment.getComment_text() + " from " + member_id + " news : " + comment.getNews_id());
			logService.insertLog(sl);
		}
	}
}
