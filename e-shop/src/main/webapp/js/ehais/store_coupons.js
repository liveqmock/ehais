
$(function(){
	coupons();
	$("#coupons_add").click(function(){coupons_add();});
	$("#couponsQuantityType div").click(function(){//数量类型，不限或设置数量
		$("#couponsQuantityType div").removeClass("active");
		$(this).addClass("active");
		if($(this).hasClass("active") && parseInt($(this).attr("value")) == 1){
			$("#couponsQuantity_div").removeClass("dn");
			$("#couponsQuantity").val($("#couponsQuantity").attr("v"));
		}else{
			$("#couponsQuantity_div").addClass("dn");
			$("#couponsQuantity").attr("v",$("#couponsQuantity").val()).val(0);
		}
	});
	$("#couponsTypeRadio div").click(function(){//优惠类型，满减、打折
		$("#couponsTypeRadio div").removeClass("active");
		$(this).addClass("active");
		$(".couponsType").html($(this).attr("txt"));		
	});
	$("#saveCoupons").click(function(){saveCoupons();});
});

window.addEventListener('popstate', function (e) {
	console.log("e.state:"+JSON.stringify(history.state)+JSON.stringify(e.state));
	$("#coupons_list").addClass("active");
	$("#coupons_form").removeClass("active");
});

function coupons(){
	$("#coupons_list li").unbind().remove();
	$.ajax({
		url : "/ws/haiCouponsListJson",data:{page:1,rows:100},success:function(res){
			$.each(res.rows,function(k,v){
				$("#coupons_list").append("<li val='"+v.couponsId+"'>"+
					"<div class='l'>"+
						"<b>" + (v.couponsType == "reduce" ? "￥" : "折" ) + v.discounts + "</b>"+
						"满"+v.quota+"元"+
					"</div>"+
					"<div class='d'>"+
						"<h4>"+v.couponsName+"</h4>"+
						"<div class='t sm'>"+(v.couponsQuantity == 0 ? "不限数量":("限"+v.couponsQuantity))+"</div>"+
						"<div class='t s'>"+stateTxt(v.isValid)+"</div>"+
						"<div class='sm'>"+formatDate(v.startDate,v.endDate)+"</div>"+
					"</div>"+
					"<i class='iconfont icon-xiangyoujiantou'></i>"+
				"</li>");
			});
			
			$("#coupons_list li").click(function(){
				coupons_info($(this).attr("val"));
			});
			
		}
	});
}

function coupons_info(id){
	$.ajax({
		url : "/ws/haiCouponsInfo",data:{id:id},success:function(res){
			$.each(res.model,function(k,v){
				$("#"+k).val(v);
			});
			if(!isBlank(res.model.startDate))$("#startDate").val(res.model.startDate.substr(0,10));
			if(!isBlank(res.model.endDate))$("#endDate").val(res.model.endDate.substr(0,10));
			
			$("#couponsQuantity").val(res.model.couponsQuantity).attr("v",res.model.couponsQuantity);
			history.pushState({ste:"coupons_edit"}, null, "#coupons_edit");
			$("#coupons_list").removeClass("active");
			$("#coupons_form").addClass("active");
			$("#saveCoupons").attr("action","haiCouponsEditSubmit");
			
			$("#couponsQuantityType div").each(function(){
				if(parseInt(res.model.couponsQuantity) == 0 && parseInt($(this).attr("value")) == 0)
				$(this).click();
				
				if(parseInt(res.model.couponsQuantity) > 0 && parseInt($(this).attr("value")) == 1)
				$(this).click();
				
			});
			$("#couponsTypeRadio div").each(function(){
				if(res.model.couponsType == $(this).attr("value"))
				$(this).click();
			});
			
			
		}
	});
}

function stateTxt(v){
	switch(parseInt(v)){
		case -1 :
			return "失效";
		case 0 :
			return "可编辑";
		case 1 : 
			return "发布中";
		default :
			return "";	
	}
}
function formatDate(sd,ed){
	if(!isBlank(sd) && !isBlank(ed))return sd.substr(0,10)+"~"+ed.substr(0,10);
	if(!isBlank(sd) && isBlank(ed))return sd.substr(0,10)+"~不限";
	if(isBlank(sd) && !isBlank(ed))return "不限~"+ed.substr(0,10);
	if(isBlank(sd) && isBlank(ed))return "不限时间";
	
	return "";
}
function coupons_add(){
	history.pushState({ste:"coupons_add"}, null, "#coupons_add");
	$("#coupons_list").removeClass("active");
	$("#coupons_form").addClass("active");
	$("#saveCoupons").attr("action","haiCouponsAddSubmit");
	
	//清空
	$("#coupons_form input").each(function(){
		$("#"+$(this).attr("id")).val("");
	});
	$("#couponsQuantityType div:first").click();
	$("#couponsTypeRadio div:first").click();
}

function saveCoupons(){
	var val = {};
	$("#coupons_form input").each(function(){
		val[$(this).attr("id")] = $(this).val();
	});
	val["couponsType"] = $("#couponsTypeRadio div.active").attr("value");

	if(isBlank(val.couponsName)){elay.toast({content:"请输入优惠券名称"});$("#couponsName").focus();return;}
	if(isBlank(val.quota)){elay.toast({content:"请输入使用优惠券的金额"});$("#quota").focus();return;}
	if(isBlank(val.discounts)){elay.toast({content:"请输入优惠值"});$("#discounts").focus();return;}
	
	$.ajax({
		url : "/ws/"+$("#saveCoupons").attr("action"),data:val,success:function(res){
			console.log(JSON.stringify(res));
			elay.toast({content:res.msg});
			coupons();
			if(res.code == 1)history.back();
		}
	});
}
