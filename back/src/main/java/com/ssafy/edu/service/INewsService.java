package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.NewsDTO;

public interface INewsService {

	NewsDTO getNewsOne(String url);
	void addNews(NewsDTO news);
	List<NewsDTO> getSamsungNews();
	List<NewsDTO> getLgNews();
	List<NewsDTO> getSkNews();
	List<NewsDTO> getSamsungRecent();
	List<NewsDTO> getLgRecent();
	List<NewsDTO> getSkRecent();
	NewsDTO getNews(int news_id);
	List<NewsDTO> findNewsSamsung(String[] str);
	List<NewsDTO> findNewsLg(String[] str);
	List<NewsDTO> findNewsSk(String[] str);
	List<NewsDTO> getAllNews();
	List<NewsDTO> getAllNewsRecent();
	List<NewsDTO> findNewsAll(String[] str);
	String[] getUserKeyword();
	List<NewsDTO> getKeywordNews(String keyword);
}
