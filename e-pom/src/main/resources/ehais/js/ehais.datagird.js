/**
 * 
 */


$(function(){
	
	$.fn.eHaisDatagrid=function(data){
		var _this = this;
		this.page = (typeof(data.page) == "undefined")?1:data.page;
		this.len = (typeof(data.len) == "undefined")?10:data.len;
		this.rollPage = (typeof(data.rollPage) == "undefined")?10:data.rollPage;
		
		this.init = function(){
			$(this).children("thead").remove();
			$(this).prepend("<thead><tr></tr></thead>");
			$.each(data.columns,function(index,val){
				$(_this).children("thead").children("tr").append("<th>"+val.title+"</th>");
			});
			
			$(this).children("tbody").remove();
			$(this).prepend("<tbody></tbody>");
			
			$(this).children("tfoot").remove();
			$(this).prepend("<tfoot></tfoot>");
			$(this).children("tfoot").prepend("<tr class='dataTables_paginate fg-buttonset ui-buttonset fg-buttonset-multi ui-buttonset-multi paging_full_numbers'><th colspan='"+data.columns.length+"'></th></tr>");
			
		},
		this.columns = function (subdata){
			
		},
		this.setDataFunc = function(func){//设置数据读取函数并执行一次
			_this.loadDataFunc = func;
			_this.loadDataFunc();
		},
		this.setEventFunc = function(func){//设置数据读取函数但不需要读取函数
			_this.loadDataFunc = func;
		},
		this.loadData = function(subdata){
			console.log("数据值："+JSON.stringify(subdata));
			var list = subdata.rows;
			if(typeof(list) == "undefined")list = new Array();
			$(this).children("tbody").children("tr").remove();
			$.each(list,function(index,val){
				var $tr = $("<tr>",{"class":""});
				$.each(data.columns,function(index_c,val_c){
					$tr.append("<td>"+((typeof(val_c.formatter) == "undefined")?val[val_c.field]:val_c.formatter(val[val_c.field],val,index))+"</td>");
				});				
				$(_this).children("tbody").append($tr);				
			});
			
			//清空
			$(_this).children("tfoot").children("tr").children("th").html("");
			
			this.totalPages = Math.ceil(subdata.total / this.len);
			var $pager = $("<span>");
			$pager.append(this.page + "/" + this.totalPages + "&nbsp;&nbsp;&nbsp;总共："+subdata.total+"条记录&nbsp;&nbsp;&nbsp;");
			$(_this).children("tfoot").children("tr").children("th").append($pager);
			
			this.nowCoolPage    =   Math.ceil(this.page/this.rollPage);
			this.linkPage = "";
			for(var i=1;i<=this.rollPage;i++){
	            this.npage       =   (this.nowCoolPage-1) * this.rollPage + i;
	            if(this.npage != this.page){
	                if(this.npage<=this.totalPages){
	                    this.linkPage += "<a class='choose fg-button ui-button ui-state-default'>"+this.npage+"</a>";
	                }else{
	                    break;
	                }
	            }else{
	                if(this.totalPages != 1){
	                    this.linkPage += "<a class='fg-button ui-button ui-state-default ui-state-disabled'>"+this.npage+"</a>";
	                }
	            }
	        }
			
			var $pagerSpan = $("<span>");
			$pagerSpan.append(this.linkPage);
			
			var $a_first = $("<a>",{"class":"first ui-corner-tl ui-corner-bl fg-button ui-button ui-state-default "});
			var $a_previous = $("<a>",{"class":"previous fg-button ui-button ui-state-default "});
			var $a_next = $("<a>",{"class":"next fg-button ui-button ui-state-default"});
			var $a_last = $("<a>",{"class":"last ui-corner-tr ui-corner-br fg-button ui-button ui-state-default"});
			$a_first.html("首页");$a_previous.html("上一页");$a_next.html("下一页");$a_last.html("末页");
			$(_this).children("tfoot").children("tr").children("th").append($a_first);
			$(_this).children("tfoot").children("tr").children("th").append($a_previous);
			$(_this).children("tfoot").children("tr").children("th").append($pagerSpan);
			$(_this).children("tfoot").children("tr").children("th").append($a_next);
			$(_this).children("tfoot").children("tr").children("th").append($a_last);
			
			$(_this).children("tfoot").children("tr").children("th").children("a").unbind();
			$(_this).children("tfoot").children("tr").children("th").children("span").children("a").unbind();
			$(_this).children("tfoot").children("tr").children("th").children("a").click(function(){
				if($(this).hasClass("ui-state-disabled"))return ;

				if($(this).hasClass("first")){
					if(_this.page = 1 || _this.totalPages == 1)return ;
					_this.page = 1;
				}else if($(this).hasClass("previous")){
					if(_this.page <= 1)return;
					_this.page --;
				}else if($(this).hasClass("next")){
					if(_this.page >= _this.totalPages)return;
					_this.page ++;
				}else if($(this).hasClass("last")){
					if(_this.totalPages == 1 || _this.page == _this.totalPages)return ;
					_this.page = _this.totalPages;
				}else{
					_this.page = 1;
				}
				_this.loadDataFunc();
			});
			$(_this).children("tfoot").children("tr").children("th").children("span").children("a").click(function(){
				if($(this).hasClass("ui-state-disabled"))return ;
				
				_this.page = $(this).html();
				_this.loadDataFunc();
			});
		},
		this.show = function(subdata){
			
			
		}
		this.init();
		return this;
	};
	
});