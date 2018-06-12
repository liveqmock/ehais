var haiPaymentModal ;
var key_paymentName = "";
$(function(){
	haiPaymentModal = $("#haiPaymentModal").modal({ keyboard: false , show : false });
	
	$("#haiPaymentAddDetail").click(function(){
		haiPaymentAddDetail();
	});
	
	$("#haiPaymentSearch").click(function(){
		key_paymentName = $.trim($("#key_paymentName").val());
		bsTable.bootstrapTable('refresh', { query : { paymentName : key_paymentName , page : 1} });
	});
    
	
	$("#haiPaymentSaveSubmit").click(function(){$("#haiPaymentForm").attr("action") == "add" ? haiPaymentAddSubmit() : haiPaymentEditSubmit() ;});

	
});

function haiPaymentAddDetail(){
	haiPaymentModal.modal("show");
	$("#haiPaymentForm").attr("action","add");
	$('#haiPaymentForm')[0].reset();
}


function haiPaymentEditDetail(paymentId){
	$("#haiPaymentForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiPaymentEditDetail",type:"post",dataType:"json",data:{paymentId:paymentId},
		success:function(result){
			layer.closeAll();
			haiPaymentModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
		}
	});
}



function haiPaymentAddSubmit(){
	
	if($("#payCode").val() == undefined || $("#payCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#payName").val() == undefined || $("#payName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiPaymentAddSubmit",
		type:"post",dataType:"json",data:$("#haiPaymentForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiPaymentForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiPaymentModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}

function haiPaymentEditSubmit(){
	
	if($("#payCode").val() == undefined || $("#payCode").val().length == 0){
		layer.msg("请输入简码");return ;
	}
	if($("#payName").val() == undefined || $("#payName").val().length == 0){
		layer.msg("请输入名称");return ;
	}

	
	
	$.ajax({
		url : "haiPaymentEditSubmit",
		type:"post",dataType:"json",data:$("#haiPaymentForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiPaymentForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiPaymentModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
				});
			}
		}
	});
}



function haiPaymentDelete(paymentId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiPaymentDelete",type:"post",dataType:"json",data:{paymentId:paymentId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

