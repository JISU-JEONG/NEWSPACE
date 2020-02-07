package com.ssafy.edu.help;

public class NewsStatusHelp {

	private String brand;
	private int count;

	@Override
	public String toString() {
		return "NewsStatusHelp [brand=" + brand + ", count=" + count + "]";
	}

	public NewsStatusHelp(String brand, int count) {
		super();
		this.brand = brand;
		this.count = count;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
