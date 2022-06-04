/*首页JS定义*/
var bEmailPriv = true;
var bSmsPriv = true;
var bTabStyle = true;
var OA_TIME = new Date(2016, 05, 26, 16, 11, 37);
var bInitWeather = true;
// var weatherCity = ConvertWeatherCity("北京_北京_北京");
var weatherCity = null;
var menuExpand = "01";
var monInterval = {
	online : 120,
	sms : 30
};
var ispirit = "";
var statusTextScroll = 60;
var newSmsSoundHtml = "<object id='sms_sound' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='/static/js/swflash.cab' width='0' height='0'><param name='movie' value='/static/wav/1.swf'><param name=quality value=high><embed id='sms_sound' src='/static/wav/1.swf' width='0' height='0' quality='autohigh' wmode='opaque' type='application/x-shockwave-flash' plugspace='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash'></embed></object>";
var show_ip = 1;
var show_button = "0";
var unit_name = '&nbsp;';
var jsonURL0 = '%2Finc%2Fonline.php%3FPARA_URL2%3D%2Fgeneral%2Fipanel%2Fuser%2Fuser_info.php%26PARA_TARGET%3D_blank%26PARA_ID%3DWINDOW%26PARA_VALUE%3D1%26SHOW_IP%3D1%26PWD%3D%26OP_SMS%3D1';
var jsonURL1 = '%2Finc%2Fuser_list%2Ftree.php%3FMANAGE_FLAG%3D0%26DEPT_ID%3D0%26PARA_URL1%3D%26PARA_URL2%3D%2Fgeneral%2Fipanel%2Fuser%2Fuser_info.php%26e%3D%26PARA_TARGET%3D_blank%26PRIV_NO%3D%26PARA_ID%3DWINDOW%26PARA_VALUE%3D1%26MODULE_ID%3D2%26SHOW_IP%3D1%26PWD%3D%26DEPT_PRIV%3D1%26ROLE_PRIV%3D2%26PRIV_NO_FLAG%3D0%26OP_SMS%3D1%26rand%3D1698834496';
var user_total_count = "20";
var func_array = [];
var first_array = [];
var second_array = [];
var third_array = [];
var cuslist = [];
var shortCut = "";
var shortcutArray = Array(1, 4, 147, 8, 130, 5, 131, 9, 16, 15, 76, 62);
var loginUser = {
	uid : 1,
	user_id : "admin",
	user_name : "系统管理员"
};
var logoutText = "轻轻的您走了，正如您轻轻的来……";
var menuExpand = "";
var bTabStyle = true;
var portalArray = [];
var ostheme = "default";
portalArray["0"] = {
	id : "5",
	src : "../res/theme/images/portal/4.png",
	url : "../admin/desktop.do",
	title : "&nbsp;&nbsp;个人桌面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
	closable : false
};
var themeArray = [];
themeArray["13"] = {
	src : "../res/theme/vogue/images/themeswitch/theme_thumb_13.jpg",
	title : "默认"
};
themeArray["15"] = {
	src : "../res/theme/vogue/images/themeswitch/theme_thumb_15.jpg",
	title : "现代"
};
// -- 一级菜单对应的字体图标 --
var module2iconfont = {
	"01" : "&#xe653;",
	"02" : "",
	"05" : "&#xe6cc;",
	"10" : "&#xe65a;",
	"13" : "&#xe648;",
	"20" : "&#xe655;",
	"30" : "&#xe612;",
	"40" : "&#xe611;",
	"45" : "&#xe645;",
	"50" : "&#xe64e;",
	"65" : "&#xe64c;",
	"70" : "&#xe64a;",
	"85" : "&#xe648;",
	"90" : "&#xe651;",
	"95" : "&#xe6cd;",
	"a0" : "&#xe649;",
	"b0" : "&#xe647;",
	"d0" : "&#xe652;",
	"dd" : "&#xe646;",
	"e0" : "&#xe613;",
	"y0" : "&#xe64b;",
	"z0" : "&#xe650;",
	"shortcut" : "&#xe65b;",
	"default" : "&#xe67a;"
};

