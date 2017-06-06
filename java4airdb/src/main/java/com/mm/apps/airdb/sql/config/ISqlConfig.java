package com.mm.apps.airdb.sql.config;

import com.mm.apps.airdb.sql.IConnectionParam;
import com.mm.apps.airdb.sql.ISqlQuerries;

public interface ISqlConfig {
	
	String getDns();
	
	IConnectionParam getConnectionParam();
	
	ISqlQuerries getQuerries();
	
}
