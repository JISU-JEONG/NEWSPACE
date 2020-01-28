package com.ssafy.edu.help;

import java.util.Arrays;

public class NewsSearch {

	private String[] search;
	private String brand;

	@Override
	public String toString() {
		return "NewsSearch [search=" + Arrays.toString(search) + ", brand=" + brand + "]";
	}

	public NewsSearch(String[] search, String brand) {
		super();
		this.search = search;
		this.brand = brand;
	}

	public NewsSearch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String[] getSearch() {
		return search;
	}

	public void setSearch(String[] search) {
		this.search = search;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

}
