package com.ssafy.edu.help;

public class NewsLogHelp {

	private int news_log_id;
	private int news_id;
	private int member_id;

	public int getNews_log_id() {
		return news_log_id;
	}

	public void setNews_log_id(int news_log_id) {
		this.news_log_id = news_log_id;
	}

	public int getNews_id() {
		return news_id;
	}

	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public NewsLogHelp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewsLogHelp(int news_log_id, int news_id, int member_id) {
		super();
		this.news_log_id = news_log_id;
		this.news_id = news_id;
		this.member_id = member_id;
	}

	@Override
	public String toString() {
		return "NewsLogHelp [news_log_id=" + news_log_id + ", news_id=" + news_id + ", member_id=" + member_id + "]";
	}

}
