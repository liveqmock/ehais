/**
 * 
 */
$(function(){
	$(".view header").prepend("<div class='backButton back goBackClick'>返回</div>");
	$(".view header").prepend("<div id='edit_user' class='rightButton oval'>编辑</div>");
	goBackClickEvent();
	
	$("#is_default_li em.checkbox").click(function(){$(this).toggleClass("selected");});
	
	$("#userRegionLi").click(function(){
		var regionData = new Array();
		$.each(region.model,function(key){
			
			if(typeof($("#"+region.model[key]).attr("data-id")) != undefined && typeof($("#"+region.model[key]).attr("data-id")) != "undefined" ) {
				regionData.push($("#"+region.model[key]).attr("data-id"));
			}
		});
		
		if(regionData.length > 0){
			region.regionByParent(regionData.join(","),callbackRegionParent);
		}else{
			//初始化地区数据时使用
			region.regionByParent("1",callbackRegionParent);
			$("#region_"+region.model[1]).attr("data-parentid","1");
			$("#region_"+region.model[1]).addClass("cur");
		}
		
	});
	
	
	$("#delete_event").click(function(){
		delete_user_address();
	});
	
});

function get_user_address(addressId){
	$.ajax({
		url : "/ws/useraddress_info",
		data : {addressId : addressId},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.afui.toast(data.msg);
				window.location.href = "user_address_list";
				return ;
			}
			var model = data.model;
			
			$("#addressId").val(model.addressId);
			$("#consignee").val(model.consignee);
			$("#mobile").val(model.mobile);
			$("#address").val(model.address);
			if(model.isDefault == "1"){
				$("#is_default_li em.checkbox").addClass("selected");
			}else{
				$("#is_default_li em.checkbox").removeClass("selected")
			}
			
			$("#country").attr("data-id",model.country);
			$("#province").attr("data-id",model.province);
			$("#city").attr("data-id",model.city);
			$("#county").attr("data-id",model.county);
			$("#district").attr("data-id",model.district);
			$("#street").attr("data-id",model.street);
			
			var region = data.map.region;
			
			localStorage["region"] = JSON.stringify(region);

			$.each(region,function(index,ele){
				$("#user_address .region").each(function(){
					var regionId = $(this).attr("data-id");
					if(parseInt(regionId) == parseInt(ele.regionId)){
						$(this).html(ele.regionName);
						
						$("#region_"+$(this).attr("id")).attr("data-id",ele.regionId);
						$("#region_"+$(this).attr("id")).attr("data-parentid",ele.parentId);
						
						$("#region_cur_"+$(this).attr("id")).attr("data-id",ele.regionId);
						$("#region_cur_"+$(this).attr("id")).attr("data-parentid",ele.parentId);
						
						$("#region_cur_"+$(this).attr("id")).html(ele.regionName);
						
					}
				});				
			});
			
			$(".delete").removeClass("dn");
		},
		error : function(e){}
	});
}

function add_user_address(){
	var consignee = $("#consignee").val();
	var mobile = $("#mobile").val();
	var address = $("#address").val();
	var province = $("#province").attr("data-id");
	var city = $("#city").attr("data-id");
	var county = $("#county").attr("data-id");
	var district = $("#district").attr("data-id");
	var street = $("#street").attr("data-id");
	var isDefault = $("#is_default_li em.checkbox").hasClass("selected")?"1":"0";
	
	if(consignee == null || consignee==""){$.afui.toast("请输入收货人姓名");return;}
	if(mobile == null || mobile==""){$.afui.toast("请输入手机号码");return;}
	if(address == null || address==""){$.afui.toast("请输入详细地址");return;}
	if(province == null || province==""){$.afui.toast("请选择地区");return;}
	if(city == null || city==""){$.afui.toast("请选择地区");return;}
	
	var data_param = {
			consignee:consignee,
			mobile:mobile,
			address:address,
			province:province,
			city:city,
			county:county,
			district:district,
			street:street,
			isDefault:isDefault
		};
	
	$.ajax({
		url:"/ws/useraddress_add_submit",
		data : data_param,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code != 1){
				$.afui.toast(data.msg);
				return ;
			}
			
			var province_val = $("#province").html();
			var city_val = $("#city").html();
			var county_val = $("#county").html();
			var district_val = $("#district").html();
			var street_val = $("#street").html();
			
			data_param.addressId = data.model.addressId;
			data_param.province_val = province_val;
			data_param.city_val = city_val;
			data_param.county_val = county_val;
			data_param.district_val = district_val;
			data_param.street_val = street_val;
			//把刚添加的信息存储存在本地给订单获取本地信息
			localStorage["cur_address"] = JSON.stringify(data_param);
			
			window.history.go(-1);
		},
		error:function(e){},
		beforeSend:function(XHR){$.afui.showMask("提交信息中...");},
		complete:function(XHR, TS){$.afui.hideMask();}
	});
}

function edit_user_address(){
	var addressId = $("#addressId").val();
	var consignee = $("#consignee").val();
	var mobile = $("#mobile").val();
	var address = $("#address").val();
	var province = $("#province").attr("data-id");
	var city = $("#city").attr("data-id");
	var county = $("#county").attr("data-id");
	var district = $("#district").attr("data-id");
	var street = $("#street").attr("data-id");
	var isDefault = $("#is_default_li em.checkbox").hasClass("selected")?"1":"0";
	
	if(consignee == null || consignee==""){$.afui.toast("请输入收货人姓名");return;}
	if(mobile == null || mobile==""){$.afui.toast("请输入手机号码");return;}
	if(address == null || address==""){$.afui.toast("请输入详细地址");return;}
	if(province == null || province==""){$.afui.toast("请选择地区");return;}
	if(city == null || city==""){$.afui.toast("请选择地区");return;}
	
	var data_param = {
			addressId : addressId,
			consignee:consignee,
			mobile:mobile,
			address:address,
			province:province,
			city:city,
			county:county,
			district:district,
			street:street,
			isDefault:isDefault
		};
	
	
	$.ajax({
		url:"/ws/useraddress_edit_submit",
		data : data_param,
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.code != 1){
				$.afui.toast(data.msg);
				return ;
			}
			
			var province_val = $("#province").html();
			var city_val = $("#city").html();
			var county_val = $("#county").html();
			var district_val = $("#district").html();
			var street_val = $("#street").html();
			
			data_param.province_val = province_val;
			data_param.city_val = city_val;
			data_param.county_val = county_val;
			data_param.district_val = district_val;
			data_param.street_val = street_val;
			
			//把刚添加的信息存储存在本地给订单获取本地信息
			localStorage["cur_address"] = JSON.stringify(data_param);
			
			window.history.go(-1);
		},
		error:function(e){},
		beforeSend:function(XHR){$.afui.showMask("提交信息中...");},
		complete:function(XHR, TS){$.afui.hideMask();}
	});
}

function delete_user_address(){
	if (!confirm("确认要删除此收货人信息吗？")) {
        return false;
    }
	
	var addressId = $("#addressId").val();
	
	$.ajax({
		url:"/ws/useraddress_delete_sumbit",
		data:{
			addressId : addressId
		},
		type:"post",
		dataType:"json",
		success:function(data){
			$.afui.toast(data.msg);
			if(data.code != 1){
				
				return ;
			}
			window.history.go(-1);
		},
		error:function(e){},
		beforeSend:function(XHR){$.afui.showMask("提交信息中...");},
		complete:function(XHR, TS){$.afui.hideMask();}
	});
	
}