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
			team_list();
		}else if(loaded == "up"){
			team_list();
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
	
	team_list();
	
	$("#sou").click(function(){$("#uu li").remove(); page = 1;team_list();});
	
});

function team_list(){
	loaded = "ing";
	$.ajax({
		url : "/ws/team_list",data : {page : page,rows : 10,nickname:$("#nickname").val()},
		success : function(result){
			loaded = "";
			if(page == 1){
				$("#juser ul li").remove();
				if(result.rows == null || result.rows.length == 0){
					$(".e").addClass("active");
					$("#juser").hide();
				}
			}
			if(result.rows!=null && result.rows.length > 0){
				page++;
				$.each(result.rows,function(i,v){
					$("#uu").append("<li>"+
							"<img src='"+(v.faceImage!=""?v.faceImage:result.action)+"' />"+
							"<b>"+(v.nickname!=""?v.nickname:"未关注用户")+"</b>"+
							"<i>注册时间："+v.regTime+"</i>"+
						"</li>");
				});
				jroll.refresh();
				
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
				
			}
		}
	})
}