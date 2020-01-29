package com.ssafy.edu.dto;

public class CommentDTO {

	private int comment_id;
	private int member_id;
	private String member_name;
	private int news_id;
	private String comment_text;
	private String comment_date;

	@Override
	public String toString() {
		return "CommentDTO [comment_id=" + comment_id + ", member_id=" + member_id + ", member_name=" + member_name
				+ ", news_id=" + news_id + ", comment_text=" + comment_text + ", comment_date=" + comment_date + "]";
	}

	public CommentDTO(int comment_id, int member_id, String member_name, int news_id, String comment_text,
			String comment_date) {
		super();
		this.comment_id = comment_id;
		this.member_id = member_id;
		this.member_name = member_name;
		this.news_id = news_id;
		this.comment_text = comment_text;
		this.comment_date = comment_date;
	}

	public CommentDTO(String member_name, int news_id, String comment_text) {
		super();
		this.member_name = member_name;
		this.news_id = news_id;
		this.comment_text = comment_text;
	}

	public CommentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getNews_id() {
		return news_id;
	}

	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}

	public String getComment_text() {
		return comment_text;
	}

	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}

	public String getComment_date() {
		return comment_date;
	}

	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

}
