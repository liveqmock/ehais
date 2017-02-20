/**
 * 
 */
var userId = 0;
$(function(){
	$(".view header").prepend("<div id='goback' class='backButton back goBackClick'>返回</div>");
	$(".view header").prepend("<div id='edit_user' class='rightButton oval'>编辑</div>");
	$(".goBackClick").click(function(){
		window.history.go(-1);
	});
	
	user_info();
	
	
	$(".rightButton").click(function(){
		if($(this).hasClass("oval")){
			$(this).html("取消");
			$(this).removeClass("oval");
			$(".list").removeClass("lbl");
			$(".list").addClass("txt");
			$("#submit").show();
		}else{
			$(this).html("编辑");
			$(this).addClass("oval");
			$(".list").removeClass("txt");
			$(".list").addClass("lbl");
			$("#submit").hide();
		}
		$(this).attr("href","javascript:;");
	});
	
	$("#submit").click(function(){
		user_info_edit();
	});
	
	$(".rdo_sex").click(function(){
		if($(this).parent().hasClass("txt")){
			$(".rdo_sex").removeClass("cur");
			$(this).addClass("cur");
			$("#sex").val($(this).attr("data-val"));
		}
	});
	
	
	$("#userRegionLi").click(function(){
		if($(this).parent().hasClass("txt")){
			var regionData = new Array();
			$.each(region.model,function(key){
				
				if(typeof($("#"+region.model[key]).attr("data-id")) != undefined && typeof($("#"+region.model[key]).attr("data-id")) != "undefined" ) {
//					console.log("s data-id:"+$("#"+region.model[key]).attr("data-id"));
					regionData.push($("#"+region.model[key]).attr("data-id"));
				}
			});
			
			if(regionData.length > 0)
			region.regionByParent(regionData.join(","),callbackRegionParent);
			
			
		}
	});
	
	$("#submit").hide();
});

function user_info(){
	$.ajax({
		url : "/ws/user_info",
		data : { },
		type : "post",
		dataType : "json",
		success : function(data){
			$.each(data.model, function(key) {
			    $("#"+key).html(data.model[key]);
			    $("#txt_"+key).val(data.model[key]);
			});
			
			userId = data.model.userId;
			
			$("#birthday").html((new Date(Date.parse(data.model["birthday"]))).format("yyyy-MM-dd"));
			$("#txt_birthday").val((new Date(Date.parse(data.model["birthday"]))).format("yyyy-MM-dd"));
			
			$("#sex").val(data.model.sex);
			$("#rdo_sex_"+data.model.sex).addClass("cur");
			$("#sexName").html($("#rdo_sex_"+data.model.sex).text());
			
//			var region_list = data.map.region_list;
//			$.each(region_list,function(index,data){
//				$("#region div").each(function(){
//					if(parseInt($(this).text()) == 0){
//						$(this).html("");
//						return true;
//					}
//					if(parseInt($(this).text()) == parseInt(data.regionId)){
//						$(this).html(data.regionName);
//						return true;
//					}
//				});
//			});
			
			
			var regionList = data.map.region_list;
			var region_name = 0;
			var parent_id = 0;
			$.each(region.model, function(key) {
				//console.log(region.model[key] + "--" + JSON.stringify(regionList));
				region_name = region.setRegionVal(regionList,data.model[region.model[key]]);
				parent_id = region.setRegionParentVal(regionList,data.model[region.model[key]]);
				
				$("#"+region.model[key]).html(region_name);
				$("#region_cur_"+region.model[key]).html(region_name);
				
				$("#"+region.model[key]).attr("data-id",data.model[region.model[key]]);
				$("#region_"+region.model[key]).attr("data-id",data.model[region.model[key]]);
				$("#region_cur_"+region.model[key]).attr("data-id",data.model[region.model[key]]);
				
				$("#"+region.model[key]).attr("data-parentid",parent_id);
				$("#region_"+region.model[key]).attr("data-parentid",parent_id);
				$("#region_cur_"+region.model[key]).attr("data-parentid",parent_id);
				
				
			});
			
			
		},
		error : function(e){
			
		},beforeSend: function(jqXHR) {
			$.ejq.showMask("数据读取中...");
		},
		complete: function() {
			$.ejq.hideMask();
		}
	});
}

function user_info_edit(){
	var dataPara = {};
	dataPara.userId = userId;
	dataPara.realname = $("#txt_realname").val();
	dataPara.sex = $(".rdo_sex.cur").attr("data-val");
	dataPara.birthday = $("#txt_birthday").val();
	
	$.each(region.model,function(index,ele){
		dataPara[ele] = $("#"+ele).attr("data-id");
	});

	
	$.ajax({
		url : "/ws/user_info_edit",
		data : dataPara,
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.ejq.toast(data.msg);
				return ;
			}
			$.ejq.toast("编辑成功");
			$(".rightButton").click();
		},
		error : function(e){
			console.log(JSON.stringify(e));
		},beforeSend: function(jqXHR) {
			$.ejq.showMask("数据提交中...");
		},
		complete: function() {
			$.ejq.hideMask();
		}
	});
	
}
