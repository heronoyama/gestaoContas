define(['knockout','gateway'],
	function(ko,Gateway){ 

	return function EstrategiaBuscaCobrancaDespesa(idDespesa){
		var self = this;
		self.idDespesa = idDespesa;

		self.getCobrancas = function(callback){
			Gateway.getCobrancasByDespesa(self.idDespesa,callback);

		};

				
	};

});