var keySubId = 0;
var bsTable ;
var userName = "";


$(function(){

	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){userName = $.trim($("#userName").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , userName : userName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiUsersListJson',//请求后台的url
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
                userName : userName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'userId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'nickname',
    title: '昵称'
},{
    field: 'regTime',
    title: '注册时间'
},{
    field: 'faceImage',
    title: '头像',formatter:function(value,rows,index){
    	return (value != null && value != "") ? "<img src='"+value+"' width='40' height='40'>" : "";
    }
},{
    field: 'subscribe',
    title: '是否关注',formatter:function(value,rows,index){return value == 1 ? "关注":"未关注"}
},{
    field: 'parentNickName',
    title: '推荐人昵称'
},{
    field: 'parentFaceImage',
    title: '推荐头像',formatter:function(value,rows,index){
    	return (value != null && value != "") ? "<img src='"+value+"' width='40' height='40'>" : "";
    }
},

//        {
//            field: 'userId',
//            title: '操作',
//            formatter : function(value,row,index){
//            	var a = "<a href ='haiUsersEditDetail?userId="+value+"'>编辑</a>";
//            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='haiUsersDelete("+value+");' >删除</a>";
//            	return a+b;
//            }
//        }
        
        ],responseHandler : function (res){
        	
        	var cat = res.map.listUserParent ;
        	var catNickName = {};
        	var catFaceImage = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catNickName[cat[i].userId] = cat[i].nickName;
        		catFaceImage[cat[i].userId] = cat[i].faceImage;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].parentNickName = catNickName[rows[i].keySubId];
        		rows[i].parentFaceImage = catFaceImage[rows[i].keySubId];
        	}
        	
        	return res;
        }
    });
    
    
});





function haiUsersDelete(userId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiUsersDelete",type:"post",dataType:"json",data:{userId:userId},
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
			        text: "会员管理分类",
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
		        	$("#userName").val("");userName = "";
		        	bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){
	layer.prompt({title: '请输入会员管理分类名称', formType: 0}, function(text, index){
	    
		if(text == null || text == ""){
			layer.msg("请输入会员管理分类名称");return ;
		}
		$.ajax({
			url : "haiTempSublateAddSubmit",
			data  : {mustSubName : text},
			success : function(result){
				if(result.code != 1){
					layer.msg(result.msg);
					return ;
				}
				layer.close(index);
				layer.msg(result.msg);
				getTree();
			}
		});
	});
}
function editCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择分类");
		return ;
	}
	var keySubId = node[0].id;
	layer.prompt({title: '请输入会员管理分类名称', formType: 0 ,value : node[0].text}, function(text, index){
	    
		if(text == null || text == ""){
			layer.msg("请输入会员管理分类名称");return ;
		}
		$.ajax({
			url : "haiTempSublateEditSubmit",
			data  : {mustSubName : text , keySubId : keySubId},
			success : function(result){
				if(result.code != 1){
					layer.msg(result.msg);
					return ;
				}
				layer.close(index);
				layer.msg(result.msg);
				getTree();
			}
		});
	});
	
}
function deleteCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择会员管理分类");
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
