
var goodsAttrId = 0;
var haiGoodsAttrModal ; 


var bsTable ;
var goodsName = "";



$(function(){
	
	
	haiGoodsAttrModal = $("#haiGoodsAttrModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	//getTree();
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
	
    $("#btnSearch").click(function(){goodsName = $.trim($("#goodsName").val());bsTable.bootstrapTable('refresh', { query : {goodsAttrId : goodsAttrId , goodsName : goodsName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiGoodsListJson',//请求后台的url
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
                goodsAttrId : goodsAttrId,
                goodsName : goodsName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'goodsId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'catId',
    title: '分类编号'
},{
    field: 'goodsSn',
    title: '条形码'
},{
    field: 'goodsCode',
    title: '简码'
},{
    field: 'goodsName',
    title: '商品名称'
},{
    field: 'shopPrice',
    title: '销售价格'
},{
    field: 'costPrice',
    title: '采购价格'
},

        {
            field: 'goodsId',
            title: '操作',
            formatter : function(value,row,index){
				var opt = "";
            	opt += "<a href ='haiGoodsEditDetail?goodsId="+value+"'  class='glyphicon glyphicon-edit'></a>";
            	opt += "&nbsp;&nbsp;<a href ='javascript:;' onclick='haiGoodsDelete("+value+");' class='glyphicon glyphicon-trash'></a>";
            	return opt;
            }
        }
        
        ],responseHandler : function (res){
        	/**
        	var cat = res.map.goodsattrList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].goodsAttrId] = cat[i].attrValue ;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].attrValue  = catObj[rows[i].goodsAttrId];
        	}
        	**/
        	return res;
        }
    });
    
    
});





function haiGoodsDelete(goodsId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiGoodsDelete",type:"post",dataType:"json",data:{goodsId:goodsId},
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
		url : "haiGoodsAttrListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].attrValue  , id : rows[i].goodsAttrId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "商品管理分类",
			        nodes: nodes
			    }],
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
		        	if(data.nodeId == 0){
		        		goodsAttrId = 0;
		        	}else{
		        		goodsAttrId = data.id;
		        	}
		        	$("#goodsName").val("");goodsName = "";
		        	bsTable.bootstrapTable('refresh', { query : {goodsAttrId : goodsAttrId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){

	$("#haiGoodsAttrFormModal")[0].reset();
	
	haiGoodsAttrModal.modal("show");
	
	$("#haiGoodsAttrFormSubmit").unbind();
	$("#haiGoodsAttrFormSubmit").click(function(){addCateSubmit();});
	
	
}


function addCateSubmit(){
	
	if($("#goodsId").val() == undefined || $("#goodsId").val().length == 0){
		layer.msg("请输入goods_id");return ;
	}
	if($("#attrId").val() == undefined || $("#attrId").val().length == 0){
		layer.msg("请输入attr_id");return ;
	}
	if($("#attrValue").val() == undefined || $("#attrValue").val().length == 0){
		layer.msg("请输入attr_value");return ;
	}
	if($("#attrPrice").val() == undefined || $("#attrPrice").val().length == 0){
		layer.msg("请输入attr_price");return ;
	}

	
	
	$.ajax({
		url : "haiGoodsAttrAddSubmit",
		data  : $("#haiGoodsAttrFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiGoodsAttrModal.modal("hide");
		}
	});
	
	
}


function editCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择分类");
		return ;
	}
	var goodsAttrId = node[0].id;
	
	
	$.ajax({
		url : "haiGoodsAttrEditDetail",
		data  : {goodsAttrId : goodsAttrId},
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			
			$("#haiGoodsAttrFormModal")[0].reset();
			
			categoryModal.modal("show");
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			})
			
			$("#haiGoodsAttrFormSubmit").unbind();
			$("#haiGoodsAttrFormSubmit").click(function(){editCateSubmit();});
			
			
		}
	});
	
}


function editCateSubmit(){
	
	if($("#goodsId").val() == undefined || $("#goodsId").val().length == 0){
		layer.msg("请输入goods_id");return ;
	}
	if($("#attrId").val() == undefined || $("#attrId").val().length == 0){
		layer.msg("请输入attr_id");return ;
	}
	if($("#attrValue").val() == undefined || $("#attrValue").val().length == 0){
		layer.msg("请输入attr_value");return ;
	}
	if($("#attrPrice").val() == undefined || $("#attrPrice").val().length == 0){
		layer.msg("请输入attr_price");return ;
	}

	
	$.ajax({
		url : "haiGoodsAttrEditSubmit",
		data  : $("#haiGoodsAttrFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiGoodsAttrModal.modal("hide");
		}
	});
}




function deleteCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择商品管理分类");
		return ;
	}
	var goodsAttrId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiGoodsAttrDelete",data:{goodsAttrId:goodsAttrId},
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

