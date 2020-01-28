package com.ssafy.edu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.Member;

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

}
