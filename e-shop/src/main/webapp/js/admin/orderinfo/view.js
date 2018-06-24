
var recId = 0;
var haiOrderGoodsModal ; 


var bsTable ;
var orderName = "";



$(function(){
	
	
	haiOrderGoodsModal = $("#haiOrderGoodsModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	//getTree();
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
	
    $("#btnSearch").click(function(){orderName = $.trim($("#orderName").val());bsTable.bootstrapTable('refresh', { query : {recId : recId , orderName : orderName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiOrderInfoListJson',//请求后台的url
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
                recId : recId,
                orderName : orderName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'orderId',//每一行的唯一标识，一般为主键列
        columns: [



        {
            field: 'orderId',
            title: '操作',
            formatter : function(value,row,index){
				var opt = "";
            	opt += "<a href ='haiOrderInfoEditDetail?orderId="+value+"'  class='glyphicon glyphicon-edit'></a>";
            	opt += "&nbsp;&nbsp;<a href ='javascript:;' onclick='haiOrderInfoDelete("+value+");' class='glyphicon glyphicon-trash'></a>";
            	return opt;
            }
        }
        
        ],responseHandler : function (res){
        	/**
        	var cat = res.map.ordergoodsList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].recId] = cat[i].goodsName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].goodsName = catObj[rows[i].recId];
        	}
        	**/
        	return res;
        }
    });
    
    
});





function haiOrderInfoDelete(orderId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiOrderInfoDelete",type:"post",dataType:"json",data:{orderId:orderId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}


function getTree() {
	$.ajax({
		url : "haiOrderGoodsListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].goodsName , id : rows[i].recId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "订单信息分类",
			        nodes: nodes
			    }],
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
		        	if(data.nodeId == 0){
		        		recId = 0;
		        	}else{
		        		recId = data.id;
		        	}
		        	$("#orderName").val("");orderName = "";
		        	bsTable.bootstrapTable('refresh', { query : {recId : recId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){

	$("#haiOrderGoodsFormModal")[0].reset();
	
	haiOrderGoodsModal.modal("show");
	
	$("#haiOrderGoodsFormSubmit").unbind();
	$("#haiOrderGoodsFormSubmit").click(function(){addCateSubmit();});
	
	
}


function addCateSubmit(){
	
	if($("#orderId").val() == undefined || $("#orderId").val().length == 0){
		layer.msg("请输入order_id");return ;
	}
	if($("#goodsId").val() == undefined || $("#goodsId").val().length == 0){
		layer.msg("请输入goods_id");return ;
	}
	if($("#goodsName").val() == undefined || $("#goodsName").val().length == 0){
		layer.msg("请输入goods_name");return ;
	}
	if($("#goodsSn").val() == undefined || $("#goodsSn").val().length == 0){
		layer.msg("请输入goods_sn");return ;
	}
	if($("#productId").val() == undefined || $("#productId").val().length == 0){
		layer.msg("请输入product_id");return ;
	}
	if($("#goodsNumber").val() == undefined || $("#goodsNumber").val().length == 0){
		layer.msg("请输入goods_number");return ;
	}
	if($("#marketPrice").val() == undefined || $("#marketPrice").val().length == 0){
		layer.msg("请输入market_price");return ;
	}
	if($("#goodsPrice").val() == undefined || $("#goodsPrice").val().length == 0){
		layer.msg("请输入goods_price");return ;
	}
	if($("#goodsAttr").val() == undefined || $("#goodsAttr").val().length == 0){
		layer.msg("请输入goods_attr");return ;
	}
	if($("#sendNumber").val() == undefined || $("#sendNumber").val().length == 0){
		layer.msg("请输入send_number");return ;
	}
	if($("#isReal").val() == undefined || $("#isReal").val().length == 0){
		layer.msg("请输入is_real");return ;
	}
	if($("#extensionCode").val() == undefined || $("#extensionCode").val().length == 0){
		layer.msg("请输入extension_code");return ;
	}
	if($("#parentId").val() == undefined || $("#parentId").val().length == 0){
		layer.msg("请输入parent_id");return ;
	}
	if($("#isGift").val() == undefined || $("#isGift").val().length == 0){
		layer.msg("请输入is_gift");return ;
	}
	if($("#goodsAttrId").val() == undefined || $("#goodsAttrId").val().length == 0){
		layer.msg("请输入goods_attr_id");return ;
	}

	
	
	$.ajax({
		url : "haiOrderGoodsAddSubmit",
		data  : $("#haiOrderGoodsFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiOrderGoodsModal.modal("hide");
		}
	});
	
	
}


function editCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择分类");
		return ;
	}
	var recId = node[0].id;
	
	
	$.ajax({
		url : "haiOrderGoodsEditDetail",
		data  : {recId : recId},
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			
			$("#haiOrderGoodsFormModal")[0].reset();
			
			categoryModal.modal("show");
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			})
			
			$("#haiOrderGoodsFormSubmit").unbind();
			$("#haiOrderGoodsFormSubmit").click(function(){editCateSubmit();});
			
			
		}
	});
	
}


function editCateSubmit(){
	
	if($("#orderId").val() == undefined || $("#orderId").val().length == 0){
		layer.msg("请输入order_id");return ;
	}
	if($("#goodsId").val() == undefined || $("#goodsId").val().length == 0){
		layer.msg("请输入goods_id");return ;
	}
	if($("#goodsName").val() == undefined || $("#goodsName").val().length == 0){
		layer.msg("请输入goods_name");return ;
	}
	if($("#goodsSn").val() == undefined || $("#goodsSn").val().length == 0){
		layer.msg("请输入goods_sn");return ;
	}
	if($("#productId").val() == undefined || $("#productId").val().length == 0){
		layer.msg("请输入product_id");return ;
	}
	if($("#goodsNumber").val() == undefined || $("#goodsNumber").val().length == 0){
		layer.msg("请输入goods_number");return ;
	}
	if($("#marketPrice").val() == undefined || $("#marketPrice").val().length == 0){
		layer.msg("请输入market_price");return ;
	}
	if($("#goodsPrice").val() == undefined || $("#goodsPrice").val().length == 0){
		layer.msg("请输入goods_price");return ;
	}
	if($("#goodsAttr").val() == undefined || $("#goodsAttr").val().length == 0){
		layer.msg("请输入goods_attr");return ;
	}
	if($("#sendNumber").val() == undefined || $("#sendNumber").val().length == 0){
		layer.msg("请输入send_number");return ;
	}
	if($("#isReal").val() == undefined || $("#isReal").val().length == 0){
		layer.msg("请输入is_real");return ;
	}
	if($("#extensionCode").val() == undefined || $("#extensionCode").val().length == 0){
		layer.msg("请输入extension_code");return ;
	}
	if($("#parentId").val() == undefined || $("#parentId").val().length == 0){
		layer.msg("请输入parent_id");return ;
	}
	if($("#isGift").val() == undefined || $("#isGift").val().length == 0){
		layer.msg("请输入is_gift");return ;
	}
	if($("#goodsAttrId").val() == undefined || $("#goodsAttrId").val().length == 0){
		layer.msg("请输入goods_attr_id");return ;
	}

	
	$.ajax({
		url : "haiOrderGoodsEditSubmit",
		data  : $("#haiOrderGoodsFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiOrderGoodsModal.modal("hide");
		}
	});
}




function deleteCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择订单信息分类");
		return ;
	}
	var recId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiOrderGoodsDelete",data:{recId:recId},
			success:function(result){
				layer.msg(result.msg);
				if(result.code != 1){
					return ;
				}
				
				getTree();
			}
		});
	}, function(){
		layer.closeAll();
	});
	
}

