var keySubId = 0;
var bsTable ;
var payName = "";


$(function(){
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){payName = $.trim($("#payName").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , payName : payName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiPaymentListJson',//请求后台的url
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
                payName : payName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'payId',//每一行的唯一标识，一般为主键列
        columns: [

//{
//    field: 'payId',
//    title: 'payId'
//},{
//    field: 'payCode',
//    title: 'payCode'
//},
{
    field: 'payName',
    title: '支付名称'
},
//{
//    field: 'payFee',
//    title: 'payFee'
//},
{
    field: 'payDesc',
    title: '描述'
},{
    field: 'payOrder',
    title: '排序'
},
//{
//    field: 'payConfig',
//    title: 'payConfig'
//},{
//    field: 'enabled',
//    title: 'enabled'
//},{
//    field: 'isDefault',
//    title: 'isDefault'
//},{
//    field: 'isCod',
//    title: 'isCod'
//},{
//    field: 'isOnline',
//    title: 'isOnline'
//},{
//    field: 'className',
//    title: 'className'
//},{
//    field: 'storeId',
//    title: 'storeId'
//},

        {
            field: 'payId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='haiPaymentEditDetail?payId="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='haiPaymentDelete("+value+");' >删除</a>";
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





function haiPaymentDelete(payId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiPaymentDelete",type:"post",dataType:"json",data:{payId:payId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}
