var page = 1;
var jroll ;
var loaded;
$(function(){
	

	$("#jroll").height(parseFloat($(window).height()) - parseFloat($(".ite").css('padding-top').replaceAll("px","")));
	
	loaded = "";
	jroll = new JRoll("#jroll", {scrollBarY:false});
	
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
			integral_list();
		}else if(loaded == "up"){
			integral_list();
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
		$(this).attr("src","http://eg.ehais.com/images/eICON.png");
		jroll.refresh();
	});
	num = null;
	
	integral_list();
	
	$("#sou").click(function(){$("#ui li").remove(); page = 1;integral_list();});
	
});

function integral_list(){
	loaded = "ing";
	$.ajax({
		url : "/ws/integral_list",data : {page : page,rows : 10},
		success : function(result){
			loaded = "";
			
			if(page == 1){
				$("#jroll ul li").remove();
				if(result.rows == null || result.rows.length == 0){
					$(".e").addClass("active");
					$("#jroll").hide();
				}
			}
			
			if(result.rows!=null && result.rows.length > 0){
				page++;
				$.each(result.rows,function(i,v){
					$("#ui").append("<li>"+
						"<div>"+
							"<div>"+v.integralIntro+"</div>"+
							"<div>"+v.addTime+"</div>"+
						"</div>"+
						"<div>"+v.integral+"</div>"+
					"</li>");
				});
				jroll.refresh();
			}
		}
	})
}