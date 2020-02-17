package com.ssafy.edu.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.cache.decorators.ScheduledCache;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.MemberDao;
import com.ssafy.edu.dao.NewsServiceDao;
import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.NewsDTO;
import com.ssafy.edu.help.NewsInsertHelp;
import com.ssafy.edu.help.NewsKeyword;
import com.ssafy.edu.help.NewsKeywordCounter;
import com.ssafy.edu.help.NewsStatusHelp;
import com.ssafy.edu.help.SearchChart;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;

@Service
public class NewsService implements INewsService {

	@Autowired
	private NewsServiceDao dao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private EmailService emailService;

	private WebDriver driver;
	private WebElement webElement;
	private List<WebElement> webElements;

	// Properties
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "lib/selenium/chromedriver.exe"; // 윈도우 서버
//	public static final String WEB_DRIVER_PATH = "/usr/bin/chromedriver";	//AWS

	private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

	@Override
	public NewsDTO getNews(int news_id) {
		NewsDTO news = null;
		news = dao.getNews(news_id);

		if (news == null) {
			return news;
		} else {
			NewsKeyword keyword = dao.newsKeywordValid(news_id);
			news.setKeyword(keyword.getKeyword());
		}

		return news;
	}

	@Override
	public NewsDTO getNewsOne(String url) {
		return dao.getNewsOne(url);
	}

	@Override
	public void addNews(NewsDTO news) {
		dao.addNews(news);
	}

	@Override
	public List<NewsDTO> getSamsungNews() {
		// TODO Auto-generated method stub
		return dao.getSamsungNews();
	}

	@Override
	public List<NewsDTO> getLgNews() {
		// TODO Auto-generated method stub
		return dao.getLgNews();
	}

	@Override
	public List<NewsDTO> getSkNews() {
		// TODO Auto-generated method stub
		return dao.getSkNews();
	}

	@Override
	public List<NewsDTO> getSamsungRecent() {
		// TODO Auto-generated method stub
		List<NewsDTO> list = dao.getSamsungRecent();

		for (int i = 0; i < list.size(); i++) {
			NewsKeyword newsKeyword = dao.newsKeywordValid(list.get(i).getNews_id());
			list.get(i).setKeyword(newsKeyword.getKeyword());
		}

		return list;
	}

	@Override
	public List<NewsDTO> getLgRecent() {
		// TODO Auto-generated method stub
		List<NewsDTO> list = dao.getLgRecent();

		for (int i = 0; i < list.size(); i++) {
			NewsKeyword newsKeyword = dao.newsKeywordValid(list.get(i).getNews_id());
			list.get(i).setKeyword(newsKeyword.getKeyword());
		}

		return list;
	}

	@Override
	public List<NewsDTO> getSkRecent() {
		// TODO Auto-generated method stub
		List<NewsDTO> list = dao.getSkRecent();

		for (int i = 0; i < list.size(); i++) {
			NewsKeyword newsKeyword = dao.newsKeywordValid(list.get(i).getNews_id());
			list.get(i).setKeyword(newsKeyword.getKeyword());
		}

		return list;
	}

	public void addNewsKeyword(int news_id, String keyword) {

		NewsKeyword check = null;
		NewsKeyword news = new NewsKeyword(news_id, keyword);

		check = dao.newsKeywordValid(news_id);

		if (check == null) {
			dao.addNewsKeyword(news);
		} else {
			dao.updateNewsKeyword(news);
		}
	}

	@Override
	public List<NewsDTO> findNewsAll(String[] str) {
		// TODO Auto-generated method stub
		return dao.findNewsAll(str);
	}

	@Override
	public List<NewsDTO> findNewsSamsung(String[] str) {
		// TODO Auto-generated method stub
		return dao.findNewsSamsung(str);
	}

	@Override
	public List<NewsDTO> findNewsLg(String[] str) {
		// TODO Auto-generated method stub
		return dao.findNewsLg(str);
	}

	@Override
	public List<NewsDTO> findNewsSk(String[] str) {
		// TODO Auto-generated method stub
		return dao.findNewsSk(str);
	}

	@Override
	public List<NewsDTO> getAllNews() {
		// TODO Auto-generated method stub
		return dao.getAllNews();
	}

	@Override
	public List<NewsDTO> getAllNewsRecent() {
		// TODO Auto-generated method stub

		List<NewsDTO> list = dao.getAllNewsRecent();

		for (int i = 0; i < list.size(); i++) {
			NewsKeyword newsKeyword = dao.newsKeywordValid(list.get(i).getNews_id());
			list.get(i).setKeyword(newsKeyword.getKeyword());
		}

		return list;
	}

	@Override
	public List<NewsDTO> getKeywordNews(String keyword) {
		// TODO Auto-generated method stub

		String[] str = keyword.split(" ");

		return dao.getKeywordNews(str);
	}

