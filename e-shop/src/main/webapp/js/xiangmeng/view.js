var keySubId = 0;
var bsTable ;
var title = "";


$(function(){
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){title = $.trim($("#title").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , title : title , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'iXiangmengListJson',//请求后台的url
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
        uniqueId: 'articleId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'articleId',
    title: '自动编号'
},{
    field: 'title',
    title: '商品标题'
},{
    field: 'link',
    title: '象盟链接'
},{
    field: 'description',
    title: '描述'
},{
    field: 'articleThumb',
    title: '图片'
},{
    field: 'articleId',
    title: '二维码',formatter : function(value,rows,index){    	
    	return "<div class='qrcode' value='"+value+"'></div>";
    }
},

        {
            field: 'articleId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='iXiangmengEditDetail?articleId="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='iXiangmengDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	return res;
        },onLoadSuccess : function(data){
        	$(".qrcode").each(function(index,ele){
        		$(ele).qrcode({width: 80,height: 80,text: "http://"+window.location.host+"/wxIXiangmeng?articleId="+$(ele).attr("value")});
        	});
        }
    });
    
    
});





function iXiangmengDelete(articleId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "iXiangmengDelete",type:"post",dataType:"json",data:{articleId:articleId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}


