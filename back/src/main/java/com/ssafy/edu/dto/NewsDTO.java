package com.ssafy.edu.dto;

public class NewsDTO {

	private int news_id;
	private String title;
	private String date;
	private String body;
	private String brand;
	private String category;
	private String keyword;
	private String url;
	private String bodytext;
	private String thumb;


	

	@Override
	public String toString() {
		return "NewsDTO [news_id=" + news_id + ", title=" + title + ", date=" + date + ", body=" + body + ", brand="
				+ brand + ", category=" + category + ", keyword=" + keyword + ", url=" + url + ", bodytext=" + bodytext
				+ ", thumb=" + thumb + "]";
	}


	public NewsDTO(String title, String date, String body, String brand, String category, String keyword, String url,
			String bodytext, String thumb) {
		super();
		this.title = title;
		this.date = date;
		this.body = body;
		this.brand = brand;
		this.category = category;
		this.keyword = keyword;
		this.url = url;
		this.bodytext = bodytext;
		this.thumb = thumb;
	}


	public NewsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public NewsDTO(int news_id, String title, String date, String body, String brand, String category, String keyword,
			String url, String bodytext) {
		super();
		this.news_id = news_id;
		this.title = title;
		this.date = date;
		this.body = body;
		this.brand = brand;
		this.category = category;
		this.keyword = keyword;
		this.url = url;
		this.bodytext = bodytext;
	}


	public NewsDTO(String title, String date, String brand, String category, String url, String thumb) {
		super();
		this.title = title;
		this.date = date;
		this.brand = brand;
		this.category = category;
		this.url = url;
		this.thumb = thumb;
	}

	public NewsDTO(String url, String thumb) {
		super();
		this.url = url;
		this.thumb = thumb;
	}

	public int getNews_id() {
		return news_id;
	}


	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBodytext() {
		return bodytext;
	}

	public void setBodytext(String bodytext) {
		this.bodytext = bodytext;
	}
	
	public String getThumb() {
		return thumb;
	}


	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public NewsDTO(String title, String date, String body, String brand, String category, String keyword, String url,
			String bodytext) {
		super();
		this.title = title;
		this.date = date;
		this.body = body;
		this.brand = brand;
		this.category = category;
		this.keyword = keyword;
		this.url = url;
		this.bodytext = bodytext;
	}

}
