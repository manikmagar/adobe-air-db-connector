package com.mm.apps.airdb.sql.impl;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import com.mm.apps.airdb.sql.IConnectionManager;
import com.mm.apps.airdb.sql.IConnectionParam;
import com.mm.apps.airdb.sql.config.ISqlConfig;

public class DnsConnectionManager implements IConnectionManager {

	private Connection conn = null;
	
	public Connection getConnection(ISqlConfig config) {
		if(conn == null){
			IConnectionParam param = config.getConnectionParam();
			 try {
				Driver d = (Driver)Class.forName
				 ("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
				if(param.getUsername().trim().length()>0){
					// USERNAME & PASSWORD is configured, let's use it for connection
					conn = DriverManager.getConnection("jdbc:odbc:"+param.getURL(),param.getUsername(),param.getPassword());	
				} else {
					conn = DriverManager.getConnection("jdbc:odbc:"+param.getURL());	
				}
		
			 } catch (Exception e) {
				throw new RuntimeException(e);
			 }
		}
		return conn;
	}

}
