//js 日期格式化 (new Date(Date.parse(data.model["birthday"]))).format("yyyy-MM-dd hh:mm:ss")

Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
}

/**
 * 格式化日期
 */
Date.prototype.formatDate = function(format)
{
var o = {
"M+" : this.getMonth()+1, // month
"d+" : this.getDate(), // day
"h+" : this.getHours(), // hour
"m+" : this.getMinutes(), // minute
"s+" : this.getSeconds(), // second
"q+" : Math.floor((this.getMonth()+3)/3), // quarter
"S" : this.getMilliseconds() // millisecond
}
if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
(this.getFullYear()+"").substr(4 - RegExp.$1.length));
for(var k in o)if(new RegExp("("+ k +")").test(format))
format = format.replace(RegExp.$1,
RegExp.$1.length==1 ? o[k] :
("00"+ o[k]).substr((""+ o[k]).length));
return format;
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


String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
 String.prototype.ltrim=function(){
    return this.replace(/(^\s*)/g,"");
}
 String.prototype.rtrim=function(){
    return this.replace(/(\s*$)/g,"");
}

/**
 * 格式化null值为""
 */
formatNull = function(str){
	if(str == null || str == "" || typeof(str) == "undefined")str = "";
	return str;
}

formatNull = function(str,value){
	if(str == null || str == "" || typeof(str) == "undefined")str = value;
	return str;
}

String.prototype.strLen = function() {
     var len = 0;
     for (var i = 0; i < this.length; i++) {
         if (this.charCodeAt(i) > 255 || this.charCodeAt(i) < 0) len += 2; else len ++;
     }
     return len;
 }
// 将字符串拆成字符，并存到数组中
String.prototype.strToChars = function(){
    var chars = new Array();
    for (var i = 0; i < this.length; i++){
        chars[i] = [this.substr(i, 1), this.isCHS(i)];
    }
    String.prototype.charsArray = chars;
    return chars;
}
// 判断某个字符是否是汉字
String.prototype.isCHS = function(i){
    if (this.charCodeAt(i) > 255 || this.charCodeAt(i) < 0) 
        return true;
    else
        return false;
}
// 截取字符串（从start字节到end字节）
String.prototype.subCHString = function(start, end){
    var len = 0;
    var str = "";
    this.strToChars();
    for (var i = 0; i < this.length; i++) {
        if(this.charsArray[i][1])
            len += 2;
        else
            len++;
        if (end < len)
            return str;
        else if (start < len)
            str += this.charsArray[i][0];
    }
    return str;
}
// 截取字符串（从start字节截取length个字节）
String.prototype.subCHStr = function(start, length){
    return this.subCHString(start, start + length);
}
	
// 动态加载js与css文件
// loadjscssfile("do.js","js");//例
// loadjscssfile("test.css","css");//例

function loadjscssfile(filename,filetype){

    if(filetype == "js"){
        var fileref = document.createElement('script');
        fileref.setAttribute("type","text/javascript");
        fileref.setAttribute("src",filename);
    }else if(filetype == "css"){
    
        var fileref = document.createElement('link');
        fileref.setAttribute("rel","stylesheet");
        fileref.setAttribute("type","text/css");
        fileref.setAttribute("href",filename);
    }
   if(typeof fileref != "undefined"){
        document.getElementsByTagName("head")[0].appendChild(fileref);
    }
    
}



$(document).ajaxSend(function() {
	//$(this).loading("open");
});
$(document).ajaxComplete(function() {
	//$(this).loading("close");
});


/**
*将文本绑定为数字格式
*val :不为数字时，默认的数字
*/
$.fn.numeral = function(val) {
    $(this).css("ime-mode", "disabled");
    this.bind("keypress",function() {
//        if (window.event.keyCode == 46) {
//            if (this.value.indexOf(".") != -1) {
//                return false;
//            }
//        } else {
//            return event.keyCode >= 46 && event.keyCode <= 57;
//        }
    	if(isNaN(this.value)){
    		this.value=val;
    	}
    });
    this.bind("blur", function() {    	
        if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
            this.value = this.value.substr(0, this.value.length - 1);
        } else if (isNaN(this.value)) {
            this.value = val;
        } else if (this.value == "" || this.value == null) {        	
            this.value = val;
        }
    });
    this.bind("paste", function() {
        var s = clipboardData.getData('text');
        if (!/^[+-]?[0-9.]*$/.test(s));
        	value = s.replace(/^0*/, '');
        return false;
    });
    this.bind("dragenter", function() {
        return false;
    });
    this.bind("keyup", function() {
    	if (/(^0+)/.test(this.value)) { // /(^0+)/
    		this.value = this.value.replace(/^0*/, ''); // /^0*/
        }
    });
//    this.bind("keydown", function() {
//    	if (/(^0+)/.test(event.keyCode)) {
//    		this.value = this.value.replace(/^0*/, '');
//        }
//    });
};


$.fn.numeral2 = function() {     
    $(this).css("ime-mode", "disabled");     
    this.bind("keypress",function(e) {     
    var code = (e.keyCode ? e.keyCode : e.which);  //兼容火狐 IE      
        if(!$.browser.msie&&(e.keyCode==0x8))  //火狐下不能使用退格键     
        {     
             return ;     
            }     
            return code >= 48 && code<= 57;     
    });     
    this.bind("blur", function() {     
        if (this.value.lastIndexOf(".") == (this.value.length - 1)) {     
            this.value = this.value.substr(0, this.value.length - 1);     
        } else if (isNaN(this.value)) {     
            this.value = "";     
        }     
    });     
    this.bind("paste", function() {     
        var s = clipboardData.getData('text');     
        if (!/\D/.test(s));     
        value = s.replace(/^0*/, '');     
        return false;     
    });     
    this.bind("dragenter", function() {     
        return false;     
    });     
    this.bind("keyup", function() {     
    if (/(^0+)/.test(this.value)) {     
        this.value = this.value.replace(/^0*/, '');     
        }     
    });     
}; 

