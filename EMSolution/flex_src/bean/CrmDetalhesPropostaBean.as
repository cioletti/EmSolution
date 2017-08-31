package bean
{
	[RemoteClass(alias="com.emsolution.bean.CrmDetalhesPropostaBean")]
	public class CrmDetalhesPropostaBean
	{
		private var _id: Number;
		private var _objetoProposta: String;
		private var _maquina: String;
		private var _condicoesPagamento: String;
		private var _validadeProposta: String;
		private var _email: String;
		private var _formaEntregaProposta: String;
		private var _observacao: String;
		private var _dataCriacao: String;
		private var _idFuncionario: Number;
		private var _prazoEntrega: Number;
		private var _idProposta: Number;
		private var _contato: String;
		private var _valorReparoAposFalha: Number;
		private var _distancia: Number;
		
		public function CrmDetalhesPropostaBean(){
		}

		public function get objetoProposta():String
		{
			return _objetoProposta;
		}

		public function set objetoProposta(value:String):void
		{
			_objetoProposta = value;
		}

		public function get maquina():String
		{
			return _maquina;
		}

		public function set maquina(value:String):void
		{
			_maquina = value;
		}

		public function get condicoesPagamento():String
		{
			return _condicoesPagamento;
		}

		public function set condicoesPagamento(value:String):void
		{
			_condicoesPagamento = value;
		}

		public function get validadeProposta():String
		{
			return _validadeProposta;
		}

		public function set validadeProposta(value:String):void
		{
			_validadeProposta = value;
		}

		public function get email():String
		{
			return _email;
		}

		public function set email(value:String):void
		{
			_email = value;
		}

		public function get formaEntregaProposta():String
		{
			return _formaEntregaProposta;
		}

		public function set formaEntregaProposta(value:String):void
		{
			_formaEntregaProposta = value;
		}

		public function get observacao():String
		{
			return _observacao;
		}

		public function set observacao(value:String):void
		{
			_observacao = value;
		}

		public function get dataCriacao():String
		{
			return _dataCriacao;
		}

		public function set dataCriacao(value:String):void
		{
			_dataCriacao = value;
		}

		public function get idFuncionario():Number
		{
			return _idFuncionario;
		}

		public function set idFuncionario(value:Number):void
		{
			_idFuncionario = value;
		}

		public function get prazoEntrega():Number
		{
			return _prazoEntrega;
		}

		public function set prazoEntrega(value:Number):void
		{
			_prazoEntrega = value;
		}

		public function get String():String
		{
			return _String;
		}

		public function set String(value:String):void
		{
			_String = value;
		}

		public function get valorReparoAposFalha():Number
		{
			return _valorReparoAposFalha;
		}

		public function set valorReparoAposFalha(value:Number):void
		{
			_valorReparoAposFalha = value;
		}

		public function get distancia():Number
		{
			return _distancia;
		}

		public function set distancia(value:Number):void
		{
			_distancia = value;
		}

		public function get idProposta():Number
		{
			return _idProposta;
		}

		public function set idProposta(value:Number):void
		{
			_idProposta = value;
		}

		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get contato():String
		{
			return _contato;
		}

		public function set contato(value:String):void
		{
			_contato = value;
		}


	}
}