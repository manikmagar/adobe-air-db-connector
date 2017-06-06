package com.mm.apps.airdb.airconnector;

import java.util.List;



public interface ISqlTransporter {

	String getRequestId();
	String getQueryId();
	List getParameters();
	String toXmlString();
}
