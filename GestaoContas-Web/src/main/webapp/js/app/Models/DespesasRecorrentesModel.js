define(['knockout'],function(ko){
	return function DespesaRecorrente(data){
		var self = this;

		self.nome = ko.observable(data.nome);
		self.id = ko.observable(data.id);
		self.diaDeCobranca = ko.observable(data.dia_de_cobranca);

		self.map = function(data){
			self.nome(data.nome);
			self.id(data.id);
			self.diaDeCobranca(data.dia_de_cobranca);
		}
	}
});