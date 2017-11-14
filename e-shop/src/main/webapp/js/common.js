function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
//全局设置
$.ajaxSetup({
	layerIndex:-1,
	timeout:120000,
	type:"post",
	dataType:"json",
	beforeSend: function () {
	    elay.loading();
	},
	complete: function () {
		elay.hide();
	},
	error: function () {
        elay.open({
		  content: '部分数据加载失败，可能会导致页面显示异常，请刷新后重试',
		  btn: '我知道了'
		});
    }
});

function is_weixin(){  
    var u = navigator.userAgent.toLowerCase();  
    if(u.match(/MicroMessenger/i)=="micromessenger") {  
        return true;  
    } else {  
        return false;  
    }  
} 

function is_iphone(){  
    var u = navigator.userAgent.toLowerCase();  
    if(u.match(/iPhone/i)=="iphone") {  
        return true;  
    } else {  
        return false;  
    }
}

function is_android(){  
    var u = navigator.userAgent.toLowerCase();  
    if(u.match(/Android/i)=="android") {  
        return true;  
    } else {  
        return false;  
    }
}


function isBlank(v){
	if(v == null || v == "null" || v == "" || v == undefined || v == "undefined" || v.length == 0)return true;
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}

$(function(){
	$("header .icon-xiangzuojiantou,header .icon-fanhui").click(function(){history.back();});
});