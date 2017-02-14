define(['knockout','app/pageControl/ListPage','app/pageControl/StaticPage','app/pageControl/ViewPage',
	'controllers/MainController',
	'controllers/DespesasRecorrentesListController',
	'controllers/DespesasRecorrentesViewController',
	'controllers/MesesCobrancaListController',
	'controllers/MesCobrancaViewController'],
	function(ko,ListPage,StaticPage,ViewPage,
	MainController,
	DespesasRecorrentesListController,
	DespesasRecorrentesViewController,
	MesesCobrancaListController,
	MesCobrancaViewController ){
 return function HeaderViewModel(){
	var self = this;

	self.defaultPage = function(){ return new ListPage({page: 'Main', controller:MainController, htmlSection:'MainView', view:'welcome'}); };


	self.pages = ko.observableArray(
			[ self.defaultPage(),
			  new ListPage ({page: 'DespesasRecorrentes', controller: DespesasRecorrentesListController, htmlSection:'DespesasList', view: 'list'}),
			  new ViewPage ({page: 'DespesasRecorrentes', controller: DespesasRecorrentesViewController, htmlSection:'DespesaView', view: 'view'}),
			  new ListPage ({page: 'MesesCobranca', controller: MesesCobrancaListController, htmlSection:'MesesList', view: 'list'}),
		  	  new ViewPage ({page: 'MesesCobranca', controller: MesCobrancaViewController, htmlSection:'MesCobrancaView', view: 'view'})
			]);

	self.currentPage = ko.observable();

	self.visiblePages = ko.computed(function(){
		return $.grep(self.pages(),function(element){
			return element.isMenuVisible()}
		);

	});
	
	self.setPage = function(page,view){
		 pageToLoad = getPage(page,view);
		 pageToLoad.load('#content');
	};

	self.setPage = function(page,view,id){
		 pageToLoad = getPage(page,view);
		 pageToLoad.load('#content',id);
	};

	function getPage (page, view){
		var pagesToLoad  = $.grep(self.pages(), function(element){
	        	return element.page == page && element.view == view;
	        }); //TODO is fucking ugly
		 var pageToLoad = pagesToLoad[0];
		 if(!pageToLoad) //TODO
			 console.log('TODO: Tratamento 404');
		 self.currentPage(pageToLoad);
		 return pageToLoad;
	}

 };
});