package bean
{
	[RemoteClass(alias="com.emsolution.bean.StatusOportunidadeBean")]
	public class StatusOportunidadeBean
	{
		private var _id:Number;
		private var _descricao:String;
		private var _sigla:String;
	
		

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get sigla():String
		{
			return _sigla;
		}

		public function set sigla(value:String):void
		{
			_sigla = value;
		}


	}
}