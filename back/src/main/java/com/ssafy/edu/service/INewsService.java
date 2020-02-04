package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.NewsDTO;
import com.ssafy.edu.help.NewsInsertHelp;

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
	List<NewsDTO> getMeberNews(int member_id);
	void addLikeNews(NewsInsertHelp nih);
	void deleteLikeNews(NewsInsertHelp nih);
	boolean checkLikeNews(NewsInsertHelp nih);
}
