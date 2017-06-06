package com.mm.apps.airdb.sql;

public interface ISqlAdapter {

	String execute(IQuery query) throws Exception;
	
}
