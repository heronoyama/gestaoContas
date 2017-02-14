define(['knockout','gateway','models/DespesasRecorrentesModel','models/MesCobrancaModel','models/Cobranca','gadgets/models/CobrancaView'],
	function(ko,Gateway,DespesaRecorrente,MesCobranca,Cobranca,CobrancaView){ 
	var DespesaView = {};
	
	DespesaView.model = CobrancaView;
	
	DespesaView.cobrancaFormModel =
		function CobrancaFormController(DespesaViewController){
			self.mesesDisponiveis = ko.observableArray([]);
			self.mesSelecionado = ko.observable();
			self.valor = ko.observable();
			self.despesaController = ko.observable(DespesaViewController);

			self.despesaId = function(){
				return self.despesaController().params.despesaRecorrente;
			};

			self.geraCobranca = function(formElement){
					var data = {
						mes: self.mesSelecionado().mes(),
						ano: self.mesSelecionado().ano(),
						valor: parseFloat(self.valor()),
						id_despesa: parseInt(self.despesaId())
					};
					Gateway.geraCobranca(data, function(result) {
						self.despesaController().atualizaCobrancas(result);
						self.valor(null);
						self.mesSelecionado(null);
					});

				};

			Gateway.getMeses(function(allData){
					var mappedMeses = $.map(allData, function(item) { return new MesCobranca(item); });
					self.mesesDisponiveis(mappedMeses);
				});

		},
			
	DespesaView.load = function(element,templateLocation,htmlSection,model){
			$(element).load(templateLocation,
			 function(){
			 		$(getElement("CobrancaView")).load("js/app/Gadgets/templates/cobrancaView.html",function(){
			 			ko.applyBindings(model, getElement(htmlSection));
			 			ko.applyBindings(new DespesaView.cobrancaFormModel(model),getElement('CobrancaForm'));
			 	});
			 });

		};

	DespesaView.loadModel = function(id,callback){
		var model = new DespesaView.model({title : 'Meses Cobrados', despesaRecorrente : id});
		callback(model);
	};

	return DespesaView;
});