define(['knockout','gateway'],
	function(ko,Gateway){ 

	return function EstrategiaBuscaCobrancaMesEAno(mes,ano){
		var self = this;
		self.mes = mes;
		self.ano = ano;

		self.getCobrancas = function(callback){
			
			Gateway.getCobrancasByMesEAno(self.mes,self.ano,callback);
		};
		
	};
});