package com.mm.apps.airdb.airconnector
{
	import com.mm.apps.airdb.AirConnectorEvent;
	import com.mm.apps.airdb.IConnector;
	import com.mm.apps.airdb.sql.ISqlTransporter;
	
	import flash.events.DataEvent;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.XMLSocket;
	
	[Event(name="dataReceived",type="com.mm.apps.airdb.AirConnectorEvent")]
	
	[Event(name="onError",type="com.mm.apps.airdb.AirConnectorEvent")]
	
	[Event(name="stateChanged",type="com.mm.apps.airdb.AirConnectorEvent")]
	
	public class XmlConnector extends EventDispatcher  implements IConnector
	{
		
		private var callbackMap:Object = new Object();
		
		private var myId:String = "";
		
		private var xmlSocket:XMLSocket = null;
		
		private static var _instance:XmlConnector;
		
		public function XmlConnector(key:ConstructorKey)
		{
			
		}
		
		public function connectToServer(serverAddr:String = "localhost", serverPort:int = 4000):void{
			xmlSocket = new XMLSocket();
			xmlSocket.connect(serverAddr,serverPort);
			xmlSocket.addEventListener(DataEvent.DATA,dataReceived);
			xmlSocket.addEventListener(Event.ACTIVATE,onStateChange);
			xmlSocket.addEventListener(Event.CLOSE,onStateChange);
			xmlSocket.addEventListener(Event.CONNECT,onStateChange);
			xmlSocket.addEventListener(Event.DEACTIVATE,onStateChange);
			xmlSocket.addEventListener(IOErrorEvent.IO_ERROR,onError);
			xmlSocket.addEventListener(SecurityErrorEvent.SECURITY_ERROR,onError);
		}
		
		public function pingServer():void{
			var pingMessage:String = "ping\n";
			xmlSocket.send(pingMessage);
		}
		
		
		public function send(transporter:ISqlTransporter,callback:Function = null):void{
			if(callback != null){
				callbackMap[transporter.requestId] = callback;
			}
			xmlSocket.send(transporter.toXmlString()+"\n");
			xmlSocket.send("end\n");
		}
		
		private function dataReceived(event:DataEvent):void{
			if(event.data.substr(0,4) == "ping"){
				myId = event.data.substr(4);
			} else {
				var xml:XML = new XML(event.data);
				var resultNode:XMLList = xml.child("data").child("result");
				
				var size:int = resultNode.@size;
							
				var resultList:XMLList = resultNode.child("row");
				
				var requestId:String = xml.requestId;
				
				dispatchEvent(new AirConnectorEvent(AirConnectorEvent.DATA_RECEIVED,event.data,requestId,size,resultList));	
			}
		}
		
		private function onStateChange(event:Event):void{
			dispatchEvent(new AirConnectorEvent(AirConnectorEvent.STATE_CHANGED,event.type,""));
		}
		
		private function onError(event:Event):void{
			var message:String = event.type;
			if(event.type == IOErrorEvent.IO_ERROR){
				message = IOErrorEvent(event).errorID +":"+IOErrorEvent(event).text;
			}
			if(event.type == SecurityErrorEvent.SECURITY_ERROR){
				message = SecurityErrorEvent(event).errorID +":"+ SecurityErrorEvent(event).text; 
			}
			dispatchEvent(new AirConnectorEvent(AirConnectorEvent.ON_ERROR,message,""));
		}
		
		public static function getInstance():XmlConnector{
			if(_instance == null){
				_instance = new XmlConnector(new ConstructorKey());
			}
			return _instance;
		}

	}

}

class ConstructorKey{
	
}