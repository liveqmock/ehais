$(function(){
	$("#sel_store_id").change(function(){IncomeStatistics();});
	
	$("#sel_partner_id").change(function(){
		if($(this).val().length == 0)return ;
		
		$("#sel_store_id option:not(:first)").remove();
		$.ajax({
			url : "partnerStoreList",data : {partnerId : $(this).val()},
			success : function(result){
				var rows = result.rows;
				$.each(rows,function(k,v){
					$("#sel_store_id").append("<option value='"+v.storeId+"'>"+v.storeName+"</option>");
				});
				
			}
		})
	});
	
	
	
	
});


function IncomeStatistics(){
	$.ajax({
		url : "IncomeStatistics",
		data : {state_date:$("#startDate").val(),end_date:$("#endDate").val(),store_id:$("#sel_store_id").val()},
		success : function(result){
			var rows = result.rows;
			var weixinAmount = new Array();
			var cashAmount = new Array();
			var dateList = new Array();
			$.each(rows,function(k,v){
				weixinAmount.push((v.weixinAmount / 100).toFixed(2));
				cashAmount.push((v.cashAmount / 100).toFixed(2));
				dateList.push( v.payTime.substr(0,10) );
			});
			option.series[0].data = weixinAmount;
			option.series[1].data = cashAmount;
			option.xAxis.data = dateList;
			
			myChart.setOption(option);
			
			bsTable.bootstrapTable("load",rows);
			bsTable.bootstrapTable('refresh');
		}
	});
}
