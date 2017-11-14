
$(function(){
	$("#keyword").focus(function(){
		$(".splate").addClass("active");
		search_history();
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
		
		if(window.location.href.indexOf("w_goods_list") > 0 ){
			resetSearch();
		}else if(window.location.href.indexOf("w_goods_search") > 0){
//			history.back();
		}
		
	});
	
	$("#splate_clear").click(function(){
		elay.confirm({"content":"确定清空搜索记录吗",btn:["确定","取消"],sure:clear_keyword_history});
	});
	
	$("#splate_empty").click(function(){$("#keyword").val("");$("#keyword").focus();$(this).removeClass("active");});
	
	
	$("#splate_search").click(function(){
		if($("#keyword").val().length == 0){
			elay.toast({"content":"请输入搜索关键字"});
			return ;
		}
		
		keyword = $.trim($("#keyword").val());
		
		var f = true;
		$("#history_keyword dd").each(function(i,ele){
			if($.trim($(ele).text()) == keyword){
				f = false;
				return true;
			}
		});
		if(f){
			$("#history_keyword").prepend("<dd>"+keyword+"</dd>");
			var rows = sessionStorage.getItem("search_history");
			if(rows == null || rows == "[]"){
				rows = [];
			}else{
				rows = JSON.parse(rows);
			}
			rows.push({"keyword":keyword,"classify":"user"});
			sessionStorage.setItem("search_history",JSON.stringify(rows));
		}
		
		//判断是否商品列表页
		if(window.location.href.indexOf("w_goods_list") > 0 || window.location.href.indexOf("w_goods_search") > 0){
			//其它的文件
			keyword = $.trim($("#keyword").val());
			resetSearch();
			$(".splate").removeClass("active");
			
			
		}else{
			sessionStorage.removeItem("cat_id");
			sessionStorage.removeItem("goodsData");
			sessionStorage.removeItem("pageData");
			sessionStorage.removeItem("scroll_y");
			sessionStorage.setItem("keyword",$("#keyword").val());
			window.location.href = "w_goods_search!"+cid;
			
		}
	});
});

function clear_keyword_history(){
	$.ajax({
		url : "clear_keyword_history",
		success:function(r){
			elay.open({content:r.msg});
			if(sessionStorage.getItem("search_history") != null && sessionStorage.getItem("search_history") != "[]"){
				var rows = JSON.parse(sessionStorage.getItem("search_history"));
				
				for(var i = rows.length - 1 ; i >= 0 ; i--){
					if(rows[i].classify == "user"){console.log("清空所有的:"+i);
						rows.remove(rows[i]);
					}
				}
				console.log(JSON.stringify(rows));
				sessionStorage.setItem("search_history",JSON.stringify(rows));
			}
			$("#history_keyword dd").remove();
			keyword = null;
			$("#keyword").val("");
		}
	});
}

function search_history(){
	console.log(sessionStorage.getItem("search_history"));
	if(sessionStorage.getItem("search_history") != null && sessionStorage.getItem("search_history") != "[]"){
		var rows = JSON.parse(sessionStorage.getItem("search_history"));
		keywordHistory(rows);
	}else{
		$.ajax({
			url : "search_history",
			success:function(result){
				var rows = result.rows;
				sessionStorage.setItem("search_history",JSON.stringify(rows));
				keywordHistory(rows);
			}
		});
	}
}

function keywordHistory(rows){
	$("#history_keyword dd,#hot_keyword dd").unbind();
	$("#history_keyword dd,#hot_keyword dd").remove();
	$.each(rows,function(k,v){
		if(v.classify == "user"){
			$("#history_keyword").append("<dd>"+v.keyword+"</dd>");
		}else if(v.classify == "store"){
			$("#hot_keyword").append("<dd>"+v.keyword+"</dd>");
		}
	});
	$("#history_keyword dd,#hot_keyword dd").click(function(){
		
		//判断是否商品列表页
		if(window.location.href.indexOf("w_goods_list") > 0 || window.location.href.indexOf("w_goods_search") > 0){
			//其它的文件
			keyword = $.trim($(this).text());
			$("#keyword").val(keyword);
			resetSearch();
			$(".splate").removeClass("active");
			
		}else{
			sessionStorage.removeItem("cat_id");
			sessionStorage.removeItem("goodsData");
			sessionStorage.removeItem("pageData");
			sessionStorage.removeItem("scroll_y");
			sessionStorage.setItem("keyword",$.trim($(this).text()));
			window.location.href = "w_goods_search!"+cid;
			
		}
		
		
		
		
	});
}

Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
	