var MENU_ITEM_HEIGHT = 45;
var MIN_PNAEL_HEIGHT = 8 * MENU_ITEM_HEIGHT;
var MAX_PNAEL_HEIGHT = 20 * MENU_ITEM_HEIGHT;
var SCROLL_HEIGHT = 4 * MENU_ITEM_HEIGHT;
var cur_login_user_id = "cw";
var bigMenuIcons = [ '@crs', 'reportshop', 'comm', 'erp', 'exam_manage', 'hrms', 'info', 'mytable', 'project', 'roll_manage', 'sale_manage', 'sys', 'system', 'training', 'workflow', 'address', 'netdisk', 'picture', 'wiki', 'wf_entrust', 'wf_destory', 'wf_log', 'wf_mine', 'wf_new', 'wf_query', 'wf_stat', 'sms', 'person_info', 'todo', 'notify', 'notify_auditing', 'email', 'calendar', 'diary', 'bbs', 'meeting', 'attendance', 'attendance_manage', 'work_plan', 'vehicle', 'score', 'vote', 'fax', 'file_folder', 'news', 'itask' ];

var timer_sms_mon = null;
var timer_online_tree_ref = null;

var closeNocPanel = null;
var nocbox_close_timeout = 3;
var timeLastLoadOnline = 0;
var nextTabId = 0;

