define(['knockout','utils','app/pageControl/Page'],function(ko,utils,Page){
	
	return function StaticPage(data){
		var self = this;
		ko.utils.extend(self,new Page(data));

		self.load = function(element){
			$(element).load(self.getTemplateLocation(),
			 function(){
			 		if(self.controller && self.htmlSection)
			 			ko.applyBindings(self.controller.model, getElement(self.htmlSection));
			 	}	
			 );
		};
	};
});