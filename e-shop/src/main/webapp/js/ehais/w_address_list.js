
$(function(){
	$("header .fa-chevron-left").click(function(){window.history.go(-1);});
	$("header .fa-plus-circle,#newAddress").click(function(){window.location.href="w_address_add";})
	
	
	useraddress_lists();
	
});


function useraddress_lists(){
	$.ajax({
		url : "/ws/useraddress_lists",
		success : function(result){
			var region = {};
			$.each(result.map.region,function(k,v){
				region[v.regionId] = v.regionName;
			});
			var active = null;
			$.each(result.rows,function(k,v){
				active = "";
				if(v.isDefault == 1)active = "active";
				$("#alist").append("<li addressid='"+v.addressId+"'>"+
					"<div class='info'>"+
						"<div class='consignee'>"+v.consignee+"</div>"+
						"<div class='mobile'>"+v.mobile+"</div>"+
						"<div class='address'>"+region[v.province]+region[v.city]+region[v.county]+region[v.district]+v.address+"</div>"+
					"</div>"+
					"<div class='operate'>"+
						"<i class='iconfont icon-default "+active+"'>  默认</i>"+
						"<i class='iconfont icon-lajixiang'>  删除</i>"+
						"<i class='iconfont icon-edit'>  编辑</i>"+
					"</div>"+
				"</li>");
				
				
			});
			active = null;
			
			
			$(".icon-default").click(function(){
				
				var addressid = $(this).parent().parent().attr("addressid");
				var that = this;
				$.ajax({
					url:"/ws/useraddress_default",type:"post",dataType:"json",
					data : {addressId:addressid},
					success : function(result){
						if(result.code == 1){
							$(".icon-default").removeClass("active");
							$(that).addClass("active");
							addressid = null;
							that = null;
						}else{
							
						}
					}
				});
			});
			
			$(".icon-edit").click(function(){
				var addressid = $(this).parent().parent().attr("addressid");
				window.location.href = "w_address_edit?addressId="+addressid;
			});
			
			$(".icon-lajixiang").click(function(){
				w_address_delete(this);
			});
			
			$(".info").click(function(){
				sessionStorage.setItem("chooseAddressId",$(this).parent().attr("addressid"));
				window.history.go(-1);
			});
			
			
			
		}
	});
}

function w_address_delete(that){
	var addressid = $(that).parent().parent().attr("addressid");
	elay.confirm({
	    content: '确定要删除此地址吗？'
	    ,btn: ['确定' , '取消']
	    ,sure: function(index){  
	    	
			$.ajax({
				url : "/ws/useraddress_delete_sumbit",
				data : {addressId : addressid},
				success : function(result){
					
					elay.open({
					    content: result.msg,
					    btn: '知道了',
					    yes: function(index){  
					    	if(result.code ==1){
					    		location.reload();
					    	}
					    }
					    });
				},error : function(err,xhr){
					
				}
			});
			
	    }
	 });
}
