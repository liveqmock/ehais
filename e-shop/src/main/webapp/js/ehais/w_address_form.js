
$(function(){
	$("header .fa-chevron-left").click(function(){window.history.back();});
	if(parseInt(window.location.pathname.indexOf("w_address_add")) > 0){
		$("#submitAddress").click(function(){w_address_add();});
	}else if(parseInt(window.location.pathname.indexOf("w_address_edit")) > 0){
		$("#submitAddress").click(function(){w_address_edit();});
		$("header .fa-trash-o").addClass("activex");
	}
	
	$(".icon-default").click(function(){
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
			elay.open({
			    content: result.msg
			    ,btn: ['朕知道了']
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
