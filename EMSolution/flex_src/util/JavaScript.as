package com.abdulqabiz.utils
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.net.*;
	import mx.core.IMXMLObject;

	[DefaultProperty( "source" )]

	public class JavaScript extends EventDispatcher implements IMXMLObject
	{

		private var _source : String;

		private var _initialized : Boolean;

		public function set source( value : String ) : void
		{
			if ( value != null )
			{
				_source = value;
				var commentPattern : RegExp = /(\/\*([^*]|[\r\n]|(\*+([^*\/]|[\r\n])))*\*+\/)|((^|[^:\/])(\/\/.*))/g;

				//TBD:: replace all single quotes with double quotes - Needs to come up with better
				//regexp to keep single quotes within text as it is and only replace the one used in statements.
				//_source = _source.replace(/(\')/g,'\"');

				//remove all comments in js code value = value.replace (commentPattern, "");

				//trace (_source);
				var u : URLRequest = new URLRequest( "javascript:eval('" + value + "');" );
				navigateToURL( u, "_self" );
			}
		}

		public function initialized( document : Object, id : String ) : void
		{
			_initialized = true;
		}

		override public function toString() : String
		{
			return _source;
		}

	}

}