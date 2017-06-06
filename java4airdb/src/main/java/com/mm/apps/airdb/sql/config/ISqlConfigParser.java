package com.mm.apps.airdb.sql.config;

public interface ISqlConfigParser {

	ISqlConfig parseConfig() throws Exception;
	
	ISqlConfig parseConfig(String xmlConfig) throws Exception;
	
}
