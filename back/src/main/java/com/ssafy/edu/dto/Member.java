package com.ssafy.edu.dto;

public class Member {
	
	private String email;
    private String password;
    private String name;
    private String[] inputkeyword;
    private String keyword;
    private String type;
    private String tokenname;
    
	public String getTokenname() {
		return tokenname;
	}
	public void setTokenname(String tokenname) {
		this.tokenname = tokenname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getInputkeyword() {
		return inputkeyword;
	}
	public void setInputkeyword(String[] inputkeyword) {
		this.inputkeyword = inputkeyword;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member(String email, String password, String name, String[] inputkeyword, String keyword, String type,
			String tokenname) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.inputkeyword = inputkeyword;
		this.keyword = keyword;
		this.type = type;
		this.tokenname = tokenname;
	}
	@Override
	public String toString() {
		return "Member [email=" + email + ", password=" + password + ", name=" + name + ", keyword=" + keyword + ", type=" + type + ", tokenname=" + tokenname
				+ "]";
	}
	
    
    
    
}