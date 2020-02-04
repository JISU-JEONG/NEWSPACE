package com.ssafy.edu.help;

public class NewsInsertHelp {

	private int news_id;
	private int member_id;

	public NewsInsertHelp(int news_id, int member_id) {
		super();
		this.news_id = news_id;
		this.member_id = member_id;
	}

	public NewsInsertHelp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "NewsInsertHelp [news_id=" + news_id + ", member_id=" + member_id + "]";
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

}
