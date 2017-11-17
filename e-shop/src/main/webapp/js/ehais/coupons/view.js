
var keySubId = 0;
var haiTempSublateModal ; 


var bsTable ;
var couponsName = "";



$(function(){
	
	
	haiTempSublateModal = $("#haiTempSublateModal").modal({ keyboard: false , show : false });
	
	//getTree();
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
	
    $("#btnSearch").click(function(){couponsName = $.trim($("#couponsName").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , couponsName : couponsName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiCouponsListJson',//请求后台的url
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
                couponsName : couponsName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'couponsId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'couponsName',
    title: '优惠名称'
},{
    field: 'couponsQuantity',
    title: '数量限额',formatter : function(value,rows,index){
    	if(value == 0){
    		return "不限";
    	}else{
    		return value;
    	}
    }
},{
    field: 'quota',
    title: '优惠方式',formatter : function(value,rows,index){
    	if(rows.couponsType=="reduce"){
    		return "满"+value+"元减"+rows.discounts+"元";
    	}else if(rows.couponsType == "rebate"){
    		return "满"+value+"元打折"+rows.discounts+"%";
    	}
    }
},{
    field: 'scope',
    title: '范围',formatter : function(value,rows,index){
    	if(value == "all"){
    		return "全场";
    	}else if(value.indexOf("g,")==0){
    		return "指定商品"
    	}else if(value.indexOf("c,")==0){
    		return "指定分类"
    	}
    }
},{
    field: 'startDate',
    title: '开始日期',formatter : function(value,rows,index){
    	if(value==null){
    		return "不限";
    	}else{
    		return value.substr(0,10);
    	}
    }
},{
    field: 'endDate',
    title: '截止日期',formatter : function(value,rows,index){
    	if(value==null){
    		return "不限";
    	}else{
    		return value.substr(0,10);
    	}
    }
},{
    field: 'isValid',
    title: '状态',formatter : function(value,rows,index){
    	if(value==0){
    		return "未发布";
    	}else if(value==1){
    		return "发布";
    	}else if(value==2){
    		return "过期";
    	}else if(value==-1){
    		return "失效";
    	}
    }
},{
    field: 'receiveCount',
    title: '领取数量'
},{
    field: 'useCount',
    title: '使用数量'
},

        {
            field: 'couponsId',
            title: '操作',
            formatter : function(value,row,index){
            	
            	var a = "<a href ='haiCouponsEditDetail?couponsId="+value+"'  class='glyphicon glyphicon-pencil'></a>";
            	var b = "&nbsp;&nbsp;<a href ='javascript:;' onclick='haiCouponsDelete("+value+");' class='glyphicon glyphicon-trash'></a>";
            	return a+b;
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





function haiCouponsDelete(couponsId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiCouponsDelete",type:"post",dataType:"json",data:{couponsId:couponsId},
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
			        text: "优惠券分类",
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
		        	$("#couponsName").val("");couponsName = "";
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
		layer.msg("请选择优惠券分类");
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

