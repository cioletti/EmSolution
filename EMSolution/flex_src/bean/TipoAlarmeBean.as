package bean
{
	[RemoteClass(alias="com.emsolution.bean.TipoAlarmeBean")]
	public class TipoAlarmeBean
	{
		private var _id:Number;
		private var _descricao:String;
	
		

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


	}
}