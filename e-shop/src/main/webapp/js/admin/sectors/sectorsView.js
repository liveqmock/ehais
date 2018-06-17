var haiSectorsModal ;
var key_sectorsName = "";
$(function(){
	haiSectorsModal = $("#haiSectorsModal").modal({ keyboard: false , show : false });
	
	$("#haiSectorsAddDetail").click(function(){
		haiSectorsAddDetail();
	});
	
	$("#haiSectorsSearch").click(function(){
		key_sectorsName = $.trim($("#key_sectorsName").val());
		bsTable.bootstrapTable('refresh', { query : { sectorsName : key_sectorsName , page : 1} });
	});
    
	
	$("#haiSectorsSaveSubmit").click(function(){$("#haiSectorsForm").attr("action") == "add" ? haiSectorsAddSubmit() : haiSectorsEditSubmit() ;});

	//设置自动简码
	$("#sectorsName").setPinyin({"code":"sectorsCode"});
});

function haiSectorsAddDetail(){
	haiSectorsModal.modal("show");
	$("#haiSectorsForm").attr("action","add");
	$('#haiSectorsForm')[0].reset();
}


function haiSectorsEditDetail(sectorsId){
	$("#haiSectorsForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiSectorsEditDetail",type:"post",dataType:"json",data:{sectorsId:sectorsId},
		success:function(result){
			layer.closeAll();
			haiSectorsModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiSectorsAddSubmit(){
	

	
	
	$.ajax({
		url : "haiSectorsAddSubmit",
		type:"post",dataType:"json",data:$("#haiSectorsForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiSectorsForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiSectorsModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiSectorsEditSubmit(){
	

	
	
	$.ajax({
		url : "haiSectorsEditSubmit",
		type:"post",dataType:"json",data:$("#haiSectorsForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiSectorsForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiSectorsModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiSectorsDelete(sectorsId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiSectorsDelete",type:"post",dataType:"json",data:{sectorsId:sectorsId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

