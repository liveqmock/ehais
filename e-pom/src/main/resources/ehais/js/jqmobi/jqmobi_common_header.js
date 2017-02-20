var web_http = "";//"http://www.ehais.org";
var web_site = web_http;
var store_id = 101;//微商城

$.afui.useOSThemes = false;
$.afui.loadDefaultHash = true;
$.afui.autoLaunch = false;
$.afui.useAutoPressed = false;

//check search
var search = document.location.search.toLowerCase().replace("?", "");
if (search.length > 0) {

	$.afui.useOSThemes = true;
	if (search == "win8")
		$.os.ie = true;
	else if (search == "firefox")
		$.os.fennec = "true";
	$.afui.ready(function() {
		$(document.body).get(0).className = (search);
	});
}

$.afui.ready(function(){
	
});


$(document).ready(function() {
	$.afui.launch();
	$("body").removeClass();
	$("body").addClass("jqmobi");
});

//if($.os.ios)
$.afui.animateHeader(true);

/**
 * 获取url地址的参数
 * @param {Object} name
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(decodeURI(r[2]));
	return null;
}


/**
 * $(".view header").prepend("<div class='backButton back goBackClick'>返回</div>");
	$(".view header").prepend("<div id='edit_user' class='rightButton oval'>编辑</div>");
	goBackClickEvent();
	
 *	$(".view header").prepend("<a href='javascript:;' class='backButton back goBackClick'>返回</a>");
 *	goBackClickEvent(); 
 */
function goBackClickEvent(){
	$(".goBackClick").unbind();
	$(".goBackClick").click(function(){
		window.history.go(-1);
	});
}
