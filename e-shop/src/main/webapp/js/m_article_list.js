var myArticleCateModal;//软文分类模态框
var cat_id = 0;
var bsTable ;
var store_id=55;
var keyword = "";
function getArticleCatList(){
	$.ajax({
		url : "/ws/article_cat_list",
		type : "post",
		dataType:"json",
		data:{store_id:store_id,parent_id:0},
		success:function(result){
			var nodes = new Array();
			var rows = result.rows;
			for(var i = 0 ; i < rows.length; i++){
				nodes.push({ text: rows[i].catName, id: rows[i].catId, nodeId: rows[i].catId });
			}
			var treeData = [{
		        text: "金藤酒荟",
		        nodes: nodes
		    }];
			
			$('#tree').treeview({
		        data: treeData,         // data is not optional
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
		            //准备刷新商品列表
		        	if(data.nodeId == 0){
		        		cat_id = 0;
		        	}else{
		        		cat_id = data.id;
		        	}
		        	$("#keyword").val("");keyword = "";
		        	bsTable.bootstrapTable('refresh', { query : {cat_id : cat_id , page : 1} });
		        }
		    });			
		}
	});
}
$(function(){
   
    //获取资讯分类列表
    getArticleCatList();
    
    $("#btnSearch").click(function(){
    	if($.trim($("#keyword").val())!=""){
    		keyword = $.trim($("#keyword").val());
    		bsTable.bootstrapTable('refresh', { query : { keyword : keyword , page : 1 } });
    	}
    });
    
    $("#addArticleInfo").click(function(){
    	window.location.href = "m_article_info";
    });
    
    
    bsTable = $('#bsTable').bootstrapTable({
        url: '/ws/article_list_cid',//请求后台的url
        method: 'post',//请求方式
        toolbar: '#toolbar',//工具按钮用哪个容器
        dataType: "json",
        contentType: "application/x-www-form-urlencoded",
		pagination: true, //分页
		pageNumber: 1,
	    pageSize: 10,
	    pageList: [10, 20,30],
	    queryParamsType:'',
	    queryParams: function (params) {
	        return {
	            rows: params.pageSize,   //页面大小  
                page: params.pageNumber,  //页码        
                sort: params.sort,  //排序列名  
                sortOrder: params.order,//排位命令（desc，asc）
                store_id : store_id,
                cat_id : cat_id,
                keyword : keyword
	        }
	    },
		//search: true, //显示搜索框
        striped: true,//是否显示行间隔色
        cache: false,
        sortable: true,//是否启用排序
        sortOrder: 'asc',//排序方式
        clickToSelect: true,//是否启用点击选中行
        sidePagination: "server", //服务端处理分页
        uniqueId: 'articleId',//每一行的唯一标识，一般为主键列
        columns: [{
            field: 'articleId',
            title: '软文编号'
        }, {
            field: 'title',
            title: '标题'
        }, {
            field: 'description',
            title: '描述 '
        }, {
            field: 'author',
            title: '笔者'
        }, {
            field: 'articleDate',
            title: '发布时间'
        }, {
            field: 'forward_count',
            title: '转发人数'
        }, {
            field: 'buy_count',
            title: '购买数量'
        }, {
            field: 'articleId',
            title: '操作',
            formatter:function(value,row,index){  
            	var a = '<a href="m_article_edit?id='+row.articleId+'">编辑</a>';  
            	var b = '<a href="m_article_delete">删除</a>';  
                return a+b;  
            } 
        }]
    });
    
    myArticleCateModal = $("#myArticleCateModal").modal({backdrop: 'static', keyboard: false , show : false });
    
    
    $("#addCate").click(function(){addCate();});
    $("#editCate").click(function(){editCate();});
    $("#deleteCate").click(function(){deleteCate();});
    
});

function getTree() {
    var data = [{
        text: "金藤酒荟",
        nodes: [{ text: "红酒课堂", id: '00001', nodeId: '00001' }, { text: "红酒产区", id: '00004', nodes: [{ text: '澳洲产区', id: '00005'}]}, { text: "红酒品鉴", id: '00002' }, { text: "红酒美食", id: '00003' }]
    }]
    return data;
}

function addCate(){
	var arr = $('#tree').treeview('getSelected');
	
            
//	myArticleCateModal.modal("show");
}
function editCate(){
	
}
function deleteCate(){
	
}
