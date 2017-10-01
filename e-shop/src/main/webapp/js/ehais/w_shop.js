/**
 * http://usejsdoc.org/
 */
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
	wx_config(signature);
});


