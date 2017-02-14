define(['knockout','gateway','components/EstrategiaBuscaCobranca','models/DespesasRecorrentesModel','models/MesCobrancaModel','models/Cobranca'],
	function(ko,Gateway,EstrategiaBuscaCobranca,DespesaRecorrente,MesCobranca,Cobranca){ 
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
					var mappedCobranca = self.mapCobranca(cobranca);
					var cobrancas = self.cobrancas();
					ko.utils.arrayPushAll(cobrancas,[mappedCobranca]);
					self.cobrancas.valueHasMutated();
					
					var valorTotal = self.total();
					valorTotal += cobranca.valor;
					self.total(valorTotal);
		};

		self.mapCobranca = function(data){
					var mesCobranca = new MesCobranca(data.mes_cobranca);
					var despesaRecorrente = new DespesaRecorrente(data.despesa_recorrente);
						var item = {};
						item.mes = mesCobranca;
						item.despesa = despesaRecorrente;
						item.id = data.id;
						item.valor = data.valor;
						return new Cobranca(item); 
					};

	};
});