//	返回上一页
	$(".header>img:first-child").click(function() {
		window.history.back(-1);
	})
//	每一页底部进入首页
	$(".footer>ul>li:nth-child(1)").click(function() {
		location.href = "index.html";
	})	
//	每一页底部进入订单
	$(".footer>ul>li:nth-child(2)").click(function() {
		location.href = "2-1_myorder.html";
	})
//	每一页底部进入我的中心
	$(".footer>ul>li:nth-child(3)").click(function() {
		location.href = "3_usercenter.html";//如果没有登录,先进入登录页面
	})

//	点击注册进入注册
	$(".login_code>p:nth-child(2)>span:nth-child(2)").click(function() {
		location.href = "register.html";
	})	