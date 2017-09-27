var orderData = {};
var pageData = {all:1,paywaiting:1,payed:1,shiped:1,returned:1};
var jroll;
$(function(){
	
	orderinfo_list($("dl dd:first").attr("for"));
	
	$("dl dd").click(function(){
		
		if($(this).attr("for") != location.hash){
			$("dl dd").removeClass("active");
			$(this).addClass("active");
			if(orderData[$(this).attr("for")] == null){
				orderinfo_list($(this).attr("for"));
			}else{
				driverOrderInfo(orderData[$(this).attr("for")]);
			}
		}
		
//		history.pushState({tfor:$(this).attr("for")},$(this).attr("for"),"#"+$(this).attr("for"));
	});
	
	

	console.log(parseFloat($(window).height())+"=="+parseFloat($(".wm").css('padding-top').replaceAll("px","")));
	$("#jorder").height(parseFloat($(window).height()) - parseFloat($(".wm").css('padding-top').replaceAll("px","")));
	
	
	jroll = new JRoll("#jorder", {scrollBarY:false});
	jroll.pulldown({
	    refresh: function(complete) {
	        //完成加载数据的操作后回调执行complete方法
	        console.log("complete();");
	        complete();
	    }
	});
	
	jroll.infinite({
//		loadingTip : "<div></div>",
	    getData: function(page, callback, errorCallback) {
	    	console.log("page();"+page);
	    	jroll.options.total = 10;
	    	callback([{
	            "index": "ffffff",
	            "text": "自定义图标+开启blank"
	          }]);
//	        $.ajax({
//	            url : "getdata.php",
//	            data : condition,
//	            type : "POST",
//	            success : function(data) {
//	                jroll.options.total = data.total;
//	                callback(data.items);
//	            },
//	            error: function() {
//	                errorCallback()
//	            }
//	        });
	    },
	    template: "<div class='item'>{{=_obj.index}}、{{=_obj.text}}</div>" //每条数据模板
	});
	
	
	
});

function orderinfo_list(status){
	var order_status = null;
	var pay_status = null;
	var shipping_status = null;
	if(status == "pw"){order_status = 0 ; pay_status = 0;}
	if(status == "rw"){order_status = 1 ; pay_status = 1;}
	if(status == "rd"){order_status = 4 ; pay_status = 4;}
	$.ajax({
		url : "/ws/orderinfo_list",
		data:{
			order_status:order_status,
			pay_status:pay_status,
			shipping_status:shipping_status,
			page:pageData[status],
			rows:10
			},
		success : function(result){
			if(orderData[status] == null){
				orderData[status] = result;
				driverOrderInfo(result);
			}
		}
	});
}

function driverOrderInfo(result){
	$("#order li").remove();
	if(result.rows != null && result.rows.length > 0){
		$(".e").removeClass("active");
		$.each(result.rows,function(v){
			$("#order").append("<li>"+
					"<div class='h'>"+
						"订单：1028349734859348"+
						"<b>已完成</b>"+
					"</div>"+
					"<ul>"+
						"<li>"+
							"<img src='images/s3.jpg' />"+
							"白色职业装连衣裤衫配吊坠、红色+黑色、GR-1848"+
						"</li>"+
						"<li>"+
							"<img src='images/s3.jpg' />"+
							"白色职业装连衣裤衫配吊坠、红色+黑色、GR-1848"+
						"</li>"+
					"</ul>"+
					"<div class='t'>"+
						"共2件商品 实付款：￥234.08元"+
					"</div>"+
					"<div class='b'>"+
						"<button class='o'>详情</button>"+
						"<button class='o'>付款</button>"+
						"<button class='d'>删除订单</button>"+
					"</div>"+
				"</li>");
		});
		
		jroll.refresh();
		
	}else{
		$(".e").addClass("active");
	}
}

//window.addEventListener("hashchange", function(){
//	 var hashStr = location.hash.replace("#","");
//	 console.log("hashchange:"+hashStr);
//	 alert("hashchange:"+hashStr);
//}, true);
//
//window.addEventListener("popstate", function() {
//    var currentState = history.state;
//    console.log("currentState:"+JSON.stringify(currentState));
//    alert(("currentState:"+JSON.stringify(currentState)));
//});



