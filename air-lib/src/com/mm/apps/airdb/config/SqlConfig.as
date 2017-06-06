package com.mm.apps.airdb.config
{
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	
	import mx.collections.ArrayCollection;
	
	[Bindable]
	public class SqlConfig implements ISqlConfig
	{
		public function SqlConfig()
		{
			
		}


		public var connection:Connection = new Connection();

		public var queries:ArrayCollection = new ArrayCollection();
		
		
		public function get queryIdMap():ArrayCollection{
			var _queryIdMap:ArrayCollection = new ArrayCollection();
			for each (var q:Query in queries){
				_queryIdMap.addItem(q.queryId);
			}
			return _queryIdMap;
		}
		
		public function toXml():String{
			var xmlString:String  = "";
			
			var doc:XMLDocument = new XMLDocument();
			
			var rootNode:XMLNode = doc.createElement('sql-config');
			
			var conNode:XMLNode = doc.createElement('connection');
			
			var dsnNode:XMLNode = doc.createElement('dsn');
			dsnNode.attributes.name = connection.dsnName;
			dsnNode.attributes.username = connection.dsnUsername;
			dsnNode.attributes.password = connection.dsnPassword;
			conNode.appendChild(dsnNode);
			
			rootNode.appendChild(conNode);
			
			var queriesNode:XMLNode = doc.createElement('queries');
			
			for each (var query:Query in queries){
				queriesNode.appendChild(query.toXmlNode());
			}
			
			rootNode.appendChild(queriesNode);
			
			doc.appendChild(rootNode);
			
			var xml:XML = new XML(doc);
			
			return xml.toXMLString();
		}
	}
}