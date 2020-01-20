package com.ssafy.edu.dto;

import java.sql.Date;

public class PostsDTO {
//	Post_id, Title, Content, Writer
	private int post_id;
	private String title;
	private String content;
	private String writer;
	private Date created_at;
	public int getPost_id() {
		return post_id;
	}
	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public PostsDTO(int post_id, String title, String content, String writer, Date created_at) {
		super();
		this.post_id = post_id;
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.created_at = created_at;
	}
	@Override
	public String toString() {
		return "PostsDTO [post_id=" + post_id + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", created_at=" + created_at + "]";
	}
}
