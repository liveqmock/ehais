$(function(){
	$("#shareTip").click(function(){$(".share_wrapper").addClass("active");});
	$(".share_wrapper .pic").click(function(){$(".share_wrapper").removeClass("active");});
	$("#buynow").click(function(){buynow();});//立即购买
	$(".write").click(function(){
		window.location.href = "w_write_message!"+sid;
	});
	$(".recommend > .item").click(function(){
		window.location.href = $(this).attr("href");
	});
	
	message();//查看留言
});

//立即购买
function buynow(){
	
	$.ajax({
		url : "/ws/cart_add_submit",type:"post",dataType:"json",
		data : {"goods_id":$("#goodsId").val(),"parent_user_id":$("#parentId").val(),"agency_id":$("#agencyId").val(),"article_id":$("#articleId").val(),"quantity":1},
		success : function(result){
			if(result.code != 1 && result.code != 2){
				layer.open({
				    content: result.msg
				    ,btn: '朕知道了'
				});
				return ;
			}
			//更新购物车数量
			sessionStorage.setItem("recIds",result.model.recId);
			window.location.href = "w_check_order";
		},error : function(err,xhr){
			
		}
	});
	
}

function message(){
	$.ajax({
		url : "/ws/listArticleForum",type:"post",dataType:"json",
		data : {sid:sid},
		success : function(result){
			if(result.code != 1)return ;
			var rows = result.rows;
			
			$.each(rows,function(index,value){
				$("#message").append("<li>"+
					"<img class=\"pic\" src=\""+value.faceImage+"\">"+
					"<div class=\"info\">"+
						"<div class=\"t\">"+value.nickname+"</div>"+
						"<div class=\"d\">"+value.content+"</div>"+
					"</div>"+
				"</li>");
			});
		}
	});
}

