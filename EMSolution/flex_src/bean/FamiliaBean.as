package bean
{
	[RemoteClass(alias="com.emsolution.bean.FamiliaBean")]
	public class FamiliaBean
	{
		private var _id:Number;
		private var _descricao:String;
		private var _idConsumoCombustivel:Number;
		public function get id(): Number{return _id};
		public function set id(id: Number): void{this._id = id}; 

		public function get descricao(): String{return _descricao};
		public function set descricao(descricao: String): void{this._descricao = descricao}
		public function get idConsumoCombustivel():Number
		{
			return _idConsumoCombustivel;
		}

		public function set idConsumoCombustivel(value:Number):void
		{
			_idConsumoCombustivel = value;
		}

; 
		
		public function FamiliaBean()
		{
		}
	}
}