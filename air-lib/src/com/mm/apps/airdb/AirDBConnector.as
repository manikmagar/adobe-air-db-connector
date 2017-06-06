package com.mm.apps.airdb
{
	import com.mm.apps.airdb.airconnector.XmlConnector;
	import com.mm.apps.airdb.sql.SqlTransporter;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	
	[Event(name="dataReceived",type="com.mm.apps.airdb.AirConnectorEvent")]
	
	[Event(name="onError",type="com.mm.apps.airdb.AirConnectorEvent")]
	
	[Event(name="stateChanged",type="com.mm.apps.airdb.AirConnectorEvent")]
	
	public class AirDBConnector extends EventDispatcher
	{
		private var xmlConnector:XmlConnector = null;
		
		private var callbackFunMap:Object = new Object();
		
		private var _serverAddress:String = "localhost"
		
		private var _serverPort:int = 4000;
		
		public function AirDBConnector()
		{
			xmlConnector = XmlConnector.getInstance();
		}
		

		public function init():void{
			xmlConnector.connectToServer(serverAddress, serverPort);
			xmlConnector.pingServer();
			xmlConnector.addEventListener(AirConnectorEvent.DATA_RECEIVED,dataReceived);	
		}

		public function get serverAddress():String{
			return _serverAddress;
		}
		
		public function set serverAddress(addr:String):void{
			_serverAddress = addr;
		}
		
		public function get serverPort():int{
			return _serverPort;
		}
		
		public function set serverPort(port:int):void{
			_serverPort = port;
		}
		
		private function dataReceived(event:Event):void{
			var airEvent:AirConnectorEvent =(event as AirConnectorEvent); 
			var x:XML = new XML(airEvent.message);
			var requestId:String = x.child("requestId");
			if(callbackFunMap[requestId] != null){
				var callback:Function = callbackFunMap[requestId];
				callback.call(this,airEvent);
				delete callbackFunMap[requestId];
			}
			dispatchEvent(event);	
		}

		public function executeQuery(queryId:String, callback:Function = null):String{
			return this.executeQueryParams(queryId,new Array(),callback);
		}

		/**
		 * @param queryID
		 * @param params
		 * @param callback function to be called after data is received from server. Method signature should be 
		 * 			function (event:AirConnectorEvent) void. 
		 * 
		 * @return Unique String id for this method request
		 * 
		 */ 
		public function executeQueryParams(queryId:String, params:Array, callback:Function = null):String{
			var transporter:SqlTransporter = new SqlTransporter(queryId);
			transporter.parameters = params;
			
			if(callback != null){
				callbackFunMap[transporter.requestId] = callback;
			}
			
			xmlConnector.send(transporter);
			return transporter.requestId;
		}
		
		
	}
}