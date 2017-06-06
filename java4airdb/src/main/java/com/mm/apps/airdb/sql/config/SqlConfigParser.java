package com.mm.apps.airdb.sql.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mm.apps.airdb.sql.IParameter;
import com.mm.apps.airdb.sql.IQuery;
import com.mm.apps.airdb.sql.QueryType;
import com.mm.apps.airdb.sql.impl.ConnectionParam;
import com.mm.apps.airdb.sql.impl.Parameter;
import com.mm.apps.airdb.sql.impl.Query;
import com.mm.apps.airdb.sql.impl.SqlParameters;
import com.mm.apps.airdb.sql.impl.SqlQuerries;

public class SqlConfigParser implements ISqlConfigParser {

	public ISqlConfig parseConfig() throws Exception {
		
		return parseConfig("./sql-config.xml");
	}

	public ISqlConfig parseConfig(String xmlConfig) throws Exception {
		
		SqlConfig config = new SqlConfig();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
		InputStream stream = new FileInputStream(new File(xmlConfig));
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.parse(stream);
		
		Element docEle = doc.getDocumentElement();
		
		NodeList nodeList = docEle.getElementsByTagName("dsn");
		
		Node node = nodeList.item(0);
		
		String dnsName = node.getAttributes().getNamedItem("name").getNodeValue();
		String dnsUsername = node.getAttributes().getNamedItem("username").getNodeValue();
		String dnsPassword = node.getAttributes().getNamedItem("password").getNodeValue();
		
		ConnectionParam param = new ConnectionParam();
		param.setDNSConnection(true);
		param.setURL(dnsName);
		param.setUsername(dnsUsername);
		param.setPassword(dnsPassword);
		
		config.setConnectionParam(param);

		SqlQuerries querries = new SqlQuerries();
		
		NodeList queryNodeList = docEle.getElementsByTagName("select");
		for (int i = 0; i < queryNodeList.getLength(); i++){
			Node qNode = queryNodeList.item(i);
			IQuery query = getQuery(qNode);
			((Query)query).setType(QueryType.SELECT);
			querries.addQuery(query);
		}
		
		queryNodeList = docEle.getElementsByTagName("update");
		for (int i = 0; i < queryNodeList.getLength(); i++){
			Node qNode = queryNodeList.item(i);
			IQuery query = getQuery(qNode);
			((Query)query).setType(QueryType.UPDATE);
			querries.addQuery(query);
		}
		
		queryNodeList = docEle.getElementsByTagName("procedure");
		for (int i = 0; i < queryNodeList.getLength(); i++){
			Node qNode = queryNodeList.item(i);
			IQuery query = getQuery(qNode);
			((Query)query).setType(QueryType.STORED_PROCEDURE);
			querries.addQuery(query);
		}
		
		config.setQuerries(querries);
		return config;
	}

	private IQuery getQuery(Node ele){
		Query query = new Query();
		query.setParameters(new SqlParameters());
		String id = ele.getAttributes().item(0).getNodeValue().trim();
		query.setId(id);
		
		NodeList nodeList = ele.getChildNodes();
		
		for(int i=0; i<nodeList.getLength(); i++){
			Node node = nodeList.item(i);
			if(node.getNodeName().equalsIgnoreCase("sql")){
				query.setSql(node.getTextContent().trim());
			}
			
			if(node.getNodeName().equalsIgnoreCase("parameters")){
				NodeList paramNodes = node.getChildNodes();
				for (int j = 0; j < paramNodes.getLength(); j++) {
					Node paramNode = paramNodes.item(j);
					if(paramNode.getNodeType() == Node.ELEMENT_NODE){
						IParameter param = getParameter(paramNode);
						query.getParameters().addParameter(param);
					}
				}
			}
		}
		
		if(query.getSql().trim().length() <= 0){
			System.err.println("Invalid SQL with id "+ query.getId());
		}
		
		return query;
	}
	
	private IParameter getParameter(Node node){
		Parameter param = new Parameter();
		
		NodeList childs = node.getChildNodes();
		
		for (int i = 0; i < childs.getLength(); i++) {
			Node child = childs.item(i);
			if(child.getNodeName().equalsIgnoreCase("name")){
				param.setName(child.getTextContent());
			}
			if(child.getNodeName().equalsIgnoreCase("type")){
				param.setType(child.getTextContent());
			}
			if(child.getNodeName().equalsIgnoreCase("typeVal")){
				param.setTypeVal(Integer.valueOf(child.getTextContent()));
			}
		}
		
		return param;
	}
	
}
