$(function(){
	
	$("#sel_store_id").change(function(){
		if($(this).val()!=""){
			loadCategoryGoodsStatistics($(this).val());
			store_id = $(this).val();
		}
	});
});


function OrderDiningStatistics(){
	if($("#sel_goods_id").val().length==0)return ;
	option.title.text = $("#sel_goods_id").find("option:selected").text()+"销量统计";
	$.ajax({
		url : "OneOrderGoodsStatistics",
		data : {state_date:$("#startDate").val(),end_date:$("#endDate").val(),"goods_id":$("#sel_goods_id").val(),store_id:store_id},
		success : function(result){
			var rows = result.rows;
			var saleDateArray = new Array();
			var quantityArray = new Array();
			var dateList = new Array();
			$.each(rows,function(k,v){
				saleDateArray.push(v.saleDate.substr(0,10));
				quantityArray.push(v.quantity);
			});
			option.series[0].data = quantityArray;
			option.xAxis.data = saleDateArray;
			
			myChart.setOption(option);
			
			bsTable.bootstrapTable("load",rows);
			bsTable.bootstrapTable('refresh');
		}
	});
}