/**
 * 
 */

var user_address_page = 1;
var len = 10;
$(function(){
	$(".view header").prepend("<div id='goback' class='backButton back goBackClick'>返回</div>");
	$(".view header").prepend("<div id='add_user' class='rightButton oval'>新增</div>");
	goBackClickEvent();
	
	user_address_list(0);
	
	$("#add_user").click(function(){
		window.location.href = "user_address_add";
	});
});

function user_address_list(parent_id){
	$.ajax({
		url : "/ws/useraddress_lists",
		data : { page : user_address_page , len : len},
		type : "post",
		dataType : "json",
		success : function(data){
			if(data.code != 1){
				$.afui.toast(data.msg);
				return ;
			}
			
			var list = (typeof(data.rows) == undefined ) ? new Array() : data.rows;
			
			for (var i = 0; i < list.length; i++) {
				$("#user_address_ul").append(
						'<li data-id="'+list[i].addressId+'">'+
							'<div class="info">'+
								'<span class="consignee">'+list[i].consignee+ '</span>&nbsp;&nbsp;' +
								'<span class="mobile">'+list[i].mobile+'</span>&nbsp;&nbsp;'+
								'<span class="default">'+(list[i].isDefault == "1"?"[默认地址]":"")+'</span>'+
							'</div>'+
							'<div class="user_address">'+
								'<span class="province regionVal'+list[i].province+'" data-id="'+list[i].province+'"></span>'+
								'<span class="city regionVal'+list[i].city+'" data-id="'+list[i].city+'"></span>'+
								'<span class="county regionVal'+list[i].county+'" data-id="'+list[i].county+'"></span>'+
								'<span class="district regionVal'+list[i].district+'" data-id="'+list[i].district+'"></span>'+
								'<span class="street regionVal'+list[i].street+'" data-id="'+list[i].street+'"></span>'+
								'<span class="address region_">'+list[i].address+'</span>&nbsp;&nbsp;'+
							'</div>'+
						'</li>'
				);
			}
			
			if(typeof(data.map) != "undefined" && typeof(data.map.region) != "undefined"){
				$.each(data.map.region,function(index,val){
					$(".regionVal"+val.regionId).html(val.regionName);
				});
			}
			
			$("#user_address_ul li").unbind();
			$("#user_address_ul li").click(function(){
				if(action == "choose"){
					var addressId = $(this).attr("data-id");
					var consignee = $(this).children("div").children(".consignee").html();
					var mobile = $(this).children("div").children(".mobile").html();
					var address = $(this).children("div").children(".address").html();
					var province = $(this).children("div").children(".province").attr("data-id");
					var city = $(this).children("div").children(".city").attr("data-id");
					var county = $(this).children("div").children(".county").attr("data-id");
					var district = $(this).children("div").children(".district").attr("data-id");
					var street = $(this).children("div").children(".street").attr("data-id");
					
					var province_val = $(this).children("div").children(".province").html();
					var city_val = $(this).children("div").children(".city").html();
					var county_val = $(this).children("div").children(".county").html();
					var district_val = $(this).children("div").children(".district").html();
					var street_val = $(this).children("div").children(".street").html();
					
					
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
							province_val:province_val,
							city_val:city_val,
							county_val:county_val,
							district_val:district_val,
							street_val:street_val
						};
					
					localStorage["cur_address"] = JSON.stringify(data_param);
					
					window.history.go(-1);
				}else if(action = "manager"){
					window.location.href = "user_address_info?addressId="+$(this).attr("data-id");
				}
				
			});
		},
		error : function(e){}
	});
}

