package com.mm.apps.airdb.sql.impl;

import java.util.HashMap;
import java.util.Map;

import com.mm.apps.airdb.sql.IQuery;
import com.mm.apps.airdb.sql.ISqlQuerries;

public class SqlQuerries implements ISqlQuerries {

	private Map<String, IQuery> queryMap = new HashMap<String, IQuery>(); 
	
	
	public void addQuery(IQuery query) {
		queryMap.put(query.getId(), query);
	}

	public IQuery getQuery(String id) {
		return queryMap.get(id);
	}

}
