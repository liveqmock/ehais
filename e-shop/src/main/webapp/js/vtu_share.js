$(function(){
	signature.shareSuccess = function (m){
		$.get("/vtuShareSuccess!"+vid);
	}
	signature.shareCancel = function (m){
		
	}
	wx_config(signature);
	$("#vtuSign").click(function(){window.location.href=$(this).attr("href")});
});