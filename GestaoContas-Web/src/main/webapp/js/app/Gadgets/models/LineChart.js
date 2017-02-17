define(['knockout','chart'],function(ko,chart){

	return function LineChart(params){
		var self = this;

		self.cobrancas = params.cobrancas;
		self.getLabels = function(){
			var labels = $.map(self.cobrancas(),function(item){
				return item.mes().dataFormatada();
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

			var dataColor = getRandomColor();
			var lineColor = getRandomColor();
			var data = {
				labels: self.getLabels(),

				datasets: [
					{
						label: "My First dataset",
						fill: false,
						lineTension: 0.1,
						backgroundColor: dataColor,
						borderColor: lineColor,
						borderCapStyle: 'butt',
						borderDash: [],
						borderDashOffset: 0.0,
						borderJoinStyle: 'miter',
						pointBorderColor: lineColor,
						pointBackgroundColor: dataColor,
						pointBorderWidth: 1,
						pointHoverRadius: 5,
						pointHoverBackgroundColor: dataColor,
						pointHoverBorderColor: lineColor,
						pointHoverBorderWidth: 2,
						pointRadius: 1,
						pointHitRadius: 10,
						data: self.getValues(),
						spanGaps: false,
			        }
			    ]
			};

			new Chart($('#LineChart'), {
				    type: 'line',
				    data: data ,
				    options: {}
				});
		};
	}

});