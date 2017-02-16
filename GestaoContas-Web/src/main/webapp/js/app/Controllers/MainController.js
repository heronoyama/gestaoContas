define(['knockout','components/EstrategiaBuscaCobranca'],function(ko,EstrategiaBuscaCobranca){ 
	//TODO após renomear ListController para SimpleController, fazer ele extender
	var MainController = {}

	MainController.model = 
		function MainController(){
			var self = this;
			var dataAtual = new Date();
			self.mes = dataAtual.getMonth() + 1;
			self.ano = dataAtual.getFullYear();
			self.total = ko.observable();
			self.cobrancas = ko.observableArray([]);
			
			var params = {title : 'Cobranças do mês atual', mesCobranca : { mes:self.mes, ano:self.ano}};
			new EstrategiaBuscaCobranca(params).getStrategy()
			.getCobrancas(function(allData){
					self.total(allData.total);
					self.cobrancas(allData.cobrancas);
				});

		};

	MainController.load = function(element,templateLocation,htmlSection,model){
		$(element).load(templateLocation,
		 function(){
	 			ko.applyBindings(model, getElement(htmlSection));
		 	}	
		 );
	};

	return MainController;
});