
var hotelRoomId = 0;
var haiHotelRoomModal ; 


var bsTable ;
var hotelName = "";



$(function(){
	
	
	haiHotelRoomModal = $("#haiHotelRoomModal").modal({backdrop: 'static', keyboard: false , show : false });
	
	//getTree();
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	
	
	
    $("#btnSearch").click(function(){hotelName = $.trim($("#hotelName").val());bsTable.bootstrapTable('refresh', { query : {hotelRoomId : hotelRoomId , hotelName : hotelName , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'haiHotelListJson',//请求后台的url
        method: 'post',//请求方式
        toolbar: '#toolbar',//工具按钮用哪个容器
        dataType: "json",
		pagination: true, //分页
		//search: true, //显示搜索框
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
                hotelRoomId : hotelRoomId,
                hotelName : hotelName
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'hotelId',//每一行的唯一标识，一般为主键列
        columns: [



        {
            field: 'hotelId',
            title: '操作',
            formatter : function(value,row,index){
				var opt = "";
            	opt += "<a href ='haiHotelEditDetail?hotelId="+value+"'  class='glyphicon glyphicon-edit'></a>";
            	opt += "&nbsp;&nbsp;<a href ='javascript:;' onclick='haiHotelDelete("+value+");' class='glyphicon glyphicon-trash'></a>";
            	return opt;
            }
        }
        
        ],responseHandler : function (res){
        	/**
        	var cat = res.map.hotelroomList ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].hotelRoomId] = cat[i].hotelRoomName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].hotelRoomName = catObj[rows[i].hotelRoomId];
        	}
        	**/
        	return res;
        }
    });
    
    
});





function haiHotelDelete(hotelId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiHotelDelete",type:"post",dataType:"json",data:{hotelId:hotelId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}


function getTree() {
	$.ajax({
		url : "haiHotelRoomListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				nodes.push({text : rows[i].hotelRoomName , id : rows[i].hotelRoomId });
			}
			
		    $('#tree').treeview({
		        data: [{
			        text: "酒店信息分类",
			        nodes: nodes
			    }],
		        levels: 5,
		        multiSelect: false,
		        onNodeSelected: function (event, data) {
		        	if(data.nodeId == 0){
		        		hotelRoomId = 0;
		        	}else{
		        		hotelRoomId = data.id;
		        	}
		        	$("#hotelName").val("");hotelName = "";
		        	bsTable.bootstrapTable('refresh', { query : {hotelRoomId : hotelRoomId , page : 1} });
		        }
		    });
		}
	});
	

}


function addCate(){

	$("#haiHotelRoomFormModal")[0].reset();
	
	haiHotelRoomModal.modal("show");
	
	$("#haiHotelRoomFormSubmit").unbind();
	$("#haiHotelRoomFormSubmit").click(function(){addCateSubmit();});
	
	
}


function addCateSubmit(){
	

	
	
	$.ajax({
		url : "haiHotelRoomAddSubmit",
		data  : $("#haiHotelRoomFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiHotelRoomModal.modal("hide");
		}
	});
	
	
}


function editCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择分类");
		return ;
	}
	var hotelRoomId = node[0].id;
	
	
	$.ajax({
		url : "haiHotelRoomEditDetail",
		data  : {hotelRoomId : hotelRoomId},
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			
			$("#haiHotelRoomFormModal")[0].reset();
			
			categoryModal.modal("show");
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			})
			
			$("#haiHotelRoomFormSubmit").unbind();
			$("#haiHotelRoomFormSubmit").click(function(){editCateSubmit();});
			
			
		}
	});
	
}


function editCateSubmit(){
	

	
	$.ajax({
		url : "haiHotelRoomEditSubmit",
		data  : $("#haiHotelRoomFormModal").serialize(),
		success : function(result){
			if(result.code != 1){
				layer.msg(result.msg);
				return ;
			}
			layer.msg(result.msg);
			getTree();
			haiHotelRoomModal.modal("hide");
		}
	});
}




function deleteCate(){
	var node = $('#tree').treeview('getSelected');	
	if(node == null || node.length == 0 || node[0].nodeId == 0){
		layer.msg("请选择酒店信息分类");
		return ;
	}
	var hotelRoomId = node[0].id;
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "haiHotelRoomDelete",data:{hotelRoomId:hotelRoomId},
			success:function(result){
				layer.msg(result.msg);
				if(result.code != 1){
					return ;
				}
				
				getTree();
			}
		});
	}, function(){
		layer.closeAll();
	});
	
}

