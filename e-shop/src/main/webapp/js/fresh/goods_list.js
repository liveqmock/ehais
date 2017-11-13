
var culw = 0;
var jcate;
var alist;

var goodsData = {};
var pageData = {};
var scroll_y = {};//记录前位置
var jroll = {};
var cat_id = catId;
var loaded;
var sort = null;
var adsc = null;
var keyword = null;

$(function(){
	wx_config(signature);
	
	$.each(cateList,function(k,v){
		if(parseInt(v.catId) == parseInt(catId)){
			$("#cate ul").prepend("<li class='active' cat_id='"+v.catId+"'>"+v.catName+"</li>");
		}else{
			$("#cate ul").append("<li cat_id='"+v.catId+"'>"+v.catName+"</li>");
		}
		jrollCat(v.catId);
	});
	
	$("#cate ul li").each(function() {
		culw += parseFloat($(this).outerWidth()) + 1;
	});
	
	$("#cate ul").outerWidth(culw);
	
	jcate = new JRoll("#cate", {scrollX : true ,scrollY : false , scrollBarX:false});

	$("#clist,#clist>div").height(parseFloat($(window).height()) - parseFloat($("header").height()) * 2);
	
	if(sessionStorage.getItem("goodsData") != null){
		goodsData = JSON.parse(sessionStorage.getItem("goodsData"));
	}
	if(sessionStorage.getItem("pageData") != null){
		pageData = JSON.parse(sessionStorage.getItem("pageData"));
	}
	if(sessionStorage.getItem("scroll_y") != null){
		scroll_y = JSON.parse(sessionStorage.getItem("scroll_y"));
	}
	
	
	loaded = "";
	
	if(sessionStorage.getItem("cat_id")!=null && parseInt(sessionStorage.getItem("cat_id")) > 0){
		cat_id = sessionStorage.getItem("cat_id");
		if(goodsData[cat_id] == null){
			goodsData[cat_id] = [];
			sessionStorage.setItem("goodsData",JSON.stringify(goodsData));
		}
		$("#cate ul li,#clist >div").removeClass("active");
		$("#cate ul li").each(function(){
			if(parseInt($(this).attr("cat_id")) == parseInt(cat_id)){
				$(this).addClass("active");
			}
		});
		$("#c"+cat_id).addClass("active");
		
	}else{
		sessionStorage.setItem("cat_id",cat_id);
		if(goodsData[cat_id].length == 0){
			goods_list_cat();
		}
	}
	
	if(goodsData != null){//加载历史的数据
		$.each(goodsData,function(k,v){
			if(v.length > 0){
				goodsItem(v,k);
			}
		});
	}
	
	
	
	
	$("#cate ul li").click(function(){
		if(parseInt(cat_id) != parseInt($(this).attr("cat_id"))){
			cat_id = $(this).attr("cat_id");
			sessionStorage.setItem("cat_id",cat_id);
			$("#cate ul li").removeClass("active");
			$(this).addClass("active");			
			$("#clist >div").removeClass("active");
			$("#c"+cat_id).addClass("active");
			
			sort = null;
			adsc = null;
			
			if(pageData[cat_id] == null || parseInt(pageData[cat_id]) == 1){
				console.log(cat_id+" page is empty");
				pageData[cat_id] = 1;
				sessionStorage.setItem("pageData",JSON.stringify(pageData));
				goodsData[cat_id] = [];
				sessionStorage.setItem("goodsData",JSON.stringify(goodsData));
				goods_list_cat();
			}
			jroll[cat_id].refresh();
			if(scroll_y[cat_id]!=null){
				jroll[cat_id].scrollTo(0, scroll_y[cat_id], 0);
			}
		}
	});
	
	//筛选
	$(".icon-shaixuan").click(function(){
		$(".category").removeClass("active");
		$(".filtrate").addClass("active");
	});
	
	//切换分类
	$(".icon-qiehuan").click(function(){
		$(".filtrate").removeClass("active");
		$(".category").addClass("active");
	});
	
	$("#filter li").click(function(){
		if($(this).hasClass("active")){
			$(this).children("div").children("i").toggleClass("active");
		}else{
			$("#filter li, #filter li div i").removeClass("active");
			$(this).addClass("active");			
			$(this).children("div").children("i:first").addClass("active");
		}
		
		sort = $(this).attr("f");
		adsc = $(this).children("div").children("i.active").attr("a");
		
		resetSearch();
		
	});

});

function resetSearch(){
	pageData[cat_id] = 1;
	sessionStorage.setItem("pageData",JSON.stringify(pageData));
	scroll_y[cat_id] = 0;
	sessionStorage.setItem("scroll_y",JSON.stringify(scroll_y));
	
	goods_list_cat();
}


