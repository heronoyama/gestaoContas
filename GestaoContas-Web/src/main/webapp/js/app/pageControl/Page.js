define(['knockout','utils'],function(ko,utils){
	return function Page(data){
		var self = this;
		self.page = data.page;
		self.view = data.view;
		self.controller = data.controller;
		self.htmlSection = data.htmlSection;
		
		self.getUrl = function(){ return '#'+ self.hashUrl(); };
		self.hashUrl = function(){ return self.page + '/' + self.view;};
		
		self.getTemplateLocation = function(){ return 'templates/'+self.page+'/'+self.view + '.html';};

		self.isMenuVisible = function(){ return true;}

		self.load = function(){};

	};
});