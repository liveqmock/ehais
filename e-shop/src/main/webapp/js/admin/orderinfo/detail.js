$(function(){

	$("#saveSubmit").click(function(){$("#haiOrderInfoForm").attr("action") == "add" ? haiOrderInfoAddSubmit() : haiOrderInfoEditSubmit() ;});

});


function haiOrderInfoAddSubmit(){
	
	if($("#orderSn").val() == undefined || $("#orderSn").val().length == 0){
		layer.msg("请输入order_sn");return ;
	}
	if($("#userId").val() == undefined || $("#userId").val().length == 0){
		layer.msg("请输入user_id");return ;
	}
	if($("#orderStatus").val() == undefined || $("#orderStatus").val().length == 0){
		layer.msg("请输入order_status");return ;
	}
	if($("#shippingStatus").val() == undefined || $("#shippingStatus").val().length == 0){
		layer.msg("请输入shipping_status");return ;
	}
	if($("#payStatus").val() == undefined || $("#payStatus").val().length == 0){
		layer.msg("请输入pay_status");return ;
	}
	if($("#consignee").val() == undefined || $("#consignee").val().length == 0){
		layer.msg("请输入consignee");return ;
	}
	if($("#country").val() == undefined || $("#country").val().length == 0){
		layer.msg("请输入country");return ;
	}
	if($("#province").val() == undefined || $("#province").val().length == 0){
		layer.msg("请输入province");return ;
	}
	if($("#city").val() == undefined || $("#city").val().length == 0){
		layer.msg("请输入city");return ;
	}
	if($("#county").val() == undefined || $("#county").val().length == 0){
		layer.msg("请输入county");return ;
	}
	if($("#address").val() == undefined || $("#address").val().length == 0){
		layer.msg("请输入address");return ;
	}
	if($("#zipcode").val() == undefined || $("#zipcode").val().length == 0){
		layer.msg("请输入zipcode");return ;
	}
	if($("#tel").val() == undefined || $("#tel").val().length == 0){
		layer.msg("请输入tel");return ;
	}
	if($("#mobile").val() == undefined || $("#mobile").val().length == 0){
		layer.msg("请输入mobile");return ;
	}
	if($("#email").val() == undefined || $("#email").val().length == 0){
		layer.msg("请输入email");return ;
	}
	if($("#bestTime").val() == undefined || $("#bestTime").val().length == 0){
		layer.msg("请输入best_time");return ;
	}
	if($("#signBuilding").val() == undefined || $("#signBuilding").val().length == 0){
		layer.msg("请输入sign_building");return ;
	}
	if($("#postscript").val() == undefined || $("#postscript").val().length == 0){
		layer.msg("请输入postscript");return ;
	}
	if($("#shippingId").val() == undefined || $("#shippingId").val().length == 0){
		layer.msg("请输入shipping_id");return ;
	}
	if($("#shippingName").val() == undefined || $("#shippingName").val().length == 0){
		layer.msg("请输入shipping_name");return ;
	}
	if($("#payId").val() == undefined || $("#payId").val().length == 0){
		layer.msg("请输入pay_id");return ;
	}
	if($("#payName").val() == undefined || $("#payName").val().length == 0){
		layer.msg("请输入pay_name");return ;
	}
	if($("#howOos").val() == undefined || $("#howOos").val().length == 0){
		layer.msg("请输入how_oos");return ;
	}
	if($("#howSurplus").val() == undefined || $("#howSurplus").val().length == 0){
		layer.msg("请输入how_surplus");return ;
	}
	if($("#packName").val() == undefined || $("#packName").val().length == 0){
		layer.msg("请输入pack_name");return ;
	}
	if($("#cardName").val() == undefined || $("#cardName").val().length == 0){
		layer.msg("请输入card_name");return ;
	}
	if($("#cardMessage").val() == undefined || $("#cardMessage").val().length == 0){
		layer.msg("请输入card_message");return ;
	}
	if($("#invPayee").val() == undefined || $("#invPayee").val().length == 0){
		layer.msg("请输入inv_payee");return ;
	}
	if($("#invContent").val() == undefined || $("#invContent").val().length == 0){
		layer.msg("请输入inv_content");return ;
	}
	if($("#goodsAmount").val() == undefined || $("#goodsAmount").val().length == 0){
		layer.msg("请输入goods_amount");return ;
	}
	if($("#shippingFee").val() == undefined || $("#shippingFee").val().length == 0){
		layer.msg("请输入shipping_fee");return ;
	}
	if($("#insureFee").val() == undefined || $("#insureFee").val().length == 0){
		layer.msg("请输入insure_fee");return ;
	}
	if($("#payFee").val() == undefined || $("#payFee").val().length == 0){
		layer.msg("请输入pay_fee");return ;
	}
	if($("#packFee").val() == undefined || $("#packFee").val().length == 0){
		layer.msg("请输入pack_fee");return ;
	}
	if($("#cardFee").val() == undefined || $("#cardFee").val().length == 0){
		layer.msg("请输入card_fee");return ;
	}
	if($("#moneyPaid").val() == undefined || $("#moneyPaid").val().length == 0){
		layer.msg("请输入money_paid");return ;
	}
	if($("#surplus").val() == undefined || $("#surplus").val().length == 0){
		layer.msg("请输入surplus");return ;
	}
	if($("#integral").val() == undefined || $("#integral").val().length == 0){
		layer.msg("请输入integral");return ;
	}
	if($("#integralMoney").val() == undefined || $("#integralMoney").val().length == 0){
		layer.msg("请输入integral_money");return ;
	}
	if($("#bonus").val() == undefined || $("#bonus").val().length == 0){
		layer.msg("请输入bonus");return ;
	}
	if($("#orderAmount").val() == undefined || $("#orderAmount").val().length == 0){
		layer.msg("请输入order_amount");return ;
	}
	if($("#fromAd").val() == undefined || $("#fromAd").val().length == 0){
		layer.msg("请输入from_ad");return ;
	}
	if($("#referer").val() == undefined || $("#referer").val().length == 0){
		layer.msg("请输入referer");return ;
	}
	if($("#confirmTime").val() == undefined || $("#confirmTime").val().length == 0){
		layer.msg("请输入confirm_time");return ;
	}
	if($("#payTime").val() == undefined || $("#payTime").val().length == 0){
		layer.msg("请输入pay_time");return ;
	}
	if($("#shippingTime").val() == undefined || $("#shippingTime").val().length == 0){
		layer.msg("请输入shipping_time");return ;
	}
	if($("#packId").val() == undefined || $("#packId").val().length == 0){
		layer.msg("请输入pack_id");return ;
	}
	if($("#cardId").val() == undefined || $("#cardId").val().length == 0){
		layer.msg("请输入card_id");return ;
	}
	if($("#bonusId").val() == undefined || $("#bonusId").val().length == 0){
		layer.msg("请输入bonus_id");return ;
	}
	if($("#invoiceNo").val() == undefined || $("#invoiceNo").val().length == 0){
		layer.msg("请输入invoice_no");return ;
	}
	if($("#extensionCode").val() == undefined || $("#extensionCode").val().length == 0){
		layer.msg("请输入extension_code");return ;
	}
	if($("#extensionId").val() == undefined || $("#extensionId").val().length == 0){
		layer.msg("请输入extension_id");return ;
	}
	if($("#toBuyer").val() == undefined || $("#toBuyer").val().length == 0){
		layer.msg("请输入to_buyer");return ;
	}
	if($("#payNote").val() == undefined || $("#payNote").val().length == 0){
		layer.msg("请输入pay_note");return ;
	}
	if($("#agencyId").val() == undefined || $("#agencyId").val().length == 0){
		layer.msg("请输入agency_id");return ;
	}
	if($("#invType").val() == undefined || $("#invType").val().length == 0){
		layer.msg("请输入inv_type");return ;
	}
	if($("#tax").val() == undefined || $("#tax").val().length == 0){
		layer.msg("请输入tax");return ;
	}
	if($("#isSeparate").val() == undefined || $("#isSeparate").val().length == 0){
		layer.msg("请输入is_separate");return ;
	}
	if($("#parentId").val() == undefined || $("#parentId").val().length == 0){
		layer.msg("请输入parent_id");return ;
	}
	if($("#discount").val() == undefined || $("#discount").val().length == 0){
		layer.msg("请输入discount");return ;
	}

	
	
	$.ajax({
		url : "haiOrderInfoAddSubmit",
		type:"post",dataType:"json",data:$("#haiOrderInfoForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiOrderInfoForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
				}, function(){
				  //返回列表
					layer.closeAll();
					window.history.back();
				});
			}
		}
	});
}

