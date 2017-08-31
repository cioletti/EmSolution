package bean
{
	[RemoteClass(alias="com.emsolution.bean.ConsultorBean")]
	public class ConsultorBean
	{
		private var _matriculaDbs:String;
		private var _nome:String;
		private var _email:String;
		private var _isSelected:Boolean;
		private var _matricula:String;
	
		

		public function get matriculaDbs():String
		{
			return _matriculaDbs;
		}

		public function set matriculaDbs(value:String):void
		{
			_matriculaDbs = value;
		}

		public function get nome():String
		{
			return _nome;
		}

		public function set nome(value:String):void
		{
			_nome = value;
		}

		public function get isSelected():Boolean
		{
			return _isSelected;
		}

		public function set isSelected(value:Boolean):void
		{
			_isSelected = value;
		}

		public function get email():String
		{
			return _email;
		}

		public function set email(value:String):void
		{
			_email = value;
		}

		public function get matricula():String
		{
			return _matricula;
		}

		public function set matricula(value:String):void
		{
			_matricula = value;
		}


	}
}