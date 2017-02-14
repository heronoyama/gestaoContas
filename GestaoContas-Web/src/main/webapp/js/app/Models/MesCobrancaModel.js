define(['knockout'],function(ko){
	return function MesCobranca(data){
		var self = this;
		self.id = ko.observable(data.id);
		self.mes = ko.observable(data.mes);
		self.ano = ko.observable(data.ano);

		self.dataFormatada = ko.computed(function(){
			return self.mes()+"/"+self.ano();
		});

	}
});