	@Override
	public String[] getUserKeyword() {
		// TODO Auto-generated method stub

		List<NewsKeyword> list = dao.getNewsKeywordAll();

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for (NewsKeyword nk : list) {

			String[] str = nk.getKeyword().split(" ");

			if (str.length <= 1) {
				continue;
			}

			for (String s : str) {
				if (map.containsKey(s)) {
					int value = map.get(s);
					value++;
					map.put(s, value);
				} else {
					map.put(s, 1);
				}
			}
		}

		ArrayList<NewsKeywordCounter> counterList = new ArrayList<NewsKeywordCounter>();

		for (String key : map.keySet()) {
			counterList.add(new NewsKeywordCounter(key, map.get(key)));
		}

		counterList.sort(new Comparator<NewsKeywordCounter>() {

			@Override
			public int compare(NewsKeywordCounter o1, NewsKeywordCounter o2) {
				// TODO Auto-generated method stub
				return o2.getCount() - o1.getCount();
			}
		});

		int index = 0;
		int size = 20;
		String[] keywordList = new String[size];

		for (NewsKeywordCounter nkc : counterList) {
//			System.out.println(nkc.toString());
			keywordList[index] = nkc.getKeyword();
			index++;

			if (index >= size) {
				break;
			}
		}

		return keywordList;
	}

	@Override
	public List<NewsDTO> getMeberNews(int member_id) {
		// TODO Auto-generated method stub
		return dao.getMeberNews(member_id);
	}

	@Override
	public void addLikeNews(NewsInsertHelp nih) {
		// TODO Auto-generated method stub
		dao.addLikeNews(nih);
	}

	@Override
	public void deleteLikeNews(NewsInsertHelp nih) {
		// TODO Auto-generated method stub
		dao.deleteLikeNews(nih);
	}

	@Override
	public boolean checkLikeNews(NewsInsertHelp nih) {
		return dao.checkLikeNews(nih);
	}

	@Override
	public boolean checkNewsLog(NewsInsertHelp nih) {
		// TODO Auto-generated method stub
		return dao.checkNewsLog(nih);
	}

	@Override
	public void updateNewsLog(NewsInsertHelp nih) {
		// TODO Auto-generated method stub
		dao.updateNewsLog(nih);
	}

	@Override
	public void insertNewsLog(NewsInsertHelp nih) {
		// TODO Auto-generated method stub
		dao.insertNewsLog(nih);
	}

	@Override
	public List<NewsDTO> getMyRecentNews(int member_id) {
		// TODO Auto-generated method stub
		return dao.getMyRecentNews(member_id);
	}

	@Override
	public List<NewsStatusHelp> getNewsStatus() {
		List<NewsStatusHelp> list = new ArrayList<NewsStatusHelp>();

		int c1 = dao.getNewsStatus("ALLALL");
		list.add(new NewsStatusHelp("ALLALL", c1));

		int c2 = dao.getNewsStatus("SAMSUNGALL");
		list.add(new NewsStatusHelp("SAMSUNGALL", c2));

		int c3 = dao.getNewsStatus("LGALL");
		list.add(new NewsStatusHelp("LGALL", c3));

		int c4 = dao.getNewsStatus("SKALL");
		list.add(new NewsStatusHelp("SKALL", c4));

		int c5 = dao.getNewsStatus("ALLTODAY");
		list.add(new NewsStatusHelp("ALLTODAY", c5));

		int c6 = dao.getNewsStatus("SAMSUNGTODAY");
		list.add(new NewsStatusHelp("SAMSUNGTODAY", c6));

		int c7 = dao.getNewsStatus("LGTODAY");
		list.add(new NewsStatusHelp("LGTODAY", c7));

		int c8 = dao.getNewsStatus("SKTODAY");
		list.add(new NewsStatusHelp("SKTODAY", c8));

		return list;
	}

