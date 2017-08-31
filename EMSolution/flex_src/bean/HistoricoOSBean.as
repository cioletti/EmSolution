package bean
{
	[RemoteClass(alias="com.emsolution.bean.HistoricoOSBean")]
	public class HistoricoOSBean
	{
		private var _numOs:String;
		private var _cliente:String;
		private var _horimetro:String;
		private var _jbcd:String;
		private var _descJbcd:String;
		private var _cptcd:String;
		private var _descCptcd:String;
		private var _filial:String;
		private var _dtAbertura:String;
		private var _dtFinalizacao:String;
	



		public function get numOs():String
		{
			return _numOs;
		}

		public function set numOs(value:String):void
		{
			_numOs = value;
		}

		public function get cliente():String
		{
			return _cliente;
		}

		public function set cliente(value:String):void
		{
			_cliente = value;
		}

		public function get horimetro():String
		{
			return _horimetro;
		}

		public function set horimetro(value:String):void
		{
			_horimetro = value;
		}

		public function get jbcd():String
		{
			return _jbcd;
		}

		public function set jbcd(value:String):void
		{
			_jbcd = value;
		}

		public function get descJbcd():String
		{
			return _descJbcd;
		}

		public function set descJbcd(value:String):void
		{
			_descJbcd = value;
		}

		public function get cptcd():String
		{
			return _cptcd;
		}

		public function set cptcd(value:String):void
		{
			_cptcd = value;
		}

		public function get descCptcd():String
		{
			return _descCptcd;
		}

		public function set descCptcd(value:String):void
		{
			_descCptcd = value;
		}

		public function get filial():String
		{
			return _filial;
		}

		public function set filial(value:String):void
		{
			_filial = value;
		}

		public function get dtAbertura():String
		{
			return _dtAbertura;
		}

		public function set dtAbertura(value:String):void
		{
			_dtAbertura = value;
		}

		public function get dtFinalizacao():String
		{
			return _dtFinalizacao;
		}

		public function set dtFinalizacao(value:String):void
		{
			_dtFinalizacao = value;
		}


	}
}