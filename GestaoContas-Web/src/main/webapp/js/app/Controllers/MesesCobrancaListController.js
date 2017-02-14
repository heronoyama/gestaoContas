define(['knockout','navigator','gateway','controllers/ListController','models/MesCobrancaModel'],
	function(ko,navigator,Gateway,ListController,MesCobranca){ 
	var controller = {};
	ko.utils.extend(controller,ListController);

	controller.model = 
		function MesesCobrancaListController(){
			var self = this;

			self.meses = ko.observableArray([]);
			self.mes = ko.observable("mes");
			self.ano = ko.observable("ano");

			self.criaMes = function(formElement){
				Gateway.postMes(self.mes(),self.ano(),function(result) { 
					var meses = self.meses();
					ko.utils.arrayPushAll(meses,[new MesCobranca(result)]);
					self.meses.valueHasMutated();
					self.mes(null);
					self.ano(null);
				});
			};

			self.navigateToMes = function(mes){
				navigator.navigateTo('MesesCobranca/view/'+mes.id());
			};

			Gateway.getMesesMapeados(function(meses){
					self.meses(meses);
				});
			self.mes(null);
			self.ano(null);
		};

		return controller;
});