	public void setKeyword(NewsDTO n) {

		n = dao.getNewsOne(n.getUrl());

		int news_id = n.getNews_id();
		String word = n.getKeyword();

		HashMap<String, Integer> map = new HashMap<String, Integer>();

		String[] keys = word.split(" ");

		for (String s : keys) {
			if (s.length() <= 1 || s.equals("삼성전자") || s.equals("삼성") || s.equals("전자") || s.equals("분야")
					|| s.equals("사람") || s.equals("임직원") || s.equals("월드") || s.equals("최대") || s.equals("선정")
					|| s.equals("코리아") || s.equals("최적") || s.equals("진행") || s.equals("아래") || s.equals("소개")
					|| s.equals("부문") || s.equals("적용") || s.equals("기간") || s.equals("이상") || s.equals("상판")
					|| s.equals("규모") || s.equals("북미") || s.equals("지급") || s.equals("사원") || s.equals("규모")
					|| s.equals("지원") || s.equals("대상") || s.equals("영민") || s.equals("주옥") || s.equals("구성")
					|| s.equals("사용") || s.equals("자녀") || s.equals("사용자") || s.equals("시간") || s.equals("경험")
					|| s.equals("신창") || s.equals("제공") || s.equals("대표") || s.equals("레이") || s.equals("학교")
					|| s.equals("아우") || s.equals("아이") || s.equals("이두") || s.equals("사이") || s.equals("기준")
					|| s.equals("리뷰") || s.equals("으뜸") || s.equals("구매") || s.equals("관련") || s.equals("건조")
					|| s.equals("마음") || s.equals("시장") || s.equals("지역") || s.equals("상무") || s.equals("모습")
					|| s.equals("그니") || s.equals("그랑") || s.equals("튜브") || s.equals("빌리") || s.equals("이노")
					|| s.equals("베이") || s.equals("방식") || s.equals("빌트") || s.equals("프리") || s.equals("제품")
					|| s.equals("레드") || s.equals("하이") || s.equals("기능") || s.equals("상배") || s.equals("양사")
					|| s.equals("바이") || s.equals("인공") || s.equals("지능") || s.equals("LG전자") || s.equals("LG")
					|| s.equals("10") || s.equals("SK텔레콤") || s.equals("SK이노베이션")) {
				continue;
			}
			if (map.containsKey(s)) {
				int value = map.get(s);
				value++;
				map.put(s, value);
			} else {
				map.put(s, 1);
			}
		}

		ArrayList<NewsKeywordCounter> counterList = new ArrayList<NewsKeywordCounter>();

		for (String key : map.keySet()) {
			counterList.add(new NewsKeywordCounter(key, map.get(key)));
		}

		counterList.sort(new Comparator<NewsKeywordCounter>() {

			@Override
			public int compare(NewsKeywordCounter o1, NewsKeywordCounter o2) {
				// TODO Auto-generated method stub
				return o2.getCount() - o1.getCount();
			}
		});

		String keyword = "";

		for (int i = 0; i < counterList.size(); i++) {
			if (i >= 5) {
				break;
			}
			keyword += counterList.get(i).getKeyword() + " ";
		}
		if (keyword.length() >= 1) {
			keyword = keyword.substring(0, keyword.length() - 1);
		}
		addNewsKeyword(news_id, keyword);

		List<Member> memberList = memberDao.emailSendList(keyword);

		for (Member m : memberList) {

			try {

				StringBuffer emailcontent = new StringBuffer();
				emailcontent.append("<!DOCTYPE html>");
				emailcontent.append("<html>");
				emailcontent.append("<head>");
				emailcontent.append("</head>");
				emailcontent.append("<body>");
				emailcontent.append("<strong>" + m.getName() + "</strong>" + "님 안녕하세요.<br>");
				emailcontent.append("당신이 선택하신 키워드에 매칭되는 최신 뉴스가 올라왔습니다. :)<br>");
				emailcontent.append("<br><br><br>");
				emailcontent.append(n.getTitle() + "<br>");
				emailcontent.append(n.getBody() + "<br>");
				emailcontent.append("<a href='" + n.getUrl() + "'>해당 기사 사이트로 가기</a><br>");
				emailcontent.append("</body>");
				emailcontent.append("</html>");
				emailService.sendMail(m.getEmail(), "[NEWSPACE] 추천 뉴스가 도착했습니다.", emailcontent.toString());
				logger.info("Email Service Send To : " + m.getName() + "\t" + "news_id : " + news_id);
			} catch (MessagingException e) {
				logger.error(e.getMessage());
			}

		}
	}

	@Override
	public List<List<NewsKeywordCounter>> getChartKeyword() {
		// TODO Auto-generated method stub
		List<List<NewsKeywordCounter>> result = new ArrayList<>();

		List<NewsDTO> alist = null;
		alist = dao.getChartKeyword();

		List<NewsDTO> samsunglist = null;
		samsunglist = dao.getChartKeyword("SAMSUNG");

		List<NewsDTO> lglist = null;
		lglist = dao.getChartKeyword("LG");

		List<NewsDTO> sklist = null;
		sklist = dao.getChartKeyword("SK");

		result.add(chartKeywordSet(alist));
		result.add(chartKeywordSet(samsunglist));
		result.add(chartKeywordSet(lglist));
		result.add(chartKeywordSet(sklist));

		return result;

	}

