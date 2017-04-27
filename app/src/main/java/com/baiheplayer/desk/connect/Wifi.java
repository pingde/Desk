package com.baiheplayer.desk.connect;

import java.io.Serializable;

public class Wifi implements Serializable{
	private String name;
	// 信号强度
	private int dbm;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDbm() {
		return dbm;
	}

	public void setDbm(int dbm) {
		this.dbm = dbm;
	}

}
