$(function(){
	OrderDiningStatistics();
});
function OrderDiningStatistics(){
	$.ajax({
		url : "AllOrderGoodsStatistics",
		data : {state_date:$("#startDate").val(),end_date:$("#endDate").val()},
		success : function(result){
			var rows = result.rows;
			var goodsArray = new Array();
			var saleCountArray = new Array();
			var dateList = new Array();
			$.each(rows,function(k,v){
				goodsArray.push(v.goodsName);
				saleCountArray.push(v.saleCount);
			});
			option.series[0].data = saleCountArray;
			option.xAxis.data = goodsArray;
			
			myChart.setOption(option);
			
			bsTable.bootstrapTable("load",rows);
			bsTable.bootstrapTable('refresh');
		}
	});
}