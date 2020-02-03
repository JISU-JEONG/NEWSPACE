package com.ssafy.edu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.NewsDTO;
import com.ssafy.edu.help.UserKeywordNews;
import com.ssafy.edu.service.INewsService;
import com.ssafy.edu.service.JwtService;
import com.ssafy.edu.service.MemberService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api")
@RestController
public class NewsController {

	private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

	@Autowired
	INewsService newsService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private MemberService memberservice;

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
		logger.info("NewsController Excute ! getNews :" + news_id + "\t" + new Date());

		NewsDTO news = newsService.getNews(news_id);

		if (news == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<NewsDTO>(news, HttpStatus.OK);
	}

	@RequestMapping(value = "/findNews/{search}", method = RequestMethod.GET)
	public ResponseEntity<List<List<NewsDTO>>> findNews(@PathVariable String search) throws Exception {
		logger.info("NewsController Excute ! findNews :" + search + "\t" + new Date());

		List<List<NewsDTO>> list = new ArrayList<>();

		List<NewsDTO> allList = new ArrayList<>();
		List<NewsDTO> samsungList = new ArrayList<>();
		List<NewsDTO> lgList = new ArrayList<>();
		List<NewsDTO> skList = new ArrayList<>();

		String[] str = search.split(" ");
		
		allList = newsService.findNewsAll(str);
		samsungList = newsService.findNewsSamsung(str);
		lgList = newsService.findNewsLg(str);
		skList = newsService.findNewsSk(str);

		list.add(allList);
		list.add(samsungList);
		list.add(lgList);
		list.add(skList);

		if (list == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<List<NewsDTO>>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllNews", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getAllNews() throws Exception {
		logger.info("NewsController Excute ! getAllNews \t" + new Date());

		List<NewsDTO> list = newsService.getAllNews();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllNewsRecent", method = RequestMethod.GET)
	public ResponseEntity<List<NewsDTO>> getAllNewsRecent() throws Exception {
		logger.info("NewsController Excute ! getAllNewsRecent \t" + new Date());

		List<NewsDTO> list = newsService.getAllNewsRecent();
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsDTO>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserKeyword", method = RequestMethod.GET)
	public ResponseEntity<String[]> getUserKeyword() throws Exception{
		logger.info("NewsController Excute ! getUserKeyword \t" + new Date());
		
		String[] keywords = newsService.getUserKeyword();
		
		return new ResponseEntity<String[]>(keywords, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserKeywordNews/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<List<UserKeywordNews>> getUserKeywordNews(@PathVariable String keyword) throws Exception {
		
		logger.info("NewsController Excute ! getUserKeywordNews KEYWORD : " + keyword + "\t" + new Date());

		Map<String, Object> resultMap = new HashMap<>();
		
		List<NewsDTO> news = null;
		
		List<UserKeywordNews> list = null;
		
		news = newsService.getKeywordNews(keyword);
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		for(NewsDTO n : news) {
			if(list.size() != 0) {
				for(int i=0; i<list.size(); i++) {
					UserKeywordNews ukn = list.get(i);
					
				}
			}
			
			System.out.println(n.getDate());
			
			Calendar cal = Calendar.getInstance();
			
			Date newsDate = formatter.parse(n.getDate());
			
			cal.setTime(newsDate);
			
			long diffsec = (now.getTimeInMillis() - cal.getTimeInMillis()) / 1000;
			long diffday = diffsec / (24*60*60);
			System.out.println(diffday);
			
			if(diffday < 7) {
				
			}
			
		}
		
		
		if (news == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserKeywordNews>>(list, HttpStatus.OK);
	}
}
