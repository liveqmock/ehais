var keySubId = 0;
var bsTable ;
var tablename = "";


$(function(){

	
    $("#btnSearch").click(function(){tablename = $.trim($("#tablename").val());bsTable.bootstrapTable('refresh', { query : {cat_id : cat_id , tablename : tablename , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'tpDiningTableListJson',//请求后台的url
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
                tablename : tablename
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'dtId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'dtId',
    title: '编号'
},{
    field: 'tablename',
    title: '台桌编号/名称'
},{
    field: 'dtId',
    title: '预览/下载二维码',
    formatter : function(value,row,index){
    	return "<a href ='tpDiningTableQRCodeExport?dtId="+value+"&preview=1' target='_blank'>预览</a>&nbsp;|&nbsp;"+
    	"<a href ='tpDiningTableQRCodeExport?dtId="+value+"&download=1' target='_blank'>导出二维码</a>";
    }
},

        {
            field: 'dtId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='tpDiningTableEditDetail?dtId="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='tpDiningTableDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	
        	return res;
        }
    });
    
    
});





function tpDiningTableDelete(dtId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "tpDiningTableDelete",type:"post",dataType:"json",data:{dtId:dtId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}


