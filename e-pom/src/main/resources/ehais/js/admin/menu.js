/**
 * 
 */
$(function(){
	
	
	var url = (window.location.href).replaceAll("http://","").replaceAll(window.location.host,"");

	$("#sidebar li.menu").removeClass("active").removeClass("open");
	$("#sidebar li").removeClass("subactive");
	
	$("#sidebar a").each(function(index,ele){
		if(url.indexOf($(ele).attr("href")) >= 0){
			$(ele).parent("li").addClass("subactive");
			$(ele).parent().parent().parent().addClass("active").addClass("open");
			return true;
		}
		
		var active_href = $(ele).attr("active-href");
		if(typeof(active_href) != "undefined" && active_href != "" && active_href != null){
			$.each(active_href.split(","),function(index,e){
				if(url.indexOf(e) >= 0){
					$(ele).parent("li").addClass("subactive");
					$(ele).parent().parent().parent().addClass("active").addClass("open");
					return true;
				}
			});
		}
		
	});

	
});

