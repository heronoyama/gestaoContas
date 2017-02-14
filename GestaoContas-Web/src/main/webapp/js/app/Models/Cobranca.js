define(['knockout'],function(ko){
	return function Cobranca(data){
		var self = this;
		self.id = ko.observable(data.id);
		self.despesa = ko.observable(data.despesa);
		self.mes = ko.observable(data.mes);
		self.valor = ko.observable(data.valor);

		self.valorFormatado = ko.computed(function(){
			var valor = self.valor();
			return valor.toFixed(2);
		});

	};
});