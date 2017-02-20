/**
 * 
 */
$(function(){
	$("#submit").click(function(){
		questionnaire_submit();
	});
	
	$("#add_questions").click(function(){
		questions_container_load();
	});
	
});

//提交问卷信息
function questionnaire_submit(){
	$.ajax({
        
        url : "questionnaire_save_submit",
        data : {
        	id : $("#id").val().trim(),
        	title : $("#title").val().trim(),
        	instructions : $("#instructions").val().trim(),
        	startTime : $("#startTime").val().trim(),
        	endTime : $("#endTime").val().trim(),
        },
		method : 'POST',
        dataType : 'json',
        beforeSend : function(jqXHR) {
        	$("#submit").attr("disabled",true);
        },
        success : function(data) {
        	$("#submit").val("编辑提交");
        	$("#submit").attr("disabled",false);
        	if(data.code != 1){        		
        		return ;
        	}
        	
        	$.gritter.add({
                title: '毕业生服务系统提示',
                text: '问卷信息保存成功',
                time: 2000,
                position: 'center'
           });
        	if($("#id").val() == ""){
        		questions_container_load();
        	}
        	//展示添加问题
        	$("#id").val(data.model.id);
        	$("#questions_container").removeClass("dn");
        	$("#option_container").removeClass("dn");
        	
        },
        error : function() {
            alert('error');
        }
    });
}

//删除问题
function questions_delete(_this){
	var index = $(_this).parent().attr("data-index");
	
	if(!confirm("确定要删除此记录吗？")){
		return ;
	}
	
	$.ajax({        
        url : "questions_delete",
        data : {
        	keyId : $("#questionsId_"+index).val().trim(),
        	
        },
		method : 'POST',
        dataType : 'json',
        beforeSend : function(jqXHR) {
        	
        },
        success : function(data) {
        	
        	if(data.code != 1){
        		return ;
        	}
        	
        	window.location.reload();
        },
        error : function(e) {
            alert(JSON.stringify(e));
        }
    });
	
	
}

//提交问题
function questions_submit(_this){
	var index = $(_this).parent().attr("data-index");
	var title = $("#questionsTitle_"+index).val();
	var type = $("#questionsType_"+index).val();
	var notEmpty = ($("#bs_checkbox_questionsNotEmpty_"+index+"_1").is(':checked')) ? 1 : 0 ;
	var sort = $("#questions_container .questions_item").index($("#questions_container .questions_item.questions_item_"+index));
	
	
	$.ajax({        
        url : "questions_save_submit",
        data : {
        	id : $("#questionsId_"+index).val().trim(),
        	questionnaireId : $("#id").val().trim(),
        	title : title,
        	type : type,
        	notEmpty : notEmpty,
        	sort : sort,
        },
		method : 'POST',
        dataType : 'json',
        beforeSend : function(jqXHR) {
        	$("#submit").attr("disabled",true);
        },
        success : function(data) {
        	$("#submit").val("编辑更新");
        	$("#submit").attr("disabled",false);
        	if(data.code != 1){
        		$("#submit").attr("disabled",false);
        		return ;
        	}
        	$("#questionsId_"+index).val(data.model.id);
        	
        	questions_answer_submit(index);	
        	
        },
        error : function(e) {
            alert(JSON.stringify(e));
        }
    });
}

//提交问题答案
function questions_answer_submit(index){
	var qaData = new Array();
	var isBool = true;
	$("#questions_answer_item_"+index+" .questions_answer_item").each(function(eleindex,ele){
		
		if($("#questionsAnswerName_"+index+"_"+(eleindex+1)).val() == null || $("#questionsAnswerName_"+index+"_"+(eleindex+1)).val() == ""){
			isBool = false;			
			alert("存在空答案，请填写!");			
			return isBool;
		}
		
		var qaItem = new Array();
		qaItem[0] = $("#questionsAnswerId_"+index+"_"+(eleindex+1)).val();
		qaItem[1] = $("#questionsId_"+index).val();
		qaItem[2] = $("#id").val();//questionnaireId
		qaItem[3] = $("#questionsAnswerName_"+index+"_"+(eleindex+1)).val();//name
		qaItem[4] = (eleindex+1);//sort
		qaData.push(qaItem);
	});
	
	if(!isBool)return ;
	
	
	var qaExtend = JSON.stringify(qaData);
	console.log(qaExtend);
	
	$.ajax({        
        url : "questions_answer_save_submit",
        data : {
        	extend : qaExtend,
        },
		method : 'POST',
        dataType : 'json',
        beforeSend : function(jqXHR) {
        },
        success : function(data) {
        	if(data.code != 1){
        		return ;
        	}
        	var list = data.rows;
        	for(var i = 0 ; i < list.length ; i ++){
        		$("#questionsAnswerId_"+index+"_"+(list[i].sort)).val(list[i].id);
        	}
        	
        	$.gritter.add({
                title: '系统提示',
                text: '保存成功',
                time: 2000,
                position: 'center'
           });
        	
        },
        error : function() {
            alert('error');
        }
    });
	
	
}

