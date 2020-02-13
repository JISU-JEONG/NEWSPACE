package com.ssafy.edu.service;

import java.util.List;

import com.ssafy.edu.dto.ServerLog;

public interface ILogService {

	List<ServerLog> getRecentLog();
	void insertLog(ServerLog sl);
}
