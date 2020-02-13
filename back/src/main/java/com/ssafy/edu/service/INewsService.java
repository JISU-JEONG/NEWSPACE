package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.NewsDTO;
import com.ssafy.edu.help.NewsInsertHelp;
import com.ssafy.edu.help.NewsKeywordCounter;
import com.ssafy.edu.help.NewsStatusHelp;
import com.ssafy.edu.help.SearchChart;

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
	List<List<NewsKeywordCounter>> getChartKeyword();
	List<SearchChart> getSearchChartKeyword(String search);
	List<NewsStatusHelp> getNewsStatus();
	List<Boolean> getServerStatus();
	boolean checkNewsLog(NewsInsertHelp nih);
	void updateNewsLog(NewsInsertHelp nih);
	void insertNewsLog(NewsInsertHelp nih);
	List<NewsDTO> getMyRecentNews(int member_id);
}
