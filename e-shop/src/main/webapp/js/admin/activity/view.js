var catId = 0;
var bsTable ;
var activityName = "";


$(function(){
	getTree();
	
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){activityName = $.trim($("#activityName").val());bsTable.bootstrapTable('refresh', { query : {catId : catId , activityName : activityName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiActivityListJson',//请求后台的url
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
                catId : catId,
                activityName : activityName,
                module:module
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'activityId',//每一行的唯一标识，一般为主键列
        //fixedColumns: true,
        //fixedNumber: 3,
        columns: [

{
    field: 'activityName',
    title: '活动名称',formatter:function(value,rows,index){
    	if(rows.level > 0){
    		return "<i style='padding-right:35px;'></i>"+value;
    	}else{
    		return "<b><i style='padding-right:15px;'></i>"+value+"</b>";
    	}
    },sortable:true
},{
    field: 'catName',
    title: '分类'
},{
    field: 'startTime',
    title: '开始时间',formatter:function(value,rows,index){
    	if(value != null){
    		return value.substr(0,10);
    	}
    }
},{
    field: 'endTime',
    title: '结束时间',formatter:function(value,rows,index){
    	if(value != null){
    		return value.substr(0,10);
    	}
    }
},{
    field: 'releaseTime',
    title: '发布时间',formatter:function(value,rows,index){
    	if(value != null){
    		return value.substr(0,10);
    	}
    }
},{
    field: 'cutOffTime',
    title: '截止报名',formatter:function(value,rows,index){
    	if(value != null){
    		return value.substr(0,10);
    	}
    }
},{
    field: 'applyCount',
    title: '申请人数'
},{
    field: 'playCount',
    title: '参与人数'
},{
    field: 'money',
    title: '活动费用'
},{
    field: 'limitCount',
    title: '限制人数'
},{
    field: 'readCount',
    title: '阅读人数'
},{
    field: 'linkMan',
    title: '联系人'
},{
    field: 'mobile',
    title: '手机'
},{
    field: 'keywords',
    title: '关键词'
},{
    field: 'address',
    title: '地址'
},{
    field: 'organisers',
    title: '举办方'
},

        {
            field: 'activityId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='haiActivityEditDetail?activityId="+value+"&module="+module+"' class='glyphicon glyphicon-pencil'></a>";
            	var b = "<a href ='javascript:;' onclick='haiActivityDelete("+value+");' class='glyphicon glyphicon-trash' ></a>";
            	var c = "<a href ='haiActivityQRcode?activityId="+value+"&module="+module+"' class='glyphicon glyphicon-qrcode' alt='二维码'></a>";
            	var d = "<a href ='haiActivitySignStatistics?activityId="+value+"&module="+module+"' class='glyphicon glyphicon-flag' alt='签到统计'></a>";
            	var e = "";//"<a href ='haiActivityApplyStatistics?activityId="+value+"&module="+module+"' class='glyphicon glyphicon-bullhorn' alt='报名统计'></a>";
            	return c+d+e+a+b;
            },sortable:true
        }
        
        ],responseHandler : function (res){
        	
        	var cat = res.map.articlecatList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].catId] = cat[i].catName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].catName = catObj[rows[i].catId];
        	}
        	
        	var nrows = [];
        	var rows = res.rows;
        	$.each(rows,function(k,v){
        		
        		if(v.parentId == 0){
        			v.level = 0;
        			nrows.push(v);
        			$.each(rows,function(k2,v2){
        				if(v2.parentId == v.activityId){
        					v2.level = 1;
        					nrows.push(v2);
        				}
        			});
        		}
        	});
        	
        	res.rows = nrows;
        	
        	return res;
        }
    });
    
    
});





function haiActivityDelete(activityId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiActivityDelete",type:"post",dataType:"json",data:{activityId:activityId,module:module},
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
		url : "haiActicityCatListJson",type:"post",dataType:"json",data:{module:module},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].catName , id : rows[i].catId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "活动会议管理分类",
			        nodes: nodes
			    }],
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
		        	if(data.nodeId == 0){
		        		catId = 0;
		        	}else{
		        		catId = data.id;
		        	}
		        	$("#activityName").val("");activityName = "";
		        	bsTable.bootstrapTable('refresh', { query : {catId : catId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){
	layer.prompt({title: '请输入活动会议管理分类名称', formType: 0}, function(text, index){
	    
		if(text == null || text == ""){
			layer.msg("请输入活动会议管理分类名称");return ;
		}
		$.ajax({
			url : "haiActicityCatAddSubmit",
			data  : {catName : text,module:module},
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
	var catId = node[0].id;
	layer.prompt({title: '请输入活动会议管理分类名称', formType: 0 ,value : node[0].text}, function(text, index){
	    
		if(text == null || text == ""){
			layer.msg("请输入活动会议管理分类名称");return ;
		}
		$.ajax({
			url : "haiActicityCatEditSubmit",
			data  : {catName : text , catId : catId,module:module},
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
		layer.msg("请选择活动会议管理分类");
		return ;
	}
	var catId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiActicityCatDelete",data:{catId:catId,module:module},
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
