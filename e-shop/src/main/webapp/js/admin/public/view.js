var keySubId = 0;
var bsTable ;
var publicName = "";


$(function(){
//	getTree();
	
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
    $("#btnSearch").click(function(){publicName = $.trim($("#publicName").val());bsTable.bootstrapTable('refresh', { query : {keySubId : keySubId , publicName : publicName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'wpPublicListJson',//请求后台的url
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
                publicName : publicName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'id',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'id',
    title: 'id'
},{
    field: 'uid',
    title: 'uid'
},{
    field: 'publicName',
    title: 'publicName'
},{
    field: 'publicNo',
    title: 'publicNo'
},{
    field: 'publicId',
    title: 'publicId'
},{
    field: 'wechat',
    title: 'wechat'
},{
    field: 'interfaceUrl',
    title: 'interfaceUrl'
},{
    field: 'headfaceUrl',
    title: 'headfaceUrl'
},{
    field: 'area',
    title: 'area'
},{
    field: 'addonConfig',
    title: 'addonConfig'
},{
    field: 'addonStatus',
    title: 'addonStatus'
},{
    field: 'token',
    title: 'token'
},{
    field: 'isUse',
    title: 'isUse'
},{
    field: 'type',
    title: 'type'
},{
    field: 'appid',
    title: 'appid'
},{
    field: 'secret',
    title: 'secret'
},{
    field: 'groupId',
    title: 'groupId'
},{
    field: 'encodingaeskey',
    title: 'encodingaeskey'
},{
    field: 'tipsUrl',
    title: 'tipsUrl'
},{
    field: 'domain',
    title: 'domain'
},{
    field: 'isBind',
    title: 'isBind'
},{
    field: 'payAppid',
    title: 'payAppid'
},{
    field: 'paySecret',
    title: 'paySecret'
},{
    field: 'mchId',
    title: 'mchId'
},{
    field: 'subMchId',
    title: 'subMchId'
},{
    field: 'mchSecret',
    title: 'mchSecret'
},

        {
            field: 'id',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "&nbsp;|&nbsp;<a href ='wpPublicEditDetail?id="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='wpPublicDelete("+value+");' >删除</a>";
            	var c = "<a href ='javascript:;' onclick='wpPublicCache("+value+");' >缓存</a>";
            	return c+a+b;
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





function wpPublicDelete(id){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "wpPublicDelete",type:"post",dataType:"json",data:{id:id},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}


function wpPublicCache(publicId){
	$.ajax({
		url : "wpPublicCache",type:"post",dataType:"json",data:{publicId:publicId},
		success:function(result){
			layer.msg(result.msg);
		}
	});
}
