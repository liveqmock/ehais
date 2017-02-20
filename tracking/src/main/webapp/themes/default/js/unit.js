
/**
 * 获取url地址的参数
 * @param {Object} name
 */
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(decodeURI(r[2]));
	return null;
}
//alert(GetQueryString("参数名1"));alert(GetQueryString("参数名2"));alert(GetQueryString("参数名3"));

/* 质朴长存法  by lifesinger */  
function pad(num, n) {  
    var len = num.toString().length;  
    while(len < n) {  
        num = "0" + num;  
        len++;  
    }  
    return num;  
}  

/**
 * 重构替换函数
 */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
}

/**
 * new Date((Date.parse(startTime+" GMT+0800"))).pattern("yyyy-MM-dd hh:mm:ss")
 * var date = new Date();      
 * window.alert(date.pattern("yyyy-MM-dd hh:mm:ss"));
 * @param {Object} fmt
 */
Date.prototype.pattern=function(fmt) {         
    var o = {         
    "M+" : this.getMonth()+1, //月份         
    "d+" : this.getDate(), //日         
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时         
    "H+" : this.getHours(), //小时         
    "m+" : this.getMinutes(), //分         
    "s+" : this.getSeconds(), //秒         
    "q+" : Math.floor((this.getMonth()+3)/3), //季度         
    "S" : this.getMilliseconds() //毫秒         
    };         
    var week = {         
    "0" : "/u65e5",         
    "1" : "/u4e00",         
    "2" : "/u4e8c",         
    "3" : "/u4e09",         
    "4" : "/u56db",         
    "5" : "/u4e94",         
    "6" : "/u516d"        
    };         
    if(/(y+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));         
    }         
    if(/(E+)/.test(fmt)){         
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);         
    }         
    for(var k in o){         
        if(new RegExp("("+ k +")").test(fmt)){         
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));         
        }         
    }         
    return fmt;         
} 

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

//设置全局beforeSend
$.ajaxSettings.beforeSend = function(xhr, setting) {

};

$.ajaxSettings.complete = function(xhr, status) {

};


/**
 * 格式化字符串，保证不会出错
 * @param {Object} txt
 */
function formatTxt(txt){
	if(txt == undefined || txt == null || txt.length == 0)txt = "";
	return txt;
}

/**
 * 格式化字符串，保证不会出错
 * @param {Object} txt
 */
function formatImg(images){
	if(images == undefined || images == null || images.length == 0)images = "/themes/default/images/default-activity.png";
	return images;
}

/** 
 * js截取字符串，中英文都能用 
 * @param str：需要截取的字符串 
 * @param len: 需要截取的长度 
 */
function formatCutstr(str, len) {
	var str_length = 0;
	var str_len = 0;
	str_cut = new String();
	str_len = str.length;
	for (var i = 0; i < str_len; i++) {
		a = str.charAt(i);
		str_length++;
		if (escape(a).length > 4) {
			//中文字符的长度经编码之后大于4  
			str_length++;
		}
		str_cut = str_cut.concat(a);
		if (str_length >= len) {
			str_cut = str_cut.concat("...");
			return str_cut;
		}
	}
	//如果给定字符串小于指定长度，则返回源字符串；  
	if (str_length < len) {
		return str;
	}
}
/** 分割字符串 */
function formatKeywords(keywords){
	if(keywords == undefined || keywords == null || keywords.length == 0 || keywords == "")return "";
	var keywords_ = keywords.split(/,|，/);
	var str = "";
	$.each(keywords_,function(index,val){
		str += '<a href="javascript:;" class="btn btn-default btn-xs">'+val+'</a>&nbsp;&nbsp;';
	});
	return str;
}


