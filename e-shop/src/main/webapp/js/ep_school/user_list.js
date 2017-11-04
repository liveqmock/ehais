

$(function(){

	$("#upload_file_users").fileupload({
        url: "ep_school_excel.upd",
        formData : { //"file_path":"goods",
			//"is_thumb":<if condition="$bootstrap.is_thumb eq true">true<else />false</if>,
			//"field_name":"import_users"
		},
        dataType: 'json',
        done: function (e, data) {
        	
        },
        beforeSend: function(jqXHR) {
        	layer.load(1, {
  			  shade: [0.1,'#fff'] //0.1透明度的白色背景
  			});
        },complete: function() {
        	layer.closeAll();
        },
        success : function(data, textStatus, jqXHR){
        	console.log(JSON.stringify(data));
        	if(parseInt(data.code) != 1){
				alert(data.info);
				return;
			}
			
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
	
	
	$("#import_users").click(function(){
		$("#upload_file_users").click();
	});
	
    
});