	public List<NewsKeywordCounter> chartKeywordSet(List<NewsDTO> list) {

		List<NewsKeywordCounter> resultList = new ArrayList<>();

		if (list.isEmpty()) {
			return null;
		} else {
			HashMap<String, Integer> map = new HashMap<String, Integer>();

			for (NewsDTO n : list) {
				String[] str = n.getKeyword().split(" ");

				if (str.length <= 1) {
					continue;
				}

				for (String s : str) {
					if (map.containsKey(s)) {
						int value = map.get(s);
						value++;
						map.put(s, value);
					} else {
						map.put(s, 1);
					}
				}
			}
			ArrayList<NewsKeywordCounter> counterList = new ArrayList<NewsKeywordCounter>();

			for (String key : map.keySet()) {
				counterList.add(new NewsKeywordCounter(key, map.get(key)));
			}

			counterList.sort(new Comparator<NewsKeywordCounter>() {

				@Override
				public int compare(NewsKeywordCounter o1, NewsKeywordCounter o2) {
					// TODO Auto-generated method stub
					return o2.getCount() - o1.getCount();
				}
			});

			int index = 0;
			int size = 30;

			for (NewsKeywordCounter nkc : counterList) {
				resultList.add(new NewsKeywordCounter(nkc.getKeyword(), nkc.getCount()));
				index++;

				if (index >= size) {
					break;
				}
			}
		}
		return resultList;
	}

