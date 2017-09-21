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
	
	$(".fa-shopping-cart").click(function(){
		window.location.href="w_cart!"+sid;
	});

});