package com.ssafy.edu.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BlobTypeHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.NewsServiceDao;
import com.ssafy.edu.dto.NewsDTO;
import com.ssafy.edu.help.NewsKeyword;
import com.ssafy.edu.help.NewsKeywordCounter;

import kr.co.shineware.nlp.komoran.core.analyzer.Komoran;
import kr.co.shineware.util.common.model.Pair;

@Service
public class NewsService implements INewsService {

	@Autowired
	private NewsServiceDao dao;

	private WebDriver driver;
	private WebElement webElement;
	private List<WebElement> webElements;

	// Properties
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	public static final String WEB_DRIVER_PATH = "C:\\JAVA\\selenium\\chromedriver.exe";

	@Override
	public NewsDTO getNews(int news_id) {
		NewsDTO news = null;
		news = dao.getNews(news_id);
		
		if(news == null) {
			return news;
		}else {
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

	public void keywordSet() {

		List<NewsDTO> listSamsung = new ArrayList<>();
		List<NewsDTO> listLg = new ArrayList<>();
		List<NewsDTO> listSk = new ArrayList<>();

		listSamsung = getSamsungNews();
		listLg = getLgNews();
		listSk = getSkNews();

		addKeyword(listSamsung);
		addKeyword(listLg);
		addKeyword(listSk);
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

	////////////////////////////////// 스케쥴러메소드
	////////////////////////////////// ////////////////////////////////////////////

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

						Komoran komoran = new Komoran("C:\\KOMORAN\\models");
						List<List<Pair<String, String>>> result = komoran.analyze(bodytext);
						for (List<Pair<String, String>> eojeolResult : result) {
							for (Pair<String, String> wordMorph : eojeolResult) {
								if (wordMorph.getSecond().equals("SN") || wordMorph.getSecond().equals("SW")
										|| wordMorph.getSecond().equals("SL")) {
									txt += wordMorph.getFirst();
								}
								if (wordMorph.getSecond().equals("NNG") || wordMorph.getSecond().equals("NNP")) {
									keyword += wordMorph.getFirst() + " ";
								}
							}
						}

						String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV" };
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
						NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, url, bodytext);
						check = getNewsOne(news.getUrl());
						if (check == null) {
							addNews(news);
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

						Komoran komoran = new Komoran("C:\\KOMORAN\\models");
						List<List<Pair<String, String>>> result = komoran.analyze(bodytext);
						for (List<Pair<String, String>> eojeolResult : result) {
							for (Pair<String, String> wordMorph : eojeolResult) {
								if (wordMorph.getSecond().equals("SN") || wordMorph.getSecond().equals("SW")
										|| wordMorph.getSecond().equals("SL")) {
									txt += wordMorph.getFirst();
								}
								if (wordMorph.getSecond().equals("NNG") || wordMorph.getSecond().equals("NNP")) {
									keyword += wordMorph.getFirst() + " ";
								}
							}
						}

						String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV" };
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
						NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, url, bodytext);
						check = getNewsOne(news.getUrl());
						if (check == null) {
							addNews(news);
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
		driver = new ChromeDriver();

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
				NewsDTO news = new NewsDTO(title, date, brand, cate, newsurl);
				list.add(news);
			}
		}

		driver.close();

		for (NewsDTO n : list) {
			Document doc = Jsoup.connect(n.getUrl()).get();

			String title = n.getTitle();
			String date = n.getDate();
			String brand = n.getBrand();
			String cate = n.getCategory();
			String newsurl = n.getUrl();
			String body = doc.getElementsByClass("single_cont").toString();
			String bodytext = doc.getElementsByClass("single_cont").text();

			String keyword = "";
			String txt = "";
			Komoran komoran = new Komoran("C:\\KOMORAN\\models");
			List<List<Pair<String, String>>> result = komoran.analyze(bodytext);
			for (List<Pair<String, String>> eojeolResult : result) {
				for (Pair<String, String> wordMorph : eojeolResult) {
					if (wordMorph.getSecond().equals("SN") || wordMorph.getSecond().equals("SW")
							|| wordMorph.getSecond().equals("SL")) {
						txt += wordMorph.getFirst();
					}
					if (wordMorph.getSecond().equals("NNG") || wordMorph.getSecond().equals("NNP")) {
						keyword += wordMorph.getFirst() + " ";
					}
				}
			}

			String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV" };
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
			NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, newsurl, bodytext);
			check = getNewsOne(news.getUrl());

			if (check == null) {
				addNews(news);
			} else {
				break;
			}
		}
		list.clear();
	}

	public void sk_Crawling() throws ParseException, IOException {

		ArrayList<String> list = new ArrayList<>();

		String url = "http://mediask.co.kr/category/news/%eb%b3%b4%eb%8f%84%ec%9e%90%eb%a3%8c-news-2";

		// http://mediask.co.kr/category/news/%eb%b3%b4%eb%8f%84%ec%9e%90%eb%a3%8c-news-2

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		/////////////////////////////////////////////////////////////

		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

		// Driver SetUp
		driver = new ChromeDriver();

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
			webElements = driver.findElements(By.tagName("a"));

			for (int i = 0; i < webElements.size(); i++) {
				webElement = webElements.get(i);

				String checkUrl = webElement.getAttribute("href");
				if (checkUrl.length() >= 21) {
					String temp = checkUrl.substring(21, checkUrl.length());
					char[] checked = temp.toCharArray();
					if (checked.length >= 5) {
						if (checked[0] >= '0' && checked[0] <= '9') {
							list.add(checkUrl);
						} else {
							continue;
						}
					} else {
						continue;
					}
				}
			}
		}
		driver.close();

		for (String s : list) {
			Document doc = Jsoup.connect(s).get();

			String title = doc.getElementsByClass("postTitle").get(0).getElementsByTag("h2").text();
			String date = doc.getElementsByClass("postInfo").get(0).getElementsByTag("p").get(1).text();
			String brand = "SK";
			String cate = "ALL";
			String newsurl = s;
			String body = doc.getElementsByClass("post").toString();
			String bodytext = doc.getElementsByClass("post").text();

			String keyword = "";
			String txt = "";

			Komoran komoran = new Komoran("C:\\KOMORAN\\models");
			List<List<Pair<String, String>>> result = komoran.analyze(bodytext);
			for (List<Pair<String, String>> eojeolResult : result) {
				for (Pair<String, String> wordMorph : eojeolResult) {
					if (wordMorph.getSecond().equals("SN") || wordMorph.getSecond().equals("SW")
							|| wordMorph.getSecond().equals("SL")) {
						txt += wordMorph.getFirst();
					}
					if (wordMorph.getSecond().equals("NNG") || wordMorph.getSecond().equals("NNP")) {
						keyword += wordMorph.getFirst() + " ";
					}
				}
			}

			String[] countString = { "5G", "SW", "AI", "SSAFY", "LTE", "4G", "QLED", "OLED", "SSD", "TV" };
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
			NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, newsurl, bodytext);
			check = getNewsOne(news.getUrl());

			if (check == null) {
				addNews(news);
			} else {
				break;
			}
		}
		list.clear();
	}

	public void addKeyword(List<NewsDTO> list) {

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
						|| s.equals("마음") || s.equals("시장") || s.equals("지역") || s.equals("상무") || s.equals("모습")) {
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
			addNewsKeyword(news_id, keyword);
		}
	}

//	@Scheduled(cron = "0 31 10 * * *")
	@Scheduled(fixedDelay = 600000)
	public void Scheduler() throws IOException, ParseException {
//		System.out.println("SAMSUNG CRAWLING 1 START");
//		samsung_Crawling1();
//		System.out.println("SAMSUNG CRAWLING 1 COMPLETE");
//		System.out.println("SAMSUNG CRAWLING 2 START");
//		samsung_Crawling2();
//		System.out.println("SAMSUNG CRAWLING 2 COMPLETE");
//		System.out.println("LG CRAWLING START");
//		lg_Crawling();
//		System.out.println("LG CRAWLING COMPLETE");
//		System.out.println("SK CRAWLING START");
//		sk_Crawling();
//		System.out.println("SK CRAWLING COMPLETE");
		System.out.println("KEYWORD SETTING START");
		keywordSet();
		System.out.println("KEYWORD SETTING COMPLETE");
	}
}