(function($) {
	$.fn.addTab = function(option) {
		var url ="/admin/"+ option.url;
		$('.over-mask-layer').hide();
		$('#overlay_panel').hide();
		if (!option.id) {
			return;
		}
		if (option.cid) {
			option.id = option.id + "_child_" + option.cid;
		}
		if (option.params) {
			url =initParams(url, option.params);
		}
		closable = (typeof (option.closable) == 'undefined') ? true : option.closable;
		selected = (typeof (option.selected) == 'undefined') ? true : option.selected;
		var height = isTouchDevice() ? 'auto' : '100%';
		$('#tabs_container').tabs('add', {
			id : option.id,
			title : option.title,
			subtitle : option.subtitle,
			closable : closable,
			selected : selected,
			style : 'height:' + height + ';',
			content : '<iframe id="tabs_' + option.id + '_iframe" name="tabs_' + option.id + '_iframe" allowTransparency= "true" src="' + url + '" onload="IframeLoaded(\'' + option.id + '\');" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:' + height + ';"></iframe>'
		});
	};
	$.fn.selectTab = function(id) {
		$('#tabs_container').tabs('select', id);
	};
	$.fn.closeTab = function(id) {
		$('#tabs_container').tabs('close', id);
	};
	$.fn.getSelected = function() {
		return $('#tabs_container').tabs('selected');
	};

	// 延迟图片加载
	function LoadImage(selector) {
		$(selector).each(function() {
			this.src = this.getAttribute("_src");
			this.removeAttribute("_src");
		});
	}
	function initParams(url, params) {
		var timestamp = new Date().getTime();
		if (params) {
			url += "?time=" + timestamp;
			var temp = "";
			$.each(params, function(k, v) {
				temp += "&" + k + "=" + v;
			});
			url += temp;
		}
		return url;
	}
	function checkActive(id) {
		if ($('#' + id + '_panel:hidden').length > 0)
			$('#' + id).removeClass('active');
		else
			window.setTimeout(checkActive, 300, id);
	}
	function getSecondMenuHTML(id) {
		var html = '';
		for (var i = 0; i < second_array[id].length; i++) {
			var func_id = 'f' + second_array[id][i];
			if (!func_array[func_id])
				continue;

			var func_name = func_array[func_id][0];
			var func_code = func_array[func_id][1];
			var open_window = func_array[func_id][3] ? func_array[func_id][3] : '';
			var bExpand = func_code.substr(0, 1) == "@" && third_array[func_id];
			func_code = func_code.replace("LOGIN_USER_ID", cur_login_user_id)
			var onclick = bExpand ? "" : "createTab(" + func_id.substr(1) + ",'" + func_name.replace("'", "\'") + "','" + func_code.replace("'", "\'") + "','" + open_window + "');";

			html += '<li><a id="' + func_id + '" href="javascript:;" onclick="' + onclick + '"' + (bExpand ? ' class="expand"' : '') + ' hidefocus="hidefocus"><span>' + func_name + '</span></a>';
			if (bExpand) {
				html += '<ul>';
				for (var j = 0; j < third_array[func_id].length; j++) {
					var func_id1 = 'f' + third_array[func_id][j];
					var func_name1 = func_array[func_id1][0];
					var func_code1 = func_array[func_id1][1];
					var open_window1 = func_array[func_id1][3] ? func_array[func_id1][3] : '';
					var onclick1 = "createTab(" + func_id1.substr(1) + ",'" + func_name1.replace("'", "\'") + "','" + func_code1.replace("'", "\'") + "','" + open_window1 + "');";
					html += '<li><a id="' + func_id1 + '" href="javascript:;" onclick="' + onclick1 + '" hidefocus="hidefocus"><span>' + func_name1 + '</span></a></li>';
				}
				html += '</ul>';
			}
			html += '</li>';
		}

		return '<ul id="second_menu">' + html + '</ul>';
	}
	;

	function resizeLayout() {
		// 主操作区域高度
		var wWidth = (window.document.documentElement.clientWidth || window.document.body.clientWidth || window.innerHeight);
		var wHeight = (window.document.documentElement.clientHeight || window.document.body.clientHeight || window.innerHeight);
		var nHeight = $('#north').is(':visible') ? $('#north').outerHeight() : 0;
		var fHeight = $('#funcbar').is(':visible') ? $('#funcbar').outerHeight() : 0;
		var cHeight = wHeight - nHeight - fHeight - $('#south').outerHeight() - $('#hero_bar').outerHeight() - $('#taskbar').outerHeight();
		$('#center').height(cHeight);
		$("#center iframe").css({
			height : cHeight
		});

		// 一级标签宽度
		var width = wWidth - $('#taskbar_left').outerWidth() - $('#taskbar_right').outerWidth();
		$('#tabs_container').width(width - $('#tabs_left_scroll').outerWidth() - $('#tabs_right_scroll').outerWidth() - 2);
		$('#taskbar_center').width(width - 1); // -1是为了兼容iPad

		$('#tabs_container').triggerHandler('_resize');
	}
	;

	// 菜单滚动箭头事件,id为first_menu
	function initMenuScroll(id) {
		// 菜单向上滚动箭头事件
		$('#' + id + ' > .scroll-up:first').hover(function() {
			$(this).addClass('scroll-up-hover');
			if (id == 'first_panel') {
				$("#first_menu > li > a.active").removeClass('active'); // 恢复一级active的菜单为正常
			}
		}, function() {
			$(this).removeClass('scroll-up-hover');
		});

		// 点击向上箭头
		$('#' + id + ' > .scroll-up:first').click(function() {
			var ul = $('#' + id + ' > ul:first');
			ul.animate({
				'scrollTop' : (ul.scrollTop() - SCROLL_HEIGHT)
			}, 600);
		});

		// 向下滚动箭头事件
		$('#' + id + ' > .scroll-down:first').hover(function() {
			$(this).addClass('scroll-down-hover');
			if (id == 'first_panel') {
				$("#first_menu > li > a.active").removeClass('active'); // 恢复一级级active的菜单为正常
			}
		}, function() {
			$(this).removeClass('scroll-down-hover');
		});

		// 点击向下箭头
		$('#' + id + ' > .scroll-down:first').click(function() {
			var ul = $('#' + id + ' > ul:first');
			ul.animate({
				'scrollTop' : (ul.scrollTop() + SCROLL_HEIGHT)
			}, 600);
		});
	}
	;

	function initStartMenu() {
		// 点击页面，隐藏各级菜单面板，并清除二级和三级菜单的active状态
		$('#overlay_startmenu').click(function() {
			if ($('#start_menu_panel:visible').length) {
				$('#overlay_startmenu').hide();
				$('#start_menu_panel').slideUp(300);
				$('#start_menu').removeClass('active');
			}
		});

		// 鼠标点击导航图标按钮弹出菜单面板
		$('#start_menu').bind('click', function() {
			if ($('#start_menu_panel:visible').length) {
				$('#overlay_startmenu').hide();
				$('#start_menu_panel').slideUp(300);
				$(this).removeClass('active');
			}

			// 延迟加载图片
			LoadImage("#first_menu img[_src]");

			// 设置导航图标为active状态
			$(this).addClass('active');

			// 遮罩层位置和显示
			$('#overlay_startmenu').show();

			// 菜单面板位置
			var top = $('#start_menu').offset().top + $('#start_menu').outerHeight() - 6;
			$('#start_menu_panel').css({
				top : top
			});
			$('#start_menu_panel').slideDown('fast');

			// //计算并设置菜单面板的高度,是否显示滚动箭头
			var scrollHeight = $("#first_menu").attr('scrollHeight');
			if ($("#first_menu").height() < scrollHeight) {
				var height = ($('#south').offset().top - $('#start_menu').offset().top) * 0.7; // 可用高度为开始菜单和状态栏高差的70%
				height = height - height % MENU_ITEM_HEIGHT; // 可用高度为
				// MENU_ITEM_HEIGHT
				// 的整数倍
				// 如果可用高度大于允许的最高高度，则限制
				height = height <= MAX_PNAEL_HEIGHT ? height : MAX_PNAEL_HEIGHT;
				// 如果可用高度超过scrollHeight，则设置高度为scrollHeight
				height = height > scrollHeight ? scrollHeight : height;
				$('#first_menu').height(height);
			} else {
				var height = scrollHeight > MIN_PNAEL_HEIGHT ? scrollHeight : MIN_PNAEL_HEIGHT;
				$('#first_menu').height(height);
			}
			if ($("#first_menu").height() >= $("#first_menu").attr('scrollHeight')) {
				$('#first_panel > .scroll-up:first').hide();
				$('#first_panel > .scroll-down:first').hide();
			}

			// 计算并设置二级菜单面板的位置
			$('#second_panel > .second-panel-menu').css('height', $('#first_menu').height());

			// 第一次打开时设置二级菜单滚动事件
			if ($('#second_panel > .second-panel-menu > .jscroll-c').length <= 0)
				$('#second_panel > .second-panel-menu').jscroll();
		});
		// 生成一级菜单
		var html = "";
		for (var i = 0; i < first_array.length; i++) {
			var menu_id = first_array[i];
			if (typeof (func_array['m' + menu_id]) != "object") {
				continue;
			}
			var image = !func_array['m' + menu_id][2] ? 'icon_default' : func_array['m' + menu_id][2];
			html += '<li><a id="m' + menu_id + '" href="javascript:;" hidefocus="hidefocus"><img src="../res/theme/default/images/menu_icon/transparent.gif" align="absMiddle" _src="../res/theme/default/images/menu_icon/' + image + '.png"/> ' + func_array['m' + menu_id][0] + '</a></li>';
		}
		$("#first_menu").html(html);
		$("#first_menu").mousewheel(function() {
			$('#first_menu').stop().animate({
				'scrollTop' : ($('#first_menu').scrollTop() - this.D)
			}, 300);
		});

		// 一级菜单滚动箭头事件
		initMenuScroll('first_panel');

		// 一级菜单hover和click事件
		$("#first_menu > li > a").click(function() {
			// 如果当前一级菜单为active，则返回
			if (this.className.indexOf('active') >= 0)
				return;

			$("#second_menu > li > a.expand").removeClass('active'); // 恢复二级expand菜单为正常
			$("#first_menu > li > a.active").removeClass('active'); // 恢复一级级active的菜单为正常

			// 获取当前一级菜单下属二级菜单的HTML代码，并更新二级菜单面板
			$('#second_panel > .second-panel-menu').html(getSecondMenuHTML(this.id));
			$("#" + this.id).addClass('active'); // 将当前一级菜单设为active

			// 二级级菜单滚动事件
			$('#second_panel > .second-panel-menu').jscroll();

			// 二级菜单点击展开三级菜单
			$('#second_menu > li > a.expand').click(function() {
				$(this).toggleClass('active');
				$(this).parent().children('ul').toggle();
				$('#second_panel > .second-panel-menu').jscroll();
			});
		});

		if (menuExpand != "" && typeof (second_array['m' + menuExpand]) == "object") {
			// 展开定义的二级菜单
			$('#m' + menuExpand).addClass('active');
			$('#second_panel > .second-panel-menu').html(getSecondMenuHTML('m' + menuExpand));

			// 二级菜单点击展开三级菜单
			$('#second_menu > li > a.expand').click(function() {
				$(this).toggleClass('active');
				$(this).parent().children('ul').toggle();
				$('#second_panel > .second-panel-menu').jscroll();
			});
		} else {
			// 登录时把常用任务菜单项作为二级菜单的内容
			var html = "";
			for (var i = 0; i < shortcutArray.length; i++) {
				if (typeof (func_array['f' + shortcutArray[i]]) != "object")
					continue;

				var func_id = 'f' + shortcutArray[i];
				var func_name = func_array[func_id][0];
				var func_code = func_array[func_id][1];
				var open_window = func_array[func_id][3] ? func_array[func_id][3] : "";

				if (func_code.substr(0, 1) == "@")
					continue;

				var onclick = "createTab(" + func_id.substr(1) + ",'" + func_name.replace("'", "\'") + "','" + func_code.replace("'", "\'") + "','" + open_window + "');";
				html += '<li><a id="' + func_id + '" href="javascript:;" onclick="' + onclick + '" hidefocus="hidefocus"><span>' + func_name + '</span></a></li>';
			}
			html = '<ul id="second_menu">' + html + '</ul>';
			$('#second_panel > .second-panel-menu').html(html);
		}
		$('#second_panel, #second_menu').bind('selectstart', function() {
			return false;
		});

		// 在线状态相关事件
		$('#start_menu_panel > .panel-user > .avatar').hover(function() {
		}, function() {
			window.setTimeout(function() {
				$('#on_status').fadeOut();
			}, 300);
		});
		$('#start_menu_panel > .panel-user > .avatar').click(function() {
			$('#on_status').fadeIn(300, function() {
				$(this).css('filter', 'progid:DXImageTransform.Microsoft.shadow(strength=5, direction=135, color=#a3a4a8);');
			});
		});

		$('#on_status > a').click(function() {
			var status = $(this).attr('status');
			if (status < "1" || status > "4")
				return;

			$.get("ipanel/pheader.php", {
				ON_STATUS_SET : status
			});
			$('#start_menu_panel > .panel-user > .avatar > .status_icon').attr('class', 'status_icon status_icon_' + status);
			$('#on_status').fadeOut(300);
		});
	}

	function initTabs() {
		$('#tabs_container').tabs({
			tabsLeftScroll : 'tabs_left_scroll',
			tabsRightScroll : 'tabs_right_scroll',
			panelsContainer : 'center',
			secondTabsContainer : 'funcbar_left'
		});
		$('#tabs_container').bind('_close', function() {
			if ($('a.tab', this).length <= 0)
				$('#portal').trigger('click');
		});
	}
	function initSelectCommunity() {
		$('#select_community').bind('click', function() {
			if ($('#' + this.id + '_panel:visible').length) {
				$('#' + this.id + '_panel').animate({
					top : -$('#' + this.id + '_panel').outerHeight()
				}, 900, function() {
					$(this).hide();
				});
				$('#overlay_panel').hide();
				return;
			}
			if ($('#' + this.id + '_panel').html() == "") {
				$('#' + this.id + '_panel').html('<iframe id="select_community_iframe" name="select_community_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:100%;"></iframe>');
				$('#' + this.id + '_panel iframe').attr('src','/admin/community/list_selectcommunity_layout.do');
			} else {
				if (typeof (frames['select_community_iframe'].init) == 'function')
					frames['select_community_iframe'].init();
			}
			$('.over-mask-layer').hide();
			$('#overlay_panel').show();
			$('#' + this.id + '_panel').css('left', ($(document).width() - $('#' + this.id + '_panel').width()) / 2);
			var top = $('#' + this.id + '_panel').outerHeight() > $('#center').outerHeight() ? 0 : 20;
			$('#' + this.id + '_panel').css({
				top : -$('#' + this.id + '_panel').outerHeight()
			});
			$('#' + this.id + '_panel').show().animate({
				top : top-20
			}, 800);
			$(this).addClass('active');
			window.setTimeout(checkActive, 300, this.id);
		});
	}
	
	function initOwningcount() {
		$('#task_center').bind('click', function() {
			if ($('#' + this.id + '_panel:visible').length) {
				$('#' + this.id + '_panel').animate({
					top : -$('#' + this.id + '_panel').outerHeight()
				}, 900, function() {
					$(this).hide();
				});
				$('#overlay_panel').hide();
				return;
			}
			if ($('#' + this.id + '_panel').html() == "") {
				$('#' + this.id + '_panel').html('<iframe id="task_center_iframe" name="task_center_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:100%;"></iframe>');
				$('#' + this.id + '_panel iframe').attr('src', '/admin/owningcount/list_owningcount_layout.do');
			} else {
				if (typeof (frames['task_center_iframe'].init) == 'function')
					frames['task_center_iframe'].init();
			}
			$('.over-mask-layer').hide();
			$('#overlay_panel').show();
			$('#' + this.id + '_panel').css('left', ($(document).width() - $('#' + this.id + '_panel').width()) / 2);
			var top = $('#' + this.id + '_panel').outerHeight() > $('#center').outerHeight() ? 0 : 20;
			$('#' + this.id + '_panel').css({
				top : -$('#' + this.id + '_panel').outerHeight()
			});
			$('#' + this.id + '_panel').show().animate({
				top : top-20
			}, 800);
			$(this).addClass('active');
			window.setTimeout(checkActive, 300, this.id);
		});
	}
	function initSettingGroup() {
		$('#setting_group').bind('click', function() {
			if($('#' + this.id + '_panel:visible').length){
				$('#' + this.id + '_panel').animate({
					top :　-$('#' +　this.id + '_panel').outerHeight()
				},900,function(){
					$(this).hide();
				});
				$("#overlay_panel").hide();
				return;
			}
			if ($('#' + this.id + '_panel').html() == "") {
				$('#' + this.id + '_panel').html('<iframe id="setting_group_iframe" name="setting_group_iframe" allowTransparency= "true" src="" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" style="width:100%;height:100%;"></iframe>');
				$('#' + this.id + '_panel iframe').attr('src','/admin/setting/setting_community_turn.do');
			}
			$('.over-mask-layer').hide();
			$('#overlay_panel').show();
			$('#' + this.id + '_panel').css('left', ($(document).width() - $('#' + this.id + '_panel').width()));
			var top = $('#' + this.id + '_panel').outerHeight() > $('#center').outerHeight() ? 0 : 20;
			$('#' + this.id + '_panel').css({
				top : -$('#' + this.id + '_panel').outerHeight()
			});
			$('#' + this.id + '_panel').show().animate({
				top : top-20
			}, 600);
			$(this).addClass('active');
			window.setTimeout(checkActive, 300, this.id);
		});
	}
	function initPersonInfo() {
		$('#person_info').bind('click', function() {
			$().addTab({
				id : '11',
				title : func_array["f11"][0],
				url : func_array["f11"][1],
				closable : func_array["f11"][3]

			});

		});
	}
	function initLogout() {
		$('#logout').bind('click', function() {
			Dg.exit('祝您工作愉快,万事如意');
			$('#overlay_startmenu').hide();
			$('#start_menu_panel').slideUp(300);
			$('#start_menu').removeClass('active');
			return false;
		});
		$("#changePassword").bind('click', function() {
			Dg.open2({
				url: "/admin/user/password_user_turn.do",
				title:"为确保账户安全,请修改您的登陆密码",
				h : '200px',
				w : '500px'
			});
			$('#overlay_startmenu').hide();
			$('#start_menu_panel').slideUp(300);
			$('#start_menu').removeClass('active');
		});
		$('#logout_topbar').bind('click', function() {
				Dg.exit('祝您工作愉快,万事如意');
				$('#overlay_startmenu').hide();
				$('#start_menu_panel').slideUp(300);
				return false;
		});
	}

	function initHideTopbar() {
		// 隐藏topbar事件
		$('#hide_topbar').bind('click', function() {
			$('#north').slideToggle(300, function() {
				resizeLayout();
			});
			$(this).toggleClass('up');

			var hidden = $(this).attr('class').indexOf('up') >= 0;
			$.cookie('hideTopbar', (hidden ? '1' : null), {
				expires : 1000,
				path : '/'
			});
		});

		if ($.cookie('hideTopbar') == '1')
			$('#hide_topbar').triggerHandler('click');
	}
	$(window).resize(function() {
		resizeLayout();
	});
	$(document).ready(function() {
	// $('#loading').remove();
		if (isTouchDevice()) {
			$('body').addClass('mobile-body');
			$('#center').addClass('mobile-center');
		}
		resizeLayout();
		initStartMenu();
		initTabs();
		initSelectCommunity();
		initOwningcount();
		initSettingGroup();
		initPersonInfo();
		initLogout();
		initHideTopbar();
		// 加载主页
		for (var i = 0; i < portalLoadArray.length; i++) {
			$().addTab({
				id : 'portal_' + portalArray[portalLoadArray[i]].id,
				title : portalArray[portalLoadArray[i]].title,
				url : portalArray[portalLoadArray[i]].url,
				closable : portalArray[portalLoadArray[i]].closable,
				selected : (i == 0)
			});
		}

	

		// 点击overlay_panel时自动收回门户或者切换主题的面板
		$("#overlay_panel").click(function() {
			if ($("#task_center_panel:visible").length) {
				$("#task_center").trigger("click");
			}  else if ($("#theme_panel:visible").length) {
				$("#theme").trigger("click");
			} else if ($("#setting_group_panel:visible").length){
				$("#setting_group").trigger("click");
			}
		});

		// 紧急通知相关事件
		if ($('#notify_panel').length > 0) {
			$('#overlay').show();
			$('#notify_panel').show();
			$('#notify_panel').css('left', ($(window).width() - $('#notify_panel').width()) / 2);

			$('#notify_panel .btn-ok').click(function() {
				$.cookie($(this).attr("cookie_name"), $(this).attr("cookie_value"), {
					expires : 1000,
					path : '/'
				});
				$('#notify_panel .btn-close').click();
			});
			$('#notify_panel .btn-close').click(function() {
				$('#overlay').hide();
				$('#notify_panel').hide();
			});
			$('#notify_panel .head-close').click(function() {
				$('#notify_panel .btn-close').click();
			});
		}

		CheckBkImg('div,a,ul,li,span');
	});
})(jQuery);

