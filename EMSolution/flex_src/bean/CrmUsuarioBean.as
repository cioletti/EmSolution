package bean
{
	[RemoteClass(alias="com.gestaoequipamentos.bean.ClienteBean")]
	public class ClienteBean
	{
		private var _id:Number;
		private var _idTwFuncionario:String;
		private var _idProposta:Number;

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get idTwFuncionario():String
		{
			return _idTwFuncionario;
		}

		public function set idTwFuncionario(value:String):void
		{
			_idTwFuncionario = value;
		}

		public function get idProposta():Number
		{
			return _idProposta;
		}

		public function set idProposta(value:Number):void
		{
			_idProposta = value;
		}


	}
}