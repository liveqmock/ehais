var keyword = null;
var pages = {};
var len = 50;

function getPages(catId){
	if(pages[catId] == null){
		pages[catId] = 1;
	}
	return pages[catId];
}

$(function(){
	$(".main").height($(window).height() - $("header").height() - $("footer").height() );
	$("#scroller_cate_parent").height($(".main").height());
	$("#scroller_menu_goods_list").height($(".main").height());
	
	$.each(categoryList,function(k,v){
		if(v.parentId == 0){
			$("#scroller_cate_parent").append("<li catId='"+v.catId+"' >"+v.catName+"</li>");
		}
	});
	$("#scroller_cate_parent li:first").addClass("active");
	
	if(categoryList.length > 0)goodsList(categoryList[0].catId);
	
	$("#scroller_cate_parent li").click(function(){
		goodsList($(this).attr("catId"));
		$("#scroller_cate_parent li").removeClass("active");
		$(this).addClass("active");
	});
	
	wx_config(signature);
	
});

function goodsList(catId){
	$("#scroller_menu_goods_list li").hide();
	if($("#scroller_menu_goods_list li.cat_"+catId).length > 0){
		$("#scroller_menu_goods_list li.cat_"+catId).show();		
	}else{
		$.ajax({
			url : "/ws/goods_list_aid!"+cid,
			data:{catId:catId,store_id:store_id,page : getPages(catId) ,len : len},
			success:function(data){
				
				if(data.rows.length > 0)pages[catId] = parseInt(pages[catId]) + 1 ;
				
				$.each(data.rows,function(k,v){
					$("#scroller_menu_goods_list").append("<li class='cat_"+catId+"' h='"+v.goodsUrl+"'>"+
							"<div class='img'><img src='"+v.originalImg+"'></div>"+
							"<div class='i'>"+
							"	<div class='t'>"+v.goodsName+"</div>"+
							"	<div class='l'>"+v.actDesc+"</div>"+
							"	<div class='d'>"+
							"		<div>￥</div>"+
							"		<div>"+(parseInt(v.shopPrice) / 100).toFixed(2) +"</div>"+
							"		<div>"+(parseInt(v.marketPrice) / 100).toFixed(2) +"</div>"+
							"		<div class='iconfont icon-gouwuche1'></div>"+
							"	</div>"+
							"</div>"+
						"</li>");
				});
				
			}
		})
	}
	
}
