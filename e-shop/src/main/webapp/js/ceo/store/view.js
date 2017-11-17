var partnerId = 0;
var bsTable ;
var storeName = "";


$(function(){
	
	$("#sel_partner_id").change(function(){
		partnerId = $(this).val();
		if(parseInt(partnerId) > 0){
			bsTable.pageNumber =1;
			bsTable.bootstrapTable('refresh', { query : {partnerId : partnerId , storeName : storeName , page : 1} });
		}
	});
	
	
    $("#btnSearch").click(function(){storeName = $.trim($("#storeName").val());bsTable.bootstrapTable('refresh', { query : {partnerId : partnerId , storeName : storeName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiStoreListJson',//请求后台的url
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
                partnerId : partnerId,
                storeName : storeName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'storeId',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'storeName',
    title: '商家名称'
},{
    field: 'address',
    title: '地址详情'
},{
    field: 'contacts',
    title: '联系人'
},{
    field: 'mobile',
    title: '手机号'
},{
    field: 'state',
    title: '状态',formatter : function(value,row,index){
    	if(value == 1){
    		return "生效";
    	}else{
    		return "冻结";
    	}
    }
},

        {
            field: 'storeId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='javascript:;'>编辑</a>";
            	var a = "<a href ='javascript:;' onclick='storeCache("+value+");'>缓存</a>";
            	return a;
            }
        }
        
        ],responseHandler : function (res){
        	
        	/*var cat = res.map.tempSublateList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].partnerId] = cat[i].mustSubName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].mustSubName = catObj[rows[i].partnerId];
        	}
        	*/
        	return res;
        }
    });
    
    
});

function storeCache(storeId){
	$.ajax({
		url : "haiStoreCache",data:{storeId:storeId},
		success : function(result){
			layer.msg(result.msg);
		}
	});
}

