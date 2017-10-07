$(function(){
	setInterval(new_order_tip,10000);
});

function new_order_tip(){
	var timestamp = Date.parse(new Date());
	timestamp = timestamp / 1000;
	
	$.ajax({
		url : "/ws/new_order_tip",type:"post",dataType:"json",data:{paytime:timestamp},
		success:function(result){
			if(result.code == 1 && result.total > 0){
				//语音提示
				$('embed').remove();   
		         $('body').append('<embed src="http://ovug9f17p.bkt.clouddn.com/neworder.mp3" autostart="true" hidden="true" loop="false">'); 
			}
		}
	});
}
