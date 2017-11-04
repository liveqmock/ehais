$(function(){
	$("#num li").click(function(){
		$("#num li").removeClass("active");
		$("#number_item").addClass("dn");
		$(this).addClass("active");
		if($(this).attr("num") == "3"){
			if($("#number").val().length == 0)$("#number").val(3);
			$("#number_item").removeClass("dn");
		}
	});
});