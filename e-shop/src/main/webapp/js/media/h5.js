
var culw = 0;
var jcate;
var alist;
var orderData = {0:json.hotArticleList};
var pageData = {0:2};
var scroll_y = {0:0};//记录前位置
var jroll = {};
var cat_id = 0;
var loaded;
var defaultimg = "http://eg.ehais.com/images/eICON.png";

$(function(){
	
	var mySwiper = new Swiper ('.swiper-container', {
	    direction: 'horizontal',
	    loop: true,
	    autoplay: 5000,
	    pagination: '.swiper-pagination',
	}); 
	
	
	$("#clist").height(parseFloat($(window).height()) - parseFloat($("header").height()) - parseFloat($("footer").height()) - parseFloat($("#cate").height()) - parseFloat($("footer").css('padding-top').replaceAll("px","")) - parseFloat($("footer").css('padding-bottom').replaceAll("px","")));
	
	$.each(json.listArticleCat,function(k,v){
		$("#cate ul").append("<li cat_id='"+v.catId+"'>"+v.catName+"</li>");
		jrollCat(v.catId);
	});
	
	$(".swiper-wrapper").width($(window).outerWidth());
	$("#cate").width($(window).outerWidth());
	$("#cate_bor").width($(window).outerWidth()-$(".nav").width());
	
	jrollCat(cat_id);
	$("#cate_bor ul li").each(function() {
		culw += parseFloat($(this).outerWidth()) + 1;
	});
	
	$("#cate ul").outerWidth(culw);
	
	
	jcate = new JRoll("#cate_bor", {scrollX : true ,scrollY : false , scrollBarX:false});

	$("#cate_bor ul li").click(function(){
		
		if(parseInt(cat_id) != parseInt($(this).attr("cat_id"))){
			
			cat_id = $(this).attr("cat_id");
			sessionStorage.setItem("cat_id",cat_id);
			
			$("#cate_bor ul li").removeClass("active");
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
			
			
			bind_play_click();
			
		}
		
		
	});
	
	bind_play_click();
	
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
		$("#a"+catId).append("<li class='rec play' val='"+v.articleId+"'>"+
								"<div>"+
									"<img src="+video_url_website+ ((v.articleThumb == null || v.articleThumb == "")?defaultimg:v.articleThumb) +" />"+
									"<div>"+v.articleLabel+"</div>"+
									"<img src='img/media_h5/play.png' />"+
								"</div>"+
								"<div>"+v.title+"</div>"+
							"</li>"
				);
	});
	
	jroll[catId].refresh();
	
	bind_play_click();
	
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
		$(this).attr("src",defaultimg);
		jroll[catId].refresh();
	});
	num = null;
}

function article_list_cat_id(){
	loaded = "ing";
	$.ajax({
		url : "media_cat_list_"+cat_id+".lv",data:{page:pageData[cat_id],rows:10},
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


function bind_play_click(){
	$(".play").unbind();
	$(".play").on("click",function(e){
		
		window.location.href = "play"+$(this).attr("val")+".lv";
	});
}



