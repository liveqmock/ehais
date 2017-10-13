var keySubId = 0;
var bsTable ;
var title = "";


$(function(){

    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'wpCustomMenuListJson',//请求后台的url
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
                title : title
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'id',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'title',
    title: '标题',formatter:function(value,rows,index){
    	if(rows.level > 0){
    		return "<i style='padding-right:35px;'></i>"+value;
    	}else{
    		return "<b><i style='padding-right:15px;'></i>"+value+"</b>";
    	}
    }
},{
    field: 'type',
    title: '类型'
},{
    field: 'sort',
    title: '排序'
},{
    field: 'url',
    title: '链接'
},

        {
            field: 'id',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='wpCustomMenuEditDetail?id="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='wpCustomMenuDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	      	
        	var nrows = [];
        	var rows = res.rows;
        	$.each(rows,function(k,v){
        		
        		if(v.pid == 0){
        			v.level = 0;
        			nrows.push(v);
        			$.each(rows,function(k2,v2){
        				if(v2.pid == v.id){
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





function wpCustomMenuDelete(id){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "wpCustomMenuDelete",type:"post",dataType:"json",data:{id:id},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}



