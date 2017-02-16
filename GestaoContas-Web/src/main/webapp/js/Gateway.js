define(['knockout','components/Conversor'],function(ko,Conversor){
	return {
		getDespesas : function(callback){
			$.getJSON('servicos/despesasRecorrentes', callback);
		},

		getDespesasMapeadas: function(callback){
			$.getJSON('servicos/despesasRecorrentes', function(allData){
				var despesas = Conversor.mapDespesas(allData);
				callback(despesas);
			});
		},

		getDespesa : function(id,callback){
			$.getJSON('servicos/despesasRecorrentes/'+id,callback);
		},

		postDespesa : function(nomeToPost,diaToPost,callback){
			$.ajax('servicos/despesasRecorrentes',{
					data : ko.toJSON( {nome: nomeToPost,dia_de_cobranca:diaToPost}),
					type : 'post',
					contentType: 'application/json',
					success: callback,
					error: function(result) { console.log(result);}
				});
		},

		getMeses : function(callback){
					$.getJSON('servicos/mesesCobranca',callback);
		},

		getMesesMapeados : function(callback){
				$.getJSON('servicos/mesesCobranca',function(allData){
					var meses = Conversor.mapMesesCobranca(allData);
					callback(meses);
				});
		},

		getMesById : function(id,callback){
				$.getJSON('servicos/mesesCobranca/'+id,callback);
		},

		getMes : function(mes,ano,callback){
				$.getJSON('servicos/mesesCobranca/'+mes+'/'+ano,callback);		
		},

		postMes : function(mesToPost,anoToPost,callback){
			$.ajax('servicos/mesesCobranca',{
				data : ko.toJSON( {mes:parseInt(mesToPost), ano:parseInt(anoToPost)}),
				type : 'post',
				contentType: 'application/json',
				success: callback,
				error: function(result) { console.log(result);}
			});
		},

		getCobrancasByDespesa : function(id,callback){
			$.getJSON('servicos/relatorios/cobranca/despesas('+id+')',function(allData){
				var cobrancas = Conversor.mapCobrancas(allData);
				callback(cobrancas);
			});
		},

		getCobrancasByMes : function(mesId,callback){
			$.getJSON('servicos/relatorios/cobranca/'+mesId,function(allData){
				var cobrancas = Conversor.mapCobrancas(allData);
				callback(cobrancas);
			});
		},

		getCobrancasByMesEAno : function(mes,ano,callback){
			$.getJSON('servicos/relatorios/cobranca/mesCobranca('+mes+','+ano+')',function(allData){
				var cobrancas = Conversor.mapCobrancas(allData);
				callback(cobrancas);
			});
		},

		getCobrancasByMesInicioEFim : function(mesInicio,mesFim,callback){
			$.getJSON('servicos/relatorios/cobranca/'+mesInicio+'/'+mesFim,
				function(allData){
				var cobrancas = Conversor.mapCobrancas(allData);
				callback(cobrancas);
			}); 
		},

		getCobrancasByDespesaMesInicioEFim : function(despesasIds,mesInicio,mesFim,callback){
			$.getJSON('servicos/relatorios/cobranca/despesas('+despesasIds.join(',')+')/'+mesInicio+'/'+mesFim,
				function(allData){
				var cobrancas = Conversor.mapCobrancas(allData);
				callback(cobrancas);
			}); 
		},

		getCobrancasByDespesasComMes : function(despesasIds,idMes,callback){
			$.getJSON('servicos/relatorios/cobranca/despesas('+despesasIds.join(',')+')/'+idMes,
				function(allData){
				var cobrancas = Conversor.mapCobrancas(allData);
				callback(cobrancas);
			}); 
		},

		geraCobranca: function(data,callback){
			$.ajax('servicos/cobrancas',{
				data : ko.toJSON(data),
				type : 'post',
				contentType : 'application/json'
			})
			.done(callback)
			.fail(function(result){ console.log(result)});
		}

	};
});