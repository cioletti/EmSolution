package bean
{
	[RemoteClass(alias="com.emsolution.bean.CodigoClienteBean")]
	public class CodigoClienteBean
	{
		private var _id:Number;
		private var _idLoginCliente:Number;
		private var _codigo:String;
		private var _loginCliente:String;
	

		public function get id():Number
		{
			return _id;
		}
		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get idLoginCliente():Number
		{
			return _idLoginCliente;
		}

		public function set idLoginCliente(value:Number):void
		{
			_idLoginCliente = value;
		}

		public function get codigo():String
		{
			return _codigo;
		}

		public function set codigo(value:String):void
		{
			_codigo = value;
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