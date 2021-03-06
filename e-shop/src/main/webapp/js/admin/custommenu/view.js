var keySubId = 0;
var bsTable ;
var title = "";
var weixin_menu_type = {"none":"一级菜单","click":"点击推送事件","view":"页面链接","scancode_push":"扫码推事件","scancode_waitmsg":"扫码推事件","pic_sysphoto":"拍照发图","pic_photo_or_album":"拍照/相册","pic_weixin":"弹出相册","location_select":"地理位置","media_id":"下发消息","view_limited":"跳转图文"};


$(function(){

    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'wpCustomMenuListJson',//请求后台的url
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
                keySubId : keySubId,
                title : title
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'id',//每一行的唯一标识，一般为主键列
        columns: [

{
    field: 'title',
    title: '标题',formatter:function(value,rows,index){
    	if(rows.level > 0){
    		return "<i style='padding-right:35px;'></i>"+value;
    	}else{
    		return "<b><i style='padding-right:15px;'></i>"+value+"</b>";
    	}
    }
},{
    field: 'type',
    title: '类型',formatter : function(value,rows,index){
    	if(value.length > 0){
    		return weixin_menu_type[value];
    	}else{
    		return "";
    	}
    }
},{
    field: 'sort',
    title: '排序'
},{
    field: 'url',
    title: '链接'
},{
    field: 'keyword',
    title: '关键词'
},

        {
            field: 'id',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='wpCustomMenuEditDetail?id="+value+"'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='wpCustomMenuDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }
        
        ],responseHandler : function (res){
        	      	
        	var nrows = [];
        	var rows = res.rows;
        	$.each(rows,function(k,v){
        		
        		if(v.pid == 0){
        			v.level = 0;
        			nrows.push(v);
        			$.each(rows,function(k2,v2){
        				if(v2.pid == v.id){
        					v2.level = 1;
        					nrows.push(v2);
        				}
        			});
        		}
        	});
        	
        	res.rows = nrows;
        	
        	return res;
        }
    });
    
    
    $("#wpCustomMenuSend").click(function(){wpCustomMenuSend();});//同步微信菜单
    
});





function wpCustomMenuDelete(id){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "wpCustomMenuDelete",type:"post",dataType:"json",data:{id:id},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

function wpCustomMenuSend(){
	$.ajax({
		url:"wpCustomMenuSend",type:"post",dataType:"json",
		success:function(result){
			layer.msg(result.msg);
		}
	});
}

