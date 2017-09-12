var positionId = 0;
var bsTable ;
var adName = "";


$(function(){
	getTree();
	
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){adName = $.trim($("#adName").val());bsTable.bootstrapTable('refresh', { query : {positionId : positionId , adName : adName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiAdListJson',//请求后台的url
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
                positionId : positionId,
                adName : adName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'adId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'adId',
    title: 'adId'
},{
    field: 'positionId',
    title: 'positionId'
},{
    field: 'mediaType',
    title: 'mediaType'
},{
    field: 'adName',
    title: 'adName'
},{
    field: 'adLink',
    title: 'adLink'
},{
    field: 'adPic',
    title: 'adPic'
},{
    field: 'adCode',
    title: 'adCode'
},{
    field: 'linkMan',
    title: 'linkMan'
},{
    field: 'linkEmail',
    title: 'linkEmail'
},{
    field: 'linkPhone',
    title: 'linkPhone'
},{
    field: 'clickCount',
    title: 'clickCount'
},{
    field: 'enabled',
    title: 'enabled'
},{
    field: 'storeId',
    title: 'storeId'
},{
    field: 'startTime',
    title: 'startTime'
},{
    field: 'endTime',
    title: 'endTime'
},{
    field: 'navId',
    title: 'navId'
},{
    field: 'sort',
    title: 'sort'
},{
    field: 'isMobile',
    title: 'isMobile'
},{
    field: 'agencyId',
    title: 'agencyId'
},{
    field: 'partnerId',
    title: 'partnerId'
},{
    field: 'isVoid',
    title: 'isVoid'
},

        {
            field: 'adId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='haiAdEditDetail?adId="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='haiAdDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	
        	var cat = res.map.adpositionList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].positionId] = cat[i].positionName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].positionName = catObj[rows[i].positionId];
        	}
        	
        	return res;
        }
    });
    
    
});





function haiAdDelete(adId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiAdDelete",type:"post",dataType:"json",data:{adId:adId},
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
		url : "haiAdPositionListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].positionName , id : rows[i].positionId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "广告位置",
			        nodes: nodes
			    }],
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
		        	if(data.nodeId == 0){
		        		positionId = 0;
		        	}else{
		        		positionId = data.id;
		        	}
		        	$("#adName").val("");adName = "";
		        	bsTable.bootstrapTable('refresh', { query : {positionId : positionId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){
	layer.prompt({title: '请输入广告管理分类名称', formType: 0}, function(text, index){
	    
		if(text == null || text == ""){
			layer.msg("请输入广告管理分类名称");return ;
		}
		$.ajax({
			url : "haiAdPositionAddSubmit",
			data  : {positionName : text},
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
	var positionId = node[0].id;
	layer.prompt({title: '请输入广告管理分类名称', formType: 0 ,value : node[0].text}, function(text, index){
	    
		if(text == null || text == ""){
			layer.msg("请输入广告管理分类名称");return ;
		}
		$.ajax({
			url : "haiAdPositionEditSubmit",
			data  : {positionName : text , positionId : positionId},
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
		layer.msg("请选择广告管理分类");
		return ;
	}
	var positionId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiAdPositionDelete",data:{positionId:positionId},
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
