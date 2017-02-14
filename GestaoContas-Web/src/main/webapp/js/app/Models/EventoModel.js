define(['knockout'],function(ko){
	return function Evento(data){
		this.nome = ko.observable(data.nome);
		this.id = ko.observable(data.id);
	}
});