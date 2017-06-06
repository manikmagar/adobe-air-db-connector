package com.mm.apps.airdb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

public class PropertiesManager {

	private static PropertiesManager instance;
	
	private Properties airDBProperties; 
	
	private PropertiesManager(){
		airDBProperties = new Properties();
		try{
			
			File propFile = new File("./airdb.properties");
			
			InputStream is = null;
			
			if(propFile.exists()){
				is = new FileInputStream(propFile);
			}
			
			if(is == null){
				is  = Thread.currentThread().getContextClassLoader().getResourceAsStream("./airdb.properties");
			}
			if(is == null){
				String airDBPropPath = System.getenv("airdb.properties");
				
					is = new FileInputStream(airDBPropPath);
	
			}
			airDBProperties.load(is);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public static PropertiesManager getInstance(){
		if(instance == null){
			instance = new PropertiesManager();
		}
		return instance;
	}
	
	public String getString(String key){
		String value = "";
		if(airDBProperties.containsKey(key)){
			value = airDBProperties.getProperty(key);
		}
		return value;
	}
	
	public Integer getInt(String key){
		String value = getString(key);
		Integer intValue = new Integer(0);
		if(value.trim().length() > 0){
			intValue = Integer.valueOf(value);
		}
		return intValue;
	}
	
	public Integer getConnectorPort(){
		Integer port = getInt("airdb.connector.port");
		if( port == null || port == 0){
			port = 4000;
		}
		return port;
	}
	
	public Level getLogLevel(){
		String levelStr =  getString("airdb.log.level");
		Level level = Level.parse(levelStr);
		return level;
	}
	
	public String getConfigPath(){
		String configPath = "sql-config.xml";
		if(getString("airdb.connector.sql-config").trim().length() > 0){
			configPath = getString("airdb.connector.sql-config");
		}
		return configPath;
	}
}
