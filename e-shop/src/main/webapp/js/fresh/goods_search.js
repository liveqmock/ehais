var culw = 0;
var jcate;
var alist;

var goodsData = {0:[]};
var pageData = {0:1};
var scroll_y = {0:0};//记录前位置
var jroll = {};
var cat_id = 0;
var loaded;
var sort = null;
var adsc = null;
var keyword = sessionStorage.getItem("keyword");
var l_s = "search";

$(function(){
	$("#keyword").val(keyword);
	wx_config(signature);
	
	jrollCat(cat_id);
	
	initData();
	
	if(goodsData[cat_id].length == 0){
		goods_list_cat();
	}else{
		goodsItem(goodsData[cat_id],cat_id);
	}
	
});


