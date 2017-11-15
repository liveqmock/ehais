var keySubId = 0;
var bsTable ;
var orderSn = "";


$(function(){
	
	$("#sel_store_id").change(function(){
		store_id = $(this).val();
		bsTable.pageNumber = 1;
	});

    $("#btnSearch").click(function(){
    	orderSn = $.trim($("#orderSn").val());
	    bsTable.bootstrapTable('refresh', { 
	    	query : {
	    		keySubId : keySubId , 
	    		orderSn : orderSn , 
	    		page : 1,
	    		startDate:$("#startDate").val(),
	    		endDate:$("#endDate").val(),
	    	} 
	    });
    });
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'ehaisOrderListJson',//请求后台的url
        method: 'post',//请求方式
        toolbar: '#toolbar',//工具按钮用哪个容器
        dataType: "json",
		pagination: true, //分页
		//search: true, //显示搜索框
        striped: true,//是否显示行间隔色
        cache: false,
        sortable: true,//是否启用排序
        sortOrder: 'asc',//排序方式
        clickToSelect: true,//是否启用点击选中行
        pageNumber: 1,
	    pageSize: 10,
	    pageList: [10,20,30,40,50],
	    queryParamsType:'',
	    queryParams: function (params) {
	        return {
	            rows: params.pageSize,   //页面大小  
                page: params.pageNumber,  //页码        
                sort: params.sort,  //排序列名  
                sortOrder: params.order,//排位命令（desc，asc）
                orderStatus : 1,
                classify : "dining",
                orderSn : orderSn,
                store_id : store_id
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'orderId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'orderSn',
    title: '订单编号'
},{
    field: 'cardName',
    title: '用户',formatter:function(value,rows,index){
    	return "<img src='"+value+"' width='40' height='40'>";
    }
},{
    field: 'payName',
    title: '支付方式'
},{
    field: 'orderAmount',
    title: '订单金额',
    formatter : function(value,rows,index){
    	return ( value / 100 ).toFixed(2);
    }
},{
    field: 'payTime',
    title: '支付时间',
    formatter : function(value,rows,index){
    	if(parseInt(value) > 0){
    		var date =  new Date(value * 1000);
    		return date.format("yyyy-MM-dd");
    	}else{
    		return "";
    	}
    }
},{
    field: 'zipcode',
    title: '台号'
},{
    field: 'goodsDesc',
    title: '菜品信息'
}
        
        ],responseHandler : function (res){
        	
        	return res;
        }
    });
    
    
});



