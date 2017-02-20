/**
 * 
 */

var mProjectCalendar ;
$(function(){	
	mProjectCalendar = $("#mProjectCalendar").MProjectDatePicker({year:2015,month:11});
	mProjectCalendar.setProjectData("AABBCC",setProjectDataFunc);
	mProjectCalendar.setIconCalendarAddEvent(setIconCalendarAddFunc);
	
	$('.datepicker').datepicker({
	    inline: true,dateFormat: "yy-mm-dd"
	});
	
	
	loadProjectWBS();
	
});

function setProjectDataFunc(){
	
}

function setIconCalendarAddFunc(el){
	//alert($(el).parent().parent().attr("data-date"));
	var left = $(el).position().left;
	var top = $(el).position().top;
}

//加载分解的数据
function loadProjectWBS(){
	 $.ajax({
	        method : 'POST',
	        url : 'P_WBS_Data',
	        data : null,
	        async : false,
	        dataType : 'json',
	        beforeSend : function(jqXHR) {
	            jqXHR.setRequestHeader('Authorization', 'fuckyou');
	            jqXHR.setRequestHeader('User-Agent-', 'tylerfuckyou');
	           
	        },
	        success : function(data) {
	        	console.log(JSON.stringify(data));
	        	var rows = data.rows;
	        	
	        	for(var i = 0 ; i < rows.length ; i ++){
	        		if(typeof(rows[i].planStartDate) != "undefined" && typeof(rows[i].planEndDate) != "undefined"){
	        			var pStartDate = (new Date(rows[i].planStartDate)).pattern("yyyy-MM-dd");
	        			var pEndDate = (new Date(rows[i].planEndDate)).pattern("yyyy-MM-dd");
	        			var iDays = dateDiff(new Date(rows[i].planStartDate),new Date(rows[i].planEndDate)) + 1;
	        			var iWeek = (new Date(rows[i].planStartDate)).getDay();
	        			
	        			if($(".day_"+pStartDate).length > 0){
	        				$(".day_"+pStartDate).parent().parent().children(".events").append(calendarEventData(rows[i],0));
	        				$("#wbsId_"+rows[i].wbsId).css("left",$(".day_"+pStartDate).position().left+"px");
	        				
	        				if( iDays <= 7-iWeek ){
	        					$("#wbsId_"+rows[i].wbsId).css("width",$(".day_"+pStartDate).width() * iDays+"px");	        					
	        				}else{
	        					$("#wbsId_"+rows[i].wbsId).css("width",$(".day_"+pStartDate).width() * (7-iWeek)+"px");
	        					//当前序号
	        					var current_index = $(".day_"+pStartDate).parent().parent().parent().children(".week").index($(".day_"+pStartDate).parent().parent());
	        					iDays = iDays - (7 - iWeek);
	        					if(parseInt (iDays / 7) > 0){
	        						for(var j = 0 ; j < parseInt (iDays / 7) ; j++){
	        							$(".day_"+pStartDate).parent().parent().parent().children(".week").eq(current_index + j + 1).children(".events").append(calendarEventData(rows[i],j + 1));
	        							$("#wbsId_"+rows[i].wbsId+"_"+(j + 1)).css("width",$(".day_"+pStartDate).width() * 7+"px");	
	        						}
	        					}
	        					if(iDays % 7 > 0){
	        						$(".day_"+pStartDate).parent().parent().parent().children(".week").eq(current_index + parseInt(iDays / 7) + 1).children(".events").append(calendarEventData(rows[i],parseInt(iDays / 7) + 1));
	        						$("#wbsId_"+rows[i].wbsId+"_"+(parseInt(iDays / 7) + 1)).css("width",$(".day_"+pStartDate).width() * parseInt(iDays % 7)+"px");
	        					}	        					
	        				}	        				
	        			}
	        		}
//	        		console.log(pStartDate + " width :"+$(".day_"+pStartDate).width());
	        	}
	        	
	        	$(".events").each(function(index,ele){
	        		$(ele).children(".event").each(function(num,e){
	        			$(e).css("top",(18 * num)+"px");
	        		});
	        	});
	        },
	        error : function() {
	        	
	        }
	    });
}

//输出日历事件的数据
function calendarEventData(rowsData,index){
	return "<div class='event' id='wbsId_"+rowsData.wbsId + ((index == 0)?"":"_"+index) + "' user_id='"+rowsData.userId+"'><span class='event_username'></span>"+rowsData.wbsName+"</div>";
}

//exampe
//var date = new Date();	  
//window.alert(date.pattern("yyyy-MM-dd hh:mm:ss"));
Date.prototype.pattern=function(fmt) {		 
	  var o = {		 
	  "M+" : this.getMonth()+1, //月份		 
	  "d+" : this.getDate(), //日		 
	  "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时		 
	  "H+" : this.getHours(), //小时		 
	  "m+" : this.getMinutes(), //分		 
	  "s+" : this.getSeconds(), //秒		 
	  "q+" : Math.floor((this.getMonth()+3)/3), //季度		 
	  "S" : this.getMilliseconds() //毫秒		 
	  };		 
	  var week = {		 
	  "0" : "/u65e5",		 
	  "1" : "/u4e00",		 
	  "2" : "/u4e8c",		 
	  "3" : "/u4e09",		 
	  "4" : "/u56db",		 
	  "5" : "/u4e94",		 
	  "6" : "/u516d"		
	  };		 
	  if(/(y+)/.test(fmt)){		 
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));		 
	  }		 
	  if(/(E+)/.test(fmt)){		 
	    fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);		 
	  }		 
	  for(var k in o){		 
	    if(new RegExp("("+ k +")").test(fmt)){		 
	      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));		 
	    }		 
	  }		 
	  return fmt;		 
}

function dateDiff(sDate,eDate){
	iDays  =  parseInt(Math.abs(sDate.getTime()   -  eDate.getTime() )  /  1000  /  60  /  60  /24);
	return iDays;
}
