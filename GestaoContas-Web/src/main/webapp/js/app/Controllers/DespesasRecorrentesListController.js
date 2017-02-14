define(['knockout',
	'navigator',
	'gateway',
	'controllers/ListController',
	'models/DespesasRecorrentesModel'],
	function(ko,navigator,Gateway,ListController, DespesaRecorrente){ 
	
	var controller = {};
	ko.utils.extend(controller,ListController);

	controller.model = 
		function DespesasRecorrentesListController(){
			var self = this;
			//Data-binding

			self.despesas = ko.observableArray([]);
			self.nome = ko.observable('nome');
			self.diaDeCobranca = ko.observable();
			
			self.criaDespesa = function(formElement){
				Gateway.postDespesa(self.nome(),self.diaDeCobranca(),
					function(result) { 
						var despesas = self.despesas();
						ko.utils.arrayPushAll(despesas,[new DespesaRecorrente(result)]);
						self.despesas.valueHasMutated();
						self.nome(null);
						self.diaDeCobranca(null);
					})
			};

			self.navigateToDespesa = function(despesa){
				navigator.navigateTo('DespesasRecorrentes/view/'+despesa.id());
			};
			
			Gateway.getDespesas(function(allData){
				var mappedDespesas = $.map(allData, function(item) { return new DespesaRecorrente(item); });
				self.despesas(mappedDespesas);
			});

			self.nome(null);
		};
		
	return controller;

});