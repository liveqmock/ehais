var warehouseId = 0;
var bsTable ;
var purchaseNo = "";
var haiWarehouseModal ; 


$(function(){
	haiWarehouseModal = $("#haiWarehouseModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	getTree();
	
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){purchaseNo = $.trim($("#purchaseNo").val());bsTable.bootstrapTable('refresh', { query : {warehouseId : warehouseId , purchaseNo : purchaseNo , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiPurchaseListJson',//请求后台的url
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
                warehouseId : warehouseId,
                purchaseNo : purchaseNo
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'purchaseId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'purchaseId',
    title: '进货单'
},{
    field: 'purchaseNo',
    title: '进货单'
},{
    field: 'orderId',
    title: '订单编号'
},{
    field: 'orderSnRmk',
    title: '无主订单编码'
},{
    field: 'goodsId',
    title: '商品编号'
},{
    field: 'goodsNameRmk',
    title: '无记录的商品名称'
},{
    field: 'purchaseTime',
    title: '进货时间'
},{
    field: 'quantity',
    title: '进货数量'
},{
    field: 'warehouseId',
    title: '仓库编号'
},

        {
            field: 'purchaseId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='haiPurchaseEditDetail?purchaseId="+value+"'  class='glyphicon glyphicon-pencil'></a>";
            	var b = "&nbsp;&nbsp;<a href ='javascript:;' onclick='haiPurchaseDelete("+value+");' class='glyphicon glyphicon-trash'></a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	
        	var cat = res.map.warehouseList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].warehouseId] = cat[i].warehouseName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].warehouseName = catObj[rows[i].warehouseId];
        	}
        	
        	return res;
        }
    });
    
    
});





function haiPurchaseDelete(purchaseId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiPurchaseDelete",type:"post",dataType:"json",data:{purchaseId:purchaseId},
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
		url : "haiWarehouseListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].warehouseName , id : rows[i].warehouseId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "进货管理分类",
			        nodes: nodes
			    }],
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
		        	if(data.nodeId == 0){
		        		warehouseId = 0;
		        	}else{
		        		warehouseId = data.id;
		        	}
		        	$("#purchaseNo").val("");purchaseNo = "";
		        	bsTable.bootstrapTable('refresh', { query : {warehouseId : warehouseId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){

	$("#haiWarehouseFormModal")[0].reset();
	
	haiWarehouseModal.modal("show");
	
	$("#haiWarehouseFormSubmit").unbind();
	$("#haiWarehouseFormSubmit").click(function(){addCateSubmit();});
	
	
}


function addCateSubmit(){
	
	if($("#warehouseName").val() == undefined || $("#warehouseName").val().length == 0){
		layer.msg("请输入仓库名称");return ;
	}
	if($("#sort").val() == undefined || $("#sort").val().length == 0){
		layer.msg("请输入排序");return ;
	}

	
	
	$.ajax({
		url : "haiWarehouseAddSubmit",
		data  : $("#haiWarehouseFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiWarehouseModal.modal("hide");
		}
	});
	
	
}


function editCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择分类");
		return ;
	}
	var warehouseId = node[0].id;
	
	
	$.ajax({
		url : "haiWarehouseEditDetail",
		data  : {warehouseId : warehouseId},
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			
			$("#haiWarehouseFormModal")[0].reset();
			
			categoryModal.modal("show");
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			})
			
			$("#haiWarehouseFormSubmit").unbind();
			$("#haiWarehouseFormSubmit").click(function(){editCateSubmit();});
			
			
		}
	});
	
}


function editCateSubmit(){
	
	if($("#warehouseName").val() == undefined || $("#warehouseName").val().length == 0){
		layer.msg("请输入仓库名称");return ;
	}
	if($("#sort").val() == undefined || $("#sort").val().length == 0){
		layer.msg("请输入排序");return ;
	}

	
	$.ajax({
		url : "haiWarehouseEditSubmit",
		data  : $("#haiWarehouseFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiWarehouseModal.modal("hide");
		}
	});
}




function deleteCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择进货管理分类");
		return ;
	}
	var warehouseId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiWarehouseDelete",data:{warehouseId:warehouseId},
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
