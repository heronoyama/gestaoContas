define(['knockout',
	'navigator',
	'gateway',
	'controllers/ListController',
	'components/Conversor'],
	function(ko,navigator,Gateway,ListController,Conversor){ 
	
	var controller = {};
	
	ko.utils.extend(controller,ListController);

	controller.model =  function RelatorioController(){
		var self = this;

		self.despesasDisponiveis = ko.observableArray([]);
		self.mesesDisponiveis = ko.observableArray([]);
		self.cobrancas = ko.observableArray([]);

		self.despesasSelecionadas = ko.observableArray([]);
		self.mesInicio = ko.observable();
		self.mesFim = ko.observable();

		self.total = ko.observable();

		self.buscaCobrancas = function(formElement){
			self.cobrancas([]);
			self.total(null);

			var ids = $.map(self.despesasSelecionadas(),function(item){return item.id();});
			
			var data = {};
			data.ids = ids;
			data.mesInicio = self.mesInicio();
			data.mesFim = self.mesFim();

			new GatewayHelper(data,self.updateCobrancas).getCobrancas();

			self.despesasSelecionadas([]);
			self.mesInicio(null);
			self.mesFim(null);

		};

		self.updateCobrancas = function(cobrancas){
			self.total(cobrancas.total);
			self.cobrancas(cobrancas.cobrancas);
		};

		Gateway.getDespesasMapeadas(function(despesas){
			self.despesasDisponiveis(despesas);
		});

		Gateway.getMesesMapeados(function(meses){
			self.mesesDisponiveis(meses);
		});

	};

	//TODO extrair
	function GatewayHelper(data,callback){
		var self = this;
		self.ids = data.ids;
		self.mesInicio = data.mesInicio;
		self.mesFim = data.mesFim;
		self.callback = callback;

		self.getCobrancas = function(){
			if(self.ids.length > 0)
				return self.getCobrancasComDespesas();
			return self.getCobrancasSemDespesa();
		};

		self.getCobrancasSemDespesa = function(){
			if(self.possuiAmbosMeses())
				return self.getCobrancasDosMeses();
			if(self.possuiApenasMesInicio())
				return self.getCobrancasDoMesInicio();
		};

		self.getCobrancasComDespesas = function(){
			if(self.possuiAmbosMeses())
				return self.getCobrancasCompleto();
			if(self.possuiApenasMesInicio())
				return self.getCobrancasDoMesComDespesa();
			if(self.possuiNenhumMes())
				return self.getCobrancasApenasByDespesa();
		};

		self.possuiApenasMesInicio = function(){
			return self.mesInicio && !self.mesFim;
		};

		self.possuiAmbosMeses = function(){
			return self.mesInicio && self.mesFim;
		};

		self.possuiNenhumMes = function(){
			return !self.mesInicio && !self.mesFim;
		};

		self.getCobrancasDosMeses = function(){
			Gateway.getCobrancasByMesInicioEFim(self.mesInicio.id(),self.mesFim.id(),self.callback);
		};

		self.getCobrancasApenasByDespesa = function(){
			Gateway.getCobrancasByDespesa(self.ids.join(','),self.callback);
		};

		self.getCobrancasDoMesComDespesa = function(){
			Gateway.getCobrancasByDespesasComMes(self.ids,self.mesInicio.id(),self.callback);
		};

		self.getCobrancasCompleto = function(){
			Gateway.getCobrancasByDespesaMesInicioEFim(self.ids,self.mesInicio.id(),self.mesFim.id(),self.callback);
		};

		self.getCobrancasDoMesInicio = function(){
			Gateway.getCobrancasByMes(self.mesInicio.id(),self.callback);
		};
	};

	return controller;
});