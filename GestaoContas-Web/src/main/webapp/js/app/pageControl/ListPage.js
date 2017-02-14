define(['knockout','utils','app/pageControl/Page'],function(ko,utils,Page){
	return function ListPage(data){
		var self = this;
		ko.utils.extend(self,new Page(data));

		self.load = function(element) {
			self.controller.load(element,self.getTemplateLocation(),self.htmlSection,self.controller.model);
			return;
		}
	}
});