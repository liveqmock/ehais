$(function(){
	bsTable = $('#bsTable').bootstrapTable({
        url: bootstrap_table_url,//请求后台的url
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
	        return $.extend({
		            rows: params.pageSize,   //页面大小  
	                page: params.pageNumber,  //页码        
	                sort: params.sort,  //排序列名  
	                sortOrder: params.order,//排位命令（desc，asc）
	       		} ,
	       			(typeof(query_parames) == "undefined" || query_parames == null ? {}  : query_parames)
	       		);
	    },
		//search: true, //显示搜索框
        striped: true,//是否显示行间隔色
        cache: false,
        sortable: true,//是否启用排序
        sortOrder: 'asc',//排序方式
        clickToSelect: true,//是否启用点击选中行
        sidePagination: "server", //服务端处理分页
//      uniqueId: 'Id',//每一行的唯一标识，一般为主键列
        columns: columns 
    });
})
