define(['knockout','chart'],function(ko,chart){

	return function PieChart(params){
		var self = this;

		self.cobrancas = params.cobrancas;
		self.getLabels = function(){
			var labels = $.map(self.cobrancas(),function(item){
				return item.despesa().nome();
			});
			return labels;
		};
		self.getValues = function(){
			return $.map(self.cobrancas(),function(item){
				return item.valor();
			});
		};

		self.getColors = function(){
			var colors = [];
			for (var i = 0; i < self.cobrancas().length; i++) {
				colors.push(getRandomColor());
			}
			return colors;
		};

		self.cobrancas.subscribe(buildChart);
		//TODO fucking ugly
		buildChart();
		function buildChart(cobrancas){
			if(self.cobrancas().length == 0)
				return;

			var data = {
				labels: self.getLabels(),
				datasets: [{
					data: self.getValues(),
				 	backgroundColor: self.getColors()
				 }]
				};

			new Chart($('#PieChart'), {
				    type: 'pie',
				    data: data ,
				    options: {}
				});
		};
	}

});