function CheckBkImg(selector) {
	$(selector).each(function() {
		$(this).css('background-image');
	});
}

// 修复setTimeout bug，使用window.setTimeout调用
if (!+'\v1') {
	(function(f) {
		window.setTimeout = f(window.setTimeout);
		window.setInterval = f(window.setInterval);
	})(function(f) {
		return function(c, t) {
			var a = [].slice.call(arguments, 2);
			return f(function() {
				c.apply(this, a)
			}, t)
		}
	});
}

var $1 = function(id) {
	return document.getElementById(id);
};

function HTML2Text(html) {
	var div = document.createElement('div');
	div.innerHTML = html;
	return div.innerText;
}

function Text2Object(data) {
	try {
		var func = new Function("return " + data);
		return func();
	} catch (ex) {
		return '<b>' + ex.description + '</b><br /><br />' + HTML2Text(data) + '';
	}
}

function createTab(id, name, code, open_window) {
	$('#overlay_startmenu').triggerHandler('click');
	$('#funcbar_left > div.second-tabs-container').hide();
	openURL(id, name, code, open_window);
	$(document).trigger('click');
}

function closeTab(id) {
	id = (typeof (id) != 'string') ? $().getSelected() : id;
	$().closeTab(id);
}

function IframeLoaded(id) {
	var iframe = window.frames['tabs_' + id + '_iframe'];
	if (iframe && $1('tabs_link_' + id) && $1('tabs_link_' + id).innerText == '') {
		$1('tabs_link_' + id).innerText = !iframe.document.title ? td_lang.inc.msg_27 : iframe.document.title;// "无标题"
	}
}

