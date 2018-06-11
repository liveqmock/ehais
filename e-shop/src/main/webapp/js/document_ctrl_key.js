$(function() {

	$(document).keydown(function(e) {
		key_event("keydown",e);
	});

	$(document).keypress(function(e) {
		key_event("keypress",e);
	});

	$(document).keyup(function(e) {
		key_event("keyup",e);
	});


	//设置页面所有控件的顺序
	set_tab_index();
	
	$(".dialog.glyphicon.glyphicon-remove")
	.click(function(){dialog_close(this);})
	.keyup(function(e){
		e = window.event || e;
		var keycode = e.keyCode || e.which;
		if(keycode == 27)dialog_close(this);
		keycode = null;
	});
	
});
var inputs = [];
//设置页面所有控件的顺序
function set_tab_index(){
	inputs = [];
	$("input[type='text']").each(function(index,ele){
		$(this).attr("tabindex",index);
		inputs[index] = $(this).attr("id");
	});
}

//统一快捷键监听
function key_event(name,e) {
	e = window.event || e;
	var keycode = e.keyCode || e.which;

	if(e.ctrlKey && keycode == 87) { //屏蔽Ctrl+w    
		e.preventDefault();
		window.event.returnValue = false;
	}
	
	if(e.ctrlKey && keycode == 119) { //屏蔽Ctrl+w    
		e.preventDefault();
		window.event.returnValue = false;
	}

	if(e.ctrlKey && keycode == 82) { //Ctrl + R   
		e.preventDefault();
		window.event.returnValue = false;
	}
	if(e.ctrlKey && keycode == 83) { //Ctrl + S    
		e.preventDefault();
		window.event.returnValue = false;
	}

	if(e.ctrlKey && keycode == 72) { //Ctrl + H   
		e.preventDefault();
		window.event.returnValue = false;
	}
	if(e.ctrlKey && keycode == 74) { //Ctrl + J  
		e.preventDefault();
		window.event.returnValue = false;
	}
	if(e.ctrlKey && keycode == 75) { //Ctrl + K   
		e.preventDefault();
		window.event.returnValue = false;
	}
	if(e.ctrlKey && keycode == 78) { //Ctrl + N  
		e.preventDefault();
		window.event.returnValue = false;
	}
	if((e.ctrlKey && keycode == 187) || (e.ctrlKey && keycode == 107)) { //Ctrl + '+' 
		e.preventDefault();
		window.event.returnValue = false;
	}
	if(e.ctrlKey && keycode == 189) { //Ctrl + '-' 
		e.preventDefault();
		window.event.returnValue = false;
	}
}

//弹出窗退出
function dialog_close(that){
	$(".sDialog,.dialog_bg,.dialog.glyphicon.glyphicon-remove,#"+$(that).attr("id")).removeClass("open");
	$("#name-"+$(that).attr("control")).focus();
}
