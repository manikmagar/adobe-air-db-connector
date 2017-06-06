package com.mm.apps.airdb.xml;

import java.sql.ResultSet;

public interface IXmlEncoder {

	String encode(ResultSet rs) throws Exception;
	
	String convertToXml(Object obj) throws Exception;
}
