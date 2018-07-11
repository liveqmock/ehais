var catId = 0;
var bsTable ;
var keyword = "";
var className = "";
$(function(){

	$("#upload_file_users").fileupload({
        url: "ep_school_excel.upd",
        formData : { //"file_path":"goods",
			//"is_thumb":<if condition="$bootstrap.is_thumb eq true">true<else />false</if>,
			//"field_name":"import_users"
		},
        dataType: 'json',
        done: function (e, data) {

        },
        beforeSend: function(jqXHR) {
        	layer.load(1, {
  			  shade: [0.5,'#ccc'] //0.1透明度的白色背景
  			});
        },complete: function() {
        	layer.closeAll();
        },
        success : function(result, textStatus, jqXHR){
        	console.log(JSON.stringify(result));
        	layer.msg(result.msg);
			bsTable.bootstrapTable('refresh');
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
	
	
	$("#import_users").click(function(){
		$("#upload_file_users").click();
	});
	

    $("#btnSearch").click(function(){
    	keyword = $.trim($("#keyword").val());
    	className = $.trim($("#className").val());
    	bsTable.bootstrapTable('refresh', { query : { keyword : keyword , className : className , page : 1} });
    });
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'ep_school_user_list_json',//请求后台的url
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
                keyword : keyword,
                className : className,
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'userId',//每一行的唯一标识，一般为主键列
        //fixedColumns: true,
        //fixedNumber: 3,
        columns: [

{
    field: 'userName',
    title: '学号'
},{
    field: 'nickname',
    title: '卡号'
},{
    field: 'realname',
    title: '姓名'
},{
    field: 'question',
    title: '班级'
},{
    field: 'answer',
    title: '上级工号'
},{
    field: 'alias',
    title: '身份'
},

        {
            field: 'userId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='epUserEdit?userId="+value+"' class='glyphicon glyphicon-edit' ></a>";
            	var b = "<a href ='javascript:;' onclick='haiUsersDelete("+value+");' class='glyphicon glyphicon-trash' ></a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	return res;
        }
    });
    
    
    
    
    
});


function haiUsersDelete(userId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "ep_school_user_delete",type:"post",dataType:"json",data:{userId:userId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}
