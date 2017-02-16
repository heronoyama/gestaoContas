define(['knockout','gateway','components/EstrategiaBuscaCobranca','models/DespesasRecorrentesModel','models/MesCobrancaModel','models/Cobranca'],
	function(ko,Gateway,EstrategiaBuscaCobranca,DespesaRecorrente,MesCobranca,Cobranca){ 
	return function CobrancaView(params){
		var self = this;

		self.title = params.title;
		self.cobrancas = params.cobrancas;
		self.total = params.total;

	};
});