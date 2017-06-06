package com.mm.apps.airdb.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XmlEncoder implements IXmlEncoder {

	private static IXmlEncoder instance;
	
	private XmlEncoder(){
		
	}
	
	public static IXmlEncoder getInstance(){
		if(instance == null){
			instance = new XmlEncoder();
		}
		return instance;
	}
	
	public String encode(ResultSet rs) throws Exception {
		ResultSetMetaData rsmd;
		rsmd = rs.getMetaData();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc = db.newDocument();

		Element ele = doc.createElement("result");
		Integer count = 0;
		while(rs.next()){
			count++;
			Node rowNode = doc.createElement("row"); 
			for (int i=1; i<= rsmd.getColumnCount(); i++){
				 Node colNode = doc.createElement(rsmd.getColumnName(i)) ;
				 colNode.setTextContent(rs.getObject(colNode.getNodeName()).toString());
				 rowNode.appendChild(colNode);
			}
			ele.appendChild(rowNode);
		}
		ele.setAttribute("size", count.toString());
		doc.appendChild(ele);
		return toXmlString(doc);
	}
	
	public String toXmlString(final Document doc){
		String xmlString = "";
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			
			xmlString = result.getWriter().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlString;
	}
	
	public String convertToXml(Object obj) throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc = db.newDocument();

		Element ele = doc.createElement(obj.getClass().getSimpleName().toLowerCase());
		
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			field.setAccessible(true);
			Node node = doc.createElement(field.getName());
			String value = field.get(obj).toString();
			if(value.startsWith("<?xml")){
				Element xmlEle = stringToElement(value);
				Node xmlNode = doc.importNode(xmlEle, true);
				node.appendChild(xmlNode);
			} else {
				node.setTextContent(value);	
			}
			
			ele.appendChild(node);
		}
		
		doc.appendChild(ele);
		
		return toXmlString(doc);
	}
	
	public Element stringToElement(String xmlString) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		Document doc = builder.parse(new InputSource(new StringReader(xmlString)));

		return doc.getDocumentElement();
	}

}
