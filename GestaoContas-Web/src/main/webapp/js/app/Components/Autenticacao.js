define(['knockout'],
	function(ko){

	function AuthenticationModule(){
		var self = this;
		self.lock = new Auth0Lock('3cy6NfVP5EGmXBUlTcabSxCKlMWGny3U', 'heron-oyama.auth0.com', {
	    	auth: { 
	      	params: { 
		        scope: 'openid email' 
		      }
		    }
	  	});

	  	self.isAuthenticated = ko.computed(function(){
	  		return localStorage.id_token != undefined;
	  	});

		self.lock.on("authenticated",function(authResult){
			self.lock.getProfile(authResult.idToken, function(error, profile) {
	        	if (error) { console.log("Deu ruim!"); return; }

	    		localStorage.setItem('id_token', authResult.idToken);
	    		localStorage.setItem('profile',profile);
	    		console.log(profile);
	  		});
		});

		self.login  = function(){
			self.lock.show();
			
		},

		self.logout = function(){
			localStorage.removeItem('id_token');
    		localStorage.removeItem('profile');
	  		window.location.href = window.location.origin + window.location.pathname;

		}

	};
	return new AuthenticationModule();
		
});