var cat_id = 0;
var bsTable ;
var keyword = "";
var mediaDetailModal;
var mediaCategoryModal;
var modifyPwdModal;

$(function(){
	getTree();
	
	mediaDetailModal = $("#mediaDetailModal").modal({ backdrop: 'static', keyboard: false , show : false });
	mediaCategoryModal = $("#mediaCategoryModal").modal({ backdrop: 'static', keyboard: false , show : false });
	modifyPwdModal = $("#modifyPwdModal").modal({ backdrop: 'static', keyboard: false , show : false });
	
	
	$("#addCate").click(function(){addCate();});
	$("#editCate").click(function(){editCate();});
	$("#deleteCate").click(function(){deleteCate();});
	$("#mediaArticleAddDetail").click(function(){mediaArticleAddDetail();});
	$("#logout").click(function(){logout();});
	
	
    $("#btnSearch").click(function(){keyword = $.trim($("#keyword").val());bsTable.bootstrapTable('refresh', { query : {cat_id : cat_id , title : keyword , page : 1} });});
    
    bsTable = $('#bsTable').bootstrapTable({
    	contentType: "application/x-www-form-urlencoded",
        url: 'mediaArticleListJson',//请求后台的url
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
	    pageSize: 50,
	    pageList: [10, 20,30,40,50],
	    queryParamsType:'',
	    queryParams: function (params) {
	        return {
	            rows: params.pageSize,   //页面大小  
                page: params.pageNumber,  //页码        
                sort: params.sort,  //排序列名  
                sortOrder: params.order,//排位命令（desc，asc）
                cat_id : cat_id,
                keyword : keyword
	        }
	    },
        sidePagination: "server", //服务端处理分页
        uniqueId: 'goodsId',//每一行的唯一标识，一般为主键列
        columns: [

			{
			    field: 'articleId',
			    title: '编号'
			},{
			    field: 'catName',
			    title: '视频分类'
			},{
			    field: 'title',
			    title: '标题'
			},{
			    field: 'articleThumb',
			    title: '图片',formatter:function(value,rows,index){
			    	if(!isBlank(value))return "<img src='"+value+"' width='60'>";
			    	return "";
			    }
			},{
			    field: 'articleDate',
			    title: '发布日期',
			    formatter : function(value,rows,index){
			    	return value == null ? "" : value.formatDate("yyyy-MM-dd");
			    }
			}, {
	            field: 'openType',
	            title: '首页推荐',
	            formatter : function(value,rows,index){
	            	var c = "" ;
	            	if(value) c = "active";
	            	
	            	return "<i href='javascript:;' class='"+c+" iconfont icon-remai' onClick='setOpenType(this,"+rows.articleId+");'></i>";
	            }
	        }, {
	            field: 'isHot',
	            title: '推荐',
	            formatter : function(value,rows,index){
	            	var c = "" ;
	            	if(value) c = "active";
	            	
	            	return "<i href='javascript:;' class='"+c+" iconfont icon-remai' onClick='setHot(this,"+rows.articleId+");'></i>";
	            }
	        },
		{
            field: 'articleId',
            title: '操作',
            formatter : function(value,row,index){
            	var a = "<a href ='javascript:;' onclick='mediaArticleEditDetail("+value+");'>编辑</a>";
            	var b = "&nbsp;|&nbsp;<a href ='javascript:;' onclick='mediaArticleDelete("+value+");' >删除</a>";
            	return a+b;
            }
        }
        
        ],
        responseHandler : function (res){
        	var cat = res.map.listCat ;
        	var catObj = {};
        	for(var i = 0 ; i < cat.length ; i ++){
        		catObj[cat[i].catId] = cat[i].catName;
        	}
        	var rows = res.rows;
        	for(var i = 0 ; i < rows.length ; i++){
        		rows[i].catName = catObj[rows[i].catId];
        	}
        	return res;
        }
    });
    
    
    
    $("#ftp_videoUrl").click(function(){
    	getFTP();
    });
    
    
    $("#modifyPwd").click(function(){
    	modifyPwdModal.modal("show");
    });
    
    
    $("#mediaMedifyPwdFormSubmit").click(function(){
    	mediaMedifyPwdFormSubmit();
    });
});

