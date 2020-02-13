package com.ssafy.edu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.dao.LogServiceDao;
import com.ssafy.edu.dto.ServerLog;

@Service
public class LogService implements ILogService {

	@Autowired
	private LogServiceDao dao;

	@Override
	public List<ServerLog> getRecentLog() {
		// TODO Auto-generated method stub
		return dao.getRecentLog();
	}

	@Override
	public void insertLog(ServerLog sl) {
		dao.insertLog(sl);
	}
	
	
}
