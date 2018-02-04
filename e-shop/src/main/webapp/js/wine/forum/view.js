var keySubId = 0;
var bsTable ;
var forumName = "";


$(function(){

	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){forumName = $.trim($("#forumName").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , forumName : forumName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiForumListJson',//请求后台的url
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
                tablename : "hai_article"
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'forumId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'title',
    title: '文章'
},{
    field: 'nickname',
    title: '用户名'
},{
    field: 'content',
    title: '内容'
},{
    field: 'createDate',
    title: '品评时间'
}
        
        ],responseHandler : function (res){
        	var listUser = res.map.listUser ;
        	var listArticle = res.map.listArticle ;
        	var userObj = {};
        	if(listUser!=null && listUser.length > 0){
        		for(var i = 0 ; i < listUser.length ; i ++){
            		userObj[listUser[i].userId] = listUser[i].nickname;
            	}
        	}
        	
        	var articleObj = {};
        	if(listArticle!=null && listArticle.length > 0){
        		for(var i = 0 ; i < listArticle.length ; i ++){
            		articleObj[listArticle[i].articleId] = listArticle[i].title;
            	}
        	}
        	
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].nickname = userObj[rows[i].userId];
        		rows[i].title = articleObj[rows[i].tableId];
        	}
        	return res;
        }
    });
    
    
});





function haiForumDelete(forumId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiForumDelete",type:"post",dataType:"json",data:{forumId:forumId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}
