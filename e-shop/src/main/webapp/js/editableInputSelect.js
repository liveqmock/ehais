/**
 * @author tyler ehais <lgj628@126.com>
 * version: 1.1.1
 * http://ehais.com
 */
(function($){
	var timer = null;
	
	$.fn.editableInputSelect = function(options){
		
		var editableInputSelect = new EditableInputSelect();
		editableInputSelect.id=$(this).attr("id");
		editableInputSelect.options = (options == "undefined" || options == null) ? {} : options;
		
		editableInputSelect.init(this);
		
		return editableInputSelect;
		
	}
	
	
	
	var EditableInputSelect = function(){
		
	}
	EditableInputSelect.prototype = {
		id:null,
		options:null,
		init:function(that){
			var eThat = this;
			$(that).attr("id","editable-"+this.id);
			var type = $(that).attr("type");
			var url = $(that).attr("url");
			var value = $(that).attr("value");
			var code = $(that).attr("code");
			var name = $(that).attr("name");
			if(this.options.type == null || this.options.type == "undefined") this.options.type = type;
			if(this.options.url == null || this.options.url == "undefined") this.options.url = url;
			if(value == null || value == "undefined") value = "";
			if(code == null || code == "undefined") code = "";
			if(name == null || name == "undefined") name = "";
			
			
			
			var html =  '<div id="controls-'+this.id+'" class="controls">'+
						'	<div id="input-group-'+this.id+'" class="input-group w100 lucency">'+
						'		<input type="text" class="form-control" id="name-'+this.id+'" value="'+name+'" autocomplete="off">'+
						'		<div id="refresh-'+this.id+'" class="input-group-addon glyphicon glyphicon-refresh hide"></div>'+
						'		<div id="plus-'+this.id+'" class="input-group-addon glyphicon glyphicon-plus"></div>'+
						'	</div>'+
						'	<ul id="search-dropdown-'+this.id+'" class="dropdown-menu"></ul>'+
						'</div>'+
						'<input type="hidden" id="'+this.id+'" value="'+value+'">'+
						'<input type="hidden" id="code-'+this.id+'" value="'+code+'">';
			$(that).html(html);
			
			
			$("#name-"+$(this).attr("id"))
			.focus(function(event){eThat.focus(event);})
			.blur(function(event){eThat.blur(event);})
			.keypress(function(event){eThat.keypress(event);})
			.keydown(function(event){eThat.keydown(event);})
			.keyup(function(event){eThat.keyup(event);});
			
			$("#plus-"+$(this).attr("id")).click(function(){
				if(eThat.options.plus != "undefined" && eThat.options.plus != null){
					$(".dialog_bg,.dialog.glyphicon.glyphicon-remove,#"+eThat.options.plus).attr("control",eThat.id).addClass("open");
					$("#"+eThat.options.plus+" input[type='text']:first").focus();
					eThat.hide_dropdown_menu();
				}
			});
			
			set_tab_index();//src=js/document_ctrl_key.js
		},focus:function(event){
			if(this.options.plus != "undefined" && this.options.plus != null){
				$("#input-group-"+this.id).removeClass("lucency");
			}
		},blur:function(event){
			var eThat = this;
			timer = setTimeout(function(){
				$("#input-group-"+eThat.id).addClass("lucency");
				eThat.hide_dropdown_menu();
			},800);
		},keypress:function(event){
			clearTimeout(timer);
		},keydown:function(event){
			clearTimeout(timer);
		},keyup:function(event){
//			console.log(event.keyCode+"keyup:");

			event = window.event || event;
			var keycode = event.keyCode || event.which;
			if(!event.ctrlKey && keycode == 17)return ;//ctrl弹起，事件取消
			if((event.ctrlKey && keycode == 187) || (event.ctrlKey && keycode == 107)) {
				$("#plus-"+this.id).click();
				keycode = null;
				return ;
			}
			
			$("#refresh-"+this.id).addClass("hide");
			var eThat = this;
			
			if((keycode > 48 && keycode <= 57) || (keycode > 96 && keycode <= 105)){
				var lenght = $("#search-dropdown-"+this.id+" li").length ;
				var index = keycode - ((keycode > 48 && keycode <= 57) ? 48 : 96);
				if(index > 0 && index <= lenght){
					$("#search-dropdown-"+this.id+" li").removeClass("active");
					$("#search-dropdown-"+this.id+" li").eq(index-1).addClass("active");
					this.ajax_function();					
					index = null;lenght=null;
					return ;
				}
				
			}
			
			
			switch(keycode){
				case 27://esc
					{
						this.hide_dropdown_menu();
					}
					break;
				case 13://回车
					{
						var index = $("#search-dropdown-"+this.id+" li.active").index();
						if(index >= 0)this.active_li(index);
						index = null;
						
						var tindex = parseInt($("#name-"+this.id).attr("tabindex"));
						if(tindex < inputs.length - 2){
							$("#"+inputs[(tindex+1)]).focus();
						}else{
							$("#"+inputs[0]).focus();
						}
						tindex = null;
					}
					break;
				case 40://向下箭头
					{
						var index = $("#search-dropdown-"+this.id+" li.active").index();
						var lenght = $("#search-dropdown-"+this.id+" li").length ;
						if(index >= 0 && index < lenght - 1 ){
							$("#search-dropdown-"+this.id+" li").removeClass("active");
							$("#search-dropdown-"+this.id+" li").eq(index + 1).addClass("active");
						}else{
							$("#search-dropdown-"+this.id+" li").removeClass("active");
							$("#search-dropdown-"+this.id+" li:first").addClass("active");
						}
						index = null;lenght=null;
					}
					break;
				case 38://向上箭头
					{
						var index = $("#search-dropdown-"+this.id+" li.active").index();
						var lenght = $("#search-dropdown-"+this.id+" li").length ;
						if(index > 0 && index <= lenght - 1 ){
							$("#search-dropdown-"+this.id+" li").removeClass("active");
							$("#search-dropdown-"+this.id+" li").eq(index - 1).addClass("active");
						}else{
							$("#search-dropdown-"+this.id+" li").removeClass("active");
							$("#search-dropdown-"+this.id+" li:last").addClass("active");
						}
						index = null;lenght=null;
						var nid = document.getElementById("name-"+this.id);
						nid.setSelectionRange(nid.value.length,nid.value.length);
					}
					break;
				
				default:
					{
						timer = setTimeout(function(){
							if($.trim($("#name-"+eThat.id).val())=="" || $("#name-"+eThat.id).val() == null)return ;
							
							if(eThat.ajax_function != null && typeof(eThat.ajax_function) === "function"){
								eThat.ajax_function();
							}
							
						},800);
					}
				
			}
			
			
			
		},ajax_function:function(){//请求数据
			var eThat = this;
			if($("#name-"+eThat.id).val() == null || $.trim($("#name-"+eThat.id).val()) == "")return ;
			$.ajax({
				url:this.options.url,
				type:this.options.type,
				dataType:"json",
				data:{"code":$.trim($("#name-"+eThat.id).val())},
				beforeSend: function(){
					$("#refresh-"+eThat.id).removeClass("hide");
					$("#plus-"+eThat.id).addClass("hide");
			    },
			    complete: function(){
			    	$("#refresh-"+eThat.id).addClass("hide");
			    	$("#plus-"+eThat.id).removeClass("hide");
			    },
				success:function(data){
					var index = $("#search-dropdown-"+eThat.id+" li.active").index();
					if(index >= 0 && data.rows.length <= 1){//判断是否选中的状态
						eThat.active_li(index);
						return ;
					}
					
					eThat.show_dropdown_menu();//显示下拉框
					
					$("#search-dropdown-"+eThat.id+" li").unbind();
					$("#search-dropdown-"+eThat.id+" li").remove();
					if(data.rows.length > 0){
						$.each(data.rows,function(k,v){
							$("#search-dropdown-"+eThat.id).append("<li value='"+v.id+"' code='"+v.code+"' name='"+v.name+"'><a href='javascript:;'><span>"+v.code+"</span>"+v.name+"</a></li>");
						});
						$("#search-dropdown-"+eThat.id+" li").click(function(){
							$(this).addClass("active");
							eThat.active_li($(this).index());
						});
					}else{
						$("#search-dropdown-"+eThat.id).append("<li>暂无搜索数据</li>");
					}
				}
			})
		},active_li:function(index){//回车或用数字选中当前项
			var ele = $("#search-dropdown-"+this.id+" li.active");
			$("#"+this.id).val($(ele).attr("value"));	
			
			$("#name-"+this.id).val($(ele).attr("name")).focus();
			$("#code-"+this.id).val($(ele).attr("code"));
			
			$("#search-dropdown-"+this.id+" li").unbind();
			$("#search-dropdown-"+this.id+" li").remove();			
			this.hide_dropdown_menu();
			
			//回调的数据
			var eData = {"id":$(ele).attr("value"),"code":$(ele).attr("code"),"name":$(ele).attr("name")};
			this.success(eData);
			
			ele = null;
			
		},show_dropdown_menu:function(){//显示下拉选择项
			$("#controls-"+this.id).addClass("open");
		},hide_dropdown_menu:function(){//隐藏下拉选择项
			$("#controls-"+this.id).removeClass("open");
		},success:function(data){
			if(typeof(this.options.success) == "function"){
				this.options.success(data);
			}
		}
		
		
	};
})(jQuery);