	@Override
	public List<SearchChart> getSearchChartKeyword(String search) {
		// TODO Auto-generated method stub

		List<SearchChart> result = new ArrayList<SearchChart>();

		List<List<NewsDTO>> list = new ArrayList<List<NewsDTO>>();

		String[] find = search.split(" ");

		for (int i = 11; i >= 0; i--) {
			List<NewsDTO> temp = dao.getSearchChartKeyword(new SearchChart("ALL", i, 0, find));
			list.add(temp);
		}

		int max = 0;

		int index = 11;
		for (List<NewsDTO> l : list) {

			int lmax = 0;

			for (NewsDTO n : l) {

				int[] count = new int[find.length];

				for (int c = 0; c < count.length; c++) {
					count[c] = StringUtils.countMatches(n.getKeyword(), find[c]);
				}

				for (int c = 0; c < count.length; c++) {
					lmax += count[c];
				}
			}
			
			if (max == 0) {
				max = lmax;
				result.add(new SearchChart("ALL", index--, max - lmax, find, search));
			} else {
				result.add(new SearchChart("ALL", index--, max - lmax, find, search));
			}
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
		Calendar cal = Calendar.getInstance();

		for (SearchChart sc : result) {
			cal = Calendar.getInstance();
			cal.add(cal.MONTH, -sc.getDatecount());
			sc.setDate(df.format(cal.getTime()));
		}

		return result;
	}

	private boolean samsungCrawling;
	private boolean lgCrawling;
	private boolean skCrawling;
	private boolean keywordSet;
	private boolean serverCheck;

	@Override
	public List<Boolean> getServerStatus() {
		// TODO Auto-generated method stub
		List<Boolean> list = new ArrayList<>();

		list.add(samsungCrawling);
		list.add(lgCrawling);
		list.add(skCrawling);
		list.add(keywordSet);

		return list;
	}

	public void samsung_Crawling1() throws IOException, ParseException {

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		String[] category = { "기업문화", "기술", "디자인", "사회공헌" };

		for (int i = 0; i < category.length; i++) {

			Document doc = null;

			int pageIndex = 1;
			boolean page = true;

			while (page) {

				doc = Jsoup.connect("https://news.samsung.com/kr/category/%ea%b8%b0%ec%97%85/" + category[i] + "/page/"
						+ pageIndex++).get();

				if (doc.getElementsByClass("page_404").isEmpty()) {

					Elements ele1 = doc.getElementsByClass("item").get(0).getElementsByTag("a");

					for (int j = 0; j < ele1.size(); j++) {

						Document ele1Doc = Jsoup.connect(ele1.get(j).attr("href")).get();

						String title = ele1Doc.getElementsByClass("title").text().substring(0,
								ele1Doc.getElementsByClass("title").text().length() - 4);
						String date = ele1Doc.getElementsByClass("meta").text();
						String body = ele1Doc.getElementsByClass("text_cont").toString();
						String brand = "SAMSUNG";
						String cate = category[i];
						String url = ele1.get(j).attr("href");
						String bodytext = ele1Doc.getElementsByClass("text_cont").text();

						String thumb = ele1.get(j).getElementsByClass("thumb_wrap").attr("style");
						if (thumb.length() > 23) {
							thumb = thumb.substring(21, thumb.length() - 2);
						} else {
							thumb = "";
						}

						date = date.replaceAll("/", "-");
						date = date.substring(0, 10);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date newsDate = formatter.parse(date);
						Calendar cal = Calendar.getInstance();
						cal.setTime(newsDate);

						long diffSec = (now.getTimeInMillis() - cal.getTimeInMillis()) / 1000;
						long diffDay = diffSec / (24 * 60 * 60);

						if (diffDay > 7) {
							page = false;
							pageIndex = 1;
							break;
						}

						String keyword = "";
						String txt = "";

						if (bodytext.length() < 10) {
							bodytext = "no content";
						}

						Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
						KomoranResult analyzeResultList = komoran.analyze(bodytext);
						List<Token> tokenList = analyzeResultList.getTokenList();
						for (Token token : tokenList) {
							if (token.getPos().equals("SN") || token.getPos().equals("SW")
									|| token.getPos().equals("SL")) {
								txt += token.getMorph();
							}
							if (token.getPos().equals("NNG") || token.getPos().equals("NNP")) {
								keyword += token.getMorph() + " ";
							}
						}

						String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV",
								"Auto", "webOS" };
						int[] count = new int[countString.length];

						for (int c = 0; c < count.length; c++) {
							count[c] = StringUtils.countMatches(txt, countString[c]);
						}

						for (int c = 0; c < count.length; c++) {
							for (int index = 0; index < count[c]; index++) {
								keyword += countString[c] + " ";
							}
						}

						NewsDTO check = null;
						NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, url, bodytext, thumb);
						check = getNewsOne(news.getUrl());
						if (check == null) {
							logger.info("Added News : " + news.getUrl());
							addNews(news);
							setKeyword(news);
						} else {
							page = false;
							pageIndex = 1;
							break;
						}
					}
					doc = null;
				} else {
					pageIndex = 1;
					break;
				}
			}
		}
	}

	public void samsung_Crawling2() throws IOException, ParseException {

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		String[] category = { "모바일", "스마트tv", "가전", "반도체ssd", "기타" };

		for (int i = 0; i < category.length; i++) {

			Document doc = null;

			int pageIndex = 1;
			boolean page = true;

			while (page) {

				doc = Jsoup.connect("https://news.samsung.com/kr/category/%ec%a0%9c%ed%92%88/" + category[i] + "/page/"
						+ pageIndex++).get();

				if (doc.getElementsByClass("page_404").isEmpty()) {

					Elements ele1 = doc.getElementsByClass("item").get(0).getElementsByTag("a");

					for (int j = 0; j < ele1.size(); j++) {

						Document ele1Doc = Jsoup.connect(ele1.get(j).attr("href")).get();

						String title = ele1Doc.getElementsByClass("title").text().substring(0,
								ele1Doc.getElementsByClass("title").text().length() - 4);
						String date = ele1Doc.getElementsByClass("meta").text();
						String body = ele1Doc.getElementsByClass("text_cont").toString();
						String brand = "SAMSUNG";
						String cate = category[i];
						String url = ele1.get(j).attr("href");
						String bodytext = ele1Doc.getElementsByClass("text_cont").text();

						String thumb = ele1.get(j).getElementsByClass("thumb_wrap").attr("style");
						if (thumb.length() > 23) {
							thumb = thumb.substring(21, thumb.length() - 2);
						} else {
							thumb = "";
						}

						date = date.replaceAll("/", "-");
						date = date.substring(0, 10);
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date newsDate = formatter.parse(date);
						Calendar cal = Calendar.getInstance();
						cal.setTime(newsDate);

						long diffSec = (now.getTimeInMillis() - cal.getTimeInMillis()) / 1000;
						long diffDay = diffSec / (24 * 60 * 60);

						if (diffDay > 7) {
							page = false;
							pageIndex = 1;
							break;
						}

						String keyword = "";
						String txt = "";

						if (bodytext.length() < 10) {
							bodytext = "no content";
						}

						Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
						KomoranResult analyzeResultList = komoran.analyze(bodytext);
						List<Token> tokenList = analyzeResultList.getTokenList();
						for (Token token : tokenList) {
							if (token.getPos().equals("SN") || token.getPos().equals("SW")
									|| token.getPos().equals("SL")) {
								txt += token.getMorph();
							}
							if (token.getPos().equals("NNG") || token.getPos().equals("NNP")) {
								keyword += token.getMorph() + " ";
							}
						}

						String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV",
								"Auto", "webOS" };
						int[] count = new int[countString.length];

						for (int c = 0; c < count.length; c++) {
							count[c] = StringUtils.countMatches(txt, countString[c]);
						}

						for (int c = 0; c < count.length; c++) {
							for (int index = 0; index < count[c]; index++) {
								keyword += countString[c] + " ";
							}
						}

						NewsDTO check = null;
						NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, url, bodytext, thumb);
						check = getNewsOne(news.getUrl());
						if (check == null) {
							logger.info("Added News : " + news.getUrl());
							addNews(news);
							setKeyword(news);
						} else {
							page = false;
							pageIndex = 1;
							break;
						}
					}
					doc = null;
				} else {
					pageIndex = 1;
					break;
				}
			}
		}
	}

	public void lg_Crawling() throws ParseException, IOException {

		ArrayList<NewsDTO> list = new ArrayList<>();

		String url = "https://social.lge.co.kr/newsroom/social-news/";

		// https://social.lge.co.kr/newsroom/social-news/
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		/////////////////////////////////////////////////////////////

		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// Driver SetUp
		ChromeOptions option = new ChromeOptions();

//		option.setBinary("/usr/bin/google-chrome");
		option.addArguments("headless");
		option.addArguments("--no-sandbox");
		option.addArguments("--disable-dev-shm-usage");

		driver = new ChromeDriver(option);
		driver.get(url);

		int tempy = 0;
		int locationy = 0;

		boolean flag = true;

		while (flag) {

			while (true) {
				webElement = driver.findElement(By.className("basic_list_morebtn"));

				if (webElement.getText().equals("더 보기") && locationy == tempy) {
					locationy = webElement.getLocation().y;
					break;
				}
			}

			if (tempy != locationy) {
				webElement.click();
				webElement = null;
				tempy = locationy;
			}
			webElements = driver.findElements(By.className("date"));

			for (int i = 0; i < webElements.size(); i++) {
				String temp = webElements.get(i).getText();
				String date = "";
				if (temp.length() >= 12) {
					date = temp.substring(0, 4) + "-" + temp.substring(6, 8) + "-" + temp.substring(10, 12);

					Date newsDate = formatter.parse(date);

					Calendar cal = Calendar.getInstance();
					cal.setTime(newsDate);

					long diffSec = (now.getTimeInMillis() - cal.getTimeInMillis()) / 1000;
					long diffDay = diffSec / (24 * 60 * 60);

					if (diffDay > 7) {
						flag = false;
					}
				}
			}
			webElements.clear();
		}

		if (!flag) {
			webElements = driver.findElements(By.className("basictwo_itempostbx"));
			for (int i = 0; i < webElements.size(); i++) {
				webElement = webElements.get(i);
				String title = webElement.findElement(By.className("title")).getText();
				String date = webElement.findElement(By.className("date")).getText();
				date = date.substring(0, 4) + "-" + date.substring(6, 8) + "-" + date.substring(10, 12);
				String brand = "LG";
				String cate = "ALL";
				String newsurl = webElement.findElement(By.className("thum_itembx")).getAttribute("href");
				String thumb = webElement.findElement(By.className("thum")).findElement(By.tagName("img"))
						.getAttribute("src");
				NewsDTO news = new NewsDTO(title, date, brand, cate, newsurl, thumb);
				list.add(news);
			}
		}

		driver.close();
		driver.quit();

		for (NewsDTO n : list) {

			Document doc = Jsoup.connect(n.getUrl()).timeout(120000).get();

			String title = n.getTitle();
			String date = n.getDate();
			String brand = n.getBrand();
			String cate = n.getCategory();
			String newsurl = n.getUrl();
			String body = doc.getElementsByClass("sin" + "gle_cont").toString();
			String bodytext = doc.getElementsByClass("single_cont").text();

			String keyword = "";
			String txt = "";

			if (bodytext.length() < 10) {
				bodytext = "no content";
			}

			Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
			KomoranResult analyzeResultList = komoran.analyze(bodytext);
			List<Token> tokenList = analyzeResultList.getTokenList();
			for (Token token : tokenList) {
				if (token.getPos().equals("SN") || token.getPos().equals("SW") || token.getPos().equals("SL")) {
					txt += token.getMorph();
				}
				if (token.getPos().equals("NNG") || token.getPos().equals("NNP")) {
					keyword += token.getMorph() + " ";
				}
			}

			String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV", "Auto",
					"webOS" };
			int[] count = new int[countString.length];

			for (int c = 0; c < count.length; c++) {
				count[c] = StringUtils.countMatches(txt, countString[c]);
			}

			for (int c = 0; c < count.length; c++) {
				for (int index = 0; index < count[c]; index++) {
					keyword += countString[c] + " ";
				}
			}

			NewsDTO check = null;
			NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, newsurl, bodytext, n.getThumb());
			check = getNewsOne(news.getUrl());
			if (check == null) {
				logger.info("Added News : " + news.getUrl());
				addNews(news);
				setKeyword(news);
			} else {
				break;
			}
		}
		list.clear();
		return;
	}

	public void sk_Crawling() throws ParseException, IOException {

//		ArrayList<String> list = new ArrayList<>();

		ArrayList<NewsDTO> list = new ArrayList<>();

		String url = "http://mediask.co.kr/category/news/%eb%b3%b4%eb%8f%84%ec%9e%90%eb%a3%8c-news-2";

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		/////////////////////////////////////////////////////////////

		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// Driver SetUp
		ChromeOptions option = new ChromeOptions();

//		option.setBinary("/usr/bin/google-chrome");
		option.addArguments("headless");
		option.addArguments("--no-sandbox");
		option.addArguments("--disable-dev-shm-usage");

		driver = new ChromeDriver(option);

		driver.get(url);

		int tempy = 0;
		int locationy = 0;

		boolean flag = true;

		while (flag) {
			while (true) {
				webElement = driver.findElement(By.className("moreBtn"));
				if (locationy == tempy) {
					locationy = webElement.getLocation().y;
					break;
				}
			}

			if (tempy != locationy) {
				webElement.click();
				webElement = null;
				tempy = locationy;
			}

			webElements = driver.findElements(By.className("date"));

			for (int i = 0; i < webElements.size(); i++) {
				String temp = webElements.get(i).getText();
				String date = "";
				if (temp.length() >= 10) {
					date = temp.substring(0, 4) + "-" + temp.substring(5, 7) + "-" + temp.substring(8, 10);

					Date newsDate = formatter.parse(date);

					Calendar cal = Calendar.getInstance();
					cal.setTime(newsDate);

					long diffSec = (now.getTimeInMillis() - cal.getTimeInMillis()) / 1000;
					long diffDay = diffSec / (24 * 60 * 60);

					if (diffDay > 7) {
						flag = false;
					}
				}
			}
			webElements.clear();
		}

		if (!flag) {
//			webElements = driver.findElements(By.tagName("a"));
			webElements = driver.findElement(By.className("list")).findElements(By.tagName("a"));

			for (int i = 0; i < webElements.size() - 1; i++) {
				webElement = webElements.get(i);

				String newsurl = webElement.getAttribute("href");
				String thumb = webElement.findElement(By.className("thumnail")).findElement(By.tagName("img"))
						.getAttribute("src");

				NewsDTO news = new NewsDTO(newsurl, thumb);
				list.add(news);
			}
		}
		driver.close();
		driver.quit();

//		for (String s : list) {
		for (NewsDTO n : list) {
			Document doc = Jsoup.connect(n.getUrl()).timeout(120000).get();

			String title = doc.getElementsByClass("postTitle").get(0).getElementsByTag("h2").text();
			String date = doc.getElementsByClass("postInfo").get(0).getElementsByTag("p").get(1).text();
			String brand = "SK";
			String cate = "ALL";
			Elements bodyEle = doc.getElementsByClass("post").get(0).getElementsByTag("p");
			String body = "";
			for (int i = 0; i < bodyEle.size(); i++) {
				body += bodyEle.get(i);
			}
			String bodytext = doc.getElementsByClass("post").text();

			String keyword = "";
			String txt = "";

			if (bodytext.length() < 10) {
				bodytext = "no content";
			}

			Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
			KomoranResult analyzeResultList = komoran.analyze(bodytext);
			List<Token> tokenList = analyzeResultList.getTokenList();
			for (Token token : tokenList) {
				if (token.getPos().equals("SN") || token.getPos().equals("SW") || token.getPos().equals("SL")) {
					txt += token.getMorph();
				}
				if (token.getPos().equals("NNG") || token.getPos().equals("NNP")) {
					keyword += token.getMorph() + " ";
				}
			}

			String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV", "Auto",
					"webOS" };
			int[] count = new int[countString.length];

			for (int c = 0; c < count.length; c++) {
				count[c] = StringUtils.countMatches(txt, countString[c]);
			}

			for (int c = 0; c < count.length; c++) {
				for (int index = 0; index < count[c]; index++) {
					keyword += countString[c] + " ";
				}
			}

			NewsDTO check = null;
			NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, n.getUrl(), bodytext, n.getThumb());
			check = getNewsOne(news.getUrl());

			if (check == null) {
				logger.info("Added News : " + news.getUrl());
				addNews(news);
				setKeyword(news);
			} else {
				break;
			}
		}
		list.clear();
	}

	public void allKeywordSet() {
		// 어쩌다 전체 키워드 셋팅 시킬때 씀
		List<NewsDTO> list = dao.getAllNews();

		for (NewsDTO n : list) {

			int news_id = n.getNews_id();
			String word = n.getKeyword();

			HashMap<String, Integer> map = new HashMap<String, Integer>();

			String[] keys = word.split(" ");

			for (String s : keys) {
				if (s.length() <= 1 || s.equals("삼성전자") || s.equals("삼성") || s.equals("전자") || s.equals("분야")
						|| s.equals("사람") || s.equals("임직원") || s.equals("월드") || s.equals("최대") || s.equals("선정")
						|| s.equals("코리아") || s.equals("최적") || s.equals("진행") || s.equals("아래") || s.equals("소개")
						|| s.equals("부문") || s.equals("적용") || s.equals("기간") || s.equals("이상") || s.equals("상판")
						|| s.equals("규모") || s.equals("북미") || s.equals("지급") || s.equals("사원") || s.equals("규모")
						|| s.equals("지원") || s.equals("대상") || s.equals("영민") || s.equals("주옥") || s.equals("구성")
						|| s.equals("사용") || s.equals("자녀") || s.equals("사용자") || s.equals("시간") || s.equals("경험")
						|| s.equals("신창") || s.equals("제공") || s.equals("대표") || s.equals("레이") || s.equals("학교")
						|| s.equals("아우") || s.equals("아이") || s.equals("이두") || s.equals("사이") || s.equals("기준")
						|| s.equals("리뷰") || s.equals("으뜸") || s.equals("구매") || s.equals("관련") || s.equals("건조")
						|| s.equals("마음") || s.equals("시장") || s.equals("지역") || s.equals("상무") || s.equals("모습")
						|| s.equals("그니") || s.equals("그랑") || s.equals("튜브") || s.equals("빌리") || s.equals("이노")
						|| s.equals("베이") || s.equals("방식") || s.equals("빌트") || s.equals("프리") || s.equals("제품")
						|| s.equals("레드") || s.equals("하이") || s.equals("기능") || s.equals("상배") || s.equals("양사")
						|| s.equals("바이") || s.equals("인공") || s.equals("지능") || s.equals("LG전자") || s.equals("LG")
						|| s.equals("10") || s.equals("SK텔레콤") || s.equals("SK이노베이션")) {
					continue;
				}
				if (map.containsKey(s)) {
					int value = map.get(s);
					value++;
					map.put(s, value);
				} else {
					map.put(s, 1);
				}
			}

			ArrayList<NewsKeywordCounter> counterList = new ArrayList<NewsKeywordCounter>();

			for (String key : map.keySet()) {
				counterList.add(new NewsKeywordCounter(key, map.get(key)));
			}

			counterList.sort(new Comparator<NewsKeywordCounter>() {

				@Override
				public int compare(NewsKeywordCounter o1, NewsKeywordCounter o2) {
					// TODO Auto-generated method stub
					return o2.getCount() - o1.getCount();
				}
			});

			String keyword = "";

			for (int i = 0; i < counterList.size(); i++) {
				if (i >= 5) {
					break;
				}
				keyword += counterList.get(i).getKeyword() + " ";
			}
			if (keyword.length() >= 1) {
				keyword = keyword.substring(0, keyword.length() - 1);
			}

			NewsKeyword nk = null;
			nk = dao.newsKeywordValid(news_id);

			if (nk == null) {
				dao.addNewsKeyword(new NewsKeyword(news_id, keyword));
			} else {
				dao.updateNewsKeyword(new NewsKeyword(news_id, keyword));
			}

		}

	}

