var catId = 0;
var bsTable ;
var activityName = "";


$(function(){

    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiActivitySignStatisticsJson',//请求后台的url
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
                activityId : activityId,
                module:module
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'activityId',//每一行的唯一标识，一般为主键列
        //fixedColumns: true,
        //fixedNumber: 3,
        columns: [
{
    field: 'nickname',
    title: '昵称'
},{
    field: 'faceImage',
    title: '头像',formatter:function(value,rows,index){
    	return (value != null && value != "") ? "<img src='"+value+"' width='40' height='40'>" : "";
    }
},{
    field: 'createTime',
    title: '签到时间'
}
        
        ],responseHandler : function (res){
        	
        	var user = res.map.userList ;
        	if(user == undefined || user == "undefined" || user == null || user.length == 0 )return res;
        	
        	var userObj = {};
        	for(var i = 0 ; i < user.length ; i ++){
        		userObj[user[i].userId] = user[i];
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].nickname = userObj[rows[i].userId].nickname;
        		rows[i].faceImage = userObj[rows[i].userId].faceImage;
        	}
        	
        	return res;
        }
    });
    
    
});



