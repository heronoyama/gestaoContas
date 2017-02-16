define(['knockout'],function(ko){
	return function MesCobranca(data){
		var self = this;
		self.id = ko.observable(data.id);
		self.mes = ko.observable(data.mes);
		self.ano = ko.observable(data.ano);

		self.dataFormatada = ko.computed(function(){
			return self.mes()+"/"+self.ano();
		});

		self.isBefore = function(outroMes){
			if(self.ano() < outroMes.ano())
				return true;
			if(self.ano() > outroMes.ano())
				return false;
			return self.mes() < outroMes.mes();
		};

		self.equals = function(outroMes){
			return self.ano() == outroMes.ano() && self.mes() == outroMes.mes();
		};

	}
});