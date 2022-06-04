/*此文件用于写页面公共JS效果*/
$(function() {
	// 头部浮动
	var header = $('#globalheader');
	var headerHeight = header.outerHeight();
	$(window).scroll(function() {
	  if($(this).scrollTop() >= headerHeight) {
	    header.addClass('fixed').css('left', '0').parent().css('padding-top', headerHeight +'px');
	  } else {
	    header.removeClass('fixed').parent().css('padding-top', '0');
	  }
	});
	// 返回顶部js
	var tophtml="<div id=\"rmenu-izl\" class=\"izl-rmenu\"><div class=\"btn btn-top\"><div class=\"backtop\"></div></div>";
	$("#backtop").html(tophtml);
	$("#rmenu-izl").each(function(){
		$(this).find(".btn-wx").mouseenter(function(){
			$(this).find(".pic").fadeIn("fast");
		});
		$(this).find(".btn-wx").mouseleave(function(){
			$(this).find(".pic").fadeOut("fast");
		});
		$(this).find(".btn-wb").mouseenter(function(){
			$(this).find(".pic").fadeIn("fast");
		});
		$(this).find(".btn-wb").mouseleave(function(){
			$(this).find(".pic").fadeOut("fast");
		});
		$(this).find(".btn-phone").mouseenter(function(){
			$(this).find(".phone").fadeIn("fast");
		});
		$(this).find(".btn-phone").mouseleave(function(){
			$(this).find(".phone").fadeOut("fast");
		});
		$(this).find(".btn-top").click(function(){
			$("html, body").animate({
				"scroll-top":0
			},1000);
		});
	});
	var lastRmenuStatus=false;
	$(window).scroll(function(){//bug
		var _top=$(window).scrollTop();
		if(_top>200){
			$("#rmenu-izl").data("expanded",true);
		}else{
			$("#rmenu-izl").data("expanded",false);
		}
		if($("#rmenu-izl").data("expanded")!=lastRmenuStatus){
			lastRmenuStatus=$("#rmenu-izl").data("expanded");
			if(lastRmenuStatus){
				$("#rmenu-izl .btn-top").slideDown();
			}else{
				$("#rmenu-izl .btn-top").slideUp();
			}
		}
	});
});
// <a href=\"tencent://message/?uin=3532326912&Site=qq&Menu=yes\" class=\"btn btn-qq\" target=\"_blank\"></a>
// <div class=\"btn btn-phone\"><div class=\"phone\">18703636357</div></div>