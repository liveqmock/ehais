var myChart = null;
var bsTable = null;
$(function(){
	$("#startDate").val(moment().subtract('days', 5).format('YYYY-MM-DD'));
	$("#endDate").val(moment().format('YYYY-MM-DD'));
	$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
    $('#reportrange').daterangepicker(null, function(start, end, label) {
    	$("#startDate").val(start.format('YYYY-MM-DD'));
    	$("#endDate").val(end.format('YYYY-MM-DD'));
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
    });
    
    $("#currentWeek").click(function(){
    	$("#startDate").val(getWeekStartDate());
    	$("#endDate").val(getWeekEndDate());
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		OrderDinintStatistics();
    });
    $("#previousWeek").click(function(){
    	$("#startDate").val(getLastWeekStartDate());
    	$("#endDate").val(getLastWeekEndDate());
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		OrderDinintStatistics();
    });
	$("#currentMonth").click(function(){
		$("#startDate").val(getMonthStartDate());
    	$("#endDate").val(getMonthEndDate());
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		OrderDinintStatistics();
	});
	$("#previousMonth").click(function(){
		$("#startDate").val(getLastMonthStartDate());
    	$("#endDate").val(getLastMonthEndDate());
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		OrderDinintStatistics();
	});
    
	// 基于准备好的dom，初始化echarts实例
	$("#main").width($(document).width() - 50);
	$("#main").height($(document).height() - 150);
	
    myChart = echarts.init(document.getElementById('main'));

    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        striped: true,//是否显示行间隔色
        cache: false,
        sortable: true,//是否启用排序
        sortOrder: 'asc',//排序方式
        clickToSelect: true,//是否启用点击选中行
        pageNumber: 1,
	    pageSize: 10,
	    pageList: [10,20,30,40,50],
	    queryParamsType:'',
	    queryParams: function (params) {
	        return {
	            rows: params.pageSize,   //页面大小  
                page: params.pageNumber,  //页码        
                sort: params.sort,  //排序列名  
                sortOrder: params.order,//排位命令（desc，asc）
                orderStatus : 1,
                classify : "dining",
                orderSn : orderSn
	        }
	    },
        columns: [

			{
			    field: 'payTime',
			    title: '日期',
			    formatter : function(value,rows,index){
			    	if(parseInt(value) > 0){
			    		var date =  new Date(value * 100000);
			    		return date.format("yyyy-MM-dd");
			    	}else{
			    		return "";
			    	}
			    }
			},{
			    field: 'weixinAmount',
			    title: '微信收益'
			},{
			    field: 'cashAmount',
			    title: '现金收益'
			}
        
        ],responseHandler : function (res){
        	
        	return res;
        }
    });
    
    OrderDinintStatistics();
});


var option = {
	    title: {
	        text: '餐厅收益',
	        textStyle : {
	        	color : "#888",
	        	fontSize : 18
	        }
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['微信收益','现金收益']
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
	        },
	        {
	            name:'现金收益',
	            type:'line',
	            stack: '总量',
	            data:[0.488,0.493,0.501,0.523,0.539,0.549,0.578,0.580,0.584,0.588],
	            lineStyle : {
	            	normal : {
	            		color : "#ededed",
    	            	width : 2,
    	            	type: "solid",
	            	}
	            }  
	        }
	    ]
	};


function changeDate(that){
	$(".date.btn-group .btn-success").removeClass("btn-success").addClass("btn-default");
	$(that).removeClass("btn-default").addClass("btn-success");
}

function OrderDinintStatistics(){
	$.ajax({
		url : "OrderDinintStatistics",
		data : {state_date:$("#startDate").val(),end_date:$("#endDate").val()},
		success : function(result){
			var rows = result.rows;
			var weixinAmount = new Array();
			var cashAmount = new Array();
			var dateList = new Array();
			$.each(rows,function(k,v){
				weixinAmount.push((v.weixinAmount / 100).toFixed(2));
				cashAmount.push((v.cashAmount / 100).toFixed(2));
				var date = new Date(v.payTime * 100000);
				dateList.push( date.format("yyyy-MM-dd") );
			});
			option.series[0].data = weixinAmount;
			option.series[1].data = cashAmount;
			option.xAxis.data = dateList;
			
			myChart.setOption(option);
			
			bsTable.bootstrapTable("load",rows);
			bsTable.bootstrapTable('refresh');
		}
	});
}
