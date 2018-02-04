
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
	
	var cth = 0;
	$("#clist>li>.i>.t").each(function(i,ele){
		if(parseFloat(cth) < parseFloat($(ele).height()))cth=$(ele).height();
	});
	$("#clist>li>.i>.t").height(cth);
	
	$("#clist>li").click(function(){
		if($(this).attr("h") != null && $(this).attr("h") != "")window.location.href = "w_goods_detail!"+$(this).attr("h");
	});
	
	
});

