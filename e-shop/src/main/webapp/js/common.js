
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
	    this.layerIndex = layer.load({
				type: 1,
				shade:[0.1,"#fff"]
			});
	},
	complete: function () {
	    //ajax请求完成，不管成功失败
	    layer.close(this.layerIndex);
	},
	error: function () {
    	//ajax请求失败
        layer.open({
		  content: '部分数据加载失败，可能会导致页面显示异常，请刷新后重试',
		  btn: '我知道了',
		  shadeClose: false,
		  yes: function(){
		    layer.open({
		      content: '稍后请重试~！'
		  ,time: 2
		  ,skin: 'msg'
		    });
		  }
		});


    }
});