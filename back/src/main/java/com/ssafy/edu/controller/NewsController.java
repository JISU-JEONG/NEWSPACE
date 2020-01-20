package com.ssafy.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.NewsDTO;
import com.ssafy.edu.service.INewsService;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class NewsController {
	
	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	INewsService newsService;
	
	@RequestMapping(value = "/getNews", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getNews() throws Exception {
		logger.info("NewsController Excute ! getNews \t" + new Date());
		
		List<NewsDTO> list = newsService.getNews();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	

}
