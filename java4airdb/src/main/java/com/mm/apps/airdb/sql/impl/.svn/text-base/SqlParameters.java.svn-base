package com.mm.apps.airdb.sql.impl;

import java.util.ArrayList;
import java.util.List;

import com.mm.apps.airdb.sql.IParameter;
import com.mm.apps.airdb.sql.ISqlParameters;

public class SqlParameters implements ISqlParameters {

	private List<IParameter> parameterMap = new ArrayList<IParameter>();
	
	public void addParameter(IParameter param) {
		parameterMap.add(param);
	}

	public IParameter getParameter(int index) {
		return parameterMap.get(index);
	}

	public int size(){
		return parameterMap.size();
	}
	

}
