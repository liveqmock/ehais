var user_id = "";
$(function() {
//	if(user_id == ""){
//		$('#loginModal').modal({show:true,backdrop: 'static',keyboard: false});
//	}
	
	loginCheck();
	
});

function loginCheck(){
	$.post("/login_check.api",null,function(data){
		if(data.model.id != undefined && parseInt(data.model.id) > 0){
			user_id = data.model.id;
			$(".memberLogin").show();
			$(".memberLogout").hide();
		}else{
			$(".memberLogin").hide();
			$(".memberLogout").show();
		}
	},"json");
}