package com.mm.apps.airdb.sql
{
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	
	import mx.rpc.xml.SimpleXMLEncoder;
	import mx.utils.UIDUtil;
	
	public class SqlTransporter implements ISqlTransporter
	{
		private var _queryID:String;
		private var _parameters:Array;
		private var _requestId:String;
		
		public function SqlTransporter(strQueryId:String)
		{
			_queryID = strQueryId;
			_requestId = UIDUtil.createUID();
		}

		/** This is a dummy setter to include this property in xml encoding */
		public function set requestId(id:String):void{
			
		}
		public function get requestId():String{
			return _requestId;
		}

		/** This is a dummy setter to include this property in xml encoding */
		public function set queryId(value:String):void{
			
		}

		public function get queryId():String
		{
			return _queryID;
		}
		
		public function set parameters(value:Array):void{
			_parameters = value;
		}
		
		public function get parameters():Array
		{
			return _parameters;
		}
		
		public function toXmlString():String{
			var qName:QName = new QName("transporter");
            var xmlDocument:XMLDocument = new XMLDocument();
            var simpleXMLEncoder:SimpleXMLEncoder = new SimpleXMLEncoder(xmlDocument);
            var xmlNode:XMLNode = simpleXMLEncoder.encodeValue(this, qName, xmlDocument);
            var xml:XML = new XML(xmlDocument.toString());
			 return xml.toString();
		}
	}
}