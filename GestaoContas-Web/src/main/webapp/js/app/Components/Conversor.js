define(['knockout','models/DespesasRecorrentesModel','models/MesCobrancaModel','models/Cobranca'],
	function(ko,DespesaRecorrente,MesCobranca,Cobranca){

		var conversor = {};

		conversor.mapDespesas = function(rawData){
			var mappedDespesas = $.map(rawData, function(item) { return new DespesaRecorrente(item); });
			return mappedDespesas;
		};

		conversor.mapMesesCobranca = function(rawData){
			var mappedMeses = $.map(rawData, function(item) { return new MesCobranca(item); });
			return mappedMeses;
		};

		conversor.mapCobrancas = function(rawData){

				var mappedCobrancas = $.map(rawData.cobrancas,conversor.mapCobranca);
				var cobrancas = {};
				cobrancas.total = rawData.total
				cobrancas.cobrancas = mappedCobrancas;
				return cobrancas;
			};

		conversor.mapCobranca = function(data){
				var mesCobranca = new MesCobranca(data.mes_cobranca);
				var despesaRecorrente = new DespesaRecorrente(data.despesa_recorrente);
					var item = {};
					item.mes = mesCobranca;
					item.despesa = despesaRecorrente;
					item.id = data.id;
					item.valor = data.valor;
					return new Cobranca(item); 
				};


		return conversor;
});