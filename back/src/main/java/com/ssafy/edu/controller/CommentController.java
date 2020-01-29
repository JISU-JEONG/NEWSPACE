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
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.service.ICommentService;
import com.ssafy.edu.service.JwtService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	ICommentService commentService;

	@Autowired
	private JwtService jwtService;

	@RequestMapping(value = "/getComment/{news_id}", method = RequestMethod.GET)
	public ResponseEntity getComment(@PathVariable int news_id) throws Exception {
		logger.info("CommentController Excute ! getComment :" + news_id + "\t" + new Date());

		List<CommentDTO> comments = commentService.getComment(news_id);

		if (comments == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<CommentDTO>>(comments, HttpStatus.OK);
	}

	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public void addComment(@RequestBody CommentDTO comment, HttpServletRequest req) throws Exception {
		
		logger.info("CommentController Excute ! addComment :" + comment.toString() + "\t" + new Date());

		Map<String, Object> resultMap = new HashMap<>();

		resultMap = jwtService.get(req.getHeader("login-token"));

		int member_id = (int) resultMap.get("member_id");

		comment.setMember_id(member_id);

		commentService.addComment(comment);
	}

	@RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
	public void deleteComment(@RequestBody int comment_id, HttpServletRequest req) throws Exception {
		
		logger.info("CommentController Excute ! deleteComment :" + comment_id + "\t" + new Date());

		CommentDTO comment = commentService.getCommentOne(comment_id);
		
		Map<String, Object> resultMap = new HashMap<>();

		resultMap = jwtService.get(req.getHeader("login-token"));

		int member_id = (int) resultMap.get("member_id");
		
		if(member_id != comment.getMember_id()) {
			logger.error("CommentController Error ! deleteComment :" + comment_id + "\t" + new Date());
		}else {
			commentService.deleteComment(comment_id);
		}
	}

}