function jrollCat(_cat_Id){
	if(_cat_Id==catId){
		$("#clist").prepend("<div class='active' id='c"+_cat_Id+"' ><ul id='a"+_cat_Id+"' class='r'></ul></div>");
	}else{
		$("#clist").append("<div id='c"+_cat_Id+"' ><ul id='a"+_cat_Id+"' class='r'></ul></div>");
	}
	$("#c"+_cat_Id).height($("#clist").height());
	
	if(sessionStorage.getItem("goodsData") == null){
		goodsData[_cat_Id] = [];
		sessionStorage.setItem("goodsData",JSON.stringify(goodsData));
	}
	
	if(sessionStorage.getItem("pageData") == null){
		pageData[_cat_Id] = 1;
		sessionStorage.setItem("pageData",JSON.stringify(pageData));
	}
	
	if(sessionStorage.getItem("scroll_y") == null){
		scroll_y[_cat_Id] = 0;
		sessionStorage.setItem("scroll_y",JSON.stringify(scroll_y));
	}
	
	jroll[_cat_Id] = new JRoll("#c"+_cat_Id, {scrollBarY:false});
	jroll[_cat_Id].on("touchEnd", function() {
		if (this.y >= 44) {
			if(loaded=="")loaded="pull";
		}else if (this.y < this.maxScrollY - 10){
			if(loaded=="")loaded="up";
		}
	});
	jroll[_cat_Id].on("scrollEnd", function() {
		scroll_y[_cat_Id] = this.y;
		sessionStorage.setItem("scroll_y",JSON.stringify(scroll_y));
		if(loaded == "pull"){
			pageData[_cat_Id] = 1;
			goods_list_cat();
		}else if(loaded == "up"){
			goods_list_cat();
		}
	});
	
	if(goodsData[_cat_Id] != null && goodsData[_cat_Id].length > 0)goodsItem(goodsData[_cat_Id],_cat_Id);
	
	
}

function goodsItem(_goods_Data,_cat_Id){
	var t = "";	
	$.each(_goods_Data,function(k,v){
				
		$("#a"+_cat_Id).append("<li h='"+v.goodsUrl+"'>"+
							"<div class='img'><img src='"+(v.goodsThumb==null || v.goodsThumb=="" ? defaultimg : v.goodsThumb)+"'></div>"+
							"<div class='i'>"+
								"<div class='t'>"+v.goodsName+"</div>"+
								"<div class='l'>"+v.actDesc+"</div>"+
								"<div class='d'>"+
									"<div>￥</div>"+
									"<div>"+(v.shopPrice / 100).toFixed(2)+"</div>"+
									"<div>"+(v.marketPrice /  100).toFixed(2)+"</div>"+
									"<div class='iconfont icon-gouwuche1'></div>"+
								"</div>"+
							"</div>"+
						"</li>")
	});
	
	jroll[_cat_Id].refresh();
	
	$("#clist>div>ul>li").unbind();
	$("#clist>div>ul>li").click(function(){
		if($(this).attr("h") != null && $(this).attr("h") != "")window.location.href = "w_goods_detail!"+$(this).attr("h");
	});
	
	jroll[_cat_Id].refresh();
	imgRefresh(_cat_Id);
	if(scroll_y[_cat_Id]!=null){
		jroll[_cat_Id].scrollTo(0, scroll_y[_cat_Id], 0);
	}
	
}

function imgRefresh(_cat_Id){
	$("img").unbind();
	var num = $("img").length;
	$("img").load(function() {
		num--;
		if (num > 0) {
			return;
		}
		jroll[_cat_Id].refresh(); 
	}).error(function(){
		$(this).attr("src",defaultimg);
		jroll[_cat_Id].refresh();
	});
	num = null;
}

function goods_list_cat(){
	
	$.ajax({type:"post",
		dataType:"json",
		url : "goods_list_cat_id!"+aid,data:{catId:cat_id,sort:sort,adsc:adsc,keyword:keyword,page:pageData[cat_id],rows:10},
		success : function(result){
			loaded = "";
			if(parseInt(pageData[cat_id]) == 1){
				goodsData[cat_id] = [];				
				$("#a"+cat_id+" >li").remove();
			}
			
			if(result.rows!=null && result.rows.length > 0){
				pageData[cat_id] = parseInt(pageData[cat_id]) + 1;
				sessionStorage.setItem("pageData",JSON.stringify(pageData));
			}
			$.each(result.rows,function(k,v){
				goodsData[cat_id].push(v);
			});
			
			sessionStorage.setItem("goodsData",JSON.stringify(goodsData));
			goodsItem(result.rows,cat_id);
			
		}
	});
}
