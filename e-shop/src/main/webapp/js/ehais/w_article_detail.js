var store_id = 56;
$(function(){
	$("#shareTip").click(function(){$(".share_wrapper").addClass("active");});
	$(".share_wrapper .pic").click(function(){$(".share_wrapper").removeClass("active");});
	$("#buynow").click(function(){buynow();});//立即购买
});

//立即购买
function buynow(){
	
	$.ajax({
		url : "/ws/cart_add_submit",type:"post",dataType:"json",
		data : {"goods_id":$("#goodsId").val(),"parent_user_id":$("#parendId").val(),"agency_id":$("#agencyId").val(),"article_id":$("#articleId").val(),"quantity":1},
		success : function(result){
			if(result.code != 1 && result.code != 2){
				layer.open({
				    content: result.msg
				    ,btn: '朕知道了'
				});
				return ;
			}
			//更新购物车数量
			localStorage.setItem("recIds",result.model.recId);
			window.location.href = "w_check_order";
		},error : function(err,xhr){
			
		}
	});
	
}

