
var catId = 0;
var haiArticleCatModal ; 


var bsTable ;
var articleName = "";



$(function(){
	
	
	haiArticleCatModal = $("#haiArticleCatModal").modal({ keyboard: false , show : false });
	
	//getTree();
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
	
    $("#btnSearch").click(function(){articleName = $.trim($("#articleName").val());bsTable.bootstrapTable('refresh', { query : {catId : catId , articleName : articleName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiArticleListJson',//请求后台的url
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
                articleName : articleName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'articleId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'articleId',
    title: 'article_id'
},{
    field: 'catId',
    title: 'cat_id'
},{
    field: 'code',
    title: 'code'
},{
    field: 'module',
    title: '资讯模块：资讯、行业、活动'
},{
    field: 'title',
    title: 'title'
},{
    field: 'content',
    title: 'content'
},{
    field: 'author',
    title: 'author'
},{
    field: 'authorEmail',
    title: 'author_email'
},{
    field: 'articleDate',
    title: 'article_date'
},{
    field: 'startPublishDate',
    title: 'start_publish_date'
},{
    field: 'endPublishDate',
    title: 'end_publish_date'
},{
    field: 'startApplyDate',
    title: 'start_apply_date'
},{
    field: 'endApplyDate',
    title: 'end_apply_date'
},{
    field: 'keywords',
    title: 'keywords'
},{
    field: 'isOpen',
    title: 'is_open'
},{
    field: 'createDate',
    title: 'create_date'
},{
    field: 'fileUrl',
    title: 'file_url'
},{
    field: 'openType',
    title: 'open_type'
},{
    field: 'link',
    title: 'link'
},{
    field: 'description',
    title: 'description'
},{
    field: 'storeId',
    title: 'store_id'
},{
    field: 'sort',
    title: 'sort'
},{
    field: 'articleThumb',
    title: 'article_thumb'
},{
    field: 'articleImages',
    title: 'article_images'
},{
    field: 'videoUrl',
    title: 'video_url'
},{
    field: 'articleTypeCode',
    title: 'article_type_code'
},{
    field: 'userId',
    title: 'user_id'
},{
    field: 'updateDate',
    title: 'update_date'
},{
    field: 'articleLabel',
    title: 'article_label'
},{
    field: 'readCount',
    title: '阅读量'
},{
    field: 'praiseCount',
    title: '点赞量'
},{
    field: 'isHot',
    title: 'is_hot'
},{
    field: 'articleSource',
    title: '文章来源，与link兼用'
},

        {
            field: 'articleId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='haiArticleEditDetail?articleId="+value+"'  class='glyphicon glyphicon-pencil'></a>";
            	var b = "&nbsp;&nbsp;<a href ='javascript:;' onclick='haiArticleDelete("+value+");' class='glyphicon glyphicon-trash'></a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	/**
        	var cat = res.map.articlecatList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].catId] = cat[i].catName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].catName = catObj[rows[i].catId];
        	}
        	**/
        	return res;
        }
    });
    
    
});





function haiArticleDelete(articleId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiArticleDelete",type:"post",dataType:"json",data:{articleId:articleId},
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
		url : "haiArticleCatListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].catName , id : rows[i].catId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "文章管理分类",
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
		        	$("#articleName").val("");articleName = "";
		        	bsTable.bootstrapTable('refresh', { query : {catId : catId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){

	$("#haiArticleCatFormModal")[0].reset();
	
	haiArticleCatModal.modal("show");
	
	$("#haiArticleCatFormSubmit").unbind();
	$("#haiArticleCatFormSubmit").click(function(){addCateSubmit();});
	
	
}


function addCateSubmit(){
	
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入cat_name");return ;
	}
	if($("#catType").val() == undefined || $("#catType").val().length == 0){
		layer.msg("请输入cat_type");return ;
	}
	if($("#keywords").val() == undefined || $("#keywords").val().length == 0){
		layer.msg("请输入keywords");return ;
	}
	if($("#catDesc").val() == undefined || $("#catDesc").val().length == 0){
		layer.msg("请输入cat_desc");return ;
	}
	if($("#sortOrder").val() == undefined || $("#sortOrder").val().length == 0){
		layer.msg("请输入sort_order");return ;
	}
	if($("#showInNav").val() == undefined || $("#showInNav").val().length == 0){
		layer.msg("请输入show_in_nav");return ;
	}
	if($("#parentId").val() == undefined || $("#parentId").val().length == 0){
		layer.msg("请输入parent_id");return ;
	}

	
	
	$.ajax({
		url : "haiArticleCatAddSubmit",
		data  : $("#haiArticleCatFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiArticleCatModal.modal("hide");
		}
	});
	
	
}


function editCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择分类");
		return ;
	}
	var catId = node[0].id;
	
	
	$.ajax({
		url : "haiArticleCatEditDetail",
		data  : {catId : catId},
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			
			$("#haiArticleCatFormModal")[0].reset();
			
			categoryModal.modal("show");
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			})
			
			$("#haiArticleCatFormSubmit").unbind();
			$("#haiArticleCatFormSubmit").click(function(){editCateSubmit();});
			
			
		}
	});
	
}


function editCateSubmit(){
	
	if($("#catName").val() == undefined || $("#catName").val().length == 0){
		layer.msg("请输入cat_name");return ;
	}
	if($("#catType").val() == undefined || $("#catType").val().length == 0){
		layer.msg("请输入cat_type");return ;
	}
	if($("#keywords").val() == undefined || $("#keywords").val().length == 0){
		layer.msg("请输入keywords");return ;
	}
	if($("#catDesc").val() == undefined || $("#catDesc").val().length == 0){
		layer.msg("请输入cat_desc");return ;
	}
	if($("#sortOrder").val() == undefined || $("#sortOrder").val().length == 0){
		layer.msg("请输入sort_order");return ;
	}
	if($("#showInNav").val() == undefined || $("#showInNav").val().length == 0){
		layer.msg("请输入show_in_nav");return ;
	}
	if($("#parentId").val() == undefined || $("#parentId").val().length == 0){
		layer.msg("请输入parent_id");return ;
	}

	
	$.ajax({
		url : "haiArticleCatEditSubmit",
		data  : $("#haiArticleCatFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiArticleCatModal.modal("hide");
		}
	});
}




function deleteCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择文章管理分类");
		return ;
	}
	var catId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiArticleCatDelete",data:{catId:catId},
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