function openURL(id, name, url, open_window, width, height, left, top) {
	id = !id ? ('w' + (nextTabId++)) : id;
	if (open_window != "1") {
		$().addTab({
			id : id,
			title : name,
			url : url,
			closable : true
		});
	}
	$(document).trigger('click');
}

function gotoURL(id, url) {
	$1('tabs_' + id + "_iframe").src = url;
}

function BlinkTabs(id) {
}

function getEvent() // 同时兼容ie和ff的写法
{
	if (document.all)
		return window.event;
	func = getEvent.caller;
	while (func != null) {
		var arg0 = func.arguments[0];
		if (arg0) {
			if ((arg0.constructor == Event || arg0.constructor == MouseEvent) || (typeof (arg0) == "object" && arg0.preventDefault && arg0.stopPropagation)) {
				return arg0;
			}
		}
		func = func.caller;
	}
	return null;
}



function strlen(str) {
	return str.replace(/[^\x00-\xff]/g, 'xx').length;
}

function isTouchDevice() {
	try {
		document.createEvent("TouchEvent");
		return userAgent.indexOf("mobile") >= 0 || userAgent.indexOf("maxthon") < 0;
	} catch (e) {
		return false;
	}
}

function closePortal() {
	$("#portal").trigger("click");
}

function closeTaskCenter() {
	$("#task_center").trigger("click");
}

