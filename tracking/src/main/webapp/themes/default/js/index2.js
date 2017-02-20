/**
 * 
 */
$(function(){
	$(".course-menu .course-item").hover(function(){$(this).addClass("open");},function(){$(this).removeClass("open");});
	
	randAgency();

});

//随机排序服务代理机构
function randAgency(){
	var ul = document.getElementById("ul_agency"),
    lis = Array.prototype.slice.call(ul.childNodes, 0);
 
	var i = lis.length;
	var rand = 0;
	while(i-- > 0){
		rand = parseInt(Math.random() * lis.length);
		ul.insertBefore(lis[i], lis[rand]);
	}
}