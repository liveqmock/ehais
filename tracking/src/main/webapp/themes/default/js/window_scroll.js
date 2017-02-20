var debug = false;
var now_offset = 0;
var nav_offset = 0;
var nav_offset_left = 0;
var document_height = 0;
var window_height = 0;
var pageFooter = 0;

$(function(){

	if($('.nav_offset').length > 0){
	
	if(debug)$("body").append("<div id='debug' style='border: 1px solid #ccc;position: fixed;right: 10px;top: 10px;z-index: 10000;'></div>");
	
	nav_offset = $('.nav_offset').offset().top; //获取.navbar 距离顶部的绝对值 
	nav_offset_left = $('.nav_offset').offset().left;
	document_height = $(document.body).height();
	window_height = $(window).height();
	pageFooter = $('#pageFooter').offset().top;
//	console.log("pageFooter:"+pageFooter);
	$(window).on('scroll load',function(){ 
		now_offset = $(window).scrollTop(); //获取滚动条的位置 
//		window_height = $(document.body).scrollTop();
		
//		nav_offset = $('.nav_offset').offset().top; //获取.navbar 距离顶部的绝对值 
//		nav_offset_left = $('.nav_offset').offset().left;
//		document_height = $(document.body).height();
//		window_height = $(window).height();
		pageFooter = $('#pageFooter').offset().top;
		
		
		if ( now_offset >= nav_offset && (pageFooter - window_height) > now_offset) { // 
			//如果滚动条的top位置大于或者等于.navbar距离顶部的值，就将position修改为fixed 
			$('.nav_offset').css({'position':'fixed',top : 0 }); 
		} else { 
			$('.nav_offset').css('position','inherit'); 
		} 
		if(debug)$("#debug").html(
				"document_height="+document_height+"<br>"+
				"window_height="+window_height+"<br>"+
				"pageFooter="+pageFooter+"<br>"+
				"pageFooter - document_height ="+(pageFooter - window_height)+"<br>"+
				"nav_offset="+nav_offset+"<br>"+
				"now_offset="+now_offset);

	}); 
	
	}
	
	
}); 