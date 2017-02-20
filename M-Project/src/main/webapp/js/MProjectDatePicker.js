/**
 * 
 */
(function($){
	
	var myMProjectDatePicker = function (element, options, cb) {
		var _this = this;
		var IconCalendarAddEvent ;
		this.element = $(element);
		options.monthNames = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
		options.dayNames = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
		
		var now   = new Date();
		var thismonth = now.getMonth()+1;
		var thisyear  = now.getYear() + 1900;
		
		this.init(options);
		//this.setProjectData(options);
		
		
		
	};
	
	
	myMProjectDatePicker.prototype = {
			
			constructor: myMProjectDatePicker,
			
			init : function(options) {				
				console.log("myMProjectDatePicker init");
				this.element.html("");
				this.element.append("<div class='calendar-topbar'><h3><span class='year'>"+options.year+"</span>-<span class='month'>"+options.month+"</span></h3></div>");
				
				
				var week_title = "";
				week_title+='<div class="week-title">';
				week_title+='  <div class="weekdays">';
				for (d=0; d<7; d++) {
					week_title+='  <div class="weekday">' + options.dayNames[d] + '</div>';
				}
				week_title+='  </div>';
				week_title+='</div>';
				
				
				
				var lastday = this.getDaysInMonth(options.year,options.month);//当前月的最后一天
				var firstDayDate=new Date(options.year,options.month-1,1);//当前日期
				var firstDay=firstDayDate.getDay();//当前月的第一天的星期几
				
				var lastDayDate=new Date(options.year,options.month-1,lastday);//当前月最后一天日期
				var lastWeek=lastDayDate.getDay();//当前月的最一天的星期几
				
		        var today = new Date().getDate();// 今天日期
		        var week = new Date().getDay();//今天星期几
		        
		        var prev_m = options.month == 1 ? 12 : options.month-1;//前一个月份
				var prev_y = options.month == 1 ? options.year - 1 : options.year;//前一年
				var next_m = options.month == 12 ? 1 : options.month+1;//后一个月份
				var next_y = options.month == 12 ? options.year + 1 : options.year;//后一年
				
				var prev_days = this.getDaysInMonth(prev_y , prev_m);//前一个月份最后一天
				
				//var days_num = today + firstDay - 1 - week + (4*7);
				var i = v = 0;
				var validClass = "";//有效的样式类
				var dayOffClass = "";//周六日的样式类
				var YM_Class = "";//上一个月，当前月，下一个月的样式类
				var day = 0;//计算出当前日期
				var tr_row = 0;//表格的行号
				var td_num = lastday + firstDay  + (7 - lastWeek - 1)//today + firstDay - 1 - week + lastday + (7 - lastDay);
		       	var td_ymd_id = "";//每一个格里面的对应编号id
		       	
				var table = '';
				
				for (j=0;j<td_num;j++){
					

					if ((j<firstDay)){
						YM_Class = "previous-month";
						day = prev_days-firstDay+j+1;if(parseInt(day)<10)day="0"+day;
						td_ymd_id = prev_y + "-" + prev_m + "-" + day;
					}else if((j>=firstDay+this.getDaysInMonth(options.year,options.month))){//下一个月
						i = i+1;
						day = i;if(parseInt(day)<10)day="0"+day;
						YM_Class = "next-month";
						td_ymd_id = next_y + "-" + next_m + "-" + day;  
					}else{		 		
						YM_Class = "current-month";          	
						day = j-firstDay+1;if(parseInt(day)<10)day="0"+day;
						td_ymd_id = options.year + "-" + options.month + "-" + day;          	
						if(j-firstDay+1 == today)validClass = "today";
					
					}
					  
					if(j-firstDay+1 > today && j%7!=0 && j%7!=6 && v < 15){
						validClass = "valid_day";
						v ++ ;
					}
					
					if (j%7==0)  {table += ('<div class="week" data-week="'+td_ymd_id+'"><div class="days">');}
					
					table += '<div class="day day_'+td_ymd_id+'" data-date="'+td_ymd_id+'"><div class="info"><span class="icon_calendar_add glyphicon glyphicon-plus" ></span><span class="num">'+day+'</span></div></div>';
					
					if (j%7==6)  {table += ('</div><div class="events"></div></div>');}
				}
				
				this.element.append("<div id='calendar' class='simple-calendar'>"+
						week_title+
						"<div class='weeks'>"+table+"</div></div>");
				
//				this.setIconCalendarAddEvent(this);
				var _this = this;
				
				//如果存在，则绑定添加事件
				if (typeof IconCalendarAddEvent === 'function') {
					this.setIconCalendarAddEvent(IconCalendarAddEvent);
	            }
				
			} ,
			setProjectData : function(data,callback){
				if (typeof callback === 'function') {
	                callback();
	            }
			},
			
			/**
			 * 添加按钮的事件代理
			 * @param callback
			 */
			setIconCalendarAddEvent : function(callback){
				$(".icon_calendar_add").unbind();
				$(".icon_calendar_add").bind("click",function(e){
					console.log(e.target.tagName+":click");
					if (typeof callback === 'function') {
						IconCalendarAddEvent = callback;
		                callback(this);
		            }					
				});				
			},
			
			
			remove: function() {
	            this.container.remove();
	            this.element.off('.MProjectDatePicker');
	            this.element.removeData('MProjectDatePicker');
	        },
			
			getDaysInMonth : function (year,month)  {
				month --;
				var daysInMonth=[31,28,31,30,31,30,31,31,30,31,30,31];
				if ((month==2)&&(year%4==0)&&((year%100!=0)||(year%400==0))){
				  return 29;
				}else{
				  return daysInMonth[month];
				}
			}
	};
	
	
    $.fn.MProjectDatePicker = function (options, cb) {

        return new myMProjectDatePicker($(this), options, cb);
        
    };
    
	
})(jQuery);
