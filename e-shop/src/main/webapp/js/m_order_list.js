$(function(){
    
	$("#btnSearch").click(function(){$('#bsTable').bootstrapTable("refresh");});
    
    $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'tpDiningOrderListJson',//请求后台的url
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
	    pageList: [10, 20,30],
	    queryParamsType:'',
	    queryParams: function (params) {
	        return {
	            rows: params.pageSize,   //页面大小  
                page: params.pageNumber,  //页码        
                sort: params.sort,  //排序列名  
                sortOrder: params.order,//排位命令（desc，asc）
                orderSn : $.trim($("#orderSn").val()),
                goodsDesc : $.trim($("#goodsDesc").val()),
                consignee : $.trim($("#consignee").val()),
                mobile : $.trim($("#mobile").val()),
                address : $.trim($("#address").val()),
                orderStatus : $.trim($("#orderStatus").val())
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'orderId',//每一行的唯一标识，一般为主键列
        columns: [{
            field: 'orderId',
            title: '订单号'
        },{
            field: 'orderSn',
            title: '订单编号'
        }, {
            field: 'consignee',
            title: '收货人'
        }, {
            field: 'mobile',
            title: '手机号'
        }, {
            field: 'goodsDesc',
            title: '点菜名称'
        }, {
            field: 'totalAmount',
            title: '价格'
        }, {
            field: 'orderStatus',
            title: '订单状态',
            formatter : function(value,rows,index){
            	return value ? "确认" : "未确认";
            }
        }, {
            field: 'payStatus',
            title: '支付状态',
            formatter : function(value,rows,index){
            	return value ? "支付" : "未支付";
            }
        }, {
            field: 'addTime',
            title: '订单日期',
            formatter : function(value,rows,index){
            	var newDate = new Date();
            	newDate.setTime(value * 1000);
            	return newDate.format('yyyy-MM-dd h:m:s');
            }
        }, {
            field: 'diningType',
            title: '点餐类型',
            formatter : function (value , rows ,index ){
            	return value == "in_store" ? "在店" : (value == "take_out" ? "外卖" : "未知") ;
            }
        }, {
            field: 'tableno',
            title: '订单日期'
        }]
    });
    
    
});
