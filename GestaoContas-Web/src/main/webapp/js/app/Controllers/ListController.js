define(['knockout'],function(ko){
	var controller = {};

//TODO renomear para simpleController
	controller.load = function(element,templateLocation,htmlSection,model){
			$(element).load(templateLocation,
			 function(){
		 			ko.applyBindings(model, getElement(htmlSection));
			 	}	
			 );
		};
	return controller;
});