
var keySubId = 0;
var projectTempSublateModal ; 


var bsTable ;
var begOffName = "";



$(function(){
	
	
	projectTempSublateModal = $("#projectTempSublateModal").modal({ keyboard: false , show : false });
	

	
	
    $("#btnSearch").click(function(){begOffName = $.trim($("#begOffName").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , begOffName : begOffName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'projectBegOffListJson',//请求后台的url
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
                begOffName : begOffName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'begOffId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'userName',
    title: '学生'
},{
    field: 'number',
    title: '请假天数'
},{
    field: 'reason',
    title: '请假理由'
},{
    field: 'createDate',
    title: '申请日期'
},{
    field: 'teacherName',
    title: '班主任'
},{
    field: 'teacherApprove',
    title: '审批',formatter : approveResult
},{
    field: 'teacherApproveTime',
    title: '审批时间'
},{
    field: 'departmentName',
    title: '部长'
},{
    field: 'departmentApprove',
    title: '复批',formatter : approveResult
},{
    field: 'departmentApproveTime',
    title: '复批时间'
},{
    field: 'leaderName',
    title: '学生处'
},{
    field: 'leaderApprove',
    title: '终批',formatter : approveResult
},{
    field: 'leaderApproveTime',
    title: '终批时间'
}
        
        ],responseHandler : function (res){
        	
        	var userMap = res.map.userMap ;
        	
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].userName = userMap[rows[i].userId];
        		
        		if(parseInt(rows[i].teacherUserId) > 0){
        			rows[i].teacherName = userMap[rows[i].teacherUserId];
        		}else{
        			rows[i].teacherName = "";
        		}
        		
        		if(parseInt(rows[i].departmentUserId) > 0){
        			rows[i].departmentName = userMap[rows[i].departmentUserId];
        		}else{
        			rows[i].departmentName = "";
        		}
        		
        		if(parseInt(rows[i].leaderUserId) > 0){
        			rows[i].leaderName = userMap[rows[i].leaderUserId];
        		}else{
        			rows[i].leaderName = "";
        		}
        	}
        	
        	return res;
        }
    });
    
    
});


function approveResult(value,rows,index){
	if(value == 1){
		return "<i class='glyphicon glyphicon-ok'></i>";
	}else if(value == 2){
		return "<i class='glyphicon glyphicon-remove'></i>";
	}else{
		return "";
	}
}




function projectBegOffDelete(begOffId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "projectBegOffDelete",type:"post",dataType:"json",data:{begOffId:begOffId},
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
		url : "projectTempSublateListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].mustSubName , id : rows[i].keySubId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "请假分类",
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
		        	$("#begOffName").val("");begOffName = "";
		        	bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){

	$("#projectTempSublateFormModal")[0].reset();
	
	projectTempSublateModal.modal("show");
	
	$("#projectTempSublateFormSubmit").unbind();
	$("#projectTempSublateFormSubmit").click(function(){addCateSubmit();});
	
	
}


function addCateSubmit(){
	

	
	
	$.ajax({
		url : "projectTempSublateAddSubmit",
		data  : $("#projectTempSublateFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			projectTempSublateModal.modal("hide");
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
		url : "projectTempSublateEditDetail",
		data  : {keySubId : keySubId},
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			
			$("#projectTempSublateFormModal")[0].reset();
			
			categoryModal.modal("show");
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			})
			
			$("#projectTempSublateFormSubmit").unbind();
			$("#projectTempSublateFormSubmit").click(function(){editCateSubmit();});
			
			
		}
	});
	
}


function editCateSubmit(){
	

	
	$.ajax({
		url : "projectTempSublateEditSubmit",
		data  : $("#projectTempSublateFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			projectTempSublateModal.modal("hide");
		}
	});
}




function deleteCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择请假分类");
		return ;
	}
	var keySubId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "projectTempSublateDelete",data:{keySubId:keySubId},
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

