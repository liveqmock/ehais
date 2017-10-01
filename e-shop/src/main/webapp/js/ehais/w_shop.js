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
		window.location.href="w_cart!"+sid;
	});
	wx_config(signature);
});

window.onpageshow = function(event){
	alert(JSON.stringify(event));
	if (event.persisted) {
		
		wx_config(signature);
	}
}
