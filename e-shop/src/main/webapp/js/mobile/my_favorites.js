/**
 * 
 */
var goods_page = 1;
var len = 10;
$(function(){
	$(".view header").prepend("<div class='backButton back goBackClick'>返回</div>");
	$(".goBackClick").click(function(){
		window.history.go(-1);
	});
//	localStorage["store_id"] = 1;
	goods_list(0);
	//$("#goods_ul").height($(window).height() - $("header").height());
});

function goods_list(cat_id){
	$.ajax({
		url : "/ws/favorites_list",
		data : {store_id : localStorage["store_id"] , catId : cat_id,page : goods_page , len: len},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.afui.toast(data.msg);
				return ;
			}
			$("#goods_ul li").remove();
			var list = (typeof(data.rows) == undefined ) ? new Array() : data.rows;
			if(list.length == 0){
				$("#goods_ul").append('<li class="empty"><h1>:)</h1><p>此品类还没有宝贝哦， 去看看其他品类吧！</p></li>');
				return ;
			}
			for (var i = 0; i < list.length; i++) {
				if(list[i] == null)continue;
				$("#goods_ul").append("<li data-id='"+list[i].goodsId+"' class='goodsinfo'>"+
						"<div class='img'>"+	
						"<a class='img' href='javascript:;'>"+
						"<img class='' src='"+list[i].goodsThumb+"' />"+
						"</a>"+
						"</div>"+
										"<div class='item'>"+
										    "<h2>"+list[i].goodsName+"</h2>"+
										    "<p class='c'>"+list[i].goodsBrief+"</p>"+
										"</div>"+			       
										"</li>"
										);
			}
			
			$("#goods_ul li").click(function(){
				window.location.href = "g-"+store_id+"-"+$(this).attr("data-id");
			});
			
		},
		error : function(e){},
		beforeSend: function(jqXHR) {
		     $.ejq.showMask("数据获取中...");
		  },
		  complete: function() {
		   $.ejq.hideMask();
		  }
	});
}

