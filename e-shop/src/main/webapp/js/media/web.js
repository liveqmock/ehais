$(function(){
	$(".play").click(function(){
		window.location.href = "play"+$(this).attr("val")+".lv";
	});
	
	$("#menu >li").click(function(){
		window.location.href = $(this).attr("href");
	});
	
//	var mySwiper = new Swiper ('.swiper-container', {
//	    direction: 'horizontal',
//	    loop: true,
//	    autoplay: 5000,
//	    pagination: '.swiper-pagination',
//	}); 
	
});
