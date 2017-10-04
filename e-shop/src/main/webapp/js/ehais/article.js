var culw = 0;
var jcate;
var alist;

var orderData = {0:json.hotArticleList};
var pageData = {0:2};
var scroll_y = {0:0};//记录前位置
var jroll = {};
var cat_id = 0;
var loaded;
sessionStorage.setItem("header","header");
$(function(){
	
	if(sessionStorage.getItem("orderData") != null){
		orderData = JSON.parse(sessionStorage.getItem("orderData"));
	}
	if(sessionStorage.getItem("pageData") != null){
		pageData = JSON.parse(sessionStorage.getItem("pageData"));
	}
	if(sessionStorage.getItem("scroll_y") != null){
		scroll_y = JSON.parse(sessionStorage.getItem("scroll_y"));
	}
	$.each(json.adList,function(k,v){
		$(".swiper-wrapper").append("<li class='swiper-slide'><img src='"+v.adPic+"' /></li>");
	});
	
	var mySwiper = new Swiper ('.swiper-container', {
	    direction: 'horizontal',
	    loop: true,
	    autoplay: 5000,
	    pagination: '.swiper-pagination',
	});
	
	$("#clist").height(parseFloat($(window).height()) - parseFloat($(".wa").css('padding-top').replaceAll("px","")));
	
	jrollCat(cat_id);
	$.each(json.articleCatList,function(k,v){
		$("#cate ul").append("<li cat_id='"+v.catId+"'>"+v.catName+"</li>");
		jrollCat(v.catId);
	});
	
	$("#cate ul li").each(function() {
		culw += parseFloat($(this).outerWidth()) + 1;
	});
	
	$("#cate ul").outerWidth(culw);
	
	jcate = new JRoll("#cate", {scrollX : true ,scrollY : false , scrollBarX:false});

	
	loaded = "";
	
	
	$("#cate ul li").click(function(){
		
		if(parseInt(cat_id) != parseInt($(this).attr("cat_id"))){
			
			cat_id = $(this).attr("cat_id");
			sessionStorage.setItem("cat_id",cat_id);
			
			$("#cate ul li").removeClass("active");
			$(this).addClass("active");			
			$("#clist >div").removeClass("active");
			$("#c"+cat_id).addClass("active");
			
			if(pageData[cat_id] == null){
				console.log(cat_id+" page is empty");
				pageData[cat_id] = 1;
				sessionStorage.setItem("pageData",JSON.stringify(pageData));
				orderData[cat_id] = [];
				sessionStorage.setItem("orderData",JSON.stringify(orderData));
				article_list_cat_id();
			}
			
			jroll[cat_id].refresh();
			
		}
		
		
	});
	
	if(sessionStorage.getItem("cat_id")!=null){
		cat_id = sessionStorage.getItem("cat_id");
		if(orderData[cat_id] == null){
			orderData[cat_id] = [];
			sessionStorage.setItem("orderData",JSON.stringify(orderData));
		}
		$("#cate ul li,#clist >div").removeClass("active");
		$("#cate ul li").each(function(){
			if(parseInt($(this).attr("cat_id")) == parseInt(cat_id)){
				$(this).addClass("active");
			}
		});
		$("#c"+cat_id).addClass("active");
		jroll[cat_id].refresh();
		imgRefresh(cat_id);
		if(scroll_y[cat_id]!=null){
			jroll[cat_id].scrollTo(0, scroll_y[cat_id], 0);
		}
	}
	
//	sessionStorage.setItem("orderData",JSON.stringify(orderData));
//	articleItem(orderData[cat_id],cat_id);
	
});

function jrollCat(catId){
	if(catId!=0)$("#clist").append("<div id='c"+catId+"' ><ul id='a"+catId+"'></ul></div>");
	$("#c"+catId).height($("#clist").height());
	scroll_y[catId] = 0;
	sessionStorage.setItem("scroll_y",JSON.stringify(scroll_y));
	jroll[catId] = new JRoll("#c"+catId, {scrollBarY:false});
	jroll[catId].on("touchEnd", function() {
		if (this.y >= 44) {
			if(loaded=="")loaded="pull";
		}else if (this.y < this.maxScrollY - 10){
			if(loaded=="")loaded="up";
		}
	});
	jroll[catId].on("scrollEnd", function() {
		scroll_y[cat_id] = this.y;
		sessionStorage.setItem("scroll_y",JSON.stringify(scroll_y));
		if(loaded == "pull"){
			pageData[cat_id] = 1;
			article_list_cat_id();
		}else if(loaded == "up"){
			article_list_cat_id();
		}
	});
	
	if(orderData[catId] != null && orderData[catId].length > 0)articleItem(orderData[catId],catId);
	
	
}

function articleItem(d,catId){
	var t = "";
	$.each(d,function(k,v){
		t = "";
		if(v.articleImages == null || v.articleImages == "")t=" class='t' ";
		$("#a"+catId).append("<li link='"+v.link+"' "+t+">"+
				((v.articleImages == null || v.articleImages == "") ? "" : "<div class='img'><img src='"+v.articleImages+"'></div>") + 
					"<div class='i'>"+
						"<div class='t'>"+v.title+"</div>"+
						"<div class='d'>"+
							v.description +
						"</div>"+
						"<div class='h'>"+((v.articleDate != null && v.articleDate != "")?v.articleDate.substr(0,10):"")+"<i class='iconfont icon-zan'> "+v.praiseCount+"</i> <i class='iconfont icon-article'> "+v.readCount+"</i></div>"+
					"</div>"+
				"</li>");
	});
	
	jroll[catId].refresh();
	
	$("#clist>div>ul>li").unbind();
	$("#clist>div>ul>li").click(function(){
		if($(this).attr("link") != null && $(this).attr("link") != "")window.location.href = "w_article_detail!"+$(this).attr("link");
	});
	
	
	imgRefresh(catId);
	
}

function imgRefresh(catId){
	$("img").unbind();
	var num = $("img").length;
	$("img").load(function() {
		num--;
		if (num > 0) {
			return;
		}
		jroll[catId].refresh(); 
	}).error(function(){
		$(this).attr("src","http://ovug9f17p.bkt.clouddn.com/dining121.jpg");
		jroll[catId].refresh();
	});
	num = null;
}

function article_list_cat_id(){
	loaded = "ing";
	$.ajax({
		url : "article_list_cat_id!"+cid,data:{cat_id:cat_id,page:pageData[cat_id],rows:10},
		success : function(result){
			loaded = "";
			if(parseInt(pageData[cat_id]) == 1){
				orderData[cat_id] = [];
			
				if(parseInt(cat_id) == 0){
					$("#a"+cat_id+" >li:not(:first-child)").remove();
				}else{
					$("#a"+cat_id+" >li").remove();
				}
			}
			
			if(result.rows!=null && result.rows.length > 0){
				pageData[cat_id] = parseInt(pageData[cat_id]) + 1;
				sessionStorage.setItem("pageData",JSON.stringify(pageData));
			}
			$.each(result.rows,function(k,v){
				orderData[cat_id].push(v);
			});
			sessionStorage.setItem("orderData",JSON.stringify(orderData));
			articleItem(result.rows,cat_id);
		}
	});
}



