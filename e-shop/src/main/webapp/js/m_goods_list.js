var cat_id = 0;
var bsTable ;
var keyword = "";


$(function(){
	getTree();
	
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){keyword = $.trim($("#keyword").val());bsTable.bootstrapTable('refresh', { query : {cat_id : cat_id , keyword : keyword , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'tpDiningListJson',//请求后台的url
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
	    pageList: [10, 20,30],
	    queryParamsType:'',
	    queryParams: function (params) {
	        return {
	            rows: params.pageSize,   //页面大小  
                page: params.pageNumber,  //页码        
                sort: params.sort,  //排序列名  
                sortOrder: params.order,//排位命令（desc，asc）
                cat_id : cat_id,
                keyword : keyword
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'goodsId',//每一行的唯一标识，一般为主键列
        columns: [{
            field: 'goodsId',
            title: '商品编号'
        }, {
            field: 'goodsName',
            title: '标题'
        }, {
            field: 'shopPrice',
            title: '价格'
        }, {
            field: 'isOnSale',
            title: '上/下架',
            formatter : function(value,rows,index){
            	return value == true?"上架":"下架";
            }
        }, {
            field: 'isHot',
            title: '热销',
            formatter : function(value,rows,index){
            	return value == true?"热销":"普通";
            }
        }, {
            field: 'goodsId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='tpDiningEditDetail?goodsId="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='tpDiningDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }]
    });
    
    
});

function getTree() {
	$.ajax({
		url : "tpDiningCategoryListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].name , id : rows[i].id });
			}
			
			
			
		    $('#tree').treeview({
		        data: [{
			        text: "菜品分类",
			        nodes: nodes
			    }],
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
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

function tpDiningDelete(goodsId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "tpDiningDelete",type:"post",dataType:"json",data:{goodsId:goodsId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

function addCate(){
	layer.prompt({title: '请输入菜品分类名称', formType: 0}, function(text, index){
	    console.log(text);
		if(text == null || text == ""){
			layer.msg("请输入菜品分类名称");return ;
		}
		$.ajax({
			url : "tpDiningCategoryAddSubmit",
			data  : {name : text},
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
	var id = node[0].id;
	layer.prompt({title: '请输入菜品分类名称', formType: 0 ,value : node[0].text}, function(text, index){
	    
		if(text == null || text == ""){
			layer.msg("请输入菜品分类名称");return ;
		}
		$.ajax({
			url : "tpDiningCategoryEditSubmit",
			data  : {name : text,id : id},
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
		layer.msg("请选择分类");
		return ;
	}
	var id = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "tpDiningCategoryDelete",data:{id:id},
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
