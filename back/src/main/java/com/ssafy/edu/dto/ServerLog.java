package com.ssafy.edu.dto;

public class ServerLog {

	private int log_id;
	private int member_id;
	private String log_content;
	private String log_date;

	public ServerLog(int log_id, int member_id, String log_content, String log_date) {
		super();
		this.log_id = log_id;
		this.member_id = member_id;
		this.log_content = log_content;
		this.log_date = log_date;
	}

	public ServerLog(int member_id, String log_content) {
		super();
		this.member_id = member_id;
		this.log_content = log_content;
	}

	public ServerLog() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getLog_id() {
		return log_id;
	}

	public void setLog_id(int log_id) {
		this.log_id = log_id;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getLog_content() {
		return log_content;
	}

	public void setLog_content(String log_content) {
		this.log_content = log_content;
	}

	public String getLog_date() {
		return log_date;
	}

	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}

	@Override
	public String toString() {
		return "ServerLog [log_id=" + log_id + ", member_id=" + member_id + ", log_content=" + log_content
				+ ", log_date=" + log_date + "]";
	}

}
