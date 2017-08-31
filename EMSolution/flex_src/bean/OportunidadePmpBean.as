package bean
{
	[RemoteClass(alias="com.emsolution.bean.OportunidadePmpBean")]
	public class OportunidadePmpBean
	{
		private var _idOsPalmDt:Number;
		private var _descricao:String;
		private var _obs:String;
		private var _obsCabecalho:String;
		private var _tipoManutencao:String;
	
		
		public function OportunidadePmpBean()
		{
		}

		public function get idOsPalmDt():Number
		{
			return _idOsPalmDt;
		}

		public function set idOsPalmDt(value:Number):void
		{
			_idOsPalmDt = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}

		public function get obs():String
		{
			return _obs;
		}

		public function set obs(value:String):void
		{
			_obs = value;
		}

		public function get tipoManutencao():String
		{
			return _tipoManutencao;
		}

		public function set tipoManutencao(value:String):void
		{
			_tipoManutencao = value;
		}

		public function get obsCabecalho():String
		{
			return _obsCabecalho;
		}

		public function set obsCabecalho(value:String):void
		{
			_obsCabecalho = value;
		}


	}
}