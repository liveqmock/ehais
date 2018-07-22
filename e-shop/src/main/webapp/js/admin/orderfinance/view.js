
var keySubId = 0;
var haiTempSublateModal ; 


var bsTable ;
var financeName = "";



$(function(){
	
	
	haiTempSublateModal = $("#haiTempSublateModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	//getTree();
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
	
    $("#btnSearch").click(function(){financeName = $.trim($("#financeName").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , financeName : financeName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiOrderFinanceListJson',//请求后台的url
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
                keySubId : keySubId,
                financeName : financeName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'financeId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'orderDate',
    title: '订单日期'
},{
    field: 'userType',
    title: '用户类型'
},{
    field: 'habit',
    title: '记帐习惯'
},{
    field: 'docType',
    title: '帐单类型'
},{
    field: 'goodsName',
    title: '商品名称'
},{
    field: 'quantity',
    title: '数量'
},{
    field: 'sellPrice',
    title: '销售价格'
},{
    field: 'costPrice',
    title: '成本价格'
},{
    field: 'averagePrice',
    title: '移动平均价'
},{
    field: 'totalAmount',
    title: '总价格'
},{
    field: 'accountName',
    title: '帐户名称'
},{
    field: 'money',
    title: '金额'
},{
    field: 'clientName',
    title: '客户名称'
},{
    field: 'remark',
    title: '备注'
},

        {
            field: 'financeId',
            title: '操作',
            formatter : function(value,row,index){
				var opt = "";
            	opt += "<a href ='haiOrderFinanceEditDetail?financeId="+value+"'  class='glyphicon glyphicon-edit'></a>";
            	opt += "&nbsp;&nbsp;<a href ='javascript:;' onclick='haiOrderFinanceDelete("+value+");' class='glyphicon glyphicon-trash'></a>";
            	return opt;
            }
        }
        
        ],responseHandler : function (res){
        	/**
        	var cat = res.map.tempSublateList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].keySubId] = cat[i].mustSubName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].mustSubName = catObj[rows[i].keySubId];
        	}
        	**/
        	return res;
        }
    });
    
    
});





function haiOrderFinanceDelete(financeId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiOrderFinanceDelete",type:"post",dataType:"json",data:{financeId:financeId},
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
		url : "haiTempSublateListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].mustSubName , id : rows[i].keySubId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "总表管理分类",
			        nodes: nodes
			    }],
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
		        	if(data.nodeId == 0){
		        		keySubId = 0;
		        	}else{
		        		keySubId = data.id;
		        	}
		        	$("#financeName").val("");financeName = "";
		        	bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){

	$("#haiTempSublateFormModal")[0].reset();
	
	haiTempSublateModal.modal("show");
	
	$("#haiTempSublateFormSubmit").unbind();
	$("#haiTempSublateFormSubmit").click(function(){addCateSubmit();});
	
	
}


function addCateSubmit(){
	

	
	
	$.ajax({
		url : "haiTempSublateAddSubmit",
		data  : $("#haiTempSublateFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiTempSublateModal.modal("hide");
		}
	});
	
	
}


function editCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择分类");
		return ;
	}
	var keySubId = node[0].id;
	
	
	$.ajax({
		url : "haiTempSublateEditDetail",
		data  : {keySubId : keySubId},
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			
			$("#haiTempSublateFormModal")[0].reset();
			
			categoryModal.modal("show");
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			})
			
			$("#haiTempSublateFormSubmit").unbind();
			$("#haiTempSublateFormSubmit").click(function(){editCateSubmit();});
			
			
		}
	});
	
}


function editCateSubmit(){
	

	
	$.ajax({
		url : "haiTempSublateEditSubmit",
		data  : $("#haiTempSublateFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiTempSublateModal.modal("hide");
		}
	});
}




function deleteCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择总表管理分类");
		return ;
	}
	var keySubId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiTempSublateDelete",data:{keySubId:keySubId},
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

