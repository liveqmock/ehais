
$(function(){
	$("header .fa-chevron-left").click(function(){window.history.back();});
	if(parseInt(window.location.pathname.indexOf("w_address_add")) > 0){
		$("#submitAddress").click(function(){w_address_add();});
	}else if(parseInt(window.location.pathname.indexOf("w_address_edit")) > 0){
		$("#submitAddress").click(function(){w_address_edit();});
		$("header .fa-trash-o").addClass("activex");
	}
	
	$("header .fa-trash-o").click(function(){w_address_delete();});
	$(".isDefault").click(function(){
		$(this).toggleClass("active");
		if($(this).hasClass("active")){
			$("#isDefault").val(1);
		}else{
			$("#isDefault").val(0);
		}
	});
});

//新增地址
function w_address_add(){
	$.ajax({
		url : "/ws/useraddress_add_submit",type:"post",dataType:"json",
		data : $("form").serialize(),
		success : function(result){
			elay.open({
			    content: result.msg
			    ,btn: '朕知道了'
			    ,yes: function(index){  
			    	if(result.code ==1){
			    		window.history.go(-1);
			    	}
			    	
			    }
			});
		},error : function(err,xhr){
			
		}
	});
}
//编辑地址
function w_address_edit(){
	$.ajax({
		url : "/ws/useraddress_edit_submit",type:"post",dataType:"json",
		data : $("form").serialize(),
		success : function(result){
			var layerIndex = layer.open({
			    content: result.msg
			    ,btn: ['朕知道了']
			    ,yes: function(index){ 
			    	
			    	if(result.code ==1){
			    		window.history.go(-1);
			    	}
			    	layer.close(layerIndex);
			    	layerIndex = null;
			    }
			});
		},error : function(err,xhr){
			
		}
	});
}
function w_address_delete(){
	var layerIndex = layer.open({
	    content: '确定要删除此地址吗？'
	    ,btn: ['确定' , '取消']
	    ,yes: function(index){  
	    	
			layer.close(layerIndex);				
			layerIndex = null;
			
			$.ajax({
				url : "/ws/useraddress_delete_sumbit",type:"post",dataType:"json",
				data : {addressId : $("#addressId").val()},
				success : function(result){
					
					layerIndex = layer.open({
					    content: result.msg
					    ,btn: ['朕知道了']
					    ,yes: function(index){  
					    	if(result.code ==1){
					    		layer.close(layerIndex);
					    		window.history.go(-1);
					    	}
					    }
					    });
				},error : function(err,xhr){
					
				}
			});
			
	    }
	 });
}