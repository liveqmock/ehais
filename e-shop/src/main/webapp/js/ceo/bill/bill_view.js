$(function(){
	$("#bill_submit").click(function(){
		$.ajax({
			url : "haiBillListJson",
			type:"post",dataType:"json",data:{bill_date:$("#bill_date").val()},
			success:function(data){
				
			}
		});
	});
	
	
	
	
	
	$("#upload_file_csv").fileupload({
        url: "weixin_import_csv.upd",
        formData : { bill_date:$("#bill_date").val() },
        dataType: 'json',
        done: function (e, data) {

        },
        beforeSend: function(jqXHR) {
        	layer.load(1, {
  			  shade: [0.5,'#ccc'] //0.1透明度的白色背景
  			});
        },complete: function() {
        	layer.closeAll();
        },
        success : function(result, textStatus, jqXHR){
        	console.log(JSON.stringify(result));
        	
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
	
	
	$("#import_csv").click(function(){
		$("#upload_file_csv").click();
	});
	
	
	
});