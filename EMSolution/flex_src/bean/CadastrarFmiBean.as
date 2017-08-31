package bean
{
	[RemoteClass(alias="com.emsolution.bean.CadastrarFmiBean")]
	public class CadastrarFmiBean
	{
		private var _codigo:Number;
		private var _descricao:String;
	
		public function get codigo():Number
		{
			return _codigo;
		}

		public function set codigo(value:Number):void
		{
			_codigo = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}


	}
}