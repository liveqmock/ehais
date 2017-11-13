
$(function(){
	$(".main").height($(window).height() - $("header").height() - $("footer").height() );
	$("#scroller_cate_parent").height($(".main").height());
	$("#scroller_cate_sub").height($(".main").height());
	
	$.each(categoryList,function(k,v){
		if(v.parentId == 0){
			$("#scroller_cate_parent").append("<li catId='"+v.catId+"' >"+v.catName+"</li>");
		}
	});
	$("#scroller_cate_parent li:first").addClass("active");
	
	if(categoryList.length > 0)categorySub(categoryList[0].catId);
	
	$("#scroller_cate_parent li").click(function(){
		categorySub($(this).attr("catId"));
		$("#scroller_cate_parent li").removeClass("active");
		$(this).addClass("active");
	});
	
	wx_config(signature);
	
});

function categorySub(parentId){
	$("#scroller_cate_sub li").unbind();
	$("#scroller_cate_sub li").remove();
	$.each(categoryList,function(k,v){
		if(v.parentId == parentId){
			$("#scroller_cate_sub").append("<li catId='"+v.catId+"' h='"+v.cid+"'>"+
							"<img src='"+((v.thumb!=''&&v.thumb!=null)?v.thumb:defaultimg)+"'>"+
							"<div>"+v.catName+"</div>"+
						"</li>");
		}
	});
	
	$("#scroller_cate_sub li").click(function(){
		if($(this).attr("h").length > 0){
			sessionStorage.removeItem("cat_id");
			sessionStorage.removeItem("goodsData");
			sessionStorage.removeItem("pageData");
			sessionStorage.removeItem("scroll_y");
			window.location.href = "w_goods_list!"+$(this).attr("h");
		}
	});
}