// 按模块路径设置Win8样式
function SetWin8Style(url) {
	RemoveWin8Style('body');

	if (typeof (url) != 'string')
		return;

	var module = '';
	url = url.toLowerCase();
	if (url.substr(0, 9) != '/admin/')
		return;

	module = url.substr(9);
	if (module.indexOf('/') > 0) {
		var sub_module = module.substr(module.indexOf('/') + 1);
		module = module.substr(0, module.indexOf('/'));
		if (module == 'portal') {
			if (sub_module.indexOf('/') > 0)
				module = sub_module.substr(0, sub_module.indexOf('/'));
			else
				module = sub_module;

			module = 'portal_' + module;
		}
	} else if (module.indexOf('?') > 0) {
		module = module.substr(0, module.indexOf('?'));
	} else if (module.indexOf('#') > 0) {
		module = module.substr(0, module.indexOf('#'));
	}

	if (module == '' || module.indexOf('.') > 0)
		return;

	$('body').addClass('win8_module_' + module);
}
function RemoveWin8Style(selector) {
	var className = $(selector).attr('className');
	if (typeof (className) != 'string')
		return;

	var classArray = className.split(' ');
	for (var i = 0; i < classArray.length; i++) {
		if (classArray[i].substr(0, 12) == 'win8_module_')
			$(selector).removeClass(classArray[i]);
	}
}
/**
 * 关闭当前选中tab
 */
function closeTab() {
	var tabs_container = top.$("#tabs_container");
	$().closeTab(tabs_container.find('.active').attr('id').substr(5));
}
/**
 * 添加tab
 */
function addTab(option) {
	var tabs_container = top.$("#tabs_container");
	$().addTab(option);
}
/**
 * 获取当前选择选项卡ID
 */
function Current_iframeID() {
	var tabs_container = $("#tabs_container");
	return "tabs_" + tabs_container.find('.active').attr('id').substr(5) + "_iframe";
};
/**
 * 获取当前选择选项卡ID
 */
function getTabId() {
	var tabs_container = $("#tabs_container");
	return tabs_container.find('.active').attr('id').substr(5) + "_iframe";
};
function getTabById(tableId) {
	return $('#' + tableId)[0];
};
function Current_iframe() {
	return $('#' + Current_iframeID())[0];
};