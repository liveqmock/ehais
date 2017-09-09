$(function(){
	$("#shareTip").click(function(){$(".share_wrapper").addClass("active");});
	$(".share_wrapper .pic").click(function(){$(".share_wrapper").removeClass("active");});
	$("#buynow").click(function(){buynow();});//立即购买
	$(".write").click(function(){
		window.location.href = "w_write_message!"+sid;
	});
	
	
	article_extends_list_json();
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

function article_extends_list_json(){
	$.ajax({
		url : "/ws/article_extends_list_json",type:"post",dataType:"json",
		data : {sid:sid},
		success : function(result){
			if(result.code != 1)return ;
			
			var listRecommend = result.map.listRecommend;
			if(listRecommend.length > 0){
				$(".recommend_title,.recommend").removeClass("dn");
				$.each(listRecommend,function(index,value){
					$(".recommend").append("<div class=\"item\" href=\""+value.link+"\">"+
					"<div class=\"pic\"><img src=\""+value.articleImages+"\" /></div>"+
					"<div class=\"desc\">"+
						"<h4>"+value.title+"</h4>"+
						"<div class=\"intro\""+value.description+"></div>"+
					"</div>"+
					"</div>");
				});
			}
			
			$(".recommend > .item").click(function(){
				window.location.href = $(this).attr("href");
			});
			
			var listForum = result.map.listForum;
			if(listForum.length > 0){
				$.each(listForum,function(index,value){
					$("#message").append("<li>"+
						"<img class=\"pic\" src=\""+value.faceImage+"\">"+
						"<div class=\"info\">"+
							"<div class=\"t\">"+value.nickname+"</div>"+
							"<div class=\"d\">"+value.content+"</div>"+
						"</div>"+
					"</li>");
				});
			}
			
			
		}
	});
}

