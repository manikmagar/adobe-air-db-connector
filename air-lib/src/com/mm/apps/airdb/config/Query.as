package com.mm.apps.airdb.config
{
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.xml.SimpleXMLEncoder;
	
	[Bindable]
	public class Query
	{
		public function Query()
		{
		}

		public var queryType:String = "select";
		public var queryId:String;
		public var sql:String;
		
		public var parameters:ArrayCollection = new ArrayCollection();


		public function toXmlNode():XMLNode{
			
			var doc:XMLDocument = new XMLDocument();
			
			var node:XMLNode = doc.createElement(queryType);
			node.attributes.id = queryId;
			
			var sqlNode:XMLNode = doc.createElement("sql");
			sqlNode.appendChild(doc.createTextNode(sql));
			
			node.appendChild(sqlNode);
			
			var parametersNode:XMLNode = doc.createElement("parameters");
			
			if(parameters.length > 0){
				var myElement:XMLDocument = new XMLDocument();
				var encoder:SimpleXMLEncoder = new SimpleXMLEncoder(myElement);
				var itemQName:QName = new QName("", "parameter");
	
	            for (var i:uint = 0; i < parameters.length; i++)
	            {
	                encoder.encodeValue(parameters.getItemAt(i), itemQName, myElement);
	            }
				parametersNode.appendChild(myElement);
			}
			node.appendChild(parametersNode);
			
			return node;
		}
	}
}