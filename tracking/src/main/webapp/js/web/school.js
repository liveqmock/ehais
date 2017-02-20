$(function(){
	loadSchool();
	$("#school").change(function(){
		var that = this;
		$("#department option").remove();
		$("#professional option").remove();
		$("#grades option").remove();
		$.ajax({
			url:"/api/department",
			data:{schoolId:$(that).val()},
			dataType:"json",
			type:"post",
			success:function(data,status){
				$("#department").append('<option value="">请选择院系</option>');
				var rows = data.rows;
				$.each(rows,function(key,val){
					$("#department").append('<option value="'+val.id+'">'+val.name+'</option>');
				});
			},error:function(error){
				
			},beforeSend:function(xhr){
				$.ejq.showMask("亲,正努力加载中...");
			},complete:function(xhr){
				$.ejq.hideMask();
			}
		});
	});
	$("#department").change(function(){
		var that = this;
		$("#professional option").remove();
		$("#grades option").remove();
		$.ajax({
			url:"/api/professional",
			data:{departmentId:$(that).val()},
			dataType:"json",
			type:"post",
			success:function(data,status){
				$("#professional").append('<option value="">请选择专业</option>');
				var rows = data.rows;
				$.each(rows,function(key,val){
					$("#professional").append('<option value="'+val.id+'">'+val.name+'</option>');
				});
			},error:function(error){
				
			},beforeSend:function(xhr){
				$.ejq.showMask("亲,正努力加载中...");
			},complete:function(xhr){
				$.ejq.hideMask();
			}
		});
	});
	$("#professional").change(function(){
		var that = this;
		$("#grades option").remove();
		$.ajax({
			url:"/api/grades",
			data:{professionalId:$(that).val()},
			dataType:"json",
			type:"post",
			success:function(data,status){
				$("#grades").append('<option value="">请选择班级</option>');
				var rows = data.rows;
				$.each(rows,function(key,val){
					$("#grades").append('<option value="'+val.id+'">'+val.name+'</option>');
				});
			},error:function(error){
				
			},beforeSend:function(xhr){
				$.ejq.showMask("亲,正努力加载中...");
			},complete:function(xhr){
				$.ejq.hideMask();
			}
		});
	});
	$("#grades").change(function(){
		var that = this;
	});
	
	$("#submit").click(function(){
		submit();
	});

});

function loadSchool(){
	$.ajax({
		url:"/api/school",
		data:null,
		dataType:"json",
		type:"post",
		success:function(data,status){
			$("#school").append('<option value="">请选择学校</option>');
			var rows = data.rows;
			$.each(rows,function(key,val){
				$("#school").append('<option value="'+val.id+'">'+val.name+'</option>');
			});
		},error:function(error){
			
		},beforeSend:function(xhr){
			$.ejq.showMask("亲,正努力加载中...");
		},complete:function(xhr){
			$.ejq.hideMask();
		}
	});
}

function submit(){
	if($("#school").val().length == 0){
		$.ejq.toast("请选择学校");return;
	}
	if($("#department").val().length == 0){
		$.ejq.toast("请选择院系");return;
	}
	if($("#professional").val().length == 0){
		$.ejq.toast("请选择专业");return;
	}
	if($("#grades").val().length == 0){
		$.ejq.toast("请选择班级");return;
	}
	if($("#name").val().length == 0){
		$.ejq.toast("请输入姓名方便教师确认");return;
	}
	if($("#studentId").val().length == 0){
		$.ejq.toast("请输入学号方便教师确认");return;
	}
	if($("#mobile").val().length == 0){
		$.ejq.toast("请手机号码方便教师联系");return;
	}

	$.ajax({
		url:"/web/student_bind/submit",
		data:$("#student_form").serialize(),
		dataType:"json",
		type:"post",
		success:function(data,status){
			if(data.code != 1){
				$.ejq.toast(data.msg);return ;
			}
			window.location.href = data.action;
		},error:function(error){
			
		},beforeSend:function(xhr){
			$.ejq.showMask("亲,正努力加载中...");
		},complete:function(xhr){
			$.ejq.hideMask();
		}
	});
	
	
}