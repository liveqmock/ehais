! function(e) {
	"use strict";
	var that = this;
	e.elay = {
		open: function(e) {
			if(e==null)e={};
			if(e.content == null)e.content = "温馨提示";
			if(e.btn == null)e.btn = "知道了"; 
			layview();
			if($(".e-lay-view .alert").length == 0){
				$(".e-lay-view").append("<div class=\"alert\">"+
					"<div class=\"msg\">"+e.content+"</div>"+
					"<div class=\"btn\" >"+e.btn+"</div>"+
				"</div>");
				$(".e-lay-view > .bg").addClass("b");
				$(".e-lay-view .alert .btn").unbind();
				$(".e-lay-view .alert .btn").click(function(){
					if(typeof(eval(e.yes))=="function"){e.yes();}
					$(".e-lay-view .alert").remove();
					layremove();
				});
			}
		},
		toast:function(e){
			if(e==null)e={};
			if(e.content == null)e.content = "温馨提示";
			if(e.time == null)e.time = 3; 
			layview();
			if($(".e-lay-view .toast").length == 0) {
				$(".e-lay-view").append("<div class=\"toast\">"+
					"<div class=\"msg\">"+e.content+"</div>"+
				"</div>");
				$(".e-lay-view .toast").css({left:$(".e-lay-view .toast").width() / 2});
				setTimeout(function(){
					$(".e-lay-view .toast").remove();
					layremove();
				},parseInt(e.time) * 1000)
			}
		},loading: function(e){
			if(e==null)e={};
			if(e.content == null)e.content = "请稍等"; 
			layview();
			if($(".e-lay-view .loading").length == 0) $(".e-lay-view").append("<div class=\"loading\" q=\"1\">"+
					"<span class=\"fa fa-spinner\"></span>"+
					"<div class=\"elay-loading\">"+e.content+"</div>"+
				"</div>");
		},hide:function(){
			$(".e-lay-view .loading").remove();
			layremove();
		},confirm:function(e){
			if(e==null)e={};
			if(e.content == null)e.content = "温馨提示";			
			layview();
			if($(".e-lay-view .confirm").length == 0) $(".e-lay-view").append("<div class=\"confirm\">"+
				"<div class=\"msg\">"+e.content+"</div>"+
				"<div class=\"b\">"+
					"<button class='sure'>"+e.btn[0]+"</button>"+
					"<button class='cancel'>"+e.btn[1]+"</button>"+
				"</div>"+
			"</div>");
			$(".e-lay-view > .bg").addClass("b");
			$(".e-lay-view .confirm .b .sure").click(function(){cancel();e.sure();});
			$(".e-lay-view .confirm .b .cancel").click(function(){cancel();});
		}
	};
	var layview = function(){
		if($(".e-lay-view").length == 0) $(document.body).append("<div class='e-lay-view'><div class='bg'></div></div>");
	};
	var layremove = function(){if($(".e-lay-view > div").length == 1)$(".e-lay-view").remove();};
	var cancel = function(){
		$(".e-lay-view .confirm .b .sure").unbind();
		$(".e-lay-view .confirm .b .cancel").unbind();
		$(".e-lay-view .confirm").remove();
		layremove();
	};
}(window);


