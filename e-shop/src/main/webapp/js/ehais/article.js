var culw = 0;
var jcate;
var alist;
$(function(){
	
	$.each(json.adList,function(k,v){
		$(".swiper-wrapper").append("<li class='swiper-slide'><img src='"+v.adPic+"' /></li>");
	});
	
	var mySwiper = new Swiper ('.swiper-container', {
	    direction: 'horizontal',
	    loop: true,
	    autoplay: 5000,
	    pagination: '.swiper-pagination',
	});
	
	$.each(json.articleCatList,function(k,v){
		$("#cate ul").append("<li cid='"+v.catId+"'>"+v.catName+"</li>");
	});
	
	$("#cate ul li").each(function() {
		culw += parseFloat($(this).outerWidth());
	});
	
	$("#cate ul").outerWidth(culw);
	
	jcate = new JRoll("#cate", {scrollX : true ,scrollY : false , scrollBarX:false});
	
	
	$.each(json.hotArticleList,function(k,v){
		$("#alist").append("<li link='"+v.link+"'>"+
					"<div class='img'><img src='"+v.articleImages+"'></div>"+
					"<div class='i'>"+
						"<div class='t'>"+v.title+"</div>"+
						"<div class='d'>"+
							v.description +
						"</div>"+
						"<div class='h'>"+v.articleDate.substr(0,10)+"<i class='iconfont icon-zan'> "+v.praiseCount+"</i> <i class='iconfont icon-article'> "+v.readCount+"</i></div>"+
					"</div>"+
				"</li>");
	});
	
	$("#alist li").click(function(){
		window.location.href = "w_article_detail!"+$(this).attr("link");
	});
	
});
