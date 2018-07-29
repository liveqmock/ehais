
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
	timeout:120000,
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
	$(".header .nav #back").click(function(){window.parent.history.back();});
});


function initImageInput(id){
	var initialPreview = [];
	if($("#"+id).val()!="")initialPreview[0] = $("#"+id).val();
	$("#"+id+"_").fileinput({
		language : "zh",//设置语言
		uploadUrl: "/upload/image.upd",
	    allowedFileExtensions: ["jpg", "png", "gif"],
	    dropZoneTitle:"请只上传一个文件",
	    isUploadable : true,//
	    //resizeImage: true,
	    overwriteInitial: false,
	    initialPreviewAsData: true,
	    initialPreviewFileType: 'image',
	    initialPreview: initialPreview,
	}).on('filepreupload', function() {
	}).on('fileuploaded', function(event, data) {
		$("#"+id).val(data.response.msg);
	});
}


function initGalleryInput(id){
	var initialPreview = [];
	
	$("#gallery_"+id+" input[type='hidden']").each(function(index,ele){
		if($.trim($(ele).val()).length > 0)initialPreview.push($(ele).val());
	})
	
	
	$("#"+id+"_").fileinput({
		language : "zh",//设置语言
		uploadUrl: "/upload/image.upd",
	    allowedFileExtensions: ["jpg", "png", "gif"],
	    dropZoneTitle:"图片相册",
	    isUploadable : true,//
	    //resizeImage: true,
	    overwriteInitial: false,
	    initialPreviewAsData: true,
	    initialPreviewFileType: 'image',
	    initialPreview: initialPreview,
	    uploadExtraData: function(previewId, index) {   //额外参数的关键点
            var param = {};
            param.title = "hello";
            console.log(param);
            return param;
        }
	}).on('filepreupload', function() {
	}).on('fileuploaded', function(event, data, previewId, index) {		
		var maxNo = $("#gallery_"+id+" input[type='hidden']").last().attr("no");
		if(maxNo == null)maxNo = -1;
		maxNo = parseInt(maxNo) + 1;
		$("#gallery_"+id).append("<input id=\"gallery_pic_"+id+"_"+maxNo+"\" name=\""+id+"\" value=\""+data.response.msg+"\" no=\""+maxNo+"\" type=\"hidden\">");
	}).on("filedelete",function(event, id) {
		alert("filedelete"+JSON.stringify(id));
	}).on("filesuccessremove",function(event, id) {
		alert("filesuccessremove"+JSON.stringify(event));
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

String.prototype.formatDate = function(format) {
	if(this == null || this == "" || this == "null") return "";
	var date =  new Date(Date.parse(this));
	return date.format(format);
}

function isBlank(v){
	if(v == null || v == "null" || v == "" || v == undefined || v == "undefined" || v.length == 0)return true;
	return false;
}
