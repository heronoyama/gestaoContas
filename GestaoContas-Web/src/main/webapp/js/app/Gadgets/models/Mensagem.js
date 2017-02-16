define(['knockout'],function(ko){
	return function Mensagem(params){
		var self = this;
		
		self.shouldShow = ko.observable(params.shouldShow);
		self.mensagem = ko.observable(params.mensagem);

	};
});