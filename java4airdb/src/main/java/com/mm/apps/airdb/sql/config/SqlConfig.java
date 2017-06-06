package com.mm.apps.airdb.sql.config;

import com.mm.apps.airdb.sql.IConnectionParam;
import com.mm.apps.airdb.sql.ISqlQuerries;

public class SqlConfig implements ISqlConfig {

	private String dns = "";
	private ISqlQuerries querries;
	private IConnectionParam connectionParam;
	
	
	public IConnectionParam getConnectionParam() {
		return connectionParam;
	}
	public void setConnectionParam(IConnectionParam connectionParam) {
		this.connectionParam = connectionParam;
	}
	public String getDns() {
		return dns;
	}
	public void setDns(String dsn) {
		this.dns = dsn;
	}
	public ISqlQuerries getQuerries() {
		return querries;
	}
	public void setQuerries(ISqlQuerries querries) {
		this.querries = querries;
	}
	
	
	
}
