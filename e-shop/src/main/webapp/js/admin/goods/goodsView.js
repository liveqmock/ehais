var haiGoodsModal ;
var key_goodsName = "";
var validform = null;
$(function(){
	haiGoodsModal = $("#haiGoodsModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	$("#haiGoodsAddDetail").click(function(){
		haiGoodsAddDetail();
	});
	
	$("#haiGoodsSearch").click(function(){
		key_goodsName = $.trim($("#key_goodsName").val());
		bsTable.bootstrapTable('refresh', { query : { goodsName : key_goodsName , page : 1} });
	});
    
	
	$("#haiGoodsSaveSubmit").click(function(){$("#haiGoodsForm").attr("action") == "add" ? haiGoodsAddSubmit() : haiGoodsEditSubmit() ;});

	validform = $("#haiGoodsForm").validate({rules:{},messages:{}});
	
});

function haiGoodsAddDetail(){
	haiGoodsModal.modal("show");
	$("#haiGoodsForm").attr("action","add");
	$('#haiGoodsForm')[0].reset();
	select_reset_parentId();
}


function haiGoodsEditDetail(goodsId){
	$("#haiGoodsForm").attr("action","edit");
	layer.load(0, {shade: false});
	$.ajax({
		url : "haiGoodsEditDetail",type:"post",dataType:"json",data:{goodsId:goodsId},
		success:function(result){
			layer.closeAll();
			haiGoodsModal.modal("show");
			$.each(result.model,function(id,ele){
				$("#"+id).val(ele);
			});
			select_reset_parentId();
		}
	});
}