function setHot(that,id){
	var isHot = 1;
	if($(that).hasClass("active")){
		isHot = 0;
	}
	
	$.ajax({
		url : "/ws/set_hot_article",data:{articleId:id,hot:isHot},
		success : function(d){
			if(d.code == 1){
				$(that).toggleClass("active");
			}
		}
	});
}

function setOpenType(that,id){
	var openType = 1;
	if($(that).hasClass("active")){
		openType = 0;
	}
	
	$.ajax({
		url : "/ws/set_open_type_article",data:{articleId:id,open_type:openType},
		success : function(d){
			if(d.code == 1){
				$(that).toggleClass("active");
			}
		}
	});
}

function getTree() {
	$("#tree li").unbind().remove();
	$("#tree").append("<li val='0' class='active'>全部</li>");
	$.ajax({
		url : "mediaArticleCatListJson",type:"post",dataType:"json",data:{},
		success : function(result){
			var rows = result.rows;
			var nodes = new Array();
			for(var i = 0 ; i < rows.length ; i++){
				$("#tree").append("<li val='"+rows[i].catId+"'>"+rows[i].catName+"</li>");
			}
			
			$("#tree li").click(function(index,ele){
				$("#tree li").removeClass("active");
				$(this).addClass("active");
				cat_id = $(this).attr("val");
				bsTable.bootstrapTable('refresh', { query : {cat_id : cat_id , title : keyword , page : 1} });
			});
			
		}
	});
	

}

function mediaArticleDelete(articleId){
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "mediaArticleDelete",type:"post",dataType:"json",data:{articleId:articleId},
			success:function(result){
				layer.msg(result.msg);
				bsTable.bootstrapTable('refresh');
			}
		});
	}, function(){
		layer.closeAll();
	});
}

//修改密码
function mediaMedifyPwdFormSubmit(){
	
	var old_password = $("#old_password").val();
	var new_password = $("#new_password").val();
	var confirmed_password = $("#confirmed_password").val();
	if(old_password.length == 0){
		layer.msg("请输入旧密码");
		return false;
	}
	if(new_password.length == 0){
		layer.msg("请输入新密码");
		return false;
	}
	if(confirmed_password.length == 0){
		layer.msg("请输入确认密码");
		return false;
	}
	if(new_password != confirmed_password){
		layer.msg("确认密码不一致");
		return false;
	}
	
	
	$.ajax({
		url : "mediaModifyPwdSubmit",type:"post",dataType:"json",data:{
			old_password:old_password,
			new_password:new_password,
			confirmed_password:confirmed_password
		},
		success:function(result){
			layer.msg(result.msg);
			modifyPwdModal.modal("hide");
		}
	});
	
	
}

function addCate(){
	$("input,textarea").val("");
	mediaCategoryModal.modal("show");
	$("#mediaCategoryFormModal").attr("action","add");
	
	$("#qiniu_image_images").remove();
	$("#eq_preview_wrapper_images").addClass("hide");
	
//	layer.prompt({title: '请输入分类名称', formType: 0}, function(text, index){
//	    console.log(text);
//		if(text == null || text == ""){
//			layer.msg("请输入分类名称");return ;
//		}
//		$.ajax({
//			url : "mediaArticleCatAddSubmit",
//			data  : {catName : text},
//			success : function(result){
//				if(result.code != 1){
//					layer.msg(result.msg);
//					return ;
//				}
//				layer.close(index);
//				layer.msg(result.msg);
//				getTree();
//			}
//		});
//	});
}
function editCate(){
	
	$("input,textarea").val("");
	
	$("#qiniu_image_images").remove();
	$("#eq_preview_wrapper_images").addClass("hide");
	$("#mediaCategoryFormModal").attr("action","edit");
	
	var node = $('#tree li.active');	
	if(node == null || node.length == 0 || parseInt(node.attr("val")) == 0){
		layer.msg("请选择分类");
		return ;
	}
	var id = node.attr("val");
	
	$.ajax({
		url : "mediaArticleCatEdit",data:{catId:id},dataType:"json",type:"post",
		success:function(result){
			
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			});
			$("#cat_id").val(id);
			if(!isBlank(result.model.images))show_images_pic(result.model.images);
			
			$("#mediaDetailFormModal").attr("action","edit");
			
			mediaCategoryModal.modal("show");
			
			
		}
	});
	

