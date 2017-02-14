define(['knockout'],function(ko){
	return {
		getDespesas : function(callback){
			$.getJSON('servicos/despesasRecorrentes', callback);
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
			$.getJSON('servicos/relatorios/cobranca/despesas('+id+')',callback);
		},

		getCobrancasByMes : function(mesId,callback){
			$.getJSON('servicos/relatorios/cobranca/'+mesId,callback);
		},

		getCobrancasByMesEAno : function(mes,ano,callback){
			$.getJSON('servicos/relatorios/cobranca/mesCobranca('+mes+','+ano+')',callback);
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