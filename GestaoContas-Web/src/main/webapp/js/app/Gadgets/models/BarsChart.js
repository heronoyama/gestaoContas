define(['knockout','chart'],function(ko,chart){

	return function BarsChart(params){
		var self = this;

		self.cobrancas = params.cobrancas;
		self.getLabels = function(){
			var labels = $.map(self.cobrancas(),function(item){
				return item.mes().dataFormatada();
			});
			return $.unique(labels);
		};
		self.getValues = function(){
			return $.map(self.cobrancas(),function(item){
				return item.valor();
			});
		};

		self.getDataSets = function(){
			var map = {};
			var labels = self.getLabels();
			for(var i in labels){
				map[labels[i]] = {};
			}
			var despesas = $.unique($.map(self.cobrancas(),function(item){return item.despesa().nome();}));

			var cobrancas = self.cobrancas();
			for(var i in cobrancas){
				map[cobrancas[i].mes().dataFormatada()][cobrancas[i].despesa().nome()] = cobrancas[i].valor();
			}

			var dataSets = [];
			for(var i in despesas){
				var valores = [];
				for(var j in labels){
					valor = map[labels[j]][despesas[i]];
					if(!valor)
						valor = 0;
					valores.push(valor);
				}
				var dataSet = {};
				dataSet.label = despesas[i];
				dataSet.data = valores;
				dataSet.type ='bar';
				dataSet.backgroundColor = getRandomColor();
				dataSets.push(dataSet);
			}
			return dataSets;
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
				datasets: self.getDataSets()
				};

			var ctx = getElement('BarsChart').getContext("2d");
			new Chart(ctx, {
				    type: 'bar',
				    data: data ,
				 	options: {
					    scales: {
					      xAxes: [{ stacked: true }],
					      yAxes: [{ stacked: true }]
					    }
					  }   
				});
		};
	}

});