var page = 1;
var jroll ;
var loaded;
var userMap = {};
var goodsMap = {};

$(function(){
	

	$("#jorder").height(parseFloat($(window).height()) - parseFloat($(".wd").css('padding-top').replaceAll("px","")));
	
	loaded = "";
	jroll = new JRoll("#jorder", {scrollBarY:false});
	
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
			distribution_list();
		}else if(loaded == "up"){
			distribution_list();
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
	
	distribution_list();
	
	$("#sou").click(function(){$("#uu li").remove(); page = 1;distribution_list();});
	
});

function distribution_list(){
	loaded = "ing";
	$.ajax({
		url : "/ws/distribution_list",data : {page : page,rows : 10,nickname:$("#nickname").val()},
		success : function(result){
			loaded = "";
			if(page == 1){
				$("#jorder ul li").remove();
				if(result.rows == null || result.rows.length == 0){
					$(".e").addClass("active");
					$("#jorder").hide();
				}
			}
			
			
			if(result.map != null){
				if(result.map.userList != null && result.map.userList.length > 0){
					for(var k in result.map.userList ){
						userMap[result.map.userList[k].userId] = result.map.userList[k];
					}
				}
				
				if(result.map.goodsList != null && result.map.goodsList.length > 0){
					for(var k in result.map.goodsList ){
						goodsMap[result.map.goodsList[k].goodsId] = result.map.goodsList[k];
					}
				}
				
			}
			
			if(result.rows!=null && result.rows.length > 0){
				page++;
				$.each(result.rows,function(i,v){
					$("#uu").append("<li>"+
						"<img src='"+((goodsMap[v.goodsId] == null || goodsMap[v.goodsId].goodsThumb == null || goodsMap[v.goodsId].goodsThumb == "") ? result.action : goodsMap[v.goodsId].goodsThumb ) +"' />"+
						goodsMap[v.goodsId].goodsName + 
						"<div>"+
							"￥"+v.goodsPrice+" * "+v.goodsNumber+
							"<div>￥"+(v.goodsNumber * v.distributionPrice)+"元</div>"+
							"<img src='"+((userMap[v.userId] == null || userMap[v.userId].faceImage == null || userMap[v.userId].faceImage == "") ? result.action : userMap[v.userId].faceImage )+"'>"+
						"</div>"+
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
					$(this).attr("src","http://eg.ehais.com/images/eICON.png");
					jroll.refresh();
				});
				num = null;
				
			}
		}
	})
}