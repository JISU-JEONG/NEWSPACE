package com.ssafy.edu.help;

public class NewsKeywordCounter {

	private String keyword;
	private int count;

	public NewsKeywordCounter(String keyword, int count) {
		super();
		this.keyword = keyword;
		this.count = count;
	}

	public NewsKeywordCounter() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "NewsKeywordCounter [keyword=" + keyword + ", count=" + count + "]";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
