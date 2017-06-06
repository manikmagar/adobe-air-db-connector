package com.mm.apps.airdb.xml;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import com.mm.apps.airdb.airconnector.ISqlTransporter;
import com.mm.apps.airdb.airconnector.impl.SqlTransporter;

import java.io.*;
import java.util.ArrayList;

public class SqlTransportObjectMapper extends DefaultHandler {

	// Local Customer object to collect
	// customer XML data.
	private SqlTransporter transporter = new SqlTransporter();

	// Buffer for collecting data from
	// the "characters" SAX event.
	private CharArrayWriter contents = new CharArrayWriter();

	// Override methods of the DefaultHandler class
	// to gain notification of SAX Events.
	//
	// See org.xml.sax.ContentHandler for all available events.
	//

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes attr) throws SAXException {

		contents.reset();

	}

	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {

		if (localName.equals("queryId")) {
			transporter.setQueryId(contents.toString());
		}

		if (localName.equals("requestId")) {
			transporter.setRequestId(contents.toString());
		}

		if (localName.equals("item")) {
			transporter.getParameters().add(contents.toString());
		}

	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {

		contents.write(ch, start, length);

	}

	public ISqlTransporter getSqlTransporter() {
		return transporter;
	}

	public ISqlTransporter map(String xml) {

		try {
			transporter.setParameters(new ArrayList());
			// Create SAX 2 parser...
			XMLReader xr = XMLReaderFactory.createXMLReader();

			// Set the ContentHandler...
			xr.setContentHandler(this);

			// Parse the file...
			xr.parse(new InputSource(new StringReader(xml)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return transporter;
	}
}
