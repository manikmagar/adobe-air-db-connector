package com.mm.apps.airdb.config
{
	import mx.collections.ArrayCollection;
	
	public interface ISqlConfig
	{
		function get connection():Connection;
		function set connection(conn:Connection):void;
		function get queries():ArrayCollection;
		function set queries(q:ArrayCollection):void;
		function toXml():String;
		function get queryIdMap():ArrayCollection
	}
}