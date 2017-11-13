
$(function(){
	var mySwiper = new Swiper ('.swiper-container', {
	    direction: 'horizontal',
	    loop: true,
	    autoplay: 5000,
	    pagination: '.swiper-pagination',
	});  
	
	$(".icon-gouwuche").click(function(){
		window.location.href="w_cart!"+cid;
	});
	$(".icon-huiyuan").click(function(){
		window.location.href="w_member";
	});
	wx_config(signature);
});

