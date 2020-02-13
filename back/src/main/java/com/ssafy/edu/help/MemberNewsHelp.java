package com.ssafy.edu.help;

import java.util.List;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.NewsDTO;

public class MemberNewsHelp {

	private List<NewsDTO> list;
	private Member member;
	private int count;
	private List<NewsDTO> recentlist;

	public List<NewsDTO> getRecentlist() {
		return recentlist;
	}

	public void setRecentlist(List<NewsDTO> recentlist) {
		this.recentlist = recentlist;
	}

	public MemberNewsHelp(List<NewsDTO> list, Member member, int count, List<NewsDTO> recentlist) {
		super();
		this.list = list;
		this.member = member;
		this.count = count;
		this.recentlist = recentlist;
	}

	public MemberNewsHelp(List<NewsDTO> list, Member member, int count) {
		super();
		this.list = list;
		this.member = member;
		this.count = count;
	}

	public List<NewsDTO> getList() {
		return list;
	}

	public void setList(List<NewsDTO> list) {
		this.list = list;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public MemberNewsHelp() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "MemberNewsHelp [list=" + list + ", member=" + member + ", count=" + count + ", recentlist=" + recentlist
				+ "]";
	}

}