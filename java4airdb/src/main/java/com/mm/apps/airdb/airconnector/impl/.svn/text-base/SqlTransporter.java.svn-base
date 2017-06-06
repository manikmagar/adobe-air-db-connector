package com.mm.apps.airdb.airconnector.impl;

import java.util.List;

import com.mm.apps.airdb.airconnector.ISqlTransporter;
import com.mm.apps.airdb.xml.XmlEncoder;

public class SqlTransporter implements ISqlTransporter {

	private String requestId;
	private String queryId;
	private List parameters;
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public List getParameters() {
		return parameters;
	}

	public void setParameters(List parameters) {
		this.parameters = parameters;
	}

	public String toXmlString() {
		String xml = "";
		try {
			xml = XmlEncoder.getInstance().convertToXml(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

}
