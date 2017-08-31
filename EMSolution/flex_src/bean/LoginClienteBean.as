package bean
{
	[RemoteClass(alias="com.emsolution.bean.LoginClienteBean")]
	public class LoginClienteBean
	{
		private var _id:Number;
		private var _loginCliente:String;
	

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get loginCliente():String
		{
			return _loginCliente;
		}

		public function set loginCliente(value:String):void
		{
			_loginCliente = value;
		}


	}
}