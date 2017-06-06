package com.mm.apps.airdb.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import com.mm.apps.airdb.xml.XmlEncoder;


public class SqlSPAdapter extends SqlAdapterAbstract {

	protected SqlSPAdapter(Connection conn){
		super(conn);
	}
	
	public String execute(IQuery query) throws Exception {

		CallableStatement stmt = connection.prepareCall(query.getSql());
		
		ResultSet rs = stmt.executeQuery();
		
		String result = XmlEncoder.getInstance().encode(rs);

		return result;
	}

}
