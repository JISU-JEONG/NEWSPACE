package com.ssafy.edu.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.edu.dto.ServerLog;

@Repository
public class LogServiceDao {

	String ns = "ssafy.log.";
	@Autowired
	private SqlSession SQL;
	
	public List<ServerLog> getRecentLog() {
		// TODO Auto-generated method stub
		return SQL.selectList(ns + "getRecentLog");
	}

	public void insertLog(ServerLog sl) {
		SQL.insert(ns + "insertLog", sl);
	}
}
