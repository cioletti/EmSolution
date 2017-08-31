package bean
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.emsolution.bean.SegmentoBean")]
	public class SegmentoBean
	{
		private var _idProposta:Number;
		private var _numeroSegmento:String;
		private var _descricao:String;
		private var _cscc:String;
		private var _jbcd:String;
		private var _jbcdStr:String;
		private var _cptcd:String;
		private var _cptcdStr:String;
		private var _shopField:String;
		private var _jbctStr:String;
		private var _horas:String;
		private var _horasSubst:String;
		private var _pecasList: ArrayCollection;
		private var _id:Number;
		private var _operacaoList:ArrayCollection;
		private var _hasSendPecasDbsSegmento:String;
		private var _qtdComp:Number;
		private var _qtdCompSubst:Number;
		private var _msgDbs:String;
		private var _observacao:String;
		private var _idFuncionarioCriador:String;		
		private var _codErroDbs:String;		
		private var _havePecas:String;	
		private var _idFuncionarioPecas:String;
		private var _nomeFuncionarioPecas:String;
		private var _dataLiberacaoPecas:String;
		private var _dataTerminoServico:String;
		private var _numeroDoc:String;
		private var _pedido:String;
		private var _listSosAssociado:ArrayCollection;
		private var _listPlAssociado:ArrayCollection;
		private var _listPmpAssociado:ArrayCollection;
		private var _listCampoAssociado:ArrayCollection;
		private var _listSmuAssociado:ArrayCollection;
		private var _msgDocDbs:String;
		private var _codErroDocDbs:String;
		
		public function get horasSubst():String
		{
			return _horasSubst;
		}

		public function set horasSubst(value:String):void
		{
			_horasSubst = value;
		}

		public function get qtdCompSubst():Number
		{
			return _qtdCompSubst;
		}

		public function set qtdCompSubst(value:Number):void
		{
			_qtdCompSubst = value;
		}

		public function get pedido():String
		{
			return _pedido;
		}

		public function set pedido(value:String):void
		{
			_pedido = value;
		}

		public function get dataTerminoServico():String
		{
			return _dataTerminoServico;
		}

		public function set dataTerminoServico(value:String):void
		{
			_dataTerminoServico = value;
		}

		public function get dataLiberacaoPecas():String
		{
			return _dataLiberacaoPecas;
		}

		public function set dataLiberacaoPecas(value:String):void
		{
			_dataLiberacaoPecas = value;
		}

		public function get numeroDoc():String
		{
			return _numeroDoc;
		}

		public function set numeroDoc(value:String):void
		{
			_numeroDoc = value;
		}

		public function get havePecas():String
		{
			return _havePecas;
		}

		public function set havePecas(value:String):void
		{
			_havePecas = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get msgDbs():String
		{
			return _msgDbs;
		}

		public function set msgDbs(value:String):void
		{
			_msgDbs = value;
		}

		public function get descricao():String
		{
			return _descricao;
		}

		public function set descricao(value:String):void
		{
			_descricao = value;
		}
		
		public function get numeroSegmento():String{return _numeroSegmento};
		public function set numeroSegmento(numeroSegmento:String):void{this._numeroSegmento = numeroSegmento};
		
		public function get cscc():String{return _cscc};
		public function set cscc(cscc:String):void{this._cscc = cscc};
		
		public function get jbcd():String{return _jbcd};
		public function set jbcd(jbcd:String):void{this._jbcd = jbcd}; 

		public function get jbcdStr():String{return _jbcdStr};
		public function set jbcdStr(jbcdStr:String):void{this._jbcdStr = jbcdStr}; 
		
		public function get jbctStr():String{return _jbctStr};
		public function set jbctStr(jbctStr:String):void{this._jbctStr = jbctStr}; 
		
		public function get cptcd():String{return _cptcd};
		public function set cptcd(cptcd:String):void{this._cptcd= cptcd};

		public function get cptcdStr():String{return _cptcdStr};
		public function set cptcdStr(cptcdStr:String):void{this._cptcdStr = cptcdStr};
		
		public function get shopField():String{return _shopField};
		public function set shopField(shopField:String):void{this._shopField = shopField};

		public function get horas():String{return _horas};
		public function set horas(horas:String):void{this._horas = horas};

		public function get pecasList():ArrayCollection{return _pecasList};
		public function set pecasList(pecasList:ArrayCollection):void{this._pecasList = pecasList};
		
		public function get id():Number{return _id};
		public function set id(id:Number):void{this._id = id};
		
		public function get operacaoList():ArrayCollection{return _operacaoList};
		public function set operacaoList(operacaoList:ArrayCollection):void{this._operacaoList = operacaoList};

		public function get hasSendPecasDbsSegmento():String{return _hasSendPecasDbsSegmento};
		public function set hasSendPecasDbsSegmento(hasSendPecasDbsSegmento:String):void{this._hasSendPecasDbsSegmento = hasSendPecasDbsSegmento};

		public function get qtdComp():Number{return _qtdComp};
		public function set qtdComp(qtdComp:Number):void{this._qtdComp = qtdComp};
		
		public function get idFuncionarioCriador():String
		{
			return _idFuncionarioCriador;
		}

		public function set idFuncionarioCriador(value:String):void
		{
			_idFuncionarioCriador = value;
		}		

		public function get codErroDbs():String
		{
			return _codErroDbs;
		}

		public function set codErroDbs(value:String):void
		{
			_codErroDbs = value;
		}

		public function get idFuncionarioPecas():String
		{
			return _idFuncionarioPecas;
		}

		public function set idFuncionarioPecas(value:String):void
		{
			_idFuncionarioPecas = value;
		}

		public function get nomeFuncionarioPecas():String
		{
			return _nomeFuncionarioPecas;
		}

		public function set nomeFuncionarioPecas(value:String):void
		{
			_nomeFuncionarioPecas = value;
		}

		public function get listSosAssociado():ArrayCollection
		{
			return _listSosAssociado;
		}

		public function set listSosAssociado(value:ArrayCollection):void
		{
			_listSosAssociado = value;
		}

		public function get listPlAssociado():ArrayCollection
		{
			return _listPlAssociado;
		}

		public function set listPlAssociado(value:ArrayCollection):void
		{
			_listPlAssociado = value;
		}

		public function get listPmpAssociado():ArrayCollection
		{
			return _listPmpAssociado;
		}

		public function set listPmpAssociado(value:ArrayCollection):void
		{
			_listPmpAssociado = value;
		}

		public function get idProposta():Number
		{
			return _idProposta;
		}

		public function set idProposta(value:Number):void
		{
			_idProposta = value;
		}

		public function get codErroDocDbs():String
		{
			return _codErroDocDbs;
		}

		public function set codErroDocDbs(value:String):void
		{
			_codErroDocDbs = value;
		}

		public function get msgDocDbs():String
		{
			return _msgDocDbs;
		}

		public function set msgDocDbs(value:String):void
		{
			_msgDocDbs = value;
		}

		public function get listCampoAssociado():ArrayCollection
		{
			return _listCampoAssociado;
		}

		public function set listCampoAssociado(value:ArrayCollection):void
		{
			_listCampoAssociado = value;
		}

		public function get listSmuAssociado():ArrayCollection
		{
			return _listSmuAssociado;
		}

		public function set listSmuAssociado(value:ArrayCollection):void
		{
			_listSmuAssociado = value;
		}

		
		public function SegmentoBean()
		{
			_pecasList = new ArrayCollection();
			_operacaoList = new ArrayCollection();
			_listPlAssociado = new ArrayCollection();
			_listPmpAssociado = new ArrayCollection();
			_listSosAssociado = new ArrayCollection();
			_listCampoAssociado = new ArrayCollection();
		}
	}
}