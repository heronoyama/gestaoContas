define(['knockout'],function(ko){ 
	//TODO após renomear ListController para SimpleController, fazer ele extender
	var MainController = {}

	MainController.model = 
		function MainController(){
			var self = this;
			var dataAtual = new Date();
			self.mes = dataAtual.getMonth() + 1;
			self.ano = dataAtual.getFullYear();

		};

	MainController.load = function(element,templateLocation,htmlSection,model){
		$(element).load(templateLocation,
		 function(){
	 			ko.applyBindings(model, getElement(htmlSection));
		 	}	
		 );
	};

	return MainController;
});