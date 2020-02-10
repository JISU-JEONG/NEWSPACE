package com.ssafy.edu.help;

import java.util.List;

import com.ssafy.edu.dto.Member;

public class AdminManageHelp {

	private List<Member> memberList;
	private List<Boolean> checkCrawling;

	public AdminManageHelp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AdminManageHelp [memberList=" + memberList + ", checkCrawling=" + checkCrawling + "]";
	}

	public AdminManageHelp(List<Member> memberList, List<Boolean> checkCrawling) {
		super();
		this.memberList = memberList;
		this.checkCrawling = checkCrawling;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public List<Boolean> getCheckCrawling() {
		return checkCrawling;
	}

	public void setCheckCrawling(List<Boolean> checkCrawling) {
		this.checkCrawling = checkCrawling;
	}

}