// -- 系统消息提示条字体图标 --
var msgTipIconfont = {
	"0" : "&#xe65d;",
	"1" : "&#xe61e;",
	"2" : "&#xe619;",
	"3" : "&#xe64f;",
	"4" : "&#xe61a;",
	"5" : "&#xe616;",
	"6" : "&#xe615;",
	"7" : "&#xe65a;",
	"8" : "&#xe638;",
	"9" : "&#xe618;",
	"10" : "&#xe63a;",
	"11" : "&#xe64d;",
	"12" : "&#xe61c;",
	"13" : "&#xe61d;",
	"14" : "&#xe65e;",
	"15" : "&#xe614;",
	"16" : "&#xe638;",
	"17" : "&#xe65b;",
	"24" : "&#xe617;",
	"message" : "&#xe65c;",
	"default" : ""
};
/* 判断密码是否原始密码 如果是则提示对方修改 */
var pwsflag;
// -- 自定义链接 --
func_array["extWebApp"] = [];
ArtTemp = function(data) {
	var me = this;
	first_array = first_array.concat([ 'shortcut' ], data.first_array);
	$(data.fun_array).each(function(i, v) {
		for ( var key in v) {
			func_array[key] = v[key];
		}
	});

	$(data.second_array).each(function(i, v) {
		for ( var key in v) {
			second_array[key] = v[key];

		}
	});
	second_array["mshortcut"] = shortcutArray;
	$(data.third_array).each(function(i, v) {
		for ( var key in v) {
			third_array[key] = v[key];

		}
	});
	$(data.cuslist).each(function(i, v) {
		func_array["extWebApp"].push(v.customurlId);
		func_array["f" + v.customurlId] = [ v.connectName, v.connectAddress, "010.png" ];
	});
	/*
	 * // -- 个人收藏 -- func_array["extFav"] = []; // 快捷菜单 func_array["mshortcut"] = [
	 * "快捷菜单", "shortcut" ]; func_array["fshortcut"] = [ "快捷菜单",
	 * "person_info/index.php?MAIN_URL=shortcut", "", "" ];
	 */
	pwsflag = data.flag;
	ostheme = data.theme;
	shortCut = data.shortCut;
};
Dg.z.ajax("/admin/resource.do", null, function(data) {
	artTemp = new ArtTemp(data);
});
// -- 天气 --
var weather_json = [ {
	"date" : "5\u670826\u65e5",
	"img1" : "0",
	"img2" : "0",
	"weather" : "\u6674",
	"temperature" : "30\u2103~16\u2103",
	"wind" : "4-5\u7ea7\u8f6c\u5fae\u98ce"
}, {
	"date" : "5\u670827\u65e5",
	"img1" : "0",
	"img2" : "0",
	"weather" : "\u6674",
	"temperature" : "32\u2103~18\u2103",
	"wind" : "3-4\u7ea7\u8f6c\u5fae\u98ce"
}, {
	"date" : "5\u670828\u65e5",
	"img1" : "1",
	"img2" : "0",
	"weather" : "\u591a\u4e91\u8f6c\u6674",
	"temperature" : "30\u2103~19\u2103",
	"wind" : "\u5fae\u98ce"
}, {
	"date" : "5\u670829\u65e5",
	"img1" : "0",
	"img2" : "0",
	"weather" : "\u6674",
	"temperature" : "31\u2103~19\u2103",
	"wind" : "\u5fae\u98ce"
} ];

// -- 当前系统主题
var timer_sms_mon = null;
var timer_online_tree_ref = null;
// 微讯箱自动关闭时间，秒
var closeNocPanel = null;
var nocbox_close_timeout = 3;
var timeLastLoadOnline = 0;
var nextTabId = 0;
var maxSendSmsId = 0;
var newSmsArray = [];
var selectedRecvSmsIdStr = selectedSendSmsIdStr = "";
var uploadsuccess = "上传成功";
var sendsuccess = "发送成功";
var uploaderror = "上传失败";
var uploadnumlimit = "最多上传5个附件";
var uploadduplicate = "该文件已加入上传队列";
var weathertip = "未开启天气预报";
var unselectuser = "暂未选择发送人员";
var no_content_tip = "发送内容不能为空";
var theme_select_priv = 0;
var theme_select_tip = '对不起，您没有换肤权限';
var cur_login_user_id = "admin";
var cur_login_user_priv = "1";
var gz_postfix = "";
var static_server = "";
self.moveTo(0, 0);
self.resizeTo(screen.availWidth, screen.availHeight);
self.focus();
var portalLoadArray = [ "0" ];
$(function() {
	// getLodop();
	if (pwsflag) {
		Dg.z.tip("您的密码为原始密码，请尽快修改！", 4000, 300, "error");
		Dg.open2({
			url : "/admin/user/password_user_turn.do",
			title : "首次登陆修改初始密码(888888)",
			btn : [ '确定' ],
			closeBtn : false,
			h : '200px',
			w : '500px'
		});
	}

	$("#community").change(function() {
		var params = {
			communityId : $("#community").val()
		}
		Dg.z.ajax("/admin/change_community_sub.do", params, function(data) {
			location.replace(location.href);
		});
	});
});
function openLog() {
	$().addTab({
		id : 'portal_000',
		title : '更新公告',
		url : '/admin/updatelog.do',
		closable : true
	});
}

