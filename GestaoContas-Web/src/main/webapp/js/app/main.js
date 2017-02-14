require(['knockout','sammy','app/pageControl/HeaderViewModel'], 
	function(ko, Sammy,HeaderViewModel) {

	ko.components.register('cobranca-view', {
	    viewModel: {require: 'gadgets/models/CobrancaView'},
	    template: {require: 'text!gadgets/templates/cobrancaView.html'}
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