//	layer.prompt({title: '请输入分类名称', formType: 0 ,value : node.text()}, function(text, index){
//	    
//		if(text == null || text == ""){
//			layer.msg("请输入分类名称");return ;
//		}
//		$.ajax({
//			url : "mediaArticleCatEditSubmit",
//			data  : {catName : text,catId : id},
//			success : function(result){
//				if(result.code != 1){
//					layer.msg(result.msg);
//					return ;
//				}
//				layer.close(index);
//				layer.msg(result.msg);
//				getTree();
//			}
//		});
//	});
	
}
function deleteCate(){
	var node = $('#tree li.active');	
	if(node == null || node.length == 0 || parseInt(node.attr("val")) == 0){
		layer.msg("请选择分类");
		return ;
	}
	var id = node.attr("val");
	
	layer.confirm('您确定要删除此项吗？',{
		btn: ['确定删除','不删除'] //按钮
	}, function(){
		$.ajax({
			url : "mediaArticleCatDelete",data:{catId:id},
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

//组合分类的下拉菜单选项
function getSelectCat(){
	$("#catId option:not(:first-child)").remove();
	$("#tree li").each(function(index,ele){
		if(parseInt($(ele).attr("val")) > 0){
			$("#catId").append("<option value='"+$(ele).attr("val")+"' "+($(ele).hasClass("active")?" selected='selected' ":"")+">"+$(ele).text()+"</option>");
		}
	});
}

function mediaArticleAddDetail(){
	$("#qiniu_image_articleThumb").remove();
	$("#eq_preview_wrapper_articleThumb").addClass("hide");
	
	$("#qiniu_image_videoUpload").remove();
	$("#eq_preview_wrapper_videoUpload").addClass("hide");
	//分类下拉菜单
	getSelectCat();
	//初始化值
	$("input,textarea").val("");
//	ue.setContent("");
	mediaDetailModal.modal("show");	
	
	
	$("#mediaDetailFormModal").attr("action","add");
}

function mediaArticleEditDetail(articleId){
	
	$("#qiniu_image_articleThumb").remove();
	$("#eq_preview_wrapper_articleThumb").addClass("hide");
	
	$("#qiniu_image_videoUpload").remove();
	$("#eq_preview_wrapper_videoUpload").addClass("hide");
	
	
	//分类下拉菜单
	getSelectCat();
	$.ajax({
		url : "mediaArticleEditDetail",data:{articleId:articleId},dataType:"json",type:"post",
		success:function(result){
			
			$.each(result.model,function(k,v){
				$("#"+k).val(v);
			});
			if(!isBlank(result.model.articleDate))$("#articleDate").val(result.model.articleDate.substr(0,10));
			
			if(!isBlank(result.model.articleThumb))show_articleThumb_pic(result.model.articleThumb);
			if(!isBlank(result.model.videoUrl))show_videoUpload_pic("/images/video.png");
//			ue.setContent(result.model.content);
			
			mediaDetailModal.modal("show");
			$("#mediaDetailFormModal").attr("action","edit");
			
			
		}
	});
}

function logout(){
	layer.confirm('您确定要退出登录吗？',{
		btn: ['确定退出','取消'] //按钮
	}, function(){
		$.ajax({
			url : "mediaLogout.json",data:null,
			success:function(result){
				window.location.href = "login.me";
			}
		});
	}, function(){
		layer.closeAll();
	});
}


function getFTP() {

	$.ajax({
		url : "ftp.json",type:"post",dataType:"json",data:{},
		success : function(result){
			
			
		}
	});
	

}

