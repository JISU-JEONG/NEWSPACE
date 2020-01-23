package com.ssafy.edu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.NewsDTO;
import com.ssafy.edu.help.NewsKeyword;

@Repository
public class NewsServiceDao {
	
	String ns="ssafy.news.";
	@Autowired
	private SqlSession SQL;
	
	public NewsDTO getNews(int news_id){
		return SQL.selectOne(ns+"getNews", news_id);
	}

	public NewsDTO getNewsOne(String url) {
		// TODO Auto-generated method stub
		return SQL.selectOne(ns+"getNewsOne", url);
	}

	public Object addNews(NewsDTO news) {
		// TODO Auto-generated method stub
		return SQL.insert(ns+"addNews", news);
	}

	public List<NewsDTO> getSamsungNews() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns+"getSamsungNews");
	}

	public List<NewsDTO> getLgNews() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns+"getLgNews");
	}

	public List<NewsDTO> getSkNews() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns+"getSkNews");
	}

	public List<NewsDTO> getSamsungRecent() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns+"getSamsungRecent");
	}

	public List<NewsDTO> getLgRecent() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns+"getLgRecent");
	}

	public List<NewsDTO> getSkRecent() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns+"getSkRecent");
	}

	public int getNewsCountSamsung() {
		// TODO Auto-generated method stub
		return SQL.selectOne(ns + "getNewsCountSamsung");
	}

	public List<String> getNewsKeywordSamsung() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns + "getNewsKeywordSamsung");
	}

	public void addNewsKeyword(NewsKeyword news) {
		SQL.insert(ns + "addNewsKeyword", news);
	}

	public NewsKeyword newsKeywordValid(int news_id) {
		return SQL.selectOne(ns + "newsKeywordValid", news_id);
	}

	public void updateNewsKeyword(NewsKeyword news) {
		SQL.update(ns + "updateNewsKeyword", news);
	}
}
