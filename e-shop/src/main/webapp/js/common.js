
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

//全局设置
$.ajaxSetup({
	layerIndex:-1,
	timeout:30000,
	type:"post",
	dataType:"json",
	beforeSend: function () {
	    //ajax请求之前
	    elay.loading();
	},
	complete: function () {
	    //ajax请求完成，不管成功失败
		elay.hide();
	},
	error: function () {
    	//ajax请求失败
        elay.open({
		  content: '部分数据加载失败，可能会导致页面显示异常，请刷新后重试',
		  btn: '我知道了'
		});


    }
});