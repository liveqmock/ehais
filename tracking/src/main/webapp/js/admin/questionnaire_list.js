/**
 * 
 */
var myDataGird ;
var wxid = 101;
$(function(){
	myDataGird = $("#mydata").eHaisDatagrid({
		columns : [
				{ field: 'id', title: '编号', width: 80 },
				{ field: 'title', title: '标题', width: 400 },
//				{ field: 'status', title: '类别', width: 150 ,formatter : statusFormatter },
//				{ field: 'display', title: '操作', width: 400 ,formatter : displayFormatter},
				{ field: 'action', title: '编辑', width: 80, align:'center' ,formatter:function(value,row,index){  
					var f = '<a href="javascript:;" onclick="showQCode({wxid:'+wxid+',id:'+row.id+'});">分享</a> '; 
					var a = '<a href="questionnaire_report?id='+row.id+'">报告</a> ';	
					var e = '<a href="questionnaire_update?id='+row.id+'">编辑</a> ';  
		            var d = '<a href="questionnaire_delete?id='+row.id+'" onclick="return myConfirm();">删除</a> ';  
		            	return f+a+e+d;   
		         	}
		         },
		       ],
		page : 1 , len : 10
	});
	myDataGird.setDataFunc(loadData);
	
});



function loadData(){

	$.ajax({
        method : 'POST',
        url : 'questionnaire_list_json',
        data : {
        	"page":myDataGird.page,
        	"len":myDataGird.len,
        },
        traditional : true,
        async : false,
        dataType : 'json',
        beforeSend : function(jqXHR) {
            
        },
        success : function(data) {
        	//console.log(JSON.stringify(data));
        	if(data.code != 1){
        		return ;
        	}
        	//cat_list = data.map.cat_list;
        	
        	myDataGird.loadData(data);
        },
        error : function() {
            alert('error');
        }
    });
}
var status = ["未审核","审核成功"];
function statusFormatter(val,rec,index){
	return (typeof(val) == "undefined")?"未审核":(status[val]);
}
var display = ["功见","不可允"];
function displayFormatter(val,rec,index){
	return (typeof(val) == "undefined")?"不可见":(display[val]);
}

function showQCode(obj) {//
	$("#qcode").html("");
	jQuery('#qcode').qrcode("http://"+document.domain+"/weixin/go-" + obj.wxid+"-"+obj.id);
	$("#divQCode").show();
}



function closeQCode() {
	$("#divQCode").hide();
}


