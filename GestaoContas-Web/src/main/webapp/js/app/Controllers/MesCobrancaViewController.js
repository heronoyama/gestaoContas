define(['knockout','gateway','models/DespesasRecorrentesModel','models/MesCobrancaModel','models/Cobranca','gadgets/models/CobrancaView'],
	function(ko,Gateway,DespesaRecorrente,MesCobranca,Cobranca,CobrancaView){ 

	var MesCobrancaView = {};

	MesCobrancaView.model = CobrancaView;

	MesCobrancaView.cobrancaFormModel =
		function CobrancaFormController(mesCobrancaViewController){
			var self = this;
			self.despesasDisponiveis = ko.observableArray([]);
			self.despesaSelecionada = ko.observable();
			self.valor = ko.observable();
			self.mesCobrancaController = ko.observable(mesCobrancaViewController);

			self.mesCobrancaId = function(){
				return self.mesCobrancaController().params.mesCobranca.id;
			};

			self.geraCobranca = function(formElement){
					var data = {
						id_mes: parseInt(self.mesCobrancaId()),
						valor: parseFloat(self.valor()),
						id_despesa: self.despesaSelecionada().id()
					};
					Gateway.geraCobranca(data, function(result) {
						self.mesCobrancaController().atualizaCobrancas(result);
						self.valor(null);
						self.despesaSelecionada(null);
					});

				};

				Gateway.getDespesas(function(allData){
					var mappedDespesas = $.map(allData, function(item) { return new DespesaRecorrente(item); });
					self.despesasDisponiveis(mappedDespesas);
				});
		};

	MesCobrancaView.load = function(element,templateLocation,htmlSection,model){
			$(element).load(templateLocation,
			 function(){
			 	$(getElement("CobrancaView")).load("js/app/Gadgets/templates/cobrancaView.html",function(){
		 			ko.applyBindings(model, getElement(htmlSection));
		 			ko.applyBindings(new MesCobrancaView.cobrancaFormModel(model),getElement('CobrancaForm'));
			 	});	
			 });

		};

	MesCobrancaView.loadModel = function(id,callback){
		var model = new MesCobrancaView.model({title : 'Despesas do mês', mesCobranca : {id: id}});
		callback(model);
	};
	
	return MesCobrancaView;
});