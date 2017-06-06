package com.mm.apps.airdb.airconnector.impl;

import com.mm.apps.airdb.airconnector.ITransporter;
import com.mm.apps.airdb.xml.XmlEncoder;

public class Transporter implements ITransporter {

	private String message;
	private int status;
	private String data;
	private String requestId;
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
