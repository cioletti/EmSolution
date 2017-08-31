package bean
{
	import mx.messaging.channels.StreamingHTTPChannel;

	[RemoteClass(alias="com.emsolution.bean.PropostaBean")]
	public class PropostaBean
	{
		private var _id:Number;
		private var _nomeFuncionario:String;
		private var _epdino:String;
		private var _idEquipamento:String;
		private var _numSerie:String;
		private var _fatorCliente:Number;
		private var _tipoCiente:String;
		private var _codigoCiente:String;
		private var _idTipoOportunidade:Number;
		private var _idStatusOportunidade:Number;
		private var _modelo:String;
		private var _idFamilia:Number;
		private var _prefixo:String;
		private var _diagnosticoBean:DiagnosticoBean;
		private var _siglaStatusOprtunidade:String;
		private var _siglaTipoOprtunidade:String;
		private var _dataOportunidade:String;
		private var _filial:Number;
		private var _tipoOportunidade:String;
		private var _statusOportunidade:String;
		private var _obs:String;
		private var possuiSubTributaria:String;
		private var isFindSubTributaria:String;
		private var _isAdm:String;
		private var _idFuncionarioLock:String;
		private var _observacao:String;
		private var _filialStr:String;
		
		public function PropostaBean()
		{
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get nomeFuncionario():String
		{
			return _nomeFuncionario;
		}

		public function set nomeFuncionario(value:String):void
		{
			_nomeFuncionario = value;
		}

		public function get epdino():String
		{
			return _epdino;
		}

		public function set epdino(value:String):void
		{
			_epdino = value;
		}

		public function get idEquipamento():String
		{
			return _idEquipamento;
		}

		public function set idEquipamento(value:String):void
		{
			_idEquipamento = value;
		}

		public function get numSerie():String
		{
			return _numSerie;
		}

		public function set numSerie(value:String):void
		{
			_numSerie = value;
		}

		public function get fatorCliente():Number
		{
			return _fatorCliente;
		}

		public function set fatorCliente(value:Number):void
		{
			_fatorCliente = value;
		}

		public function get tipoCiente():String
		{
			return _tipoCiente;
		}

		public function set tipoCiente(value:String):void
		{
			_tipoCiente = value;
		}

		public function get codigoCiente():String
		{
			return _codigoCiente;
		}

		public function set codigoCiente(value:String):void
		{
			_codigoCiente = value;
		}

		public function get idTipoOportunidade():Number
		{
			return _idTipoOportunidade;
		}

		public function set idTipoOportunidade(value:Number):void
		{
			_idTipoOportunidade = value;
		}

		public function get idStatusOportunidade():Number
		{
			return _idStatusOportunidade;
		}

		public function set idStatusOportunidade(value:Number):void
		{
			_idStatusOportunidade = value;
		}

		public function get prefixo():String
		{
			return _prefixo;
		}

		public function set prefixo(value:String):void
		{
			_prefixo = value;
		}

		public function get idFamilia():Number
		{
			return _idFamilia;
		}

		public function set idFamilia(value:Number):void
		{
			_idFamilia = value;
		}

		public function get modelo():String
		{
			return _modelo;
		}

		public function set modelo(value:String):void
		{
			_modelo = value;
		}

		public function get diagnosticoBean():DiagnosticoBean
		{
			return _diagnosticoBean;
		}

		public function set diagnosticoBean(value:DiagnosticoBean):void
		{
			_diagnosticoBean = value;
		}

		public function get siglaStatusOprtunidade():String
		{
			return _siglaStatusOprtunidade;
		}

		public function set siglaStatusOprtunidade(value:String):void
		{
			_siglaStatusOprtunidade = value;
		}

		public function get siglaTipoOprtunidade():String
		{
			return _siglaTipoOprtunidade;
		}

		public function set siglaTipoOprtunidade(value:String):void
		{
			_siglaTipoOprtunidade = value;
		}

		public function get dataOportunidade():String
		{
			return _dataOportunidade;
		}

		public function set dataOportunidade(value:String):void
		{
			_dataOportunidade = value;
		}

		public function get filial():Number
		{
			return _filial;
		}

		public function set filial(value:Number):void
		{
			_filial = value;
		}

		public function get tipoOportunidade():String
		{
			return _tipoOportunidade;
		}

		public function set tipoOportunidade(value:String):void
		{
			_tipoOportunidade = value;
		}

		public function get statusOportunidade():String
		{
			return _statusOportunidade;
		}

		public function set statusOportunidade(value:String):void
		{
			_statusOportunidade = value;
		}

		public function get obs():String
		{
			return _obs;
		}

		public function set obs(value:String):void
		{
			_obs = value;
		}

		public function get isAdm():String
		{
			return _isAdm;
		}

		public function set isAdm(value:String):void
		{
			_isAdm = value;
		}

		public function get idFuncionarioLock():String
		{
			return _idFuncionarioLock;
		}

		public function set idFuncionarioLock(value:String):void
		{
			_idFuncionarioLock = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get filialStr():String
		{
			return _filialStr;
		}

		public function set filialStr(value:String):void
		{
			_filialStr = value;
		}


	}
}