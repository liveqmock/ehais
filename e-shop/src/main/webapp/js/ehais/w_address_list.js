
$(function(){
	$("header .fa-chevron-left").click(function(){window.history.go(-1);});
	$("header .fa-plus-circle,#newAddress").click(function(){window.location.href="w_address_add";})
	$(".list .item .operate .default").click(function(){
		
		var addressid = $(this).parent().parent().attr("addressid");
		var that = this;
		$.ajax({
			url:"/ws/useraddress_default",type:"post",dataType:"json",
			data : {addressId:addressid},
			success : function(result){
				if(result.code == 1){
					$(".list .item .operate .default").removeClass("active");
					$(that).addClass("active");
					addressid = null;
					that = null;
				}else{
					
				}
			}
		});
	});
	
	$(".list .item .operate .edit").click(function(){
		var addressid = $(this).parent().parent().attr("addressid");
		window.location.href = "w_address_edit?addressId="+addressid;
	});
	
	$(".list .item .operate .trash").click(function(){
		w_address_delete(this);
	});
	
	$(".list .item .operate .choose").click(function(){
		sessionStorage.setItem("chooseAddressId",$(this).parent().parent().attr("addressid"));
		window.history.go(-1);
	});
	
});


//返回强制刷新的代码
window.onpageshow = function(event){
    if (event.persisted) {
    	window.reload();
    }
}



function w_address_delete(that){
	var addressid = $(this).parent().parent().attr("addressid");
	var layerIndex = layer.open({
	    content: '确定要删除此地址吗？'
	    ,btn: ['确定' , '取消']
	    ,yes: function(index){  
	    	
			layer.close(layerIndex);				
			layerIndex = null;
			
			$.ajax({
				url : "/ws/useraddress_delete_sumbit",type:"post",dataType:"json",
				data : {addressId : addressid},
				success : function(result){
					
					layerIndex = layer.open({
					    content: result.msg
					    ,btn: ['朕知道了']
					    ,yes: function(index){  
					    	if(result.code ==1){
					    		layer.close(layerIndex);
					    		location.reload();
					    	}
					    }
					    });
				},error : function(err,xhr){
					
				}
			});
			
	    }
	 });
}
