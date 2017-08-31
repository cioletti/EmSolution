package bean
{
	[RemoteClass(alias="com.emsolution.bean.SmuBean")]
	public class SmuBean
	{
		private var _id:Number;
		private var _horimetroAnterior:Number;
		private var _horimetroProximo:Number;
		private var _tipoServico:String;
		private var _data:String;
		private var _observacao:String;

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get horimetroAnterior():Number
		{
			return _horimetroAnterior;
		}

		public function set horimetroAnterior(value:Number):void
		{
			_horimetroAnterior = value;
		}

		public function get horimetroProximo():Number
		{
			return _horimetroProximo;
		}

		public function set horimetroProximo(value:Number):void
		{
			_horimetroProximo = value;
		}

		public function get tipoServico():String
		{
			return _tipoServico;
		}

		public function set tipoServico(value:String):void
		{
			_tipoServico = value;
		}

		public function get data():String
		{
			return _data;
		}

		public function set data(value:String):void
		{
			_data = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}


	}
}