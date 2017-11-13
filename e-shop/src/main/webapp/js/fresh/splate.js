
$(function(){
	$("#keyword").focus(function(){
		$(".splate").addClass("active");
	});
	
	$("#keyword").keyup(function(){
		if($("#keyword").val().length > 0){
			$(this).parent().children("i").addClass("active");
		}else{
			$(this).parent().children("i").removeClass("active");
		}
	});
	
	$("#splate_cancel").click(function(){
		$(".splate").removeClass("active");
		keyword = null;
		resetSearch();
	});
	
	$("#splate_clear").click(function(){
		elay.confirm({"content":"确定清空搜索记录吗",btn:["确定","取消"],sure:splate_clear});
	});
	
	$("#splate_empty").click(function(){$("#keyword").val("");$("#keyword").focus();$(this).removeClass("active");});
	
	
	$("#splate_search").click(function(){
		if($("#keyword").val().length == 0){
			elay.toast({"content":"请输入搜索关键字"});
			return ;
		}
		
		//判断是否商品列表页
		if(window.location.href.indexOf("w_goods_list") > 0){
			//其它的文件
			keyword = $("#keyword").val();
			resetSearch();
			$(".splate").removeClass("active");
		}else{
			
			sessionStorage.setItem("keyword",$("#keyword").val());
			window.location.href = "w_goods_list!"+cid;
		}
	});
});

function splate_clear(){
	
}