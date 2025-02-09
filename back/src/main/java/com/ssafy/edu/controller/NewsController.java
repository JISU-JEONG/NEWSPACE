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
import com.ssafy.edu.dto.ServerLog;
import com.ssafy.edu.help.MemberNewsHelp;
import com.ssafy.edu.help.NewsInsertHelp;
import com.ssafy.edu.help.NewsKeywordCounter;
import com.ssafy.edu.help.NewsLogHelp;
import com.ssafy.edu.help.NewsStatusHelp;
import com.ssafy.edu.help.SearchChart;
import com.ssafy.edu.help.UserKeywordNews;
import com.ssafy.edu.help.getNewsHelp;
import com.ssafy.edu.service.ILogService;
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
	ILogService logService;

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

	@RequestMapping(value = "/news/{news_id}", method = RequestMethod.GET)
	public ResponseEntity<getNewsHelp> getNews(@PathVariable int news_id, HttpServletRequest req) throws Exception {
		logger.info("NewsController Excute ! getNews :" + news_id + "\t" + new Date());

		Map<String, Object> resultMap = new HashMap<>();
		
		if(req.getHeader("login-token") != null && req.getHeader("login-token").length() > 20) {
			resultMap.putAll(jwtService.get(req.getHeader("login-token")));
			
			int member_id = (int) resultMap.get("member_id");
			
			NewsInsertHelp nih = new NewsInsertHelp();
			
			nih.setMember_id(member_id);
			nih.setNews_id(news_id);
			
			boolean checkMyList = newsService.checkLikeNews(nih);
			
			getNewsHelp news = new getNewsHelp();
			news.setNews(newsService.getNews(news_id));
			
			if(checkMyList) {
				news.setIs_like(true);
			}else {
				news.setIs_like(false);
			}
			
			boolean checkNewsLog = newsService.checkNewsLog(nih);
			
			if(checkNewsLog) {
				newsService.updateNewsLog(nih);
			}else {
				newsService.insertNewsLog(nih);
			}
			
			if (news.getNews() == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<getNewsHelp>(news, HttpStatus.OK);
		}else {
			getNewsHelp news = new getNewsHelp();
			news.setNews(newsService.getNews(news_id));
			news.setIs_like(false);

			if (news.getNews() == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<getNewsHelp>(news, HttpStatus.OK);
		}
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
	public ResponseEntity<String[]> getUserKeyword() throws Exception {
		logger.info("NewsController Excute ! getUserKeyword \t" + new Date());

		String[] keywords = newsService.getUserKeyword();

		return new ResponseEntity<String[]>(keywords, HttpStatus.OK);
	}

	@RequestMapping(value = "/getUserKeywordNews/{keyword}", method = RequestMethod.GET)
	public ResponseEntity<List<UserKeywordNews>> getUserKeywordNews(@PathVariable String keyword) throws Exception {

		logger.info("NewsController Excute ! getUserKeywordNews KEYWORD : " + keyword + "\t" + new Date());

		List<NewsDTO> news = null;
		news = newsService.getKeywordNews(keyword);

		List<UserKeywordNews> list = new ArrayList<>();

		if (news == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();

		for (int i = 0; i < 7; i++) {
			list.add(new UserKeywordNews(df.format(cal.getTime()), new ArrayList<NewsDTO>()));
			cal.add(cal.DATE, -1);
		}

		for (NewsDTO n : news) {
			for (UserKeywordNews ukn : list) {
				if (n.getDate().equals(ukn.getDate())) {
					ukn.getList().add(n);
				}
			}
		}

		return new ResponseEntity<List<UserKeywordNews>>(list, HttpStatus.OK);
	}

	@RequestMapping(value = "/news", method = RequestMethod.POST)
	public boolean pickNews(@RequestBody NewsDTO news, HttpServletRequest req) {
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.putAll(jwtService.get(req.getHeader("login-token")));
		
		NewsInsertHelp nih = new NewsInsertHelp();
		
		int member_id = (int) resultMap.get("member_id");

		nih.setNews_id(news.getNews_id());
		nih.setMember_id(member_id);
		
		if(nih.getNews_id() != 0 && nih.getMember_id() != 0) {
			
			boolean checkMyList = newsService.checkLikeNews(nih);
			
			if(checkMyList) {
				logger.error("NewsRestController Excute ! DELETE NEWS : " + news.getNews_id() + " " + member_id);
				newsService.deleteLikeNews(nih);
				ServerLog sl = new ServerLog();
				sl.setMember_id(member_id);
				sl.setLog_content("News DisLike it " + news.getNews_id() + " from " + member_id);
				logService.insertLog(sl);
				return false;
			}else {
				logger.info("NewsRestController Excute ! INSERT NEWS : " + news.getNews_id() + " " + member_id);
				newsService.addLikeNews(nih);
				ServerLog sl = new ServerLog();
				sl.setMember_id(member_id);
				sl.setLog_content("News Like it " + news.getNews_id() + " from " + member_id);
				logService.insertLog(sl);
				return true;
			}
			
		}else {
			logger.error("NewsRestController Error ! " + news.getNews_id() + " " + member_id);
			return false;
		}
	}
	
	@RequestMapping(value = "/getChartKeyword", method = RequestMethod.GET)
	public ResponseEntity<List<List<NewsKeywordCounter>>> getChartKeyword() throws Exception {
		logger.info("NewsController Excute ! getChartKeyword \t" + new Date());

		List<List<NewsKeywordCounter>> list = newsService.getChartKeyword();
		
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<List<NewsKeywordCounter>>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSearchChartKeyword/{search}", method = RequestMethod.GET)
	public ResponseEntity<List<SearchChart>> getSearchChartKeyword(@PathVariable String search) throws Exception {
		logger.info("NewsController Excute ! getSearchChartKeyword \t" + new Date());

		List<SearchChart> list = newsService.getSearchChartKeyword(search);
		
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<SearchChart>>(list, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getNewsStatus", method = RequestMethod.GET)
	public ResponseEntity<List<NewsStatusHelp>> getNewsStatus() throws Exception {
		logger.info("NewsController Excute ! getNewsStatus \t" + new Date());

		List<NewsStatusHelp> list = newsService.getNewsStatus();
		
		if (list.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<NewsStatusHelp>>(list, HttpStatus.OK);
	}
	
}
