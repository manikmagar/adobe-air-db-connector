package com.mm.apps.airdb.sql;

import java.sql.Connection;

public abstract class SqlAdapterAbstract implements ISqlAdapter {

	protected Connection connection;
	
	protected SqlAdapterAbstract(Connection conn){
		connection = conn; 
	}
	
}
