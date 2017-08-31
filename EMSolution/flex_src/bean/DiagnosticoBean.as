package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.emsolution.bean.DiagnosticoBean")]
	public class DiagnosticoBean
	{
		private var _messageId:Number;
		private var _receivedTime:String;
		private var _filial:String;
		private var _modelo:String;
		private var _numeroSerie:String;
		private var _nomeCliente:String;
		private var _numerDiagnosticos:Number;
		private var _csa:Number;
		private var _detalhesDiagnosticoBean:DetalhesDiagnosticoBean;
		private var _cor:String;
		private var _backlog:String;
		private var _backlogCampo:String;
		private var _totalSos:Number;
		private var _corSos:String;
		private var _pip:Number;
		private var _psp:Number;
		private var _inicioGarantia:String;
		private var _fimGarantia:String;
		private var _isOportunidade:Boolean;
		private var _isLock:String;
		private var _isLockMy:String;
		private var _estimateByFuncionarioLock:String;
		private var _numLinhas:Number;
		private var _dataBacklogPmp:String;
		private var _dataBacklogCampo:String;
		private var _idFilial:String;
		private var _dataColeta:String;
		private var _dataImportacaoSos:String;
		private var _corSmu:String;
		private var _totalSmu:Number;
		public function DiagnosticoBean()
		{
		}

		public function get receivedTime():String
		{
			return _receivedTime;
		}

		public function set receivedTime(value:String):void
		{
			_receivedTime = value;
		}

		public function get filial():String
		{
			return _filial;
		}

		public function set filial(value:String):void
		{
			_filial = value;
		}

		public function get modelo():String
		{
			return _modelo;
		}

		public function set modelo(value:String):void
		{
			_modelo = value;
		}

		public function get numeroSerie():String
		{
			return _numeroSerie;
		}

		public function set numeroSerie(value:String):void
		{
			_numeroSerie = value;
		}

		public function get nomeCliente():String
		{
			return _nomeCliente;
		}

		public function set nomeCliente(value:String):void
		{
			_nomeCliente = value;
		}

		public function get numerDiagnosticos():Number
		{
			return _numerDiagnosticos;
		}

		public function set numerDiagnosticos(value:Number):void
		{
			_numerDiagnosticos = value;
		}

		public function get csa():Number
		{
			return _csa;
		}

		public function set csa(value:Number):void
		{
			_csa = value;
		}


		public function get cor():String
		{
			return _cor;
		}

		public function set cor(value:String):void
		{
			_cor = value;
		}

		public function get detalhesDiagnosticoBean():DetalhesDiagnosticoBean
		{
			return _detalhesDiagnosticoBean;
		}

		public function set detalhesDiagnosticoBean(value:DetalhesDiagnosticoBean):void
		{
			_detalhesDiagnosticoBean = value;
		}

		public function get backlog():String
		{
			return _backlog;
		}

		public function set backlog(value:String):void
		{
			_backlog = value;
		}

		public function get totalSos():Number
		{
			return _totalSos;
		}

		public function set totalSos(value:Number):void
		{
			_totalSos = value;
		}

		public function get corSos():String
		{
			return _corSos;
		}

		public function set corSos(value:String):void
		{
			_corSos = value;
		}

		public function get pip():Number
		{
			return _pip;
		}

		public function set pip(value:Number):void
		{
			_pip = value;
		}

		public function get psp():Number
		{
			return _psp;
		}

		public function set psp(value:Number):void
		{
			_psp = value;
		}

		public function get inicioGarantia():String
		{
			return _inicioGarantia;
		}

		public function set inicioGarantia(value:String):void
		{
			_inicioGarantia = value;
		}

		public function get fimGarantia():String
		{
			return _fimGarantia;
		}

		public function set fimGarantia(value:String):void
		{
			_fimGarantia = value;
		}

		public function get isOportunidade():Boolean
		{
			return _isOportunidade;
		}

		public function set isOportunidade(value:Boolean):void
		{
			_isOportunidade = value;
		}

		public function get isLock():String
		{
			return _isLock;
		}

		public function set isLock(value:String):void
		{
			_isLock = value;
		}

		public function get isLockMy():String
		{
			return _isLockMy;
		}

		public function set isLockMy(value:String):void
		{
			_isLockMy = value;
		}

		public function get estimateByFuncionarioLock():String
		{
			return _estimateByFuncionarioLock;
		}

		public function set estimateByFuncionarioLock(value:String):void
		{
			_estimateByFuncionarioLock = value;
		}

		public function get backlogCampo():String
		{
			return _backlogCampo;
		}

		public function set backlogCampo(value:String):void
		{
			_backlogCampo = value;
		}

		public function get numLinhas():Number
		{
			return _numLinhas;
		}

		public function set numLinhas(value:Number):void
		{
			_numLinhas = value;
		}

		public function get dataBacklogPmp():String
		{
			return _dataBacklogPmp;
		}

		public function set dataBacklogPmp(value:String):void
		{
			_dataBacklogPmp = value;
		}

		public function get idFilial():String
		{
			return _idFilial;
		}

		public function set idFilial(value:String):void
		{
			_idFilial = value;
		}

		public function get dataColeta():String
		{
			return _dataColeta;
		}

		public function set dataColeta(value:String):void
		{
			_dataColeta = value;
		}

		public function get dataBacklogCampo():String
		{
			return _dataBacklogCampo;
		}

		public function set dataBacklogCampo(value:String):void
		{
			_dataBacklogCampo = value;
		}

		public function get dataImportacaoSos():String
		{
			return _dataImportacaoSos;
		}

		public function set dataImportacaoSos(value:String):void
		{
			_dataImportacaoSos = value;
		}

		public function get corSmu():String
		{
			return _corSmu;
		}

		public function set corSmu(value:String):void
		{
			_corSmu = value;
		}

		public function get totalSmu():Number
		{
			return _totalSmu;
		}

		public function set totalSmu(value:Number):void
		{
			_totalSmu = value;
		}


	}
}