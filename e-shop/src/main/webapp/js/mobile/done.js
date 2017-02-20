/**
 * 
 */
$(function(){
	var json = JSON.parse(localStorage["order"]);
	var model = json.model;
	
	
	
	$("#orderSn").html(model.orderSn);
	$("#addTime").html(model.addTime);
	$("#consignee").html(model.consignee);
	$("#mobile").html(model.mobile);
	$("#address").html(model.address);
	$("#message").html(model.postscript);
	
	$("#country").attr("region",address.country);
	$("#province").attr("region",address.province);
	$("#city").attr("region",address.city);
	$("#county").attr("region",address.county);
	$("#district").attr("region",address.district);
	$("#street").attr("region",address.street);
	
	
	if(typeof(localStorage["region"]) != "undefined"){
		
		var region = JSON.parse(localStorage["region"]);

		$.each(region,function(index,ele){
			$("#user_address .region").each(function(){
				var regionId = $(this).attr("region");
				if(parseInt(regionId) == parseInt(ele.regionId)){
					$(this).html(ele.regionName);
				}
			});				
		});
		
	}
	
	
	
});