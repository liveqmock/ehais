var listSUserCountChart = null;
var listSStoreCountChart = null;
var listSUserTable = null;
var listSStoreTable = null;

$(function(){
	$("#listSUserCount").width($(document).width() - 250);
	$("#listSUserCount").height($(document).height() - 350);
	listSUserCountChart = echarts.init(document.getElementById('listSUserCount'));
	
	$("#listSStoreCount").width($(document).width() - 250);
	$("#listSStoreCount").height($(document).height() - 350);
	listSStoreCountChart = echarts.init(document.getElementById('listSStoreCount'));
	
	
	
	$("#currentWeek").click(function(){
    	$("#startDate").val(getWeekStartDate());
    	$("#endDate").val(getWeekEndDate());
 		$('#statistics_date span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		partnerStatistics();
    });
    $("#previousWeek").click(function(){
    	$("#startDate").val(getLastWeekStartDate());
    	$("#endDate").val(getLastWeekEndDate());
 		$('#statistics_date span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		partnerStatistics();
    });
	$("#currentMonth").click(function(){
		$("#startDate").val(getMonthStartDate());
    	$("#endDate").val(getMonthEndDate());
 		$('#statistics_date span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		partnerStatistics();
	});
	$("#previousMonth").click(function(){
		$("#startDate").val(getLastMonthStartDate());
    	$("#endDate").val(getLastMonthEndDate());
 		$('#statistics_date span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		partnerStatistics();
	});
	$("#before30").click(function(){
    	$("#startDate").val(moment().subtract('days', 30).format('YYYY-MM-DD'));
    	$("#endDate").val(moment().format('YYYY-MM-DD'));
 		$('#statistics_date span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		partnerStatistics();
	});
	
	
	listSUserTable = $('#listSUserTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        striped: true,//是否显示行间隔色
        cache: false,
        sortable: true,//是否启用排序
        sortOrder: 'asc',//排序方式
        clickToSelect: true,//是否启用点击选中行
	    queryParamsType:'',
        columns: [

			{
			    field: 'regTime',
			    title: '日期',width:"30%"
			},{
			    field: 'count',
			    title: '注册数量'
			}
        
        ],responseHandler : function (res){
        	
        	return res;
        }
    });
	
	listSStoreTable = $('#listSStoreTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        striped: true,//是否显示行间隔色
        cache: false,
        sortable: true,//是否启用排序
        sortOrder: 'asc',//排序方式
        clickToSelect: true,//是否启用点击选中行
	    queryParamsType:'',
        columns: [

			{
			    field: 'addTime',
			    title: '日期',width:"30%",
			    formatter : function(value,rows,index){
			    	if(parseInt(value) > 0){
			    		var date =  new Date(value * 100000);
			    		return date.format("yyyy-MM-dd");
			    	}else{
			    		return "";
			    	}
			    }
			},{
			    field: 'count',
			    title: '加盟数量'
			}
        
        ],responseHandler : function (res){
        	
        	return res;
        }
    });
	
	
	partnerStatistics();
	
});

function changeDate(that){
	$(".date.btn-group .btn-success").removeClass("btn-success").addClass("btn-default");
	$(that).removeClass("btn-default").addClass("btn-success");
}

var option = {
	    title: {
	        text: '',
	        textStyle : {
	        	color : "#888",
	        	fontSize : 18
	        }
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    toolbox: {
	        feature: {
	            //saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        boundaryGap: false,
	        data: ['09-01','09-02','09-03','09-04','09-05','09-06','09-07','09-08','09-09','09-10'],
    	    axisLine:{
                lineStyle:{
                    color:'#aaa',
                    width:1,//这里是为了突出显示加上的
                }
            } 
	    },
	    yAxis: {
	        type: 'value',
	        axisLine:{
                lineStyle:{
                    color:'#aaa',
                    width:0,//这里是为了突出显示加上的
                }
            } 
	    },
	    series: [
	        {
	            name:'微信收益',
	            type:'line',
	            stack: '总量',
	            data:[0.524,0.697,0.720,0.771,0.776,0.763,0.767,0.786,0.796,0.799],
	            lineStyle : {
	            	normal : {
	            		color : "#ededed",
    	            	width : 2
	            	}
	            }    	        	
	        }
	        
	    ]
	};

function partnerStatistics(){
	$.ajax({
		url : "partnerStatistics",
		data : {start_date:$("#startDate").val(),end_date:$("#endDate").val()},
		success : function(result){
			if(result.map == null){
				return ;
			}
			var listSUserCount = result.map.listSUserCount;
			var listSStoreCount = result.map.listSStoreCount;
			var listSUserOption = new Array();
			var listSStoreOption = new Array();
			var listSUserDate = new Array();
			var listSStoreDate = new Array();
			$.each(listSUserCount,function(k,v){
				listSUserOption.push(v.count);
				listSUserDate.push( v.regTime );
			});
			$.each(listSStoreCount,function(k,v){
				listSStoreOption.push(v.count);
				var date = new Date(v.addTime * 100000);
				listSStoreDate.push( date.format("yyyy-MM-dd") );
			});
			
			option.title.text = "会员增长数";
			option.legend.data[0] = "会员增长";
			option.xAxis.data = listSUserDate;
			option.series[0].name = "会员注册数";
			option.series[0].data = listSUserOption;			
			listSUserCountChart.setOption(option);
			
			option.title.text = "商家增长数";
			option.legend.data[0] = "商家增长";
			option.xAxis.data = listSStoreDate;
			option.series[0].name = "商家加盟数";
			option.series[0].data = listSStoreOption;			
			listSStoreCountChart.setOption(option);
			
			
			listSUserTable.bootstrapTable("load",result.map.listSUserCount);
			listSUserTable.bootstrapTable('refresh');
			
			listSStoreTable.bootstrapTable("load",result.map.listSStoreCount);
			listSStoreTable.bootstrapTable('refresh');
			
			
			
		}
	});
}
