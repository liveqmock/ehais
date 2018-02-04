
var culw = 0;
var jcate;
var alist;

var goodsData = {};
var pageData = {};
var scroll_y = {};//记录前位置
var jroll = {};
var cat_id = catId;
var loaded;
var sort = null;
var adsc = null;
var keyword = null;
var l_s = "list";

$(function(){
	wx_config(signature);
	
	$.each(cateList,function(k,v){
		if(parseInt(v.catId) == parseInt(catId)){
			$("#cate ul").prepend("<li class='active' cat_id='"+v.catId+"'>"+v.catName+"</li>");
		}else{
			$("#cate ul").append("<li cat_id='"+v.catId+"'>"+v.catName+"</li>");
		}
		jrollCat(v.catId);
	});
	
	$("#cate ul li").each(function() {
		culw += parseFloat($(this).outerWidth()) + 1;
	});
	
	$("#cate ul").outerWidth(culw);
	
	jcate = new JRoll("#cate", {scrollX : true ,scrollY : false , scrollBarX:false});

	initData();
	
	
	
	if(sessionStorage.getItem("cat_id")!=null && parseInt(sessionStorage.getItem("cat_id")) > 0){
		cat_id = sessionStorage.getItem("cat_id");
		if(goodsData[cat_id] == null){
			goodsData[cat_id] = [];
			sessionStorage.setItem("goodsData",JSON.stringify(goodsData));
		}
		$("#cate ul li,#clist >div").removeClass("active");
		$("#cate ul li").each(function(){
			if(parseInt($(this).attr("cat_id")) == parseInt(cat_id)){
				$(this).addClass("active");
			}
		});
		$("#c"+cat_id).addClass("active");
		
	}else{
		sessionStorage.setItem("cat_id",cat_id);
		if(goodsData[cat_id].length == 0){
			goods_list_cat();
		}
	}
	
	if(goodsData != null){//加载历史的数据
		$.each(goodsData,function(k,v){
			if(v.length > 0){
				goodsItem(v,k);
			}
		});
	}
	
	
	
	
	$("#cate ul li").click(function(){
		if(parseInt(cat_id) != parseInt($(this).attr("cat_id"))){
			cat_id = $(this).attr("cat_id");
			sessionStorage.setItem("cat_id",cat_id);
			$("#cate ul li").removeClass("active");
			$(this).addClass("active");			
			$("#clist >div").removeClass("active");
			$("#c"+cat_id).addClass("active");
			
			sort = null;
			adsc = null;
			
			if(pageData[cat_id] == null || parseInt(pageData[cat_id]) == 1){
				console.log(cat_id+" page is empty");
				pageData[cat_id] = 1;
				sessionStorage.setItem("pageData",JSON.stringify(pageData));
				goodsData[cat_id] = [];
				sessionStorage.setItem("goodsData",JSON.stringify(goodsData));
				goods_list_cat();
			}
			jroll[cat_id].refresh();
			if(scroll_y[cat_id]!=null){
				jroll[cat_id].scrollTo(0, scroll_y[cat_id], 0);
			}
		}
	});
	
	//筛选
	$(".icon-shaixuan").click(function(){
		$(".category").removeClass("active");
		$(".filtrate").addClass("active");
	});
	
	//切换分类
	$(".icon-qiehuan").click(function(){
		$(".filtrate").removeClass("active");
		$(".category").addClass("active");
	});
	


});




