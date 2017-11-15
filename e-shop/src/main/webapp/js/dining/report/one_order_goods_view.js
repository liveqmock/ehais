var myChart = null;
var bsTable = null;
var listGoods = null;
var listCategory = null;
var store_id = null;

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
 		OrderDiningStatistics();
    });
    $("#previousWeek").click(function(){
    	$("#startDate").val(getLastWeekStartDate());
    	$("#endDate").val(getLastWeekEndDate());
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		OrderDiningStatistics();
    });
	$("#currentMonth").click(function(){
		$("#startDate").val(getMonthStartDate());
    	$("#endDate").val(getMonthEndDate());
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		OrderDiningStatistics();
	});
	$("#previousMonth").click(function(){
		$("#startDate").val(getLastMonthStartDate());
    	$("#endDate").val(getLastMonthEndDate());
 		$('#reportrange span').html( $("#startDate").val() + ' ~ ' + $("#endDate").val() );
 		changeDate(this);
 		OrderDiningStatistics();
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
			    field: 'saleDate',
			    title: '日期',formatter:function(value,rows,index){
			    	return value.substr(0,10);
			    }
			},{
			    field: 'quantity',
			    title: '销量'
			}
        
        ],responseHandler : function (res){
        	
        	return res;
        }
    });
    
    
});


var option = {
	    title: {
	        text: '菜品销量',
	        textStyle : {
	        	color : "#888",
	        	fontSize : 18
	        }
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['销量']
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
	        data: [],
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
	            name:'销量',
	            type:'line',
	            stack: '总量',
	            data:[],
	            lineStyle : {
	            	normal : {
	            		color : "#ededed",
    	            	width : 2
	            	}
	            }    	        	
	        }
	    ]
	};


function changeDate(that){
	$(".date.btn-group .btn-success").removeClass("btn-success").addClass("btn-default");
	$(that).removeClass("btn-default").addClass("btn-success");
}


function loadCategoryGoodsStatistics(store_id){
	$.ajax({
		url : "loadCategoryGoodsStatistics",
		data:{store_id : store_id},
		success : function(result){
			var map = result.map;
			if(map==null)reutrn ;
			
			listGoods = map.listGoods;
			listCategory = map.listCategory;
			
			$("#sel_category_id").unbind();
			$("#sel_category_id option:not(:first)").remove();
			$("#sel_goods_id").unbind();
			$("#sel_goods_id option:not(:first)").remove();
			
			$.each(listCategory,function(k,v){
				$("#sel_category_id").append("<option value='"+v.catId+"'>"+v.catName+"</option>")
			});
			$("#sel_category_id").change(function(){
				$("#sel_goods_id").unbind();
				$("#sel_goods_id option:not(:first)").remove();
				var catId = $(this).val();
				
				if(catId != ""){
					console.log("change catId:"+catId);
					$.each(listGoods,function(k,v){
						if(parseInt(v.catId) == parseInt(catId)){
							$("#sel_goods_id").append("<option value='"+v.goodsId+"'>"+v.goodsName+"</option>");
						}						
					});
					
					$("#sel_goods_id").change(function(){OrderDiningStatistics()});
				}
			});
		}
	});
}