//加载插入的题目
function questions_container_load(){
	$.ajax({
        
        url : "questions_insert",
        data : {
        	questionnaireId : $("#id").val().trim(),
        },
		method : 'POST',
//		traditional : true,
        dataType : 'html',
        beforeSend : function(jqXHR) {
        },
        success : function(data) {
        	if($(".questions_item").length >= 1)$("#questions_container").append("<hr>");
        	$("#questions_container").append(data.replaceAll("_Index" , ($(".questions_item").length + 1) ));
        	
        	//设置绑定元素
        	bind_control();
        	
        },
        error : function() {
            alert('error');
        }
    });
}


function questions_answer_item_load(_this){
	var index = $(_this).attr("data-index");
	
	$.ajax({
        
        url : "questions_answer_insert",
        data : {
        	
        },
		method : 'POST',
//		traditional : true,
        dataType : 'html',
        beforeSend : function(jqXHR) {
        	
        },
        success : function(data) {
        	
        	$("#questions_answer_item_"+index).append(data.replaceAll("_Index",index).replaceAll("_Item" , ($("#questions_answer_item_"+index+" .questions_answer_item").length + 1) ));
        	
        	$("a.tip.edit").removeClass("dn");
        	$("a.tip.delete").removeClass("dn");
        	$("a.tip.up").removeClass("dn");
        	$("a.tip.down").removeClass("dn");
        },
        error : function() {
            alert('error');
        }
    });
	
}



function questions_group_list(_this){
	var index = $(_this).attr("data-index");
	
	$.ajax({
        
        url : "questions_group_list",
        data : {
        	id : $("#id").val()
        },
		method : 'POST',
//		traditional : true,
        dataType : 'html',
        beforeSend : function(jqXHR) {
        	
        },
        success : function(data) {
        	
        	$("#questions_container").removeClass("dn").html(data);
        	$("#option_container").removeClass("dn");
        	
        	$("#questions_container .questions_item").each(function(index , ele){
        		$("#questions_container").append($(ele).prop("outerHTML").replaceAll("_Index",(index + 1)));
        		$(ele).remove();
        		
        		var questionsId = $("#questionsId_"+(index+1)).val();
        		
        		$("#questions_container").children(".questions_answer_item").each(function(index1 , ele1){
            		//console.log("questionsAnswerId__Index__Item:"+$(ele1).children("#questionsAnswerId__Index__Item").val()+" -- "+$(ele1).children("#questionsId__Index__Item").val());
            		if(parseInt(questionsId) == parseInt($(ele1).children("#questionsId__Index__Item").val())){
            			$("#questions_answer_item_"+(index+1)).append(
            					$(ele1).prop("outerHTML")
            					.replaceAll("_Index",(index + 1))
            					.replaceAll("_Item",parseInt($("#questions_answer_item_"+(index+1)).children(".questions_answer_item").length) + 1)
            					);
            			//console.log("questions_answer_item_"+(index+1) + ":" +(parseInt($("#questions_answer_item_"+(index+1)).children(".questions_item").length) + 1));
            			
            			$(ele1).remove();
            		}
            	});
        		
        		var questionsType = $("#questionsType_"+(index+1)).val();
        		
        		if(parseInt(questionsType) < 3){
        			//console.log("#questionsType_"+(index+1)+":"+questionsType);
        			$(".aqnd_"+(index+1)).removeClass("dn");
        		}
        		
        	});
        	
        	
        	bind_control();
        	
        	if($("body").hasClass("mainReport")){
        		$("input").attr("disabled",true);
        		$("select").attr("disabled",true);
        		$("textarea").attr("disabled",true);
        		$("#cancel").attr("disabled",false);
        		
        		$(".questions_item").each(function(index,ele){
        			var quesionIdTmp = $(ele).children("div").children("input[type=hidden]");
        			if(typeof(quesionIdTmp) == undefined)return true;//continure
        			
        			$(ele).attr("id","quesionId_"+quesionIdTmp.val());
        			
        			var selectType = $(ele).children("div").children("div").children("div").children("select");
        			if(typeof(selectType) == undefined)return true;
        			//console.log("questionId:"+quesionIdTmp.attr("id")+"-"+quesionIdTmp.val()+"--"+questionType.val());
        			var questionType = getQuestionType(selectType.val());
        			//console.log("questionType:"+questionType+"-"+selectType.val());
        			if(typeof(questionType) == undefined || questionType == "")return true;
        			
        			$(ele).children("div").children(".questions_answer_items").children(".questions_answer_item").each(function(index,ele2){
            			if(typeof($(ele2).children("input[type=hidden]")) == undefined)return true;

            			$(ele2).attr("id",questionType+quesionIdTmp.val()+"_"+$(ele2).children("input[type=hidden]").val());
            			
            			$(ele2).append("<div class='data_statistics'>0</div>");
            		});
        			
        		});
        		
        		//读取答案
        		questions_gather_load();
        	}
        },
        error : function() {
            alert('error');
        }
    });
	
}

