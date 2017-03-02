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

		self.link = ko.observable(null);
		self.linkExport = ko.observable(null);
		self.showLink = ko.computed(function(){
			return self.link() != null && !self.hasError();
		});
		
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

			var helper = new GatewayHelper(data,self.updateCobrancas);
			
			try{
				var link = helper.getCobrancas();
				//TODO seriously?
				var finalLink = "http://"+location.host+"/gestaoContas-web/"+link;
				self.link(finalLink);

				//TODO fucking seriously? you rly insist?
				self.linkExport(finalLink.replace("cobranca","cobranca/export"));
			} catch(err) {
				self.mensagemErro(err);
			}
			

			self.despesasSelecionadas([]);
			self.mesInicio(null);
			self.mesFim(null);

		};

		self.updateCobrancas = function(cobrancas){
			if(cobrancas.cobrancas.length == 0){
				self.mensagemErro("Não foram encontradas cobranças para essas condições");
				return;
			}
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
			if(self.possuiApenasMesFim())
				return self.getCobrancasDoMesFim();
			throw "Selecione algum parâmetro";
		};

		self.getCobrancasComDespesas = function(){
			if(self.possuiAmbosMeses())
				return self.getCobrancasCompleto();
			if(self.possuiNenhumMes())
				return self.getCobrancasApenasByDespesa();
			if(self.possuiApenasMesInicio())
				return self.getCobrancasAPartirDoMesComDespesa();
			
			return self.getCobrancasAteOMesComDespesa();
		};

		self.possuiApenasMesInicio = function(){
			return self.mesInicio && !self.mesFim;
		};

		self.possuiApenasMesFim = function(){
			return !self.mesInicio && self.mesFim;
		};

		self.possuiAmbosMeses = function(){
			return self.mesInicio && self.mesFim;
		};

		self.possuiNenhumMes = function(){
			return !self.mesInicio && !self.mesFim;
		};


		//TODO - remover repetição
		self.getCobrancasDosMeses = function(){
			if(!self.mesInicio.isBefore(self.mesFim) && !self.mesInicio.equals(self.mesFim))
				throw "Mês inicio deve ser antes de Mês Fim";
			return Gateway.getCobrancasByMesInicioEFim(self.mesInicio.id(),self.mesFim.id(),self.callback);
		};

		self.getCobrancasApenasByDespesa = function(){
			return Gateway.getCobrancasByDespesa(self.ids.join(','),self.callback);
		};

		self.getCobrancasAPartirDoMesComDespesa = function(){
			return Gateway.getCobrancasByDespesasComMesFrom(self.ids,self.mesInicio.id(),self.callback);
		};

		self.getCobrancasAteOMesComDespesa = function(){
			return Gateway.getCobrancasByDespesasComMesTo(self.ids,self.mesFim.id(),self.callback);
		};

		self.getCobrancasCompleto = function(){
			if(!self.mesInicio.isBefore(self.mesFim) && !self.mesInicio.equals(self.mesFim))
				throw "Mês inicio deve ser antes de Mês Fim";
			return Gateway.getCobrancasByDespesaMesInicioEFim(self.ids,self.mesInicio.id(),self.mesFim.id(),self.callback);
		};

		self.getCobrancasDoMesInicio = function(){
			return Gateway.getCobrancasByMesFrom(self.mesInicio.id(),self.callback);
		};

		self.getCobrancasDoMesFim = function(){
			return Gateway.getCobrancasByMesTo(self.mesFim.id(),self.callback);
		};
	};

	return controller;
});