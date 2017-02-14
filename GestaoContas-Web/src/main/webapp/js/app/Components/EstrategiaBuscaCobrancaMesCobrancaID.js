define(['knockout','gateway'],
	function(ko,Gateway){ 

	return function EstrategiaBuscaCobrancaMesCobrancaID(mesId){
		var self = this;
		self.idMesCobranca = mesId;
		

		self.getCobrancas = function(callback){
			
			Gateway.getCobrancasByMes(self.idMesCobranca,callback);
		};

	};
});