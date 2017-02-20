var j_MenuNav,j_MenuPannel;
var j_menunav_timeout,j_menupannel_timeout;

$(function(){
	$(".j_MenuNav").mouseover(function(){
		clearTimeout(j_menunav_timeout);
		clearTimeout(j_menupannel_timeout);
		
		if(j_MenuNav != undefined){
			$(j_MenuNav).removeClass("selected");
			$(".j_CategoryMenuPannel").eq($(".j_MenuNav").index($(j_MenuNav))).hide();
		}
		
		
		j_MenuNav = this;
		
		$(j_MenuNav).addClass("selected");		
		$(".j_CategoryMenuPannel").eq($(".j_MenuNav").index($(j_MenuNav))).show();
	}).mouseout(function(){
		
		j_menunav_timeout = setTimeout(function(){
			$(j_MenuNav).removeClass("selected");
			$(".j_CategoryMenuPannel").eq($(".j_MenuNav").index($(j_MenuNav))).hide();
		},800);
		
	});
	
	$(".j_CategoryMenuPannel").mouseover(function(){
		clearTimeout(j_menunav_timeout);
		clearTimeout(j_menupannel_timeout);
		
		j_MenuPannel = this;
		
	}).mouseout(function(){
		j_menupannel_timeout = setTimeout(function(){
			$(j_MenuNav).removeClass("selected");
			$(".j_CategoryMenuPannel").eq($(".j_MenuNav").index($(j_MenuNav))).hide();
		},800);
	});
	
});