
$(function(){
	$("header .fa-chevron-left").click(function(){window.history.go(-1);});
	$("header .fa-plus-circle,#newAddress").click(function(){window.location.href="w_address_add";})
	$(".icon-default").click(function(){
		
		var addressid = $(this).parent().parent().attr("addressid");
		var that = this;
		$.ajax({
			url:"/ws/useraddress_default",type:"post",dataType:"json",
			data : {addressId:addressid},
			success : function(result){
				if(result.code == 1){
					$(".icon-default").removeClass("active");
					$(that).addClass("active");
					addressid = null;
					that = null;
				}else{
					
				}
			}
		});
	});
	
	$(".icon-edit").click(function(){
		var addressid = $(this).parent().parent().attr("addressid");
		window.location.href = "w_address_edit?addressId="+addressid;
	});
	
	$(".icon-lajixiang").click(function(){
		w_address_delete(this);
	});
	
	$(".info").click(function(){
		sessionStorage.setItem("chooseAddressId",$(this).parent().attr("addressid"));
		window.history.go(-1);
	});
	
});


function w_address_delete(that){
	var addressid = $(that).parent().parent().attr("addressid");
	elay.confirm({
	    content: '确定要删除此地址吗？'
	    ,btn: ['确定' , '取消']
	    ,sure: function(index){  
	    	
			$.ajax({
				url : "/ws/useraddress_delete_sumbit",
				data : {addressId : addressid},
				success : function(result){
					
					elay.open({
					    content: result.msg,
					    btn: '知道了',
					    yes: function(index){  
					    	if(result.code ==1){
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
