package com.ssafy.edu.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordExtractor;
import org.snu.ids.ha.index.KeywordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.NewsServiceDao;
import com.ssafy.edu.dto.NewsDTO;

@Service
public class NewsService implements INewsService {

//	private List<NewsDTO> list;

	@Autowired
	private NewsServiceDao dao;

//	@Scheduled(cron="0 31 10 * * *")매일 오전 10시 31분
//	public void Scheduler(){
//		System.out.println("Scheduling...\t" + new Date());
//		samsung_Crawling1();
//	}

//	@Scheduled(fixedDelay = 30000)
//	public void TestScheduler() throws IOException, ParseException {
//		System.out.println("Scheduling...\t" + new Date());
//		list = new ArrayList<>();
//		
//		samsung_Crawling1();
//		samsung_Crawling2();
//		
//		list.sort(new Comparator<NewsDTO>() {
//			@Override
//			public int compare(NewsDTO o1, NewsDTO o2) {
//				
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//				
//				Calendar o1cal = Calendar.getInstance();
//				Calendar o2cal = Calendar.getInstance();
//				
//				try {
//					o1cal.setTime(formatter.parse(o1.getDate()));
//					o2cal.setTime(formatter.parse(o2.getDate()));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				return (int) (o2cal.getTimeInMillis() - o1cal.getTimeInMillis());
//			}
//		});
//		
//		
//		for (int i = 0; i < list.size(); i++) {
//			NewsDTO news = getNewsOne(list.get(i).getUrl());
//			if (news == null) {
//				addNews(list.get(i));
//			}else {
//				break;
//			}
//		}
//		
//		System.out.println("DataBase News Add Done.");
//		list.clear();
//	}
	
//	@Scheduled(cron="0 31 10 * * *")
	@Scheduled(fixedDelay = 300000)
	public void Scheduler() throws IOException, ParseException{
		System.out.println("SAMSUNG CRAWLING 1 START");
		samsung_Crawling1();
		System.out.println("SAMSUNG CRAWLING 1 COMPLETE");
		System.out.println("SAMSUNG CRAWLING 2 START");
		samsung_Crawling2();
		System.out.println("SAMSUNG CRAWLING 2 COMPLETE");
	}

	@Override
	public List<NewsDTO> getNews() {
		return dao.getNews();
	}

	@Override
	public NewsDTO getNewsOne(String url) {
		return dao.getNewsOne(url);
	}

	@Override
	public void addNews(NewsDTO news) {
		dao.addNews(news);
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

						if (diffDay > 365) {
							page = false;
							pageIndex = 1;
							break;
						}

						KeywordExtractor ke = new KeywordExtractor();
						KeywordList kl = ke.extractKeyword(body, true);

						String keyword = "";

						for (int k = 0; k < kl.size(); k++) {
							Keyword kwrd = kl.get(k);

							keyword += kwrd.getString() + " ";
						}

//						list.add(new NewsDTO(title, date, body, brand, cate, keyword, url, bodytext));
						NewsDTO check = null;
						NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, url, bodytext);
						check = getNewsOne(news.getUrl());
						if (check == null) {
							addNews(news);
						}else {
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

		String[] category = { "모바일", "스마트tv", "가전", "반도체ssd", "기타"};

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

						if (diffDay > 365) {
							page = false;
							pageIndex = 1;
							break;
						}

						KeywordExtractor ke = new KeywordExtractor();
						KeywordList kl = ke.extractKeyword(body, true);

						String keyword = "";
						
						for (int k = 0; k < kl.size(); k++) {
							Keyword kwrd = kl.get(k);

							keyword += kwrd.getString() + " ";
						}
						
//						list.add(new NewsDTO(title, date, body, brand, cate, keyword, url, bodytext));
						NewsDTO check = null;
						NewsDTO news = new NewsDTO(title, date, body, brand, cate, keyword, url, bodytext);
						check = getNewsOne(news.getUrl());
						if (check == null) {
							addNews(news);
						}else {
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
}
