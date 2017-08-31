package bean
{
	[RemoteClass(alias="com.emsolution.bean.ConsumoCombustivelBean")]
	public class ConsumoCombustivelBean
	{
		private var _id:Number;
		private var _idFamilia:Number;
		private var _familia:String;
		private var _minPorcentagemBaixa:Number;
		private var _maxPorcentagemBaixa:Number;
		private var _minPorcentagemMedia:Number;
		private var _maxPorcentagemMedia:Number;
		private var _minPorcentagemAlta:Number;
		private var _maxPorcentagemAlta:Number;
		private var _baixo:String;
		private var _medio:String;
		private var _alto:String;


		public function get id():Number
		{
			return _id;
		}

		public function set id(value:Number):void
		{
			_id = value;
		}

		public function get idFamilia():Number
		{
			return _idFamilia;
		}

		public function set idFamilia(value:Number):void
		{
			_idFamilia = value;
		}

		public function get minPorcentagemBaixa():Number
		{
			return _minPorcentagemBaixa;
		}

		public function set minPorcentagemBaixa(value:Number):void
		{
			_minPorcentagemBaixa = value;
		}

		public function get maxPorcentagemBaixa():Number
		{
			return _maxPorcentagemBaixa;
		}

		public function set maxPorcentagemBaixa(value:Number):void
		{
			_maxPorcentagemBaixa = value;
		}

		public function get minPorcentagemMedia():Number
		{
			return _minPorcentagemMedia;
		}

		public function set minPorcentagemMedia(value:Number):void
		{
			_minPorcentagemMedia = value;
		}

		public function get maxPorcentagemMedia():Number
		{
			return _maxPorcentagemMedia;
		}

		public function set maxPorcentagemMedia(value:Number):void
		{
			_maxPorcentagemMedia = value;
		}

		public function get minPorcentagemAlta():Number
		{
			return _minPorcentagemAlta;
		}

		public function set minPorcentagemAlta(value:Number):void
		{
			_minPorcentagemAlta = value;
		}

		public function get maxPorcentagemAlta():Number
		{
			return _maxPorcentagemAlta;
		}

		public function set maxPorcentagemAlta(value:Number):void
		{
			_maxPorcentagemAlta = value;
		}

		public function get baixo():String
		{
			return _baixo;
		}

		public function set baixo(value:String):void
		{
			_baixo = value;
		}

		public function get medio():String
		{
			return _medio;
		}

		public function set medio(value:String):void
		{
			_medio = value;
		}

		public function get alto():String
		{
			return _alto;
		}

		public function set alto(value:String):void
		{
			_alto = value;
		}

		public function get familia():String
		{
			return _familia;
		}

		public function set familia(value:String):void
		{
			_familia = value;
		}


	}
}