var keySubId = 0;
var bsTable ;
var shippingName = "";


$(function(){
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){shippingName = $.trim($("#shippingName").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , shippingName : shippingName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiShippingListJson',//请求后台的url
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
                shippingName : shippingName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'shippingId',//每一行的唯一标识，一般为主键列
        columns: [

//{
//    field: 'shippingId',
//    title: 'shippingId'
//},{
//    field: 'shippingCode',
//    title: 'shippingCode'
//},
{
    field: 'shippingName',
    title: '名称'
},{
    field: 'shippingDesc',
    title: '描述'
},
//{
//    field: 'insure',
//    title: 'insure'
//},{
//    field: 'supportCod',
//    title: 'supportCod'
//},{
//    field: 'enabled',
//    title: 'enabled'
//},{
//    field: 'isDefault',
//    title: 'isDefault'
//},{
//    field: 'shippingPrint',
//    title: 'shippingPrint'
//},{
//    field: 'printBg',
//    title: 'printBg'
//},{
//    field: 'configLable',
//    title: 'configLable'
//},{
//    field: 'printModel',
//    title: 'printModel'
//},
{
    field: 'shippingOrder',
    title: '排序'
},
//	{
//    field: 'storeId',
//    title: 'storeId'
//},{
//    field: 'className',
//    title: 'className'
//},

        {
            field: 'shippingId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='haiShippingEditDetail?shippingId="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='haiShippingDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	
//        	var cat = res.map.tempSublateList ;
//        	var catObj = {};
//        	for(var i = 0 ; i < cat.length ; i ++){
//        		catObj[cat[i].keySubId] = cat[i].mustSubName;
//        	}
//        	var rows = res.rows;
//        	for(var i = 0 ; i < rows.length ; i++){
//        		rows[i].mustSubName = catObj[rows[i].keySubId];
//        	}
        	
        	return res;
        }
    });
    
    
});





function haiShippingDelete(shippingId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiShippingDelete",type:"post",dataType:"json",data:{shippingId:shippingId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

