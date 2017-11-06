var myChart = null;
var bsTable = null;
$(function(){
	
	statisticsBegOffJSON();
	
	$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
    $('#reportrange').daterangepicker(null, function(start, end, label) {
    	$("#startDate").val(start.format('YYYY-MM-DD'));
    	$("#endDate").val(end.format('YYYY-MM-DD'));
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
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
			    field: 'question',
			    title: '班级'
			},{
			    field: 'count',
			    title: '次数'
			}
        
        ],responseHandler : function (res){
        	
        	return res;
        }
    });
    
    
    $("#btnDownload").click(function(){
    	$("#statisticsBegOffExport").submit();
    });
    
});


var option = {
	    color: ['#3398DB'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'请假次数',
	            type:'bar',
	            barWidth: '60%',
	            data:[10, 52, 200, 334, 390, 330, 220]
	        }
	    ]
	};


function changeDate(that){
	$(".date.btn-group .btn-success").removeClass("btn-success").addClass("btn-default");
	$(that).removeClass("btn-default").addClass("btn-success");
}

function statisticsBegOffJSON(){
	$.ajax({
		url : "statisticsBegOffJSON",
		data : {start_date:$("#startDate").val(),end_date:$("#endDate").val()},
		success : function(result){
			var rows = result.rows;
			var countData = new Array();
			var questionData = new Array();
			$.each(rows,function(k,v){
				countData.push(v.count);
				questionData.push(v.question);
			});
			option.series[0].data = countData;
			option.xAxis[0].data = questionData;
			
			myChart.setOption(option);
			
			bsTable.bootstrapTable("load",rows);
			bsTable.bootstrapTable('refresh');
		}
	});
}
