package com.ssafy.edu.help;

import java.util.List;

import com.ssafy.edu.dto.NewsDTO;

public class UserKeywordNews {

	private String date;
	private List<NewsDTO> list;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<NewsDTO> getList() {
		return list;
	}

	public void setList(List<NewsDTO> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "UserKeywordNews [date=" + date + ", list=" + list + "]";
	}

	public UserKeywordNews(String date, List<NewsDTO> list) {
		super();
		this.date = date;
		this.list = list;
	}

	public UserKeywordNews(String date) {
		super();
		this.date = date;
	}

	public UserKeywordNews() {
		super();
		// TODO Auto-generated constructor stub
	}

}
