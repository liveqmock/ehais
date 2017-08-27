
$(function(){
	$("header .fa-plus-circle").click(function(){window.location.href="w_address_add";})
	$(".list .item .default").click(function(){
		
		var addressid = $(this).parent().attr("addressid");
		var that = this;
		$.ajax({
			url:"/ws/useraddress_default",type:"post",dataType:"json",
			data : {addressId:addressid},
			success : function(result){
				if(result.code == 1){
					$(".list .item .default").removeClass("active");
					$(that).addClass("active");
					addressid = null;
					that = null;
				}else{
					
				}
			}
		});
	});
	
	$(".edit").click(function(){
		var addressid = $(this).parent().attr("addressid");
		window.location.href = "w_address_edit?addressId="+addressid;
	});
});
