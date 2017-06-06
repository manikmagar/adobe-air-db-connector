package com.mm.apps.airdb.airconnector.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import com.mm.apps.airdb.airconnector.IClient;
import com.mm.apps.airdb.airconnector.IClientData;

public class Client implements IClient {

	private Long id;
	private Socket socket;
	private Long port;
	private IClientData data; 
	
	public IClientData getData() {
		return data;
	}
	public void setData(IClientData data) {
		this.data = data;
	}
	public Client(){
		id = UUID.randomUUID().getMostSignificantBits();
	}
	public Long getId() {
		return id;
	}

	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public Long getPort() {
		return port;
	}
	public void setPort(Long port) {
		this.port = port;
	}
	
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof IClient)) return false;
		return this.getId().equals(((IClient)obj).getId());
	}
	
	public int hashCode() {
		return this.getId().intValue();
	}

}
