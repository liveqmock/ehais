$.fn.loading = function (obj){
	if(obj == '' || obj == null){
		$(this).html("<div id=\"background\" class=\"background\" style=\"display: none; \"></div><div id=\"progressBar\" class=\"progressBar\" style=\"display: none; \">数据加载中，请稍等...</div>");
	}else if(obj == "open"){
		open();
	}else if(obj == "close"){
		close();
	}
	
	function open(){
		$("#background,#progressBar").show();
	}
	
	function close(){
		$("#background,#progressBar").hide();
	}
};