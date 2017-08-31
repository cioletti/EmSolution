package bean
{
	[RemoteClass(alias="com.emsolution.bean.CrmSegmentoServicoTerceiroBean")]
	public class CrmSegmentoServicoTerceiroBean
	{
		private var _idCrmSegmento: Number;
		private var _idEmsSegmento: Number;
		private var _idTipoServicoTerceiro: Number;
		private var _data: String;
		private var _valor: Number;
		private var _qtd: Number;
		private var _idOper: Number;
		private var _obs: String;
		private var _idFornServTerceiro: Number;
		private var _idStatusServTerceiro: Number;
		private var _localServico: String;
		
		public function get idCrmSegmento():Number
		{
			return _idCrmSegmento;
		}

		public function set idCrmSegmento(value:Number):void
		{
			_idCrmSegmento = value;
		}

		public function get idEmsSegmento():Number
		{
			return _idEmsSegmento;
		}

		public function set idEmsSegmento(value:Number):void
		{
			_idEmsSegmento = value;
		}

		public function get idTipoServicoTerceiro():Number
		{
			return _idTipoServicoTerceiro;
		}

		public function set idTipoServicoTerceiro(value:Number):void
		{
			_idTipoServicoTerceiro = value;
		}

		public function get data():String
		{
			return _data;
		}

		public function set data(value:String):void
		{
			_data = value;
		}

		public function get valor():Number
		{
			return _valor;
		}

		public function set valor(value:Number):void
		{
			_valor = value;
		}

		public function get qtd():Number
		{
			return _qtd;
		}

		public function set qtd(value:Number):void
		{
			_qtd = value;
		}

		public function get idOper():Number
		{
			return _idOper;
		}

		public function set idOper(value:Number):void
		{
			_idOper = value;
		}

		public function get obs():String
		{
			return _obs;
		}

		public function set obs(value:String):void
		{
			_obs = value;
		}

		public function get idFornServTerceiro():Number
		{
			return _idFornServTerceiro;
		}

		public function set idFornServTerceiro(value:Number):void
		{
			_idFornServTerceiro = value;
		}

		public function get idStatusServTerceiro():Number
		{
			return _idStatusServTerceiro;
		}

		public function set idStatusServTerceiro(value:Number):void
		{
			_idStatusServTerceiro = value;
		}

		public function get localServico():String
		{
			return _localServico;
		}

		public function set localServico(value:String):void
		{
			_localServico = value;
		}


	}
}