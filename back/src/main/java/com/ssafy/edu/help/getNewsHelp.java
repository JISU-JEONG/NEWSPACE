package com.ssafy.edu.help;

import com.ssafy.edu.dto.NewsDTO;

public class getNewsHelp {

	private NewsDTO news;
	private boolean is_like;

	public getNewsHelp(NewsDTO news, boolean is_like) {
		super();
		this.news = news;
		this.is_like = is_like;
	}

	public getNewsHelp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "getNewsHelp [news=" + news + ", is_like=" + is_like + "]";
	}

	public NewsDTO getNews() {
		return news;
	}

	public void setNews(NewsDTO news) {
		this.news = news;
	}

	public boolean isIs_like() {
		return is_like;
	}

	public void setIs_like(boolean is_like) {
		this.is_like = is_like;
	}

}
