package com.mm.apps.airdb;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mm.apps.airdb.airconnector.IServerConnector;
import com.mm.apps.airdb.airconnector.impl.ServerConnector;
import com.mm.apps.airdb.util.LoggerManager;
import com.mm.apps.airdb.util.PropertiesManager;

public class Java4AirDB {

	private static Logger logger = LoggerManager.getInstance().getLogger(Java4AirDB.class);
	
	private Java4AirDB(){

	}
	
	
	public static void main(String[] args) {

		logger.log(Level.INFO,"Starting Server");
		
		
		
		PropertiesManager propManager = PropertiesManager.getInstance();
		
		int port = propManager.getConnectorPort();
		
		logger.log(Level.INFO,"Starting Server at port "+port);
		
		IServerConnector serverConnector = ServerConnector.getInstance();
		
		try {
			serverConnector.startServer(port);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

}
