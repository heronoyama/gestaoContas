ko.bindingHandlers.fadeVisible = {
	init: function(element, valueAccessor){
		var shouldDisplay = valueAccessor();
		$(element).toggle(shouldDisplay);
	}
};

ko.bindingHandlers.deffaultHidden = {
	init: function(element,valueAccessor){
		$(element).toggle(false);
	}
};