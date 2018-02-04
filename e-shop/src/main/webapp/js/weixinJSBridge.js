function onBridgeReady(payment){
	if(is_weixin()){
		   WeixinJSBridge.invoke(
			       'getBrandWCPayRequest', payment,
			       function(res){
			    	   $(".w_pay_result_wrapper").addClass("active");    	   
			           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
			        	   $(".gomember").addClass("active");
			        	   $("#payState").html("支付成功");
			           }else{
			        	   $(".repay").addClass("active");
			        	   $("#payState").html("支付失败");
			           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
			       }
			   ); 
	}
}


function onBridgeReadyCall(payment,success,cancel){
	if(is_weixin()){
		   WeixinJSBridge.invoke(
			       'getBrandWCPayRequest', payment,
			       function(res){
			    	   $(".w_pay_result_wrapper").addClass("active");    	   
			           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
			        	   success();
			           }else{
			        	   cancel();
			           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
			       }
			   ); 
	}
}



