package com.mm.apps.airdb.airconnector.impl;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;

import com.mm.apps.airdb.util.LoggerManager;

public class AirPrintStream extends PrintStream {

	Logger logger = LoggerManager.getInstance().getLogger(this.getClass());
	
	public AirPrintStream(OutputStream out) {
		super(out,true);
	}
	
	public void send(Object data){
		logger.fine(data.toString());
		this.println(data);
		this.write(0);
	}

}


