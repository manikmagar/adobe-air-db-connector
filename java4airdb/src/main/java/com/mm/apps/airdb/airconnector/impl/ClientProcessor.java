package com.mm.apps.airdb.airconnector.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.UUID;
import java.util.logging.Logger;

import com.mm.apps.airdb.airconnector.IClient;
import com.mm.apps.airdb.airconnector.IClientProcessor;
import com.mm.apps.airdb.airconnector.ISqlTransporter;
import com.mm.apps.airdb.sql.IConnectionManager;
import com.mm.apps.airdb.sql.IParameter;
import com.mm.apps.airdb.sql.IQuery;
import com.mm.apps.airdb.sql.ISqlAdapter;
import com.mm.apps.airdb.sql.ISqlConstants;
import com.mm.apps.airdb.sql.SqlAdapterFactory;
import com.mm.apps.airdb.sql.config.ISqlConfig;
import com.mm.apps.airdb.sql.impl.DnsConnectionManager;
import com.mm.apps.airdb.sql.impl.Parameter;
import com.mm.apps.airdb.util.LoggerManager;
import com.mm.apps.airdb.xml.SqlTransportObjectMapper;

public class ClientProcessor extends Thread implements IClientProcessor{

	private IClient iClient;
	
	private ISqlConfig iSqlConfig;
	
	private AirPrintStream out = null;
	
	private BufferedReader br = null;
	
	private Logger logger = LoggerManager.getInstance().getLogger(this.getClass());

	public void run() {
		try {
			String line =  null;
			
			IConnectionManager manager = new DnsConnectionManager();
			
			Connection connection = manager.getConnection(iSqlConfig);
			
			StringBuffer request = new StringBuffer();
			
			while((line = br.readLine())!= null){
				
				String id  = line.trim();
				
				if(line.equalsIgnoreCase("ping")){
					logger.info("Processing ping request");
					out.send("ping"+UUID.randomUUID());
					continue;
				}
				
				if(!id.equals("end")){
					request.append(id);
					continue;
				}
				
				
				
				SqlTransportObjectMapper mapper = new SqlTransportObjectMapper();
				ISqlTransporter sqlTransporter = mapper.map(request.toString());
				logger.info("Processing request :"+sqlTransporter.getRequestId());
				logger.info(sqlTransporter.toXmlString());
				
				IQuery query = iSqlConfig.getQuerries().getQuery(sqlTransporter.getQueryId());
				if(sqlTransporter.getParameters() != null && sqlTransporter.getParameters().size() > 0){
					for(int i = 0; i < sqlTransporter.getParameters().size(); i++){
						IParameter param = query.getParameters().getParameter(i);
						((Parameter)param).setValue(sqlTransporter.getParameters().get(i));
					}
				}
				if(query == null){
					Transporter transporter = new Transporter();
					transporter.setStatus(ISqlConstants.STATUS_FAILED);
					transporter.setMessage("Query with id "+id+" is not found.");
					transporter.setRequestId(sqlTransporter.getRequestId());
					String xml = transporter.toXmlString();
					out.send(xml);
					continue;
				} 
				ISqlAdapter sqlAdapter = SqlAdapterFactory.getInstance().getSqlAdapter(query, connection);
				String result = sqlAdapter.execute(query);
				Transporter transporter = new Transporter();
				transporter.setData(result);
				transporter.setRequestId(sqlTransporter.getRequestId());
				transporter.setStatus(ISqlConstants.STATUS_PROCESSED);
				transporter.setMessage("Query with id "+id+" is processed.");
				String xml = transporter.toXmlString();
				out.send(xml);
				
				request = new StringBuffer();
			}
			out.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public synchronized void process(final IClient client,final ISqlConfig sqlConfig) {
		iClient = client;
		iSqlConfig = sqlConfig;
		try {
			br = new BufferedReader(new InputStreamReader(iClient.getSocket().getInputStream()));
			out = new AirPrintStream(iClient.getSocket().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.start();
	}


}
