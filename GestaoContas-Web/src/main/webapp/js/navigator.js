define(['knockout'],function(ko){
	return {
		navigateTo : function(url){
			location.hash = url;
		}
	}

});