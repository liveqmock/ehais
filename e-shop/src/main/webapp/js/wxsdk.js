/**
 * 
 */

function wx_config(data){
	//设置微信分享等接口
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: data.appId, // 必填，公众号的唯一标识
	    timestamp: data.timestamp, // 必填，生成签名的时间戳
	    nonceStr: data.nonceStr, // 必填，生成签名的随机串
	    signature: data.signature,// 必填，签名，见附录1
	    jsApiList: data.jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function(){
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		
		//分享到朋友圈
		wx.onMenuShareTimeline({
		    title: data.title, // 分享标题
		    link: data.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		    imgUrl: data.imgUrl, // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    	if(typeof(eval(data.shareSuccess))=="function"){e.shareSuccess("onMenuShareTimeline");}
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	if(typeof(eval(data.shareCancel))=="function"){e.shareCancel("onMenuShareTimeline");}
		    }
		});
		
		//分享给朋友
		wx.onMenuShareAppMessage({
		    title: data.title, // 分享标题
		    desc: data.desc, // 分享描述
		    link: data.link, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		    imgUrl: data.imgUrl, // 分享图标
		    type: data.type, // 分享类型,music、video或link，不填默认为link
		    dataUrl: data.dataUrl, // 如果type是music或video，则要提供数据链接，默认为空
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    	if(typeof(eval(data.shareSuccess))=="function"){e.shareSuccess("onMenuShareAppMessage");}
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	if(typeof(eval(data.shareCancel))=="function"){e.shareCancel("onMenuShareAppMessage");}
		    }
		});
		
		//分享到QQ
		wx.onMenuShareQQ({
		    title: data.title, // 分享标题
		    desc: data.desc, // 分享描述
		    link: data.link, // 分享链接
		    imgUrl: data.imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    	if(typeof(eval(data.shareSuccess))=="function"){e.shareSuccess("onMenuShareQQ");}
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    	if(typeof(eval(data.shareCancel))=="function"){e.shareCancel("onMenuShareQQ");}
		    }
		});
		
		//分享到QQ
		wx.onMenuShareWeibo({
		    title: data.title, // 分享标题
		    desc: data.desc, // 分享描述
		    link: data.link, // 分享链接
		    imgUrl: data.imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    	if(typeof(eval(data.shareSuccess))=="function"){e.shareSuccess("onMenuShareWeibo");}
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	if(typeof(eval(data.shareCancel))=="function"){e.shareCancel("onMenuShareWeibo");}
		    }
		});
		
		//分享到QQ空间
		wx.onMenuShareQZone({
		    title: data.title, // 分享标题
		    desc: data.desc, // 分享描述
		    link: data.link, // 分享链接
		    imgUrl: data.imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    	if(typeof(eval(data.shareSuccess))=="function"){e.shareSuccess("onMenuShareQZone");}
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    	if(typeof(eval(data.shareCancel))=="function"){e.shareCancel("onMenuShareQZone");}
		    }
		});
		
		
	});
	
	wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	});
	
	
}


