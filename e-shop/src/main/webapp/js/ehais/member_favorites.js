var jroll;
var loaded;
var page = 1;

$(function(){
	
	$("#roll").height(parseFloat($(window).height()) - parseFloat($(".w_shop").css('padding-top').replaceAll("px","")));
	
	loaded = "";
	jroll = new JRoll("#roll", {scrollBarY:false});
	
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
			favorites_list();
		}else if(loaded == "up"){
			favorites_list();
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
	
	favorites_list();
});

function favorites_list(){
	loaded = "ing";
	$.ajax({
		url : "/ws/favorites_list",data : {page :  page ,rows : 10},
		success : function(result){
			loaded = "";
			if(page == 1){
				$("#roll ul li").remove();
				if(result.rows == null || result.rows.length == 0){
					$(".e").addClass("active");
					$("#roll").hide();
				}
			}
			
			$.each(result.rows,function(k,v){
				$("#roll ul").append("<li h='"+v.goodsUrl+"'>"+
						"<div class='img'><img src='"+v.originalImg+"'></div>"+
						"<div class='i'>"+
							"<div class='t'>"+v.goodsName+"</div>"+
							"<div class='d'>"+
								"<div>￥</div>"+
								"<div>"+(v.shopPrice / 100 ).toFixed(2)+"</div>"+
								"<div>"+(v.marketPrice / 100 ).toFixed(2)+"</div>"+
								"<div>详情</div>"+
							"</div>"+
						"</div>"+
					"</li>");
			});
			
			jroll.refresh();
			
			if(result.rows.length > 0){
				page++;
			}
			
			$("#roll ul li").click(function(){
				window.location.href = $(this).attr("h");
			});
		}
	});
	
	
	
}