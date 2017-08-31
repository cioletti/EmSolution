package bean
{
	[RemoteClass(alias="com.emsolution.bean.ArqSosBean")]
	public class FilialBean
	{
		private var _nomeArquivo: String;
		private var _data: String;
		private var _idFuncionario: String;
		private var _tipoAnalise: String;
	
		
		public function get nomeArquivo():String
		{
			return _nomeArquivo;
		}

		public function set nomeArquivo(value:String):void
		{
			_nomeArquivo = value;
		}

		public function get data():String
		{
			return _data;
		}

		public function set data(value:String):void
		{
			_data = value;
		}

		public function get idFuncionario():String
		{
			return _idFuncionario;
		}

		public function set idFuncionario(value:String):void
		{
			_idFuncionario = value;
		}

		public function get tipoAnalise():String
		{
			return _tipoAnalise;
		}

		public function set tipoAnalise(value:String):void
		{
			_tipoAnalise = value;
		}


	}
}