function getQuestionType(val){
	switch(parseInt(val)){
	case 1:
		return "rdo_";
	case 2:
		return "cbx_";
	case 3:
		return "txt_";
	default :
		return "";	
	}
}

function questions_gather_load(_this){
	
	$.ajax({
        
        url : "questions_gather_list_json",
        data : {
        	questionnaireId : $("#id").val()
        },
		method : 'POST',
        dataType : 'json',
        beforeSend : function(jqXHR) {
        	
        },
        success : function(data) {
        	if(data.code != 1){
        		alert(data.msg);
        	}
        	var list = data.rows;
        	for(var i = 0 ; i < list.length ; i ++){
        		var gatherContent = JSON.parse(list[i].gatherContent.replaceAll('\"','"'));
        		$.each(gatherContent,function(name,value) {
        			console.log(name+"--"+value);
        			if(name.indexOf("rdo_")>=0){
        				$("#"+name+"_"+value).children(".data_statistics").html(parseInt($("#"+name+"_"+value).children(".data_statistics").html()) + 1);       				
        			}else if(name.indexOf("cbx_")>=0){
        				$("#"+name).children(".data_statistics").html(parseInt($("#"+name).children(".data_statistics").html()) + 1);
        			}else if(name.indexOf("txt_") >= 0){
        				$("#quesionId_"+name.replaceAll("txt_",""))
        				.children("div")
        				.children(".questions_answer_items")
        				.append("<div class='txt_li'>"+value+"</div>");
        			}
        		});
        	}
        	
        	$(".questions_answer_items").each(function(index,ele){
        		var sum = 0;
        		$(ele).children("div").children(".data_statistics").each(function(num,ele2){
        			sum += parseInt($(ele2).html());
        		});
        		if(sum == 0)return true;
        		
        		$(ele).children("div").children(".data_statistics").each(function(num,ele2){
        			$(ele2).attr("data-val",$(ele2).html());
        			var bar = (parseInt($(ele2).html()) / sum * 100).toFixed(2) ;
        			$(ele2).html(
        					"<div class=\"progress progress-mini progress-danger active progress-striped\">"+
        					"<div style=\"width: "+bar+"%;\" class=\"bar\"></div>"+
        					"</div>"+
        					
        					"<span class=\"percent\">"+bar+"%("+$(ele2).attr("data-val")+" / "+sum+")</span>"
        					
        					);
        		});
        		
        	});
        },
        error : function(e) {
            
        }
    });
	
}

function bind_control(){

	$(".questions_item").unbind();
	$(".questions_item").mouseover(function(){
		$(this).addClass("cur");
	}).mouseout(function(){
		$(this).removeClass("cur");
	});

	$("#questions_container select").unbind();
	$("#questions_container select").change(function(){
		var index = $(this).attr("id").replaceAll("questionsType_","");
		$(".aqnd_" + index).addClass("dn");
		if($(this).val() == "1" || $(this).val() == "2"){
			$(".aqnd_" + index).removeClass("dn");
		}
		console.log($(this).val() + " " + $(this).attr("id"));
	});

	$(".addQuestionAnswer").unbind();
	$(".addQuestionAnswer").click(function(){
		questions_answer_item_load(this);
	});

	$(".questionEdit").unbind();
	$(".questionEdit").click(function(){
		questions_submit(this);
	});

	$(".questionDelete").unbind();
	$(".questionDelete").click(function(){
		questions_delete(this);
	});
}