function haiGoodsAddSubmit(){
	
	if($("#catId").val() == undefined || $("#catId").val().length == 0){
		layer.msg("请输入分类编号");return ;
	}
	if($("#goodsSn").val() == undefined || $("#goodsSn").val().length == 0){
		layer.msg("请输入条形码");return ;
	}
	if($("#goodsName").val() == undefined || $("#goodsName").val().length == 0){
		layer.msg("请输入商品名称");return ;
	}
	if($("#goodsNameStyle").val() == undefined || $("#goodsNameStyle").val().length == 0){
		layer.msg("请输入goods_name_style");return ;
	}
	if($("#clickCount").val() == undefined || $("#clickCount").val().length == 0){
		layer.msg("请输入click_count");return ;
	}
	if($("#brandId").val() == undefined || $("#brandId").val().length == 0){
		layer.msg("请输入brand_id");return ;
	}
	if($("#providerName").val() == undefined || $("#providerName").val().length == 0){
		layer.msg("请输入provider_name");return ;
	}
	if($("#goodsNumber").val() == undefined || $("#goodsNumber").val().length == 0){
		layer.msg("请输入goods_number");return ;
	}
	if($("#goodsWeight").val() == undefined || $("#goodsWeight").val().length == 0){
		layer.msg("请输入goods_weight");return ;
	}
	if($("#marketPrice").val() == undefined || $("#marketPrice").val().length == 0){
		layer.msg("请输入market_price");return ;
	}
	if($("#shopPrice").val() == undefined || $("#shopPrice").val().length == 0){
		layer.msg("请输入销售价格");return ;
	}
	if($("#promotePrice").val() == undefined || $("#promotePrice").val().length == 0){
		layer.msg("请输入promote_price");return ;
	}
	if($("#warnNumber").val() == undefined || $("#warnNumber").val().length == 0){
		layer.msg("请输入warn_number");return ;
	}
	if($("#keywords").val() == undefined || $("#keywords").val().length == 0){
		layer.msg("请输入keywords");return ;
	}
	if($("#goodsThumb").val() == undefined || $("#goodsThumb").val().length == 0){
		layer.msg("请输入goods_thumb");return ;
	}
	if($("#goodsImg").val() == undefined || $("#goodsImg").val().length == 0){
		layer.msg("请输入goods_img");return ;
	}
	if($("#originalImg").val() == undefined || $("#originalImg").val().length == 0){
		layer.msg("请输入original_img");return ;
	}
	if($("#isReal").val() == undefined || $("#isReal").val().length == 0){
		layer.msg("请输入is_real");return ;
	}
	if($("#extensionCode").val() == undefined || $("#extensionCode").val().length == 0){
		layer.msg("请输入extension_code");return ;
	}
	if($("#isOnSale").val() == undefined || $("#isOnSale").val().length == 0){
		layer.msg("请输入is_on_sale");return ;
	}
	if($("#isAloneSale").val() == undefined || $("#isAloneSale").val().length == 0){
		layer.msg("请输入is_alone_sale");return ;
	}
	if($("#isShipping").val() == undefined || $("#isShipping").val().length == 0){
		layer.msg("请输入is_shipping");return ;
	}
	if($("#integral").val() == undefined || $("#integral").val().length == 0){
		layer.msg("请输入integral");return ;
	}
	if($("#addTime").val() == undefined || $("#addTime").val().length == 0){
		layer.msg("请输入add_time");return ;
	}
	if($("#sortOrder").val() == undefined || $("#sortOrder").val().length == 0){
		layer.msg("请输入排序");return ;
	}
	if($("#isDelete").val() == undefined || $("#isDelete").val().length == 0){
		layer.msg("请输入is_delete");return ;
	}
	if($("#isBest").val() == undefined || $("#isBest").val().length == 0){
		layer.msg("请输入is_best");return ;
	}
	if($("#isNew").val() == undefined || $("#isNew").val().length == 0){
		layer.msg("请输入is_new");return ;
	}
	if($("#isHot").val() == undefined || $("#isHot").val().length == 0){
		layer.msg("请输入is_hot");return ;
	}
	if($("#isPromote").val() == undefined || $("#isPromote").val().length == 0){
		layer.msg("请输入is_promote");return ;
	}
	if($("#isSpecial").val() == undefined || $("#isSpecial").val().length == 0){
		layer.msg("请输入is_special");return ;
	}
	if($("#bonusTypeId").val() == undefined || $("#bonusTypeId").val().length == 0){
		layer.msg("请输入bonus_type_id");return ;
	}
	if($("#lastUpdate").val() == undefined || $("#lastUpdate").val().length == 0){
		layer.msg("请输入last_update");return ;
	}
	if($("#goodsType").val() == undefined || $("#goodsType").val().length == 0){
		layer.msg("请输入goods_type");return ;
	}
	if($("#sellerNote").val() == undefined || $("#sellerNote").val().length == 0){
		layer.msg("请输入seller_note");return ;
	}
	if($("#giveIntegral").val() == undefined || $("#giveIntegral").val().length == 0){
		layer.msg("请输入give_integral");return ;
	}
	if($("#rankIntegral").val() == undefined || $("#rankIntegral").val().length == 0){
		layer.msg("请输入rank_integral");return ;
	}

	
	
	if(!validform.form())return ;
	
	$.ajax({
		url : "haiGoodsAddSubmit",
		type:"post",dataType:"json",data:$("#haiGoodsForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiGoodsForm')[0].reset();
				layer.confirm(result.msg, {
					btn: ['继续添加','返回列表'] //按钮
				}, function(){
				  //继续添加
					layer.closeAll();
					select_reset_parentId();
				}, function(){
				  //返回列表
					layer.closeAll();
					haiGoodsModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}

function haiGoodsEditSubmit(){
	
	if($("#catId").val() == undefined || $("#catId").val().length == 0){
		layer.msg("请输入分类编号");return ;
	}
	if($("#goodsSn").val() == undefined || $("#goodsSn").val().length == 0){
		layer.msg("请输入条形码");return ;
	}
	if($("#goodsName").val() == undefined || $("#goodsName").val().length == 0){
		layer.msg("请输入商品名称");return ;
	}
	if($("#goodsNameStyle").val() == undefined || $("#goodsNameStyle").val().length == 0){
		layer.msg("请输入goods_name_style");return ;
	}
	if($("#clickCount").val() == undefined || $("#clickCount").val().length == 0){
		layer.msg("请输入click_count");return ;
	}
	if($("#brandId").val() == undefined || $("#brandId").val().length == 0){
		layer.msg("请输入brand_id");return ;
	}
	if($("#providerName").val() == undefined || $("#providerName").val().length == 0){
		layer.msg("请输入provider_name");return ;
	}
	if($("#goodsNumber").val() == undefined || $("#goodsNumber").val().length == 0){
		layer.msg("请输入goods_number");return ;
	}
	if($("#goodsWeight").val() == undefined || $("#goodsWeight").val().length == 0){
		layer.msg("请输入goods_weight");return ;
	}
	if($("#marketPrice").val() == undefined || $("#marketPrice").val().length == 0){
		layer.msg("请输入market_price");return ;
	}
	if($("#shopPrice").val() == undefined || $("#shopPrice").val().length == 0){
		layer.msg("请输入销售价格");return ;
	}
	if($("#promotePrice").val() == undefined || $("#promotePrice").val().length == 0){
		layer.msg("请输入promote_price");return ;
	}
	if($("#warnNumber").val() == undefined || $("#warnNumber").val().length == 0){
		layer.msg("请输入warn_number");return ;
	}
	if($("#keywords").val() == undefined || $("#keywords").val().length == 0){
		layer.msg("请输入keywords");return ;
	}
	if($("#goodsThumb").val() == undefined || $("#goodsThumb").val().length == 0){
		layer.msg("请输入goods_thumb");return ;
	}
	if($("#goodsImg").val() == undefined || $("#goodsImg").val().length == 0){
		layer.msg("请输入goods_img");return ;
	}
	if($("#originalImg").val() == undefined || $("#originalImg").val().length == 0){
		layer.msg("请输入original_img");return ;
	}
	if($("#isReal").val() == undefined || $("#isReal").val().length == 0){
		layer.msg("请输入is_real");return ;
	}
	if($("#extensionCode").val() == undefined || $("#extensionCode").val().length == 0){
		layer.msg("请输入extension_code");return ;
	}
	if($("#isOnSale").val() == undefined || $("#isOnSale").val().length == 0){
		layer.msg("请输入is_on_sale");return ;
	}
	if($("#isAloneSale").val() == undefined || $("#isAloneSale").val().length == 0){
		layer.msg("请输入is_alone_sale");return ;
	}
	if($("#isShipping").val() == undefined || $("#isShipping").val().length == 0){
		layer.msg("请输入is_shipping");return ;
	}
	if($("#integral").val() == undefined || $("#integral").val().length == 0){
		layer.msg("请输入integral");return ;
	}
	if($("#addTime").val() == undefined || $("#addTime").val().length == 0){
		layer.msg("请输入add_time");return ;
	}
	if($("#sortOrder").val() == undefined || $("#sortOrder").val().length == 0){
		layer.msg("请输入排序");return ;
	}
	if($("#isDelete").val() == undefined || $("#isDelete").val().length == 0){
		layer.msg("请输入is_delete");return ;
	}
	if($("#isBest").val() == undefined || $("#isBest").val().length == 0){
		layer.msg("请输入is_best");return ;
	}
	if($("#isNew").val() == undefined || $("#isNew").val().length == 0){
		layer.msg("请输入is_new");return ;
	}
	if($("#isHot").val() == undefined || $("#isHot").val().length == 0){
		layer.msg("请输入is_hot");return ;
	}
	if($("#isPromote").val() == undefined || $("#isPromote").val().length == 0){
		layer.msg("请输入is_promote");return ;
	}
	if($("#isSpecial").val() == undefined || $("#isSpecial").val().length == 0){
		layer.msg("请输入is_special");return ;
	}
	if($("#bonusTypeId").val() == undefined || $("#bonusTypeId").val().length == 0){
		layer.msg("请输入bonus_type_id");return ;
	}
	if($("#lastUpdate").val() == undefined || $("#lastUpdate").val().length == 0){
		layer.msg("请输入last_update");return ;
	}
	if($("#goodsType").val() == undefined || $("#goodsType").val().length == 0){
		layer.msg("请输入goods_type");return ;
	}
	if($("#sellerNote").val() == undefined || $("#sellerNote").val().length == 0){
		layer.msg("请输入seller_note");return ;
	}
	if($("#giveIntegral").val() == undefined || $("#giveIntegral").val().length == 0){
		layer.msg("请输入give_integral");return ;
	}
	if($("#rankIntegral").val() == undefined || $("#rankIntegral").val().length == 0){
		layer.msg("请输入rank_integral");return ;
	}

	
	if(!validform.form())return ;
	
	$.ajax({
		url : "haiGoodsEditSubmit",
		type:"post",dataType:"json",data:$("#haiGoodsForm").serialize(),
		success:function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}else{
				$('#haiGoodsForm')[0].reset();
				layer.alert(result.msg, {
					skin: 'layui-layer-molv' //样式类名
					,closeBtn: 0
				}, function(){
					layer.closeAll();
					haiGoodsModal.modal("hide");
					bsTable.bootstrapTable('refresh', { query : {  page : 1} });
					select_reset_parentId();
				});
			}
		}
	});
}



function haiGoodsDelete(goodsId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiGoodsDelete",type:"post",dataType:"json",data:{goodsId:goodsId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

