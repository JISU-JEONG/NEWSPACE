package com.ssafy.edu.help;

public class NewsKeyword {

	private int news_id;
	private String keyword;

	public int getNews_id() {
		return news_id;
	}

	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public NewsKeyword(int news_id, String keyword) {
		super();
		this.news_id = news_id;
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "NewsKeyword [news_id=" + news_id + ", keyword=" + keyword + "]";
	}

	public NewsKeyword() {
		super();
		// TODO Auto-generated constructor stub
	}

}
