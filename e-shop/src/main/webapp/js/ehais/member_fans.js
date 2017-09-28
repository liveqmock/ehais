var page = 1;
var jroll ;
var loaded;
$(function(){
	

	$("#juser").height(parseFloat($(window).height()) - parseFloat($(".wm").css('padding-top').replaceAll("px","")));
	
	loaded = "";
	jroll = new JRoll("#juser", {scrollBarY:false});
	
	jroll.on("touchEnd", function() {
		
		if (this.y >= 44) {
//			if(loaded=="")loaded="pull";
		}else if (this.y < this.maxScrollY - 10){
			if(loaded=="")loaded="up";
		}
	});
	
	jroll.on("scrollEnd", function() {
		if(loaded == "pull"){
			page = 1;
			fans_list();
		}else if(loaded == "up"){
			fans_list();
		}
	});
	
	
	var num = $("img").length;
	$("img").load(function() {
		num--;
		if (num > 0) {
			return;
		}
		jroll.refresh(); 
	}).error(function(){
		$(this).attr("src","http://ovug9f17p.bkt.clouddn.com/dining121.jpg");
		jroll.refresh();
	});
	num = null;
	
	fans_list();
	
	$("#sou").click(function(){$("#uu li").remove(); page = 1;fans_list();});
	
});

function fans_list(){
	loaded = "ing";
	$.ajax({
		url : "/ws/fans_list",data : {page : page,rows : 3,nickname:$("#nickname").val()},
		success : function(result){
			loaded = "";
			if(result.rows!=null && result.rows.length > 0){
				page++;
				$.each(result.rows,function(i,v){
					$("#uu").append("<li>"+
							"<img src='"+v.faceImage+"' />"+
							"<b>"+v.nickname+"</b>"+
							"<i>关注时间："+v.regTime+"</i>"+
						"</li>");
				});
				jroll.refresh();
			}
		}
	})
}