/* $Id : region.js 4865 2007-01-31 14:04:10Z paulgao $ */

var region = new Object();

region.model = ["country","province","city","county","district","street"];


region.regionByVal = function(data, callback)
{
	$.ajax({
		url:"/ws/region_id_list",
		data : data,
		type : "post",
		dataType : "json",
		success : function(data){
			if(typeof(callback) != undefined) callback(data);
		}
	});
}

region.regionByParent = function(data, callback)
{
	$.ajax({
		url:"/ws/region_parentid_list",
		data : {region_ids : data},
		type : "post",
		dataType : "json",
		success : function(data){
			if(typeof(callback) != undefined) callback(data);
		}
	});
}

region.regionByParentId = function(data, callback)
{
	$.ajax({
		url:"/ws/region_parent_list",
		data : {parent_id : data.parentId},
		type : "post",
		dataType : "json",
		success : function(data){
			if(typeof(callback) != undefined) callback(data);
		}
	});
}



region.regionDivClick = function (_this){
	if($(_this).children("ul").children("li").length == 0)return ;
	$(".region_content div:not(:first)").removeClass("cur");
	$(_this).addClass("cur");
}

//地区i元素的点击事件
region.regionIClick = function (_this){
	if($(_this).parent().children("li").length == 0)return ;
	$(".region_content div:not(:first)").removeClass("cur");
	$(_this).parent().addClass("cur");
}

region.show = function(){
	$(".region_main").show();
}

region.hide = function(){
	$(".region_main").hide();
}

region.setRegionVal = function (regionList,regionId){
	var regionName = "";
	$.each(regionList, function(key) {
		if(parseInt(regionId) == parseInt(regionList[key].regionId)){
			regionName = regionList[key].regionName;
			return false;
		}
	});
	return regionName;
}

region.setRegionParentVal = function (regionList,regionId){
	var parentId = 0;
	$.each(regionList, function(key) {
		if(parseInt(regionId) == parseInt(regionList[key].regionId)){
			parentId = regionList[key].parentId;
			return false;
		}
	});
	return parentId;
}

//根据元素设置地区列表值
region.setRegionParentByElement = function (data,eleKey){
	var regionId = $("#region_"+region.model[eleKey]).attr("data-id");
	var parentId = $("#region_"+region.model[eleKey]).attr("data-parentid");
	$("#region_"+region.model[eleKey]+" ul li").remove();
	$.each(data, function(key) {
		if(parseInt(parentId) == parseInt(data[key].parentId) && parseInt(parentId) != 0){
			$("#region_"+region.model[eleKey]+" ul").append("<li id='region_"+region.model[eleKey]+"_li_"+data[key].regionId+"' data-id='"+data[key].regionId+"' data-parentid='"+data[key].parentId+"'>"+data[key].regionName+"</li>");
		}
	});
	$("#region_"+region.model[eleKey]+"_li_"+regionId).addClass("cur");//设置当前选中值
	if($("#region_"+region.model[eleKey]+" ul li").length > 0)$("#region_"+region.model[eleKey]).removeClass("dn");//如果存在列表，可以显示出来
}

//地区点击事件
region.regionToggleClassCurrent = function (eleKey){
	$("#region_"+region.model[eleKey]+" ul li").click(function(){
		$("#region_"+region.model[eleKey]+" ul li").removeClass("cur");
		$(this).addClass("cur");
		//当前的不用操作
		if(parseInt($("#region_cur_"+region.model[eleKey]).attr("data-id")) == parseInt($(this).attr("data-id")))return ;
		//设置选中当前值
		$("#region_cur_"+region.model[eleKey]).html($(this).html());
		$("#region_cur_"+region.model[eleKey]).attr("data-id",$(this).attr("data-id"));
		$("#region_cur_"+region.model[eleKey]).attr("data-parentid",$(this).attr("data-parentid"));
		//清空下级值
		for(var i = 0 ; i < region.model.length ; i++){
			if(i > eleKey){
				$("#region_"+region.model[i]+" ul li ").remove();
				$("#region_cur_"+region.model[i]+"").html("");
				$("#region_cur_"+region.model[i]+"").attr("data-id",0);
				$("#region_cur_"+region.model[i]+"").attr("data-parentid",0);
				$("#region_"+region.model[i]).addClass("dn");
			}
		}
		//请求下级数据
		region.regionByParentId({ parentId : $(this).attr("data-id") },function(data){
			if(data.code != 1){
				$.afui.toast(data.info);
				return ;
			}
			
			if(data.rows == null || data.rows == "" || typeof(data.rows) == "undefined") return ;
			
			var regionList = data.rows;
			$.each(regionList, function(key) {
				$("#region_"+region.model[(eleKey+1)]+" ul").append("<li id='region_"+region.model[(eleKey+1)]+"_li_"+regionList[key].regionId+"' data-id='"+regionList[key].regionId+"' data-parentid='"+regionList[key].parentId+"'>"+regionList[key].regionName+"</li>");
			});
			
			region.regionToggleClassCurrent(eleKey+1);
			$("#region_"+region.model[eleKey]).removeClass("cur");
			$("#region_"+region.model[eleKey+1]).addClass("cur");
			$("#region_"+region.model[eleKey+1]).removeClass("dn");
		});
		//如果数据存在，就关闭当前，展开下级
		
	});
}



$(function(){
	$(".region_close").click(function(){
		$(".region_main").hide();
	});
	
	$(".region_sure").click(function(){
		$.each(region.model, function(key) {
			$("#"+region.model[key]).html($("#region_cur_"+region.model[key]).html());
			$("#"+region.model[key]).attr("data-id",$("#region_cur_"+region.model[key]).attr("data-id"));
			$("#"+region.model[key]).attr("data-parentid",$("#region_cur_"+region.model[key]).attr("data-parentid"));
			
			
			$("#region_"+region.model[key]).attr("data-id",$("#region_cur_"+region.model[key]).attr("data-id"));
			$("#region_"+region.model[key]).attr("data-parentid",$("#region_cur_"+region.model[key]).attr("data-parentid"));
			
		});
		$(".region_main").hide();
	});
	
	$(".region_content i").click(function(){
		region.regionIClick(this);
	});
	
	$(".region_content div:not(.cur)").click(function(){
		region.regionDivClick(this);
	});
	
	
});

function callbackRegionParent(data){
	//console.log("地区信息回调:"+JSON.stringify(data));
	if(data.rows.length == 0){
		$.afui.toast("无地区信息");
		return ;
	}
	
	region.show();
	region.setRegionParentByElement(data.rows,1);region.regionToggleClassCurrent(1);//;province
	region.setRegionParentByElement(data.rows,2);region.regionToggleClassCurrent(2);
	region.setRegionParentByElement(data.rows,3);region.regionToggleClassCurrent(3);
	region.setRegionParentByElement(data.rows,4);region.regionToggleClassCurrent(4);
	region.setRegionParentByElement(data.rows,5);region.regionToggleClassCurrent(5);
}


