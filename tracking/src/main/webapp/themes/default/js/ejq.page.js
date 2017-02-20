
(function($) {
    "use strict";
    
    var EJQ_PAGE = function() {
    	return this;
    };
    EJQ_PAGE.prototype = {
    		pagerId:"pager",//显示分页的元素
    		groupId:"list",
    		page : 1,//分页默认值
    		len : 15,//
    		maxShowPageCount:10,
    		type:"url",//分页方式url,ajax
    		returnObject:null,    		
    		group:"ul",
    		item:"li",    		
    		errMsg:'暂无数据',
    		errItem:function(){return '<'+this.item+' class="ejq-list-group-item-err">'+this.errMsg+'</'+this.item+'>';},//错误提示
    		formatItem:function(val){return null;},
    		complete:function(val){return null;},
    		clickPager:function(val){return null;},
    		load:function(obj){
    			var that = this;
    			if(!this.checkempty(obj.pagerId))return this;
    			if(!this.checkempty(obj.groupId))return this;
    			this.pagerId = obj.pagerId;
    			this.groupId = obj.groupId;
    			this.returnObject = obj.returnObject;
    			if(this.checkempty(obj.maxShowPageCount)) this.maxShowPageCount = obj.maxShowPageCount;
    			//返回错误的情况
    			if(this.returnObject.code != 1){
    				if(this.returnObject.msg != undefined && this.returnObject.msg != null){
    					this.errMsg = this.returnObject.msg
    				}
    				$("#"+obj.groupId).append(this.errItem());
    				return that;
    			}
    			
    			var rows = this.returnObject.rows;
    			if(rows == undefined)return this;
    			this.page = this.returnObject.currentPage;
    			this.len = this.returnObject.pageSize;
    			
    			//pc版的就是所有都清除
//    			if(this.page == 1) 
    			$("#"+this.groupId).children(this.item).remove();
    			if(this.page == 1 && rows.length == 0){
    				$("#"+obj.groupId).append('<'+this.item+' class="ejq-list-group-item-empty">暂无数据<'+this.item+'>');
    			}
    			
    			$.each(rows,function(index,val){
    				$("#"+obj.groupId).append(obj.formatItem(val));
    			});
    			
    			this.formatPager(obj);
    			
    			$('html,body').animate({scrollTop: '0px'}, 800);
    			if(obj.complete!=undefined && typeof(obj.complete) == "function")obj.complete();
    			
    			return that;
    		},checkempty:function(o){
    			if(o == undefined || o == null || o == "")return false;
    			return true;
    		},formatPager:function(obj){
    			var that = this;
    			var ReturnObject = obj.returnObject;
    			var currentPage = parseInt(ReturnObject.currentPage);
    			var pageSize = parseInt(ReturnObject.pageSize);
    			var total = parseInt(ReturnObject.total);
    			var pageCount = parseInt(ReturnObject.pageCount);
    			var parameter = "" ;
//    			var baseUrl = ReturnObject.action;
//    			if(baseUrl.indexOf("?")<0){
//    				baseUrl+="?";
//    			}
    			var baseUrl = "";
    			baseUrl += "len="+pageSize;
    			
    			if(ReturnObject.parameterMap != undefined && ReturnObject.parameterMap != null && ReturnObject.parameterMap != ""){
    				$.each(ReturnObject.parameterMap,function(k,v){
    					parameter += "&"+k+"="+v;
    				});
    			}
    			
    			var firstPageUrl = baseUrl + "&page=1" + parameter ;
    			var lastPageUrl = baseUrl + "&page=" + pageCount + parameter ;
    			var prePageUrl = baseUrl + "&page=" + (currentPage - 1) + parameter ;
    			var nextPageUrl = baseUrl + "&page=" + (currentPage + 1) + parameter ;
    			
    			this.maxShowPageCount
    			var segment = parseInt(((currentPage - 1) / this.maxShowPageCount) + 1) ;
    			var segmentCount = parseInt(((pageCount - 1) / this.maxShowPageCount) + 1) ;
    			var startPageNumber = parseInt((segment - 1) * this.maxShowPageCount + 1) ;
    			var endPageNumber = parseInt(segment * this.maxShowPageCount);
    			if (parseInt(startPageNumber) < 1){
    				startPageNumber = 1;
    			}
    			if (parseInt(endPageNumber) > parseInt(pageCount)){
    				endPageNumber = pageCount;
    			}
    			
//    			console.log("pageCount="+pageCount+";segment="+segment+";segmentCount="+segmentCount+";startPageNumber="+startPageNumber+";endPageNumber="+endPageNumber);
    			
    			var formatPager = "";
    			
    			formatPager += '<div class="page">';
    			formatPager += '<ul class="pagination fr">';
    	        if (parseInt(currentPage) > 1){
    	        formatPager += '		<li>';
    	        formatPager += '			<a class="txt" data-page="1" data-param="'+firstPageUrl+'" href="javascript:;">首页</a>';
    	        formatPager += '		</li>';
    	        }else{
	        	formatPager += '		<li>';
	        	formatPager += '			<a class="txt" href="javascript:;">首页</a>';
	        	formatPager += '		</li>';
    	        }
    			if (parseInt(currentPage) > 1){
    			formatPager += '		<li>';
    			formatPager += '			<a class="txt" data-page="'+(currentPage - 1)+'" data-param="'+prePageUrl+'" href="javascript:;">上一页</a>';
    			formatPager += '		</li>';
    			}else{
				formatPager += '		<li>';
				formatPager += '			<a class="txt" href="javascript:;">上一页</a>';
				formatPager += '		</li>';
    			}
//    			if (segment > 1){
//    			formatPager += '	<li><a data-param="${baseUrl + "page=" + ((segment - 1) * maxShowPageCount) + parameter}" href="javascript:;">...</a></li>';
//    			}
    			
    			for( var index = parseInt(startPageNumber) ; index <= parseInt(endPageNumber) ; index ++ ){
    					if (currentPage != index){
    						formatPager += '<li> ';
    						formatPager += '	<a data-page="'+index+'" data-param="'+baseUrl +'&page=' + index +''+ parameter+'" href="javascript:;">'+index+'</a>';
    						formatPager += '</li>';
    					}else{
    						formatPager += '<li class="currentPage active">';
    						formatPager += '	 <a class="active" href="javascript:;">'+index+'</a>';
    						formatPager += '</li>';
    					}
    			}
//    			if (parseInt(segment) < parseInt(segmentCount)){
//    				formatPager += '<li><a data-param="${baseUrl + "page=" + ((segment * maxShowPageCount) + 1 ) + parameter}">...</a></li>';
//    			}
    			if (parseInt(currentPage) < parseInt(pageCount)){
    				formatPager += '    <li>  ';
    				formatPager += '        <a class="txt" data-page="'+(currentPage + 1)+'" data-param="'+nextPageUrl+'" href="javascript:;">下一页</a>  ';
    				formatPager += '    </li>  ';
    			}else{
    				formatPager += '    <li>  ';
    				formatPager += '         <a class="txt" href="javascript:;">下一页</a> ';
    				formatPager += '    </li>  ';
    			}
    				//<#-- 末页 -->
    			if (parseInt(currentPage) < parseInt(pageCount)){
    				formatPager += '	<li>';
    				formatPager += '		<a class="txt" data-page="'+pageCount+'" data-param="'+lastPageUrl+'" href="javascript:;">末页</a>';
    				formatPager += '	</li>';
    			}else{
    				formatPager += '	<li>';
    				formatPager += '		<a class="txt" href="javascript:;">末页</a>';
    				formatPager += '	</li>';
    			}
    				
    				
    	           //<!--  <li>到第<span class="page_input"><input type="text"></span>页</li> -->
    			formatPager += '<li><a href="javascript:;">第'+currentPage+' / '+pageCount+' 页  &nbsp; 每页显示'+pageSize+'条记录 &nbsp; 共'+total+'条记录</a></li>';
    	            //<!-- <li><a class="active txt" href="javascript:;">确定</a></li> -->
    	            
    		        
    			formatPager += '</ul>';
    			formatPager += '</div>';
    	    
    	    
    	    
    			$("#"+obj.pagerId).html(formatPager);
    			
    			$("#"+obj.pagerId+" a").each(function(index,ele){
    				if(that.checkempty($(this).attr("data-page"))){
    					$(this).click(function(){
    						that.page = parseInt($(this).attr("data-page"));
    						obj.clickPager();
    					});
    				}
    			});
    		}
    		
    };
    
    $.epage = new EJQ_PAGE();
    
})(jQuery);