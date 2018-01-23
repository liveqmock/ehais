var mediaAdModal;
var adTable ;

$(function(){
	
	mediaAdModal = $("#mediaAdModal").modal({ backdrop: 'static', keyboard: false , show : false });
	
	
	adTable = $('#adTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'mediaAdListJson',//请求后台的url
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
//                sortOrder: params.order,//排位命令（desc，asc）
//                cat_id : cat_id,
//                keyword : keyword
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'adId',//每一行的唯一标识，一般为主键列
        columns: [

			{
			    field: 'adId',
			    title: '编号'
			},{
			    field: 'adName',
			    title: '宣传图片标题'
			},{
			    field: 'adPic',
			    title: '图片',formatter:function(value,rows,index){
			    	if(!isBlank(value))return "<img src='"+value+"' height='60'>";
			    	return "";
			    }
			},
			{
	            field: 'adId',
	            title: '操作',
	            formatter : function(value,row,index){
	            	var a = "<a href ='javascript:;' onclick='mediaAdEditDetail("+value+");'>编辑</a>";
	            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='mediaAdDelete("+value+");' >删除</a>";
	            	return a+b;
	            }
	        }
        
        ]
    });
	
	
	$("#mediaAdAdd").click(function(){
		switchTable("adMainTable");
		mediaAdModal.modal("show");
		$("#mediaAdFormModal").attr("action","add");
		$("#qiniu_image_adPic").remove();
		$("#eq_preview_wrapper_adPic").addClass("hide");
		//初始化值
		$("input,textarea").val("");
	});
	
	$("#mediaAdList").click(function(){
		switchTable("adMainTable");
	});
	
	
	$("#adSubmit").click(function(){$("#mediaAdFormModal").attr("action") == "add" ? mediaAdAddSubmit() : mediaAdEditSubmit() ;});

	
});






//新增宣传片提交
function mediaAdAddSubmit(){
	
	var adName = $("#adName").val();
	var adPic = $("#adPic").val();
	if(adName.length == 0){
		layer.msg("请输入标题");
		return false;
	}
	
	if(adPic.length == 0){
		layer.msg("请上传图片");
		return false;
	}
	
	$.ajax({
		url : "mediaAdAddSubmit",
		type:"post",dataType:"json",data:$("#mediaAdFormModal").serialize(),
		success:function(result){
			layer.msg(result.msg);
			if(result.code != 1){				
				return ;
			}else{
				mediaAdModal.modal("hide");				
				adTable.bootstrapTable('refresh', { query : {cat_id : cat_id , title : keyword , page : 1} });
			}
		}
	});
}


function mediaAdEditDetail(adId){
	
	
	$("#qiniu_image_adPic").remove();
	$("#eq_preview_wrapper_adPic").addClass("hide");
	
	$.ajax({
		url : "mediaAdEditDetail",data:{adId:adId},dataType:"json",type:"post",
		success:function(result){
			
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			});
			
			if(!isBlank(result.model.adPic))show_adPic_pic(result.model.adPic);
			
			mediaAdModal.modal("show");
			$("#mediaAdFormModal").attr("action","edit");
			
			
		}
	});
	
	
}


function mediaAdEditSubmit(){
	var adName = $("#adName").val();
	var adPic = $("#adPic").val();
	if(adName.length == 0){
		layer.msg("请输入标题");
		return false;
	}
	
	if(adPic.length == 0){
		layer.msg("请上传图片");
		return false;
	}
	
	$.ajax({
		url : "mediaAdEditSubmit",
		type:"post",dataType:"json",data:$("#mediaAdFormModal").serialize(),
		success:function(result){
			layer.msg(result.msg);
			if(result.code != 1){				
				return ;
			}else{
				mediaAdModal.modal("hide");				
				adTable.bootstrapTable('refresh', { query : {cat_id : cat_id , title : keyword , page : 1} });
			}
		}
	});
}


function mediaAdDelete(adId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "mediaAdDelete",type:"post",dataType:"json",data:{adId:adId},
			success:function(result){
				layer.msg(result.msg);
				adTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}




