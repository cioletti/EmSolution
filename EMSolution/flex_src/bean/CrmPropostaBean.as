package bean
{
	[RemoteClass(alias="com.emsolution.bean.CrmPropostaBean")]
	public class CrmPropostaBean
	{
		private var _id: Number;
		private var _idEmsProposta: Number;
		private var _numSerie: String;
		private var _idStatusOpt: Number;
		private var _idtipoOpt: Number;
		private var _dataOpt: Number;
		private var _matriculaFuncionario: String;
		private var _jobControl: String;
		private var _tipoCliente: String;
		private var _codClienteExt: String;
		private var _codClienteInter: String;
		private var _codClienteGarantia: String;
		private var _idEquipamento: String;
		private var _dataEnvio: String;
		private var _dataAceite: String;
		private var _dataFinalizacao: String;
		private var _dataRejeicao: String;
		private var _fatorCliente: Number;
		private var _modelo: String;
		private var _idFamiliaCampo: Number;
		private var _prefixo: String;
		private var _idFamiliaOficina: Number;
		private var _possuiSubTributaria: String;
		private var _isFindSubTributaria: String;
		private var _filial: Number;
		private var _telefone: String;
		private var _obs: String;
		private var _fatorUrgencia: String;
		private var _horimetro: Number;
		private var _isLock: String;
		private var _idFuncionarioLock: String;
		private var _idClassificacao: Number;
		private var _idFaseNegociacao: Number;
		private var _idMotivoPerda: Number;
		private var _valorMaoDeObra: Number;
		private var _valorPecas: Number;
		private var _totalOrcamento: Number;
		private var _totalDeslocamento: Number;
		private var _estimateByFuncionarioLock: String;
		
		public function CrmPropostaBean(){}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get idEmsProposta():Number
		{
			return _idEmsProposta;
		}

		public function set idEmsProposta(value:Number):void
		{
			_idEmsProposta = value;
		}

		public function get numSerie():String
		{
			return _numSerie;
		}

		public function set numSerie(value:String):void
		{
			_numSerie = value;
		}

		public function get idStatusOpt():Number
		{
			return _idStatusOpt;
		}

		public function set idStatusOpt(value:Number):void
		{
			_idStatusOpt = value;
		}



		public function get matriculaFuncionario():String
		{
			return _matriculaFuncionario;
		}

		public function set matriculaFuncionario(value:String):void
		{
			_matriculaFuncionario = value;
		}

		public function get jobControl():String
		{
			return _jobControl;
		}

		public function set jobControl(value:String):void
		{
			_jobControl = value;
		}

		public function get tipoCliente():String
		{
			return _tipoCliente;
		}

		public function set tipoCliente(value:String):void
		{
			_tipoCliente = value;
		}

		public function get codClienteExt():String
		{
			return _codClienteExt;
		}

		public function set codClienteExt(value:String):void
		{
			_codClienteExt = value;
		}

		public function get codClienteInter():String
		{
			return _codClienteInter;
		}

		public function set codClienteInter(value:String):void
		{
			_codClienteInter = value;
		}

		public function get codClienteGarantia():String
		{
			return _codClienteGarantia;
		}

		public function set codClienteGarantia(value:String):void
		{
			_codClienteGarantia = value;
		}

		public function get idEquipamento():String
		{
			return _idEquipamento;
		}

		public function set idEquipamento(value:String):void
		{
			_idEquipamento = value;
		}

		public function get dataEnvio():String
		{
			return _dataEnvio;
		}

		public function set dataEnvio(value:String):void
		{
			_dataEnvio = value;
		}

		public function get dataFinalizacao():String
		{
			return _dataFinalizacao;
		}

		public function set dataFinalizacao(value:String):void
		{
			_dataFinalizacao = value;
		}

		public function get dataRejeicao():String
		{
			return _dataRejeicao;
		}

		public function set dataRejeicao(value:String):void
		{
			_dataRejeicao = value;
		}

		public function get fatorCliente():Number
		{
			return _fatorCliente;
		}

		public function set fatorCliente(value:Number):void
		{
			_fatorCliente = value;
		}

		public function get modelo():String
		{
			return _modelo;
		}

		public function set modelo(value:String):void
		{
			_modelo = value;
		}

		public function get idFamiliaCampo():Number
		{
			return _idFamiliaCampo;
		}

		public function set idFamiliaCampo(value:Number):void
		{
			_idFamiliaCampo = value;
		}

		public function get prefixo():String
		{
			return _prefixo;
		}

		public function set prefixo(value:String):void
		{
			_prefixo = value;
		}

		public function get idFamiliaOficina():Number
		{
			return _idFamiliaOficina;
		}

		public function set idFamiliaOficina(value:Number):void
		{
			_idFamiliaOficina = value;
		}

		public function get possuiSubTributaria():String
		{
			return _possuiSubTributaria;
		}

		public function set possuiSubTributaria(value:String):void
		{
			_possuiSubTributaria = value;
		}

		public function get isFindSubTributaria():String
		{
			return _isFindSubTributaria;
		}

		public function set isFindSubTributaria(value:String):void
		{
			_isFindSubTributaria = value;
		}

		public function get filial():Number
		{
			return _filial;
		}

		public function set filial(value:Number):void
		{
			_filial = value;
		}

		public function get obs():String
		{
			return _obs;
		}

		public function set obs(value:String):void
		{
			_obs = value;
		}

		public function get fatorUrgencia():String
		{
			return _fatorUrgencia;
		}

		public function set fatorUrgencia(value:String):void
		{
			_fatorUrgencia = value;
		}

		public function get horimetro():Number
		{
			return _horimetro;
		}

		public function set horimetro(value:Number):void
		{
			_horimetro = value;
		}

		public function get isLock():String
		{
			return _isLock;
		}

		public function set isLock(value:String):void
		{
			_isLock = value;
		}

		public function get idClassificacao():Number
		{
			return _idClassificacao;
		}

		public function set idClassificacao(value:Number):void
		{
			_idClassificacao = value;
		}

		public function get idFaseNegociacao():Number
		{
			return _idFaseNegociacao;
		}

		public function set idFaseNegociacao(value:Number):void
		{
			_idFaseNegociacao = value;
		}

		public function get idMotivoPerda():Number
		{
			return _idMotivoPerda;
		}

		public function set idMotivoPerda(value:Number):void
		{
			_idMotivoPerda = value;
		}

		public function get valorMaoDeObra():Number
		{
			return _valorMaoDeObra;
		}

		public function set valorMaoDeObra(value:Number):void
		{
			_valorMaoDeObra = value;
		}

		public function get totalOrcamento():Number
		{
			return _totalOrcamento;
		}

		public function set totalOrcamento(value:Number):void
		{
			_totalOrcamento = value;
		}

		public function get totalDeslocamento():Number
		{
			return _totalDeslocamento;
		}

		public function set totalDeslocamento(value:Number):void
		{
			_totalDeslocamento = value;
		}

		public function get dataAceite():String
		{
			return _dataAceite;
		}

		public function set dataAceite(value:String):void
		{
			_dataAceite = value;
		}

		public function get idFuncionarioLock():String
		{
			return _idFuncionarioLock;
		}

		public function set idFuncionarioLock(value:String):void
		{
			_idFuncionarioLock = value;
		}

		public function get valorPecas():Number
		{
			return _valorPecas;
		}

		public function set valorPecas(value:Number):void
		{
			_valorPecas = value;
		}

		public function get dataOpt():Number
		{
			return _dataOpt;
		}

		public function set dataOpt(value:Number):void
		{
			_dataOpt = value;
		}

		public function get telefone():String
		{
			return _telefone;
		}

		public function set telefone(value:String):void
		{
			_telefone = value;
		}

		public function get estimateByFuncionarioLock():String
		{
			return _estimateByFuncionarioLock;
		}

		public function set estimateByFuncionarioLock(value:String):void
		{
			_estimateByFuncionarioLock = value;
		}

		public function get idtipoOpt():Number
		{
			return _idtipoOpt;
		}

		public function set idtipoOpt(value:Number):void
		{
			_idtipoOpt = value;
		}


	}
}