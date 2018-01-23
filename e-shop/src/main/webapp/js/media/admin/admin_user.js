var mediaAdminUserModal;
var adminTable ;

$(function(){
	
	mediaAdminUserModal = $("#mediaAdminUserModal").modal({ backdrop: 'static', keyboard: false , show : false });
	
	
	adminTable = $('#adminTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'mediaAdminUserListJson',//请求后台的url
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
	    pageSize: 50,
	    pageList: [10, 20,30,40,50],
	    queryParamsType:'',
	    queryParams: function (params) {
	        return {
	            rows: params.pageSize,   //页面大小  
                page: params.pageNumber,  //页码        
                sort: params.sort,  //排序列名  
                sortOrder: params.order,//排位命令（desc，asc）
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'adId',//每一行的唯一标识，一般为主键列
        columns: [

			{
			    field: 'adminId',
			    title: '编号'
			},{
			    field: 'userName',
			    title: '用户名'
			},{
			    field: 'email',
			    title: '邮箱'
			},{
	            field: 'adminId',
	            title: '操作',
	            formatter : function(value,row,index){
	            	var a = "<a href ='javascript:;' onclick='mediaAdminUserEditDetail("+value+");'>编辑</a>";
	            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='mediaAdminUserDelete("+value+");' >删除</a>";
	            	return a+b;
	            }
	        }
        
        ]
    });
	
	
	$("#mediaAdminUserAdd").click(function(){
		switchTable("adminMainTable");
		mediaAdminUserModal.modal("show");
		$("#mediaAdminUserFormModal").attr("action","add");
		//初始化值
		$("input,textarea").val("");
		
		$("#div_password").show();
		$("#div_confirm_password").show();
	});
	
	$("#mediaAdminUserList").click(function(){
		switchTable("adminMainTable");
	});
	
	
	$("#AdminUserSubmit").click(function(){$("#mediaAdminUserFormModal").attr("action") == "add" ? mediaAdminUserAddSubmit() : mediaAdminUserEditSubmit() ;});

	
});






//新增宣传片提交
function mediaAdminUserAddSubmit(){
	
	var userName = $("#userName").val();
	var password = $("#password").val();
	var confirm_password = $("#confirm_password").val();
	if(userName.length == 0){
		layer.msg("请输入姓名");
		return false;
	}
	
	if(password.length == 0){
		layer.msg("请输入密码");
		return false;
	}
	
	if(confirm_password.length == 0){
		layer.msg("请输入确认密码");
		return false;
	}
	
	if(confirm_password != password){
		layer.msg("确认密码不致");
		return false;
	}
	
	
	$.ajax({
		url : "mediaAdminUserAddSubmit",
		type:"post",dataType:"json",data:$("#mediaAdminUserFormModal").serialize(),
		success:function(result){
			layer.msg(result.msg);
			if(result.code != 1){				
				return ;
			}else{
				mediaAdminUserModal.modal("hide");				
				adminTable.bootstrapTable('refresh', { query : {cat_id : cat_id , title : keyword , page : 1} });
			}
		}
	});
}


function mediaAdminUserEditDetail(adminId){
	$("#div_password").hide();
	$("#div_confirm_password").hide();
	
	$.ajax({
		url : "mediaAdminUserEditDetail",data:{adminId:adminId},dataType:"json",type:"post",
		success:function(result){
			
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			});
			
			$("#password").val("");
			$("#confirm_password").val("");
			
			
			mediaAdminUserModal.modal("show");
			$("#mediaAdminUserFormModal").attr("action","edit");
			
			
		}
	});
	
	
}


function mediaAdminUserEditSubmit(){
	var userName = $("#userName").val();
	var password = $("#password").val();
	var confirm_password = $("#confirm_password").val();
	if(userName.length == 0){
		layer.msg("请输入姓名");
		return false;
	}
	
	
	$.ajax({
		url : "mediaAdminUserEditSubmit",
		type:"post",dataType:"json",data:$("#mediaAdminUserFormModal").serialize(),
		success:function(result){
			layer.msg(result.msg);
			if(result.code != 1){				
				return ;
			}else{
				mediaAdminUserModal.modal("hide");				
				adminTable.bootstrapTable('refresh', { query : {cat_id : cat_id , title : keyword , page : 1} });
			}
		}
	});
}


function mediaAdminUserDelete(adId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "mediaAdminUserDelete",type:"post",dataType:"json",data:{adminId:adminId},
			success:function(result){
				layer.msg(result.msg);
				adminTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}




