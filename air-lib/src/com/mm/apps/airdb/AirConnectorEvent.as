package com.mm.apps.airdb
{
	import flash.events.Event;

	public class AirConnectorEvent extends Event
	{
		
		private var _message:String = null;
		
		private var _resultRowCount:int = 0;
		
		private var _rowList:XMLList;
		
		private var _requestId:String = "";
		
		public static var DATA_RECEIVED:String = "dataReceived";
		
		public static var ON_ERROR:String = "onError";
		
		public static var STATE_CHANGED:String = "stateChanged";
		
		public function AirConnectorEvent(type:String,message:String,requestId:String,resultRowCount:int = 0,resultRows:XMLList =null)
		{
			super(type, false, false);
			_message = message;
			_resultRowCount = resultRowCount;
			_rowList = resultRows;
			_requestId = requestId;
		}
		
		public function get message():String{
			return _message;
		}
		
		public function get resultRowCount():int{
			return _resultRowCount;
		}
		
		public function get resultRows():XMLList{
			return _rowList;
		}
		
		public function get resultId():String{
			return _requestId;
		}
		
		override public function clone():Event{
			return new AirConnectorEvent(this.type,this.message,this.resultId,this.resultRowCount, this.resultRows);
		}
	}
}