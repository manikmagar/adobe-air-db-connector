package com.mm.apps.airdb.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;

public class LoggerManager {

	private static LoggerManager instance;
	
	private LogManager utilLogManager; 
	
	private FileHandler handler = null;
	
	private Map<String, Logger> loggerMap;
	
	private PropertiesManager propManager = PropertiesManager.getInstance();
	
	private LoggerManager(){
		utilLogManager = LogManager.getLogManager();
		loggerMap = new HashMap<String, Logger>();
	}
	
	public static LoggerManager getInstance(){
		if(instance == null){
			instance = new LoggerManager();
		}
		return instance;
	}
	
	private Handler getFileHandler(){
		if(handler == null){
			try {
				handler = new FileHandler("AirDB.log");
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.setFormatter(new SimpleFormatter());
			handler.setLevel(propManager.getLogLevel());	
		}
		return handler;
	}
	
	
	public Logger getLogger(Class clazz){
		if(!loggerMap.containsKey(clazz.getName())){
			Logger logger = Logger.getLogger(clazz.getName());
			logger.setLevel(propManager.getLogLevel());
			logger.addHandler(getFileHandler());
			loggerMap.put(clazz.getName(), logger);
		}
		return loggerMap.get(clazz.getName());
	}
	
	
	
	protected void finalize() throws Throwable {
		if(handler != null){
			handler.close();
		}
	}
}
