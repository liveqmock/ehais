/**
 * 
 */
var goods_page = 1;
var len = 10;
$(function(){
	category_list(0);
	goods_list(0);
	$("#goods_ul").height($(window).height() - $("header").height());
	$("#category_ul").height($(window).height() - $("header").height());
	
	$("#goHome").click(function(){
		return;
			if(!$("#main").hasClass("active")){
				$(".pages .panel.active").addClass("animated bounceOutLeft");
				$("#main").addClass("active animated bounceInRight");
				$('#main').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', finishAnimated({id1 : 'cart',id2 : 'member'}));
				
			}
		
		}
	);
	$("#goCart").click(function(){
		window.location.href="cart_list ";
		return ;
		if(!$("#cart").hasClass("active")){
			$(".pages .panel.active").addClass("animated bounceOutLeft");
			$("#cart").addClass("active animated bounceInRight");
			$('#cart').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', finishAnimated({id1 : 'main',id2 : 'member'}));
		}
		}
	);
	$("#goMember").click(function(){
		window.location.href="member ";
		return ;
		if(!$("#member").hasClass("active")){
			$(".pages .panel.active").addClass("animated bounceOutLeft");
			$("#member").addClass("active animated bounceInRight");
			$('#member').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', finishAnimated({id1 : 'main',id2 : 'cart'}));
			
		}
		}
	);
});

function finishAnimated(obj){
	$("#"+obj.id1).removeClass("active").removeClass("bounceOutLeft");
	$("#"+obj.id2).removeClass("active").removeClass("bounceOutLeft");
	console.log("finishAnimated:"+obj.id1+" -- "+obj.id2);
}

function category_list(parent_id){
	$.ajax({
		url : "/ws/category_list",
		data : {store_id : store_id , parent_id : parent_id},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.ejq.toast(data.msg);
				return ;
			}
			
			var list = (typeof(data.rows) == undefined ) ? new Array() : data.rows;
			
			for (var i = 0; i < list.length; i++) {
				$("#category_ul").append("<li data-cid='"+list[i].catId+"'>"+list[i].catName.substr(0,4)+"</li>");
			}
			$("#category_ul li:first").addClass("selected");
			$("#category_ul li").unbind();
			$("#category_ul li").click(function(){
				$("#category_ul li").removeClass("selected");
				$(this).addClass("selected");
				goods_list($(this).attr("data-cid"));
			});
		},
		error : function(e){}
	});
}

function goods_list(cat_id){
	$.ajax({
		url : "/ws/goods_list",
		data : {store_id : store_id , catId : cat_id,page : goods_page , len: len},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.ejq.toast(data.msg);
				return ;
			}
			$("#goods_ul li").remove();
			var list = (typeof(data.rows) == undefined ) ? new Array() : data.rows;
			if(list.length == 0){
				$("#goods_ul").append('<li class="empty"><h1>:)</h1><p>此品类还没有宝贝哦， 去看看其他品类吧！</p></li>');
				return ;
			}
			for (var i = 0; i < list.length; i++) {
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