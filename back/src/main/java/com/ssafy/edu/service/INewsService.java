package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.NewsDTO;

public interface INewsService {

	List<NewsDTO> getNews();
	NewsDTO getNewsOne(String url);
	void addNews(NewsDTO news);
}
