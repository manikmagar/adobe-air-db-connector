package com.mm.apps.airdb.airconnector.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mm.apps.airdb.airconnector.IClient;
import com.mm.apps.airdb.airconnector.IClientProcessor;
import com.mm.apps.airdb.airconnector.IServerConnector;
import com.mm.apps.airdb.sql.config.ISqlConfig;
import com.mm.apps.airdb.sql.config.SqlConfigParser;
import com.mm.apps.airdb.util.LoggerManager;
import com.mm.apps.airdb.util.PropertiesManager;

public class ServerConnector implements IServerConnector {

	private static IServerConnector instance;
	
	private boolean redSignal = false;
	
	private Logger logger = LoggerManager.getInstance().getLogger(this.getClass());
	
	private ServerConnector(){
		
	}
	
	public static IServerConnector getInstance(){
		if(instance == null){
			instance = new ServerConnector();
			
		}
		return instance;
	}
	
	public void startServer() throws Exception{
		startServer(4000);
	}
	
	public void startServer(int port) throws Exception{
		logger.info("Starting server at port "+port);
		SqlConfigParser parser = new SqlConfigParser();
		
		final ISqlConfig sqlConfig = parser.parseConfig(PropertiesManager.getInstance().getConfigPath());
		
		final ServerSocket serverSocket = new ServerSocket(port);
		
		Thread serverThread = new Thread(new Runnable(){
			public void run(){
				
					while(true){
						if(redSignal == false){
							IClient client = null;
							try{	
								logger.info("Waiting for client message ...");
								Socket socket = serverSocket.accept();
								// avoid delay in request-response for real-time communication.
								socket.setTcpNoDelay(true);
								client = new Client();
								client.setSocket(socket);
								IClientProcessor clientProcessor = new ClientProcessor();
								clientProcessor.process(client,sqlConfig);
							} catch (IOException e) {
								logger.log(Level.WARNING, e.getMessage(), e);
							}
						} else {
							logger.info("Red Signal is ON, stopping the server thread ");
							break;
						}
					}
			}
		});
		serverThread.start();
	}

	public void stopServer() {
		redSignal = true;
	}

}
