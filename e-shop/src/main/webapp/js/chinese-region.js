/**
 * $("#chinaRegion").chineseRegion({
				province:"province",provinceValue:${model.province!0},
				city:"city",cityValue:${model.city!0},
				county:"county",countyValue:${model.county!0},
				district:"district",districtValue:${model.district!0},
				url:"/wx/region_id_list",
				param:"parent_id"
			});
 * @param $
 */
(function($) {
	

$.fn.chineseRegion = function(data) {
	var that = this;
	this.province = (data.province == null ? "province" : data.province),
	this.city = (data.city == null ? "city" : data.city),
	this.county = (data.county == null ? "county" : data.county),
	this.district = (data.district == null ? "district" : data.district),
	this.provinceValue = (data.provinceValue == null ? 0 : data.provinceValue),
	this.cityValue = (data.cityValue == null ? 0 : data.cityValue),
	this.countyValue = (data.countyValue == null ? 0 : data.countyValue),
	this.districtValue = (data.districtValue == null ? 0 : data.districtValue),
	this.id = $(this).attr("id"),
	this.param = data.param,
	this.url=data.url,
	this.regionarray= [this.province,this.city,this.county,this.district],
    this.onClose = function(){
		$(this).children(".dropdown-chinese-region").removeClass("active");
    },
    this.onOpen = function(){
    	$(this).children(".dropdown-chinese-region").addClass("active");
    	
    	$(".chinese-region div.tab-content > div.tab-pane").removeClass("active");
    	$(".chinese-region ul.nav-tabs > li").removeClass("active");
		
    	this.initRegionData();
    	
    	if($("#"+this.district).val() != "" && $("#"+this.district).val() != 0){
			$(".chinese-region div.tab-content > div.tab-pane").eq(3).addClass("active");
			$(".chinese-region ul.nav-tabs > li").eq(3).addClass("active");
    	}else if($("#"+this.county).val() != "" && $("#"+this.county).val() != 0){
			$(".chinese-region div.tab-content > div.tab-pane").eq(2).addClass("active");
			$(".chinese-region ul.nav-tabs > li").eq(2).addClass("active");
    	}else if($("#"+this.city).val() != "" && $("#"+this.city).val() != 0){
			$(".chinese-region div.tab-content > div.tab-pane").eq(1).addClass("active");
			$(".chinese-region ul.nav-tabs > li").eq(1).addClass("active");
    	}else if($("#"+this.province).val() != "" && $("#"+this.province).val() != 0){
			$(".chinese-region div.tab-content > div.tab-pane").eq(0).addClass("active");
			$(".chinese-region ul.nav-tabs > li").eq(0).addClass("active");
    	}else{
    		//初始化省份
    		this.loadRegion(this.province,0,1);
    	}
    },
    this.loadRegion = function(region,num,value){
    	$(".chinese-region div.tab-content > div.tab-pane").eq(num).addClass("active");
		$(".chinese-region ul.nav-tabs > li").eq(num).addClass("active");
		$("#chinese_region_"+region+"_"+this.id+" > div").unbind();
		$("#chinese_region_"+region+"_"+this.id+" > div").remove();
		
    	$.ajax({
			url : "/ws/region_parent_list",type:"post",dataType:"json",data:{parent_id:value},
			success : function(result){
				
				var rows = result.rows;
				if(rows.length == 0)that.onClose();
				for(var i = 0 ; i < rows.length ; i++){
					$("#chinese_region_"+region+"_"+that.id).append("<div regionId='"+rows[i].regionId+"'>"+rows[i].regionName+"</div>")
				}
				
				that.bindRegionClick();
				
			},error : function(err,xhr){
				
			}
		});
    },
    this.bindRegionClick = function(){
    	$("#"+this.id+" .tab-content .tab-pane > div").unbind();
    	$("#"+this.id+" .tab-content .tab-pane > div").click(function(){
    		var num = $(this).parent(".tab-pane").index();
    		var region = $(this).parent(".tab-pane").attr("region");
    		$("#"+that.id+" ul.nav-tabs > li,#"+that.id+" div.tab-content > div.tab-pane").removeClass("active");
			$("#"+that.id+" ul.nav-tabs > li").eq(num+1).addClass("active");
			$("#"+that.id+" div.tab-content > div.tab-pane").eq(num+1).addClass("active");
			
			$("#"+region).val($(this).attr("regionId")).attr("text",$(this).text());
			
			for(var i = num+1 ; i < 4 ; i++){
				$("#"+that.id+" div.tab-content > div.tab-pane").eq(num+1).children("div").remove();
				$("#"+that.regionarray[i]).val("").attr("text","");
			}
			$("#chinese_region_"+that.id).val("");
			for(var i = 0 ; i < 4 ; i ++){
				var txt = $(".chinese-region input[type='hidden']").eq(i).attr("text");
				if(txt == null || txt == "undefined")txt = "";
				$("#chinese_region_"+that.id).val($("#chinese_region_"+that.id).val()+txt);
				txt = null;
			}			
			if(num == 3){
				that.onClose();
				return ;
			}
			
			that.loadRegion($("#"+that.id+" div.tab-content > div.tab-pane").eq(num).attr("next"),num+1,$(this).attr("regionId"));
			
    	});
    },this.initRegionData = function(){
    	if(($("#"+this.province).val() != null && $("#"+this.province).val() != 0) ||
    			($("#"+this.city).val() != null && $("#"+this.city).val() != 0) || 
    			($("#"+this.county).val() != null && $("#"+this.county).val() != 0) ||
    			($("#"+this.district).val() != null && $("#"+this.district).val() != 0) 
    		){  
    			var region_ids = new Array();
    			region_ids.push(1);
    			if($("#"+this.province).val() != null && $("#"+this.province).val() != 0)region_ids.push($("#"+this.province).val());
				if($("#"+this.city).val() != null && $("#"+this.city).val() != 0)region_ids.push($("#"+this.city).val());
				if($("#"+this.county).val() != null && $("#"+this.county).val() != 0)region_ids.push($("#"+this.county).val());
				if($("#"+this.district).val() != null && $("#"+this.district).val() != 0)region_ids.push($("#"+this.district).val());
				
    			$.ajax({
    				url:"/ws/region_parentid_list",type:"post",dataType:"json",data:{region_ids:region_ids.join(",")},
    				success:function(result){
    					var rows = result.rows;
    					for(var i = 0 ; i < region_ids.length ; i ++){
    						for(var j = 0 ; j < rows.length ; j++){
    							if(region_ids[i] == rows[j].parentId){
    								$("#"+that.id+" .tab-content > div.tab-pane").eq(i).append("<div regionId='"+rows[j].regionId+"'>"+rows[j].regionName+"</div>")
    							}
    						}
    					}
    					that.bindRegionClick();
    				},error:function(err,xhr){
    					
    				}
    			});
    		}
    },this.initRegionText = function(){
    	
    	if(($("#"+this.province).val() != null && $("#"+this.province).val() != 0) ||
    			($("#"+this.city).val() != null && $("#"+this.city).val() != 0) || 
    			($("#"+this.county).val() != null && $("#"+this.county).val() != 0) ||
    			($("#"+this.district).val() != null && $("#"+this.district).val() != 0) 
    		){  
    			var region_ids = new Array();
    			if($("#"+this.province).val() != null && $("#"+this.province).val() != 0)region_ids.push($("#"+this.province).val());
				if($("#"+this.city).val() != null && $("#"+this.city).val() != 0)region_ids.push($("#"+this.city).val());
				if($("#"+this.county).val() != null && $("#"+this.county).val() != 0)region_ids.push($("#"+this.county).val());
				if($("#"+this.district).val() != null && $("#"+this.district).val() != 0)region_ids.push($("#"+this.district).val());
				
    			$.ajax({
    				url:"/ws/region_id_list",type:"post",dataType:"json",data:{region_ids:region_ids.join(",")},
    				success:function(result){
    					var rows = result.rows;
    					$("#chinese_region_"+that.id).val("");
    					for(var i = 0 ; i < rows.length ; i++){
    						$("#chinese_region_"+that.id).val($("#chinese_region_"+that.id).val()+rows[i].regionName);
    						$("#"+that.id+" input[type='hidden']").eq(i).attr("text",rows[i].regionName);
    					}
    				},error:function(err,xhr){
    					
    				}
    			});
    		}
    },
	this.init = function(){
		$(this).addClass("chinese-region");
    	$(this).html("<input type=\"text\" class=\"form-control\" id=\"chinese_region_"+this.id+"\" placeholder=\"选择你的地区\" data-toggle=\"dropdown\" readonly=\"readonly\">");
    	$(this).append("<input type=\"hidden\" id=\""+this.province+"\" name=\""+this.province+"\" value=\""+this.provinceValue+"\">");
    	$(this).append("<input type=\"hidden\" id=\""+this.city+"\" name=\""+this.city+"\" value=\""+this.cityValue+"\">");
    	$(this).append("<input type=\"hidden\" id=\""+this.county+"\" name=\""+this.county+"\" value=\""+this.countyValue+"\">");
    	$(this).append("<input type=\"hidden\" id=\""+this.district+"\" name=\""+this.district+"\" value=\""+this.districtValue+"\">");
    	$(this).append("<div class=\"dropdown-chinese-region\" role=\"menu\" aria-labelledby=\"dLabel\">"+
							"<div class='bg'></div>"+
							"<div class='main'>"+
								"<ul class=\"nav nav-tabs\">"+
									"<li class=\"active\"><a href=\"javascript:;\">省份</a></li>"+
									"<li><a href=\"javascript:;\">城市</a></li>"+
									"<li><a href=\"javascript:;\">县区</a></li>"+
									"<li><a href=\"javascript:;\">街道</a></li>"+
								"</ul>"+
								"<div class=\"tab-content\">"+
									"<div class=\"tab-pane active\" id=\"chinese_region_"+this.province+"_"+this.id+"\" region=\""+this.province+"\" next=\""+this.city+"\"></div>"+
									"<div class=\"tab-pane\" id=\"chinese_region_"+this.city+"_"+this.id+"\" region=\""+this.city+"\" next=\""+this.county+"\"></div>"+
									"<div class=\"tab-pane\" id=\"chinese_region_"+this.county+"_"+this.id+"\" region=\""+this.county+"\" next=\""+this.district+"\"></div>"+
									"<div class=\"tab-pane\" id=\"chinese_region_"+this.district+"_"+this.id+"\" region=\""+this.district+"\" next=\"\"></div>"+
								"</div>"+
							"</div>"+
						"</div>");
		
		
    	this.initRegionText();
		
		
		
		$(".chinese-region ul.nav-tabs > li").click(function(){
			$(".chinese-region ul.nav-tabs > li").removeClass("active");
			$(this).addClass("active");			
			$(".chinese-region div.tab-content > div.tab-pane").removeClass("active");
			$(".chinese-region div.tab-content > div.tab-pane").eq($(this).index()).addClass("active");
		});
		
		//文本点击事件
		$("#chinese_region_"+this.id).click(function(){that.onOpen();});
		$(this).children(".dropdown-chinese-region").children(".bg").click(function(){that.onClose();});
    }
    this.init();
}

})(jQuery); 

