
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

//全局设置
$.ajaxSetup({
	type:"post",
	dataType:"json",
	layerIndex:-1,
	timeout:3000,
	beforeSend: function () {
	    //ajax请求之前
		this.layerIndex = layer.load(1, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
			});
		
	},
	complete: function () {
	    //ajax请求完成，不管成功失败
	    layer.close(this.layerIndex);
	},
	error: function () {
    	//ajax请求失败        
        layer.open({
			  type: 1,
			  title: '温馨提示',
			  shadeClose: true,
			  shade: 0.8,
			  content: '部分数据加载失败，可能会导致页面显示异常，请刷新后重试' //iframe的url
			});
        
    }
});

$(function(){
	$(".header .nav #back").click(function(){window.history.back();});
});


function initFileInput(id,path){
	$("#"+id+"_").fileinput({
		language : "zh",//设置语言
	    uploadUrl: "/upload/image.upd",
	    allowedFileExtensions: ["jpg", "png", "gif"],
	    dropZoneTitle:"拖拽文件到这里 &hellip;<br>支持单文件上传",
	    resizeImage: true,
	    overwriteInitial: false,
	    initialPreviewAsData: true,
	    initialPreviewFileType: 'image',
	    initialPreview: path
	}).on('filepreupload', function() {
	}).on('fileuploaded', function(event, data) {
		$("#"+id).val(data.msg);
	});
}

Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}


