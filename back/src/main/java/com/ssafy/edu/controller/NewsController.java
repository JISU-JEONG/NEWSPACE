package com.ssafy.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value = "/getSamsungNews", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getSamsungNews() throws Exception {
		logger.info("NewsController Excute ! getSamsungNews \t" + new Date());
		
		List<NewsDTO> list = newsService.getSamsungNews();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getLgNews", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getLgNews() throws Exception {
		logger.info("NewsController Excute ! getLgNews \t" + new Date());
		
		List<NewsDTO> list = newsService.getLgNews();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSkNews", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getSkNews() throws Exception {
		logger.info("NewsController Excute ! getSkNews \t" + new Date());
		
		List<NewsDTO> list = newsService.getSkNews();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSamsungRecent", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getSamsungRecent() throws Exception {
		logger.info("NewsController Excute ! getSamsungRecent \t" + new Date());
		
		List<NewsDTO> list = newsService.getSamsungRecent();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getLgRecent", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getLgRecent() throws Exception {
		logger.info("NewsController Excute ! getLgRecent \t" + new Date());
		
		List<NewsDTO> list = newsService.getLgRecent();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSkRecent", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getSkRecent() throws Exception {
		logger.info("NewsController Excute ! getSkRecent \t" + new Date());
		
		List<NewsDTO> list = newsService.getSkRecent();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getNews/{news_id}", method = RequestMethod.GET)
	public ResponseEntity getNews(@PathVariable int news_id) throws Exception {
		logger.info("NewsController Excute ! getNews " + news_id + "\t" + new Date());
		
		NewsDTO news = newsService.getNews(news_id);
		
		if (news == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<NewsDTO>(news, HttpStatus.OK);
	}
	
	
}
