package com.ssafy.edu.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.PostsDTO;
import com.ssafy.edu.service.IPostsService;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class PostsController {
	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
	
	@Autowired
	IPostsService postsService;
	
	@RequestMapping(value = "/getPosts", method = RequestMethod.GET)
	public List<PostsDTO> getPosts() {
		logger.info("PostController Excute ! getPosts \t" + new Date());
		return postsService.getPosts();
	}
	
	@RequestMapping(value = "/getPostById/{post_id}", method = RequestMethod.GET)
	public PostsDTO getPostById(@PathVariable int post_id) {
		logger.info("PostController Excute ! getPostById \t" + new Date());
		return postsService.getPostById(post_id);
	}
}
