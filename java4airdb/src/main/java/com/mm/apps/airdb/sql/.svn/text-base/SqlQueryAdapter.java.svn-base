package com.mm.apps.airdb.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mm.apps.airdb.xml.XmlEncoder;


public class SqlQueryAdapter extends SqlAdapterAbstract {

	protected SqlQueryAdapter(Connection conn){
		super(conn);
	}
	
	public String execute(IQuery query) throws Exception {
		
		String result = null;
		
		PreparedStatement pstmt = connection.prepareStatement(query.getSql());
		if(query.getType().equals(QueryType.SELECT)){
			ResultSet rs = pstmt.executeQuery();
			result = XmlEncoder.getInstance().encode(rs);
		} else {
			for(int i=1; i<=query.getParameters().size(); i++){
				IParameter param = query.getParameters().getParameter(i-1);
				if(param.getType().equalsIgnoreCase("varchar")){
					pstmt.setString(i, param.getValue().toString());
				}
			}
			
			 int count = pstmt.executeUpdate();
			 result = "<result updateCount="+count+"></result>";
		}
		return result;
	}

}
