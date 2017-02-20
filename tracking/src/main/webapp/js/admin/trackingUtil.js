/**
 * 
 */
var school_id = "";
var department_id = "";
var professional_id = "";
var grades_id = "";


function getSchoolList(){
	$.post("/admin/school_list_json",{page:1,len:100000},function(result){
	    
	  },"json");
}

function getDepartmentList(school_id){
	$.post("/admin/department_list_json",{school_id:school_id,page:1,len:100000},function(result){
	    var list = result.rows;
	    if(typeof(list) == undefined)list = Array();
	    for(var i = 0 ; i < list.length ; i++){
	    	$("#departmentId").append("<option value='"+list[i].id+"'>"+list[i].name+"</option>");
	    }
	    if(department_id!=""){
	    	$("#departmentId").val(department_id);
	    	$("#s2id_departmentId").children("a").children("span").html($("#departmentId").find("option:selected").text());
	    }
	},"json");
}

function getProfessionalList(department_id){
	$.post("/admin/professional_list_json",{department_id:department_id,page:1,len:100000},function(result){
		var list = result.rows;
	    if(typeof(list) == undefined)list = Array();
	    for(var i = 0 ; i < list.length ; i++){
	    	$("#professionalId").append("<option value='"+list[i].id+"'>"+list[i].name+"</option>");
	    }
	    if(professional_id!=""){
	    	$("#professionalId").val(professional_id);
	    	$("#s2id_professionalId").children("a").children("span").html($("#professionalId").find("option:selected").text());
	    }
	},"json");
}

function getGradesList(professional_id){
	$.post("/admin/grades_list_json",{professional_id:professional_id,page:1,len:100000},function(result){
		var list = result.rows;
	    if(typeof(list) == undefined)list = Array();
	    for(var i = 0 ; i < list.length ; i++){
	    	$("#gradesId").append("<option value='"+list[i].id+"'>"+list[i].name+"</option>");
	    }
	    if(grades_id!=""){
	    	$("#gradesId").val(grades_id);
	    	$("#s2id_gradesId").children("a").children("span").html($("#gradesId").find("option:selected").text());
	    }
	},"json");
}


$(function(){
	$("#schoolId").change(function(){
		console.log($(this).val());
		$("#departmentId option:not(:first)").remove();
		$("#professionalId option:not(:first)").remove();
		$("#gradesId option:not(:first)").remove();
		getDepartmentList($(this).val());
	});
	$("#departmentId").change(function(){
		console.log($(this).val());
		$("#professionalId option:not(:first)").remove();
		$("#gradesId option:not(:first)").remove();
		getProfessionalList($(this).val());
	});
	$("#professionalId").change(function(){
		console.log($(this).val());
		$("#gradesId option:not(:first)").remove();
		getGradesList($(this).val());
	});
});