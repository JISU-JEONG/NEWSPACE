package com.ssafy.edu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.help.NewsSearch;

@Repository
public class MemberDao {
	
	String ns="ssafy.member.";
	
	@Autowired
	private SqlSession SQL;
	
	public Member getMember(Member member){
		return SQL.selectOne(ns+"getMember",member);
	}

	public Member getEmail(String email) {
		return SQL.selectOne(ns+"getEmail",email);
	}

	public int insertMember(Member member) {
		return SQL.insert(ns+"insertMember",member);
	}

	public List<Member> getMember() {
		return SQL.selectList(ns+"getMember");
	}

	public Member getMember(int member_id) {
		return SQL.selectOne(ns+"getMemberOne", member_id);
	}

	public Member emailcheck(Member member) {
		return SQL.selectOne(ns+"emailcheck", member);
	}

	public void emailupdate(Member member) {
		SQL.update(ns+"emailupdate", member);
		
	}

	public void updatemember(Member member) {
		SQL.update(ns+"updatemember", member);
	}

	public List<Member> emailSendList(String keyword) {
		// TODO Auto-generated method stub
		
		NewsSearch find = new NewsSearch(keyword.split(" "), "ALL");
		
		return SQL.selectList(ns+"emailSendList", find);
	}

	public List<Member> getNormalMember() {
		return SQL.selectList(ns+"getNormalMember");
		
	}

	public int getMemberId(String email) {
		// TODO Auto-generated method stub
		return SQL.selectOne(ns+"getMemberId", email);
	}

	public List<Member> getAdminMember() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns+"getAdminMember");
	}

	public void deleteMember(int member_id) {
		// TODO Auto-generated method stub
		SQL.delete(ns+"deleteMember");
	}
}
