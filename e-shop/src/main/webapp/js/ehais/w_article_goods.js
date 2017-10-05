$(function(){
	if(sessionStorage.getItem("header")!=null)$(".ww").addClass("header");
	$(".share").click(function(){$(".sw").addClass("active");});
	$(".sw .pic").click(function(){$(".sw").removeClass("active");});
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
				elay.open({
				    content: result.msg
				    ,btn: '知道了'
				});
				return ;
			}
			//更新购物车数量
			sessionStorage.setItem("recIds",result.model.recId);
			window.location.href = "w_check_order!"+sid;
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
			$(".recommend div.item").remove();
			if(listRecommend!=null && listRecommend.length > 0){
				$(".recommend_title,.recommend").removeClass("dn");
				var t = "";
				
				$.each(listRecommend,function(index,value){
					t = "";
					if(value.articleImages == null || value.articleImages == "")t=" t ";
					$(".recommend").append("<div class='item "+t+"' href='"+value.link+"'>"+
					((value.articleImages == null || value.articleImages == "") ? "" : "<div class='pic'><img src='"+value.articleImages+"' /></div>")+
					"<div class='desc'>"+
						"<h4>"+value.title+"</h4>"+
						"<div class='intro'>"+value.description+"</div>"+
					"</div>"+
					"</div>");
				});
			}
			
			$(".recommend > .item").click(function(){
				window.location.href = $(this).attr("href");
			});
			
			var listForum = result.map.listForum;
			$("#message li").remove();
			if(listForum.length > 0){
				$.each(listForum,function(index,value){
					$("#message").append("<li>"+
						"<img class='pic' src='"+value.faceImage+"'>"+
						"<div class='info'>"+
							"<div class='t'>"+value.nickname+"</div>"+
							"<div class='d'>"+value.content+"</div>"+
						"</div>"+
					"</li>");
				});
			}
			
			
		}
	});
}

