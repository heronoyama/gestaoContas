define(['knockout','utils','app/pageControl/Page'],function(ko,utils,Page){
	return function ViewPage(data){
		var self = this;
		ko.utils.extend(self,new Page(data));

		self.load = function(element,id) {
				self.controller.loadModel(id, function(controller){
				self.controller.load(element,self.getTemplateLocation(),self.htmlSection,controller);
			})
	
		};

		self.isMenuVisible = function(){ return false;};

	};

});