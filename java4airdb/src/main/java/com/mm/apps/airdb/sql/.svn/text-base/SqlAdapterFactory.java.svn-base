package com.mm.apps.airdb.sql;

import java.sql.Connection;


public class SqlAdapterFactory implements ISqlAdapterFactory {

	private static ISqlAdapterFactory factory;
	
	private SqlAdapterFactory(){
		
	}
	
	public static ISqlAdapterFactory getInstance(){
		if(factory == null){
			factory = new SqlAdapterFactory();
		}
		return factory;
	}
	
	public ISqlAdapter getSqlAdapter(IQuery query,Connection conn) {
		ISqlAdapter sqlAdapter = null;
		
		switch (query.getType()) {
		case SELECT:
			sqlAdapter = new SqlQueryAdapter(conn);
			break;
		case UPDATE:
			sqlAdapter = new SqlQueryAdapter(conn);
			break;
		case STORED_PROCEDURE:
			sqlAdapter = new SqlSPAdapter(conn);
			break;
		default:
			break;
		}

		return sqlAdapter;
	}

}
