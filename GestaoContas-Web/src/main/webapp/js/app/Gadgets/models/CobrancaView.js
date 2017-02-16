define(['knockout','gateway','components/Conversor','components/EstrategiaBuscaCobranca','models/DespesasRecorrentesModel','models/MesCobrancaModel','models/Cobranca'],
	function(ko,Gateway,Conversor,EstrategiaBuscaCobranca,DespesaRecorrente,MesCobranca,Cobranca){ 
	return function CobrancaView(params){
		var self = this;

		self.title = params.title;
		self.cobrancas = ko.observableArray([]);
		self.total = ko.observable();
		self.params = params;

		
		new EstrategiaBuscaCobranca(self.params).getStrategy()
			.getCobrancas(function(allData){
					self.total(allData.total);
					self.cobrancas(allData.cobrancas);
				});

		self.atualizaCobrancas = function(cobranca){
					var mappedCobranca = Conversor.mapCobranca(cobranca);
					var cobrancas = self.cobrancas();
					ko.utils.arrayPushAll(cobrancas,[mappedCobranca]);
					self.cobrancas.valueHasMutated();
					
					var valorTotal = self.total();
					valorTotal += cobranca.valor;
					self.total(valorTotal);
		};

	};
});