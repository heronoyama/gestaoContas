define(['knockout',
		'components/EstrategiaBuscaCobrancaDespesa',
		'components/EstrategiaBuscaCobrancaMesEAno',
		'components/EstrategiaBuscaCobrancaMesCobrancaID'],
	function(ko,
			EstrategiaBuscaCobrancaDespesa,
			EstrategiaBuscaCobrancaMesEAno,
			EstrategiaBuscaCobrancaMesCobrancaID){ 

		return function EstrategiaBuscaCobrancaFactory(params){
			var self = this;
			self.params = params;


			self.getStrategy = function(){
				if(params.despesaRecorrente)
					return new EstrategiaBuscaCobrancaDespesa(params.despesaRecorrente);
				if(params.mesCobranca && (params.mesCobranca.mes && params.mesCobranca.ano))
					return new EstrategiaBuscaCobrancaMesEAno(params.mesCobranca.mes,params.mesCobranca.ano);
				if(params.mesCobranca && params.mesCobranca.id)
					return new EstrategiaBuscaCobrancaMesCobrancaID(params.mesCobranca.id);
				alert("Não foi encontrado uma estratégia de busca de Cobrancas");
			};
		};
});