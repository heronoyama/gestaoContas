require(['knockout','sammy','app/pageControl/HeaderViewModel'], 
	function(ko, Sammy,HeaderViewModel) {

	ko.components.register('cobranca-table', {
	    viewModel: {require: 'gadgets/models/CobrancaTable'},
	    template: {require: 'text!gadgets/templates/cobrancaTable.html'}
	});

	ko.components.register('mensagem', {
	    viewModel: {require: 'gadgets/models/Mensagem'},
	    template: {require: 'text!gadgets/templates/mensagem.html'}
	});

	var headerModel  = new HeaderViewModel();
	ko.applyBindings(headerModel,getElement('headerController'));

	Sammy(function (){
		this.notFound = function(){};
		this.get('#:page/:view',function(){
			headerModel.setPage(this.params['page'],this.params['view']);
		});

		this.get('#:page/:view/:id',function(){
			headerModel.setPage(this.params['page'],this.params['view'],this.params['id']);
		});


	}).run(headerModel.defaultPage().getUrl());

});