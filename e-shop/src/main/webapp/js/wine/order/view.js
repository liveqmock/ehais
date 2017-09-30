var keySubId = 0;
var bsTable ;
var orderSn = "";
var orderId ;

$(function(){
	
	$('#myShippingModal').modal({
		keyboard: false
	});
	
	$("#shippingSave").click(function(){shippingSave();});

    $("#btnSearch").click(function(){orderSn = $.trim($("#orderSn").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , orderSn : orderSn , page : 1} });});
    
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
                classify : "shop",
                orderSn : orderSn
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'orderId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'orderSn',
    title: '订单编号'
},{
    field: 'consignee',
    title: '用户'
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
    		return date.format("yyyy-MM-dd hh:mm:ss");
    	}else{
    		return "";
    	}
    }
},{
    field: 'goodsDesc',
    title: '购买红酒'
},{
    field: 'shippingName',
    title: '物流商'
},{
    field: 'shippingNumber',
    title: '物流单号'
},{
	field: 'orderId',
	title: '发货',
	formatter : function(value,rows,index){
		return "<a href='javascript:;' onclick='shipping("+value+");'>发货单<a>";
	}
}
        
        ],responseHandler : function (res){
        	
        	return res;
        }
    });
    
    
});


function shipping(oid){
	orderId = oid;
	$.ajax({
		url : "orderinfoStore",data : {orderId : oid},
		success : function(result){
			
			$('#myShippingModal').modal('show');
			
			if(result.model.shippingId!=null && result.model.shippingId!="" && result.model.shippingId!=0){
				$("#shippingId").val(result.model.shippingId);
			}
			
			$("#shippingNumber").val(result.model.shippingNumber);
			
		}
	});
	
}

function shippingSave(){
	
	if($("#shippingId").val().length == 0){
		layer.msg('请选择物流商');
		return ;
	}
	if($("#shippingNumber").val().length == 0){
		layer.msg('请录入物流单号');
		return ;
	}
	
	$.ajax({
		url : "orderShippingSave",
		data:{
			orderId : orderId,
			shippingId:$("#shippingId").val(),
			shippingName:$("#shippingId").find("option:selected").text(),
			shippingNumber:$("#shippingNumber").val()
		},success : function(result){
			layer.alert(result.msg, {
				  skin: 'layui-layer-molv' //样式类名
				  ,closeBtn: 0
				}, function(index){
				  if(result.code == 1){
					  $('#myShippingModal').modal('hide');
					  layer.close(index);
					  bsTable.bootstrapTable('refresh');
				  }
				});
		}
	})
}