//	@Scheduled(cron = "0 31 10 * * *")
	@Scheduled(fixedDelay = 1800000)
	public void Scheduler() throws IOException, ParseException {
		serverCheck = true;
		logger.info("SAMSUNG CRAWLING1..." + "\t" + new Date());
		samsungCrawling = true;
//		samsung_Crawling1();
		logger.info("SAMSUNG CRAWLING2..." + "\t" + new Date());
//		samsung_Crawling2();
		samsungCrawling = false;
		logger.info("LG ELECTRONICS CRAWLING..." + "\t" + new Date());
		lgCrawling = true;
//		lg_Crawling();
		lgCrawling = false;
		logger.info("SK HYNIX CRAWLING..." + "\t" + new Date());
		skCrawling = true;
//		sk_Crawling();
		skCrawling = false;
		logger.info("CRAWLING DONE." + "\t" + new Date());
		keywordSet = true;
//		allKeywordSet();
		keywordSet = false;
		serverCheck = false;
	}
	
	public boolean adminServerOn() throws IOException, ParseException {
		if(serverCheck) {
			logger.info("Already Server. CRAWLING NOT EXCUTE " + new Date());
			return false;
		}else {
			serverCheck = true;
			logger.info("SAMSUNG CRAWLING1..." + "\t" + new Date());
			samsungCrawling = true;
			samsung_Crawling1();
			logger.info("SAMSUNG CRAWLING2..." + "\t" + new Date());
			samsung_Crawling2();
			samsungCrawling = false;
			logger.info("LG ELECTRONICS CRAWLING..." + "\t" + new Date());
			lgCrawling = true;
			lg_Crawling();
			lgCrawling = false;
			logger.info("SK HYNIX CRAWLING..." + "\t" + new Date());
			skCrawling = true;
			sk_Crawling();
			skCrawling = false;
			logger.info("CRAWLING DONE." + "\t" + new Date());
			keywordSet = true; 
			allKeywordSet();
			keywordSet = false;
			serverCheck = false;
			return true;
		}
	}
}