function haiOrderInfoEditSubmit(){
	
	if($("#orderSn").val() == undefined || $("#orderSn").val().length == 0){
		layer.msg("请输入order_sn");return ;
	}
	if($("#userId").val() == undefined || $("#userId").val().length == 0){
		layer.msg("请输入user_id");return ;
	}
	if($("#orderStatus").val() == undefined || $("#orderStatus").val().length == 0){
		layer.msg("请输入order_status");return ;
	}
	if($("#shippingStatus").val() == undefined || $("#shippingStatus").val().length == 0){
		layer.msg("请输入shipping_status");return ;
	}
	if($("#payStatus").val() == undefined || $("#payStatus").val().length == 0){
		layer.msg("请输入pay_status");return ;
	}
	if($("#consignee").val() == undefined || $("#consignee").val().length == 0){
		layer.msg("请输入consignee");return ;
	}
	if($("#country").val() == undefined || $("#country").val().length == 0){
		layer.msg("请输入country");return ;
	}
	if($("#province").val() == undefined || $("#province").val().length == 0){
		layer.msg("请输入province");return ;
	}
	if($("#city").val() == undefined || $("#city").val().length == 0){
		layer.msg("请输入city");return ;
	}
	if($("#county").val() == undefined || $("#county").val().length == 0){
		layer.msg("请输入county");return ;
	}
	if($("#address").val() == undefined || $("#address").val().length == 0){
		layer.msg("请输入address");return ;
	}
	if($("#zipcode").val() == undefined || $("#zipcode").val().length == 0){
		layer.msg("请输入zipcode");return ;
	}
	if($("#tel").val() == undefined || $("#tel").val().length == 0){
		layer.msg("请输入tel");return ;
	}
	if($("#mobile").val() == undefined || $("#mobile").val().length == 0){
		layer.msg("请输入mobile");return ;
	}
	if($("#email").val() == undefined || $("#email").val().length == 0){
		layer.msg("请输入email");return ;
	}
	if($("#bestTime").val() == undefined || $("#bestTime").val().length == 0){
		layer.msg("请输入best_time");return ;
	}
	if($("#signBuilding").val() == undefined || $("#signBuilding").val().length == 0){
		layer.msg("请输入sign_building");return ;
	}
	if($("#postscript").val() == undefined || $("#postscript").val().length == 0){
		layer.msg("请输入postscript");return ;
	}
	if($("#shippingId").val() == undefined || $("#shippingId").val().length == 0){
		layer.msg("请输入shipping_id");return ;
	}
	if($("#shippingName").val() == undefined || $("#shippingName").val().length == 0){
		layer.msg("请输入shipping_name");return ;
	}
	if($("#payId").val() == undefined || $("#payId").val().length == 0){
		layer.msg("请输入pay_id");return ;
	}
	if($("#payName").val() == undefined || $("#payName").val().length == 0){
		layer.msg("请输入pay_name");return ;
	}
	if($("#howOos").val() == undefined || $("#howOos").val().length == 0){
		layer.msg("请输入how_oos");return ;
	}
	if($("#howSurplus").val() == undefined || $("#howSurplus").val().length == 0){
		layer.msg("请输入how_surplus");return ;
	}
	if($("#packName").val() == undefined || $("#packName").val().length == 0){
		layer.msg("请输入pack_name");return ;
	}
	if($("#cardName").val() == undefined || $("#cardName").val().length == 0){
		layer.msg("请输入card_name");return ;
	}
	if($("#cardMessage").val() == undefined || $("#cardMessage").val().length == 0){
		layer.msg("请输入card_message");return ;
	}
	if($("#invPayee").val() == undefined || $("#invPayee").val().length == 0){
		layer.msg("请输入inv_payee");return ;
	}
	if($("#invContent").val() == undefined || $("#invContent").val().length == 0){
		layer.msg("请输入inv_content");return ;
	}
	if($("#goodsAmount").val() == undefined || $("#goodsAmount").val().length == 0){
		layer.msg("请输入goods_amount");return ;
	}
	if($("#shippingFee").val() == undefined || $("#shippingFee").val().length == 0){
		layer.msg("请输入shipping_fee");return ;
	}
	if($("#insureFee").val() == undefined || $("#insureFee").val().length == 0){
		layer.msg("请输入insure_fee");return ;
	}
	if($("#payFee").val() == undefined || $("#payFee").val().length == 0){
		layer.msg("请输入pay_fee");return ;
	}
	if($("#packFee").val() == undefined || $("#packFee").val().length == 0){
		layer.msg("请输入pack_fee");return ;
	}
	if($("#cardFee").val() == undefined || $("#cardFee").val().length == 0){
		layer.msg("请输入card_fee");return ;
	}
	if($("#moneyPaid").val() == undefined || $("#moneyPaid").val().length == 0){
		layer.msg("请输入money_paid");return ;
	}
	if($("#surplus").val() == undefined || $("#surplus").val().length == 0){
		layer.msg("请输入surplus");return ;
	}
	if($("#integral").val() == undefined || $("#integral").val().length == 0){
		layer.msg("请输入integral");return ;
	}
	if($("#integralMoney").val() == undefined || $("#integralMoney").val().length == 0){
		layer.msg("请输入integral_money");return ;
	}
	if($("#bonus").val() == undefined || $("#bonus").val().length == 0){
		layer.msg("请输入bonus");return ;
	}
	if($("#orderAmount").val() == undefined || $("#orderAmount").val().length == 0){
		layer.msg("请输入order_amount");return ;
	}
	if($("#fromAd").val() == undefined || $("#fromAd").val().length == 0){
		layer.msg("请输入from_ad");return ;
	}
	if($("#referer").val() == undefined || $("#referer").val().length == 0){
		layer.msg("请输入referer");return ;
	}
	if($("#confirmTime").val() == undefined || $("#confirmTime").val().length == 0){
		layer.msg("请输入confirm_time");return ;
	}
	if($("#payTime").val() == undefined || $("#payTime").val().length == 0){
		layer.msg("请输入pay_time");return ;
	}
	if($("#shippingTime").val() == undefined || $("#shippingTime").val().length == 0){
		layer.msg("请输入shipping_time");return ;
	}
	if($("#packId").val() == undefined || $("#packId").val().length == 0){
		layer.msg("请输入pack_id");return ;
	}
	if($("#cardId").val() == undefined || $("#cardId").val().length == 0){
		layer.msg("请输入card_id");return ;
	}
	if($("#bonusId").val() == undefined || $("#bonusId").val().length == 0){
		layer.msg("请输入bonus_id");return ;
	}
	if($("#invoiceNo").val() == undefined || $("#invoiceNo").val().length == 0){
		layer.msg("请输入invoice_no");return ;
	}
	if($("#extensionCode").val() == undefined || $("#extensionCode").val().length == 0){
		layer.msg("请输入extension_code");return ;
	}
	if($("#extensionId").val() == undefined || $("#extensionId").val().length == 0){
		layer.msg("请输入extension_id");return ;
	}
	if($("#toBuyer").val() == undefined || $("#toBuyer").val().length == 0){
		layer.msg("请输入to_buyer");return ;
	}
	if($("#payNote").val() == undefined || $("#payNote").val().length == 0){
		layer.msg("请输入pay_note");return ;
	}
	if($("#agencyId").val() == undefined || $("#agencyId").val().length == 0){
		layer.msg("请输入agency_id");return ;
	}
	if($("#invType").val() == undefined || $("#invType").val().length == 0){
		layer.msg("请输入inv_type");return ;
	}
	if($("#tax").val() == undefined || $("#tax").val().length == 0){
		layer.msg("请输入tax");return ;
	}
	if($("#isSeparate").val() == undefined || $("#isSeparate").val().length == 0){
		layer.msg("请输入is_separate");return ;
	}
	if($("#parentId").val() == undefined || $("#parentId").val().length == 0){
		layer.msg("请输入parent_id");return ;
	}
	if($("#discount").val() == undefined || $("#discount").val().length == 0){
		layer.msg("请输入discount");return ;
	}

	
	
	$.ajax({
		url : "haiOrderInfoEditSubmit",
		type:"post",dataType:"json",data:$("#haiOrderInfoForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiOrderInfoForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					window.history.back();
				});
			}
		}
	});
}
