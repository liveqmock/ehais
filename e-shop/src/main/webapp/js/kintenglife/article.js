var page = 1;
var jroll ;
var loaded;


$(function(){
	
$("#jroll").height(parseFloat($(window).height()) - parseFloat($("#clist").css('padding-top').replaceAll("px","")));
	
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
			article_list();
		}else if(loaded == "up"){
			article_list();
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
	
	article_list();
	
	
});

function article_list(){
	loaded = "ing";
	$.ajax({
		url : "list_json!"+module+".kintenglife.json",data : {page : page,rows : 10},
		success : function(result){
			loaded = "";
			
			if(page == 1){
				$("#jroll ul li:not(:first-child)").remove();
			}
			
			if(result.rows!=null && result.rows.length > 0){
				page++;
				$.each(result.rows,function(i,v){
					$("#ui").append("<li val='"+v.articleId+"'>"+
							"<div class='img'><img src='"+v.articleThumb+"'></div>"+
							"<div class='i'>"+
							"	<div class='t'>"+v.title+"</div>"+
							"	<div class='d'>"+v.description+
							"	</div>"+
							"</div>"+
					"</li>");
				});
				jroll.refresh();
			}
			
			
			$("#jroll ul li").unbind();
			$("#jroll ul li:not(:first-child)").click(function(){
				window.location.href = "detail!"+$(this).attr("val")+".kintenglife";
			});
		}
	})
}





