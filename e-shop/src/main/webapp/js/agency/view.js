var cat_id = 0;
var bsTable ;
var agencyName = "";


$(function(){

	
    $("#btnSearch").click(function(){agencyName = $.trim($("#agencyName").val());bsTable.bootstrapTable('refresh', { query : {cat_id : cat_id , agencyName : agencyName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiAgencyListJson',//请求后台的url
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
//                cat_id : cat_id,
                agencyName : agencyName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'agencyId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'agencyId',
    title: '编号'
},{
    field: 'agencyName',
    title: '代理等级名称'
},{
    field: 'agencyDesc',
    title: '代理等级描述'
},{
    field: 'agencyLevel',
    title: '代理级别'
},

        {
            field: 'agencyId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='haiAgencyEditDetail?agencyId="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='haiAgencyDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }
        
        ]
    });
    
    
});

function haiAgencyDelete(agencyId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiAgencyDelete",type:"post",dataType:"json",data:{agencyId:agencyId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}
