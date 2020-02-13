package com.ssafy.edu.help;

import java.lang.management.MemoryUsage;
import java.util.List;

import com.ssafy.edu.dto.Member;
import com.ssafy.edu.dto.ServerLog;

public class AdminManageHelp {

	private float cpuusage;
	private float cpuidle;
	private float freememory;
	private float totalmemory;
	private boolean serverSamsung;
	private boolean serverLg;
	private boolean serverSk;
	private List<ServerLog> log;

	public AdminManageHelp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminManageHelp(float cpuusage, float cpuidle, float freememory, float totalmemory, boolean serverSamsung,
			boolean serverLg, boolean serverSk, List<ServerLog> log) {
		super();
		this.cpuusage = cpuusage;
		this.cpuidle = cpuidle;
		this.freememory = freememory;
		this.totalmemory = totalmemory;
		this.serverSamsung = serverSamsung;
		this.serverLg = serverLg;
		this.serverSk = serverSk;
		this.log = log;
	}

	@Override
	public String toString() {
		return "AdminManageHelp [cpuusage=" + cpuusage + ", cpuidle=" + cpuidle + ", freememory=" + freememory
				+ ", totalmemory=" + totalmemory + ", serverSamsung=" + serverSamsung + ", serverLg=" + serverLg
				+ ", serverSk=" + serverSk + ", log=" + log + "]";
	}

	public float getCpuusage() {
		return cpuusage;
	}

	public void setCpuusage(float cpuusage) {
		this.cpuusage = cpuusage;
	}

	public float getCpuidle() {
		return cpuidle;
	}

	public void setCpuidle(float cpuidle) {
		this.cpuidle = cpuidle;
	}

	public float getFreememory() {
		return freememory;
	}

	public void setFreememory(float freememory) {
		this.freememory = freememory;
	}

	public float getTotalmemory() {
		return totalmemory;
	}

	public void setTotalmemory(float totalmemory) {
		this.totalmemory = totalmemory;
	}

	public boolean isServerSamsung() {
		return serverSamsung;
	}

	public void setServerSamsung(boolean serverSamsung) {
		this.serverSamsung = serverSamsung;
	}

	public boolean isServerLg() {
		return serverLg;
	}

	public void setServerLg(boolean serverLg) {
		this.serverLg = serverLg;
	}

	public boolean isServerSk() {
		return serverSk;
	}

	public void setServerSk(boolean serverSk) {
		this.serverSk = serverSk;
	}

	public List<ServerLog> getLog() {
		return log;
	}

	public void setLog(List<ServerLog> log) {
		this.log = log;
	}

}
