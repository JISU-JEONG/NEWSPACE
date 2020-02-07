package com.ssafy.edu.help;

import java.util.Arrays;

public class SearchChart {

	private String brand;
	private int datecount;
	private int value;
	private String[] search;
	private String keyword;
	private String date;

	public SearchChart(String brand, int datecount, int value, String[] search, String keyword, String date) {
		super();
		this.brand = brand;
		this.datecount = datecount;
		this.value = value;
		this.search = search;
		this.keyword = keyword;
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public SearchChart(String brand, int datecount, int value, String[] search, String keyword) {
		super();
		this.brand = brand;
		this.datecount = datecount;
		this.value = value;
		this.search = search;
		this.keyword = keyword;
	}

	public SearchChart(String brand, int datecount, int value, String[] search) {
		super();
		this.brand = brand;
		this.datecount = datecount;
		this.value = value;
		this.search = search;
	}

	@Override
	public String toString() {
		return "SearchChart [brand=" + brand + ", datecount=" + datecount + ", value=" + value + ", search="
				+ Arrays.toString(search) + ", keyword=" + keyword + "]";
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getDatecount() {
		return datecount;
	}

	public void setDatecount(int datecount) {
		this.datecount = datecount;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String[] getSearch() {
		return search;
	}

	public void setSearch(String[] search) {
		this.search = search;
	}

}
