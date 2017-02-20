/**
 * 
 */
$(function(){
	$("#catId").change(function(){
		$("#extend_tab").addClass("dn");
		$("#extend").html("");
		var curCatId = $(this).val();
		var code = "";
		var catList = $("#catIdList").val();
		var table = $("#table").val();
		var table_id = $("#tableId").val();
		catList = eval('(' + catList + ')');
		if(typeof(catList) == "undefined")return ;
		for(var cat in catList){
			if(parseInt(curCatId) == parseInt(catList[cat].id)){
				code = (typeof(catList[cat].code) == "undefined"?"":catList[cat].code);
			}			
		}
		if(code.length == 0)return ;
		
		$.post("/extendsFields/extends_fields_info",
				{code : code,table:table,table_id:table_id},
				function(data){
					if(data.length == 0)return;
					data = data.trim();
					if(data.length == 0)return;//防止是出现前后空格
					$("#extend_tab").removeClass("dn");
					$("#extend").html(data);
				},"html");
	});
});