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
		
		self.mensagemErro = ko.observable(null);
		self.hasError = ko.computed(function(){
			return self.mensagemErro() != null;
		});

		self.buscaCobrancas = function(formElement){
			self.cobrancas([]);
			self.total(null);
			self.mensagemErro(null);

			var ids = $.map(self.despesasSelecionadas(),function(item){return item.id();});
			
			var data = {};
			data.ids = ids;
			data.mesInicio = self.mesInicio();
			data.mesFim = self.mesFim();

			var mensagem = new GatewayHelper(data,self.updateCobrancas).getCobrancas();
			self.mensagemErro(mensagem);

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
			return "Selecione Mês Inicio e Mês Fim, ou apenas Mês Inicio. Nunca apenas mês fim";
		};

		self.getCobrancasComDespesas = function(){
			if(self.possuiAmbosMeses())
				return self.getCobrancasCompleto();
			if(self.possuiApenasMesInicio())
				return self.getCobrancasDoMesComDespesa();
			if(self.possuiNenhumMes())
				return self.getCobrancasApenasByDespesa();
			return "Selecione Mês Inicio e Mês Fim, ou apenas Mês Inicio. Nunca apenas mês fim";
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
			if(!self.mesInicio.isBefore(self.mesFim))
				return "Mês inicio deve ser antes de Mês Fim";
			Gateway.getCobrancasByMesInicioEFim(self.mesInicio.id(),self.mesFim.id(),self.callback);
			return null;
		};

		self.getCobrancasApenasByDespesa = function(){
			Gateway.getCobrancasByDespesa(self.ids.join(','),self.callback);
			return null;
		};

		self.getCobrancasDoMesComDespesa = function(){
			Gateway.getCobrancasByDespesasComMes(self.ids,self.mesInicio.id(),self.callback);
			return null;
		};

		self.getCobrancasCompleto = function(){
			if(!self.mesInicio.isBefore(self.mesFim))
				return "Mês inicio deve ser antes de Mês Fim";
			Gateway.getCobrancasByDespesaMesInicioEFim(self.ids,self.mesInicio.id(),self.mesFim.id(),self.callback);
			return null;
		};

		self.getCobrancasDoMesInicio = function(){
			Gateway.getCobrancasByMes(self.mesInicio.id(),self.callback);
			return null;
		};
	};

	return controller;
});