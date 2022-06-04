/**
 * @description:flyme插件常用函数封装类
 */
Fm = {
	id : ""
};
/**
 * 描述:获取选择复选框值 name:复选框name
 */
Fm.getCheckBoxValue = function(name) {
	var c_v = [];
	$(c_v).empty();
	$('input[name=' + name + ']:checked').each(function() {
		c_v.push($(this).val());
	});
	return c_v.join(",");
};
/**
 * 清除单选框选中状态
 */
Fm.clearRadioSelected = function(name) {
	$('input[name=' + name + ']').each(function(i, item) {
		item.checked = false;
	});
};
Fm.checkRadio = function() {
	var ipt;
	$('dd').click(function(e) {
		ipt = $('input', this)[0];
		ipt.checked = !ipt.checked;
	});
}
/* 替换值 */
Fm.formatter = function(array, v) {
	var value = '-';
	for ( var item in array) {
		if (item == v) {
			value = array[item];
			break;
		}
	}
	return "<span class='red'>" + value + "</span>";
}
/**
 * 描述:获取选择复选框属性值 name:复选框name
 */
Fm.getCheckBoxAttr = function(name, attrName) {
	var c_v = [];
	$('input[name=' + name + ']:checked').each(function() {
		c_v.push($(this).attr(attrName));
	});
	return c_v.join(",");
}
/**
 * 描述:复选框全选/全不选 name:复选框name 页面用法:<label class="checkAllOff"
 * onclick="Am.checkAll(this,'checkbox')" value="false" title="全选">&nbsp;</label>
 */
Fm.checkAll = function(obj, name) {
	var checked = $(obj).attr("value");
	var checkitem = $("input[name='" + name + "']");
	if (checked == "true") {
		checkitem.attr("checked", true);
		$(obj).attr("value", "false");
		$(obj).attr('title', '全不选');
		$(obj).attr('class', 'checkAllOn');
		checkitem.parent().attr('class', 'checked');
		checkitem.parent().parent().find('td').addClass('selected');
	} else {
		checkitem.attr("checked", false);
		$(obj).attr("value", "true");
		$(obj).attr('title', '全选');
		checkitem.parent().removeClass("checked")
		checkitem.parent().parent().find('td').removeClass('selected');
		$(obj).attr('class', 'checkAllOff');
	}
}
/**
 * 描述:复选框反选 name:复选框name
 */
Fm.checkReverse = function(name) {
	$("input[name='" + name + "']").each(function() {
		$(this).attr("checked", !$(this).attr("checked"));
	});
}
/**
 * 描述:复选框父对象级联打钩 id:复选框Id 数据格式:前缀_父ID_子ID(check_1_1)
 */
Fm.checkParent = function(id) {
	var item_id = '';
	var arry = new Array();
	arry = id.split('_');
	for (var i = 0; i < arry.length - 1; i++) {
		item_id += arry[i] + '_';
	}
	item_id = item_id.substr(0, item_id.length - 1);
	if (item_id != "") {
		var check = false;
		var n = 0;
		$("input[id^='" + item_id + "_']").each(function(i) {
			if ($(this).attr("checked")) {
				n = n + 1;
			} else {
				if (n > 1) {
					n = n - 1;
				}
			}
		});
		if (n > 0) {
			$("#" + item_id).attr("checked", true);
		} else {
			$("#" + item_id).attr("checked", false);
		}
		checkParent(item_id);
	}
}
/**
 * 描述:iframe自适应高度 id:iframe对象Id
 */
Fm.iframeresize = function(id) {
	resizeU();
	$(window).resize(resizeU);
	function resizeU() {
		var fh = Fm.getWinHeight();
		$("#" + id).height(fh);
	}
}
/**
 * 描述:获取Ztree对象 ztreeId:ztree对象Id
 */
Fm.getZtree = function(ztreeId) {
	return $.fn.zTree.getZTreeObj(ztreeId);
}

/**
 * 描述:初始化Ztree ztreeId:ztree对象Id setting:ztree设置参数
 */
Fm.initZtree = function(ztreeId, setting) {
	$.fn.zTree.init($("#" + ztreeId), setting);
}
/**
 * 描述:获取选中ztree对象的ID ztreeId:ztree对象Id
 */
Fm.getSelectZtree = function(ztreeId, property) {
	var v = [];
	var nodes = Fm.getZtree(ztreeId).getSelectedNodes();
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});
	$(nodes).each(function(i, item) {
		v.push(item[property]);
	});
	return v.join(",");
}
/**
 * 描述:下拉树形菜单点击事件
 */
Fm.getDropDownZtree = function(e, treeId, treeNode, oId) {
	var textId = oId + "text";
	var treeId = oId + "ztree";
	$("#" + oId).val(Fm.getSelectZtree(treeId, 'id'));
	$("#" + textId).val(Fm.getSelectZtree(treeId, 'name'));
	Fm.fadeOut(oId + 'Div');
}
/**
 * 描述:获取选中带复选框ztree对象的ID ztreeId:ztree对象Id property:树形ID
 */
Fm.getCheckedNodes = function(ztreeId, property) {
	var v = [];
	var nodes = Fm.getZtree(ztreeId).getCheckedNodes(true);
	nodes.sort(function compare(a, b) {
		return a.id - b.id;
	});

	$(nodes).each(function(i, item) {
		v.push(item[property]);

	});

	if (v.length > 0) {
		return v.join(",");

	} else {
		return "";
	}

}
/**
 * 描述：动态加载js或css file：加载文件可以使字符串也可是数组
 */
Fm.loadJc = function(file) {
	var files = typeof (file) == "string" ? [ file ] : file;
	$(files).each(function(i, item) {
		var name = item.replace(/^\s|\s$/g, "");
		var att = name.split('.');
		var ext = att[att.length - 1].toLowerCase();
		var styleTag;
		if (ext == "css") {
			styleTag = document.createElement('link');
			$(styleTag).attr('type', 'text/css');
			$(styleTag).attr('href', name);
			$(styleTag).attr('rel', 'stylesheet');
		} else {
			styleTag = document.createElement('script');
			$(styleTag).attr('type', 'text/javascript');
			$(styleTag).attr('language', 'javascript');
			$(styleTag).attr('src', name);
		}
		$("head")[0].appendChild(styleTag);
	});
}
/**
 * 描述:动态添加输入框到指定元素内 id:文本框Id name:文本框name
 */
Fm.createDom = function() {
	$("<input>", {
		type : 'text',
		id : 'test',
		focusin : function() {
			$(this).addClass('active');
		},
		focusout : function() {
			$(this).removeClass('active');
		}
	}).appendTo($('.htmlHidden'));
}
/**
 * 描述:动态添加隐藏域到指定元素内 id:隐藏Id name:隐藏name
 */
Fm.createHidden = function(id, value) {
	$("<input>", {
		type : 'hidden',
		id : id,
		name : id,
		value : value
	}).appendTo($('.htmlHidden'));
}
/**
 * 描述:隐藏控件 id:控件Id
 */
Fm.fadeOut = function(id) {
	$("#" + id).fadeOut("fast");
}
/**
 * 描述:显示控件 id:控件Id
 */
Fm.fadeIn = function(id) {
	$("#" + id).fadeIn("fast");
}
/**
 * 描述:隐藏显示控件 id:控件Id
 */
Fm.fade = function(id) {
	var tag = $("#" + id).is(":hidden");
	if (tag) {
		$("#" + id).slideDown(300);
		tag = false;
	} else {
		$("#" + id).slideUp(300);
		tag = true;
	}
	return tag;
};
/**
 * 描述:显示某一组属性相同的空间
 */
Fm.show = function(name, size) {
	for (var i = 1; i <= size; i++) {
		var tag = name + i;
		$("#" + tag).show();
	}
};
/**
 * 描述:隐藏某一组属性相同的空间
 */
Fm.hide = function(name, size) {
	for (var i = 1; i <= size; i++) {
		var tag = name + i;
		$("#" + tag).hide();

	}
};
/**
 * 描述:分页工具条翻页 page:页码 form:formId
 */
Fm.pageTurn = function(page, rows) {
	var linkType = config.linkType;
	$("#page").val(page);
	$("#rows").val(rows);
	if (linkType == 1) {
		$('#' + config.formName).submit();
	} else {
		Fm.pagerLoad(page, rows);
	}
};

/**
 * 描述:分页工具条翻页 page:页码 config:页面配置
 */
Fm.pagerLoad = function(page, rows) {
	vars = 'rows=_rows_&page=_page_';
	vars = vars.replace('_page_', page);
	vars = vars.replace('_rows_', rows);
	location.href = initLink(vars);
};
/**
 * 根据值显示或隐藏TR
 */
Fm.showTr = function(tag, value) {
	$("." + tag).each(function(i, item) {
		var showby = $(item).attr("showby");
		var index = $.inArray(value + "", showby.split(","));
		if (index > -1) {
			$(item).show();
		} else {
			$(item).hide();
		}
	});
}
/**
 * 描述:提交FORM form:formId
 */
Fm.submitForm = function(form) {
	$('#' + form).submit();
}
/**
 * 描述:在控件下方弹出层 id:控件Id
 */
Fm.showDiv = function(objId, divId) {
	var obj = $("#" + objId);
	$("#" + divId).css({
		left : obj.offset().left + "px",
		top : obj.offset().top + obj.outerHeight() + "px",
		right : obj.offset().right + "px"
	}).slideDown("fast");
}
/**
 * 描述:刷新页面 id:iframe对象Id
 */
Fm.relaod = function() {
	window.location.reload(true);
};
/**
 * 描述:刷新页面 id:iframe对象Id
 */
Fm.relaodiframe = function(id) {
	window.frames[id].location.reload(true);
};
/**
 * 描述：普通页面跳转 url：打开页面地址
 */
Fm.href = function(url) {
	if (Fm.isNN(url)) {

		if (Fm.getBrowser() != "ie") {
			if (Fm.isNN(url)) {
				window.location.href = url;
			} else {
				Fm.relaod();
			}

		} else {
			window.location.href = url;
		}
	}
	return false;
};
Fm.href = function(url, params) {
	var timestamp = new Date().getTime();
	if (params) {
		$.each(params, function(k, v) {
			url += Fm.getTag(url) + k + "=" + v;
		});
	}
	window.location.href = url;
};
Fm.getTag = function(url) {
	var tag = "?";
	if (url.indexOf("?") > -1) {
		tag = "&"
	}
	return tag;
};
/**
 * 描述:页面返回上一级
 */
Fm.back = function() {
	window.history.back(-1);
}
/**
 * 描述:页面前进后退
 */
Fm.go = function(n) {
	window.history.go(n);
}
/**
 * 描述:对字符串进行编码(后台需要解码)
 */
Fm.encodeURI = function(str) {
	return encodeURI(str);
}
/**
 * 描述:对字符串进行编码
 */
Fm.encodeURIComponent = function(str) {
	return encodeURIComponent(str);
}
/**
 * 描述:判断对象是否存在 obj:需要判断的对象
 */
Fm.isNN = function(obj, msg) {
	var t = true;
	if (typeof (obj) == 'undefined' || obj == "" || obj == null || obj == undefined || obj.lenght == 0) {
		t = false;
		if (msg) {
			top.Dg.z.toastr(msg, 'error', '');
		}
	}
	return t;
}

/**
 * 判断对象是否为空
 */
Fm.isEM = function(obj) {
	var t = false;
	if (typeof (obj) == 'undefined' || obj == "" || obj == null || obj == undefined || obj.lenght == 0) {
		t = true;
	}
	return t;
}
/**
 * 描述:返回操作符 url:需要判断的对象
 */
Fm.getTag = function(url) {
	var tag = "?";
	if (url.indexOf("?") > -1) {
		tag = "&"
	}
	return tag;
}
/**
 * 描述:请求Ajax 带返回值 callback:回调函数
 */
Fm.ajax = function(url, data, callback) {
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		cache : false,
		async : false,
		success : function(data) {
			if (data.info) {
				top.Dg.z.msg(data);
			}
			callback && callback(data);
		}
	});
};
/**
 * 描述:请求Ajax 带返回值 callback:回调函数
 */
Fm.ajax2 = function(url, data, callback) {
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			callback && callback(data);
			if (data.code == 100) {
				top.Dg.z.msg(data);
			}
		}
	});
};
Fm.ajax3 = function(url, data, callback) {
	$.ajax({
		type : "POST",
		url : url,
		data : data,
		dataType : 'json',
		cache : false,
		async : false,
		success : function(data) {
			callback && callback(data);
		}
	});
};
/**
 * 删除附件
 */
Fm.deleteFile = function(fileId) {
	var data = {
		fileId : fileId
	};
	Fm.ajax('../attachment/delete.do', data, function(d) {
		if (d.code = 100) {
			$("#" + fileId).remove();
		} else {
		}
	});
}
/**
 * 删除附件
 */
Fm.deleteFile = function(img, url) {
	var data = {};
	Fm.ajax(url, data, function(d) {
		if (d.code = 100) {
			$(img).parent().parent().remove();
		}
	});
}

/**
 * 删除附件,删除隐藏域赋值,勿删 th 2017/2/7
 */
Fm.deleteFile = function(img, url, id) {
	var data = {};
	Fm.ajax(url, data, function(d) {
		if (d.code) {
			$(img).parent().parent().remove();
			$("input[name='" + id + "']").val("");
		}
	});
}

/**
 * 多附件删除
 */
Fm.deleteFiles = function(img, url, id, inputId) {
	var data = {};
	Fm.ajax(url, data, function(d) {
		var imgs = $("#" + inputId).val().split(",");
		var imsUrl = "";
		for (var i = 0; i < imgs.length; i++) {
			if (imgs[i] != id) {
				imsUrl += "," + imgs[i];
			}
		}
		$("#" + inputId).val(imsUrl.substring(1));
		$(img).parent().parent().remove();
	});
}
/**
 * 删除文件夹包含文件
 */
Fm.deleteFolder = function(folderName) {
	var data = {
		folderName : folderName
	};
	Fm.ajax('../attachment/delfolder.do', data, function(d) {
		if (d.code) {
		} else {

		}
	});
}
/**
 * 检查用户屏幕是否锁定
 */
Fm.checklock = function() {
	Fm.sipajax('checklock.do', function(d) {
		if (d.code) {
			Dg.lock('unlock.do');
			$.dialog.setting.islock = false;
		}
	});
}
/**
 * 检查用户是否修改密码
 */
Fm.checkpwd = function() {
	Fm.sipajax('checkpwd.do', function(d) {
		if (d.code) {
			Dg.dialog('初始登陆请修改密码', 'jsuser/cpwd.do', 'user', 280, 450);
			$.dialog.setting.islock = false;
		}
	});
}
/**
 * 描述:请求Ajax 带返回值 callback:回调函数
 */
Fm.sipajax = function(url, callback) {
	$.ajax({
		type : "POST",
		url : url,
		cache : false,
		async : false,
		success : function(data) {
			// alert(JSON.stringify(data));
			callback(data);
		}
	});
}
/**
 * 描述:删除table中选中的一行 row:行对象
 */
Fm.removeRow = function(row) {
	if ($(row).closest('table').find('tr').size() > 2) {
		$(row).closest('tr').remove();
	} else {
		Dg.alert('别删了,再删都木牛了');
	}
}
/**
 * data-default="信息" onblur="Fm.checkInputBlur($(this))"
 * onfocus="Fm.checkInputFocus($(this))"
 */
Fm.checkInputBlur = function(obj) {
	var default_words = obj.attr("data-default");
	if (obj.val() == "") {
		obj.val(default_words);
		obj.css({
			"color" : "#a9a9a9"
		})
	}
}
Fm.checkInputFocus = function(obj) {
	var default_words = obj.attr("data-default");
	if (obj.val() == default_words) {
		obj.val("").css({
			"color" : "#333333"
		})
	}
}
/**
 * 描述:获取查询JSON字符串 obj:行对象
 */
Fm.getFiltersStr = function(obj) {
	var rule = [];
	var filters = {
		groupOp : 'AND',
		rules : [],
		groups : []
	};
	$("tbody tr", obj).each(function(i) {
		var field = $("#field", this).val(); // (2)获得查询字段
		var op = $("#op", this).val(); // (3)获得查询方式
		var data = $("#data", this).val(); // (4)获得查询值
		rule.push({
			field : field,
			op : op,
			data : data
		});
	});
	filters.rules = rule;
	return JSON.stringify(filters);
}
Fm.JsonToString = function(json) {
	return JSON.stringify(json);
}
/**
 * 描述:统计图生成函数 ID：统计图对象ID url:统计图数据地址 sdata：传递后台的参数
 */
Fm.charts = function(id, url, sdata) {
	if (!Fm.isNN(Fm.id)) {
		Dg.alert(Dg.nocheck);
		return;
	}
	var data = {
		id : Fm.id
	};
	if (Fm.isNN(sdata)) {
		$.extend(data, sdata);
	}
	Fm.ajax(url, data, function(d) {
		// alert(Fm.JsonToString(d));
		$('#' + id).highcharts(d);
	});
}

Fm.chart = function(id, url, sdata) {
	var data = {};
	if (Fm.isNN(sdata)) {
		$.extend(data, sdata);
	}
	Fm.ajax(url, data, function(d) {
		// alert(Fm.JsonToString(d));
		$('#' + id).highcharts(d);
	});
}
Fm.serialize = function(formId) {
	var data = $("#" + formId).serialize();
	data = decodeURI(data);
	var arr = data.split('&');
	var item, key, value, newData = {};
	for (var i = 0; i < arr.length; i++) {
		item = arr[i].split('=');
		key = item[0];
		value = item[1];
		if (key.indexOf('[]') != -1) {
			key = key.replace('[]', '');
			if (!newData[key]) {
				newData[key] = [];
			}
			newData[key].push(value);
		} else {
			newData[key] = value;
		}
	}
	return newData;
}
/**
 * 描述:获取不同浏览器下窗口文档的高度
 */
Fm.getWinHeight = function() {
	var height = $(window.parent.document).height();
	if ($.browser.msie && ($.browser.version == "6.0")) {
	} else if ($.browser.msie && ($.browser.version == "8.0")) {
		height = thisheight * 0.73;
	} else if ($.browser.msie && ($.browser.version == "9.0")) {
		height = height * 0.82;
	} else if ($.browser.version == "10.0") {
		height = height * 0.70;
	} else if ($.browser.webkit) {
		// 谷歌内核
		height = height * 0.80;
	} else if ($.browser.mozilla) {
		// 火狐内核
		height = height * 0.80;
	} else if ($.browser.safari) {
		// 苹果内核
		height = height * 0.80;
	} else {
		height = height * 0.75;
	}
	return height;
}
/**
 * 描述:获取浏览器类型
 */
Fm.getBrowser = function() {
	var type = "";
	if ($.browser.msie && ($.browser.version == "6.0")) {
		type = "ie";
	} else if ($.browser.msie && ($.browser.version == "8.0")) {
		type = "ie";
	} else if ($.browser.msie && ($.browser.version == "9.0")) {
		type = "ie";
	} else if ($.browser.version == "10.0") {
		type = "ie";
	} else if ($.browser.chrome) {
		// 谷歌内核
		type = "chrome";
	} else if ($.browser.chrome) {
		type = "webkit";
	} else if ($.browser.mozilla) {
		// 火狐内核
		type = "mozilla";
	} else if ($.browser.safari) {
		// 苹果内核
		type = "safari";
	}
	return type;
}
Fm.getdataSx = function() {
	now = new Date();
	hour = now.getHours();
	if (hour < 6) {
		document.write("凌晨好！")
	} else if (hour < 9) {
		document.write("早上好！")
	} else if (hour < 12) {
		document.write("上午好！")
	} else if (hour < 14) {
		document.write("中午好！")
	} else if (hour < 17) {
		document.write("下午好！")
	} else if (hour < 19) {
		document.write("傍晚好！")
	} else if (hour < 22) {
		document.write("晚上好！")
	} else {
		document.write("夜里好！")
	}
}
/**
 * 获取当前日期
 */
Fm.getNowDate = function(id) {
	var day = "";
	var month = "";
	var ampm = "";
	var ampmhour = "";
	var myweekday = "";
	var year = "";
	mydate = new Date();
	myweekday = mydate.getDay();
	mymonth = mydate.getMonth() + 1;
	myday = mydate.getDate();
	myyear = mydate.getYear();
	year = (myyear > 200) ? myyear : 1900 + myyear;
	if (myweekday == 0)
		weekday = " 星期日";
	else if (myweekday == 1)
		weekday = " 星期一";
	else if (myweekday == 2)
		weekday = " 星期二";
	else if (myweekday == 3)
		weekday = " 星期三";
	else if (myweekday == 4)
		weekday = " 星期四";
	else if (myweekday == 5)
		weekday = " 星期五";
	else if (myweekday == 6)
		weekday = " 星期六";
	$("#" + id).text(year + "年" + mymonth + "月" + myday + "日 " + weekday);
}
Fm.getWeek = function() {
	var objDate = new Date();
	var week = objDate.getDay();
	switch (week) {
	case 0:
		week = "周日";
		break;
	case 1:
		week = "周一";
		break;
	case 2:
		week = "周二";
		break;
	case 3:
		week = "周三";
		break;
	case 4:
		week = "周四";
		break;
	case 5:
		week = "周五";
		break;
	case 6:
		week = "周六";
		break;
	}
	return week;
}, Fm.GetDateDiff = function(startDate, endDate) {
	var startTime = new Date(Date.parse(startDate.replace(/-/g, "/"))).getTime();
	var endTime = new Date(Date.parse(endDate.replace(/-/g, "/"))).getTime();
	var dates = Math.abs((startTime - endTime)) / (1000 * 60 * 60 * 24);
	return Math.ceil(dates);
},
/* 设置提交按钮的禁用状态 */
Fm.setSubmitButton = function(formID, action) {
	var submitButton = $(formID).find(':submit');
	label = submitButton.val();
	loading = submitButton.data('loading');
	disabled = action == 'disable';
	submitButton.attr('disabled', disabled);
	submitButton.val(loading);
	submitButton.data('loading', label);
},

/* 启用按钮 */
Fm.disableForm = function(formID) {
	Fm.setSubmitButton(formID, 'disable');
},

/* 禁用按钮 */
Fm.enableForm = function(formID) {
	Fm.setSubmitButton(formID, 'enable');
}
/* 表单按钮附加提示消息 */
Fm.msg = function(formID, msg) {
	var submitButton = $(formID).find(':input[type=submit], .submit');
	submitButton.popover({
		trigger : 'manual',
		content : msg,
		placement : 'right'
	}).popover('show');
	/** popover-danger */
	submitButton.next('.popover').addClass('popover-success');
	function distroy() {
		submitButton.popover('destroy')
	}
	setTimeout(distroy, 2000);
}
$(document).keydown(function(e) {
	var doPrevent = true;
	if (e.keyCode == 8) {
		var d = e.srcElement || e.target;
		if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') {
			doPrevent = d.readOnly || d.disabled;
		} else {
			doPrevent = false;
		}
	} else {
		doPrevent = false;
	}
	if (doPrevent) {
		e.preventDefault();
	}
});
Fm.strCots = function(str1, str2) {
	var str = [];
	var t = {
		id : "",
		opt : ""
	}
	var arr1 = str1;
	var arr2 = str2;
	$.each(arr1, function(key, val) {
		if (val != "") {
			t = {
				id : "",
				opt : ""
			}
			if ($.inArray(val, arr2) < 0) {
				t.id = val;
				t.opt = "del";
				str.push(t);
			}
		}
	});
	$.each(arr2, function(key, val) {
		if (val != "") {
			t = {
				id : "",
				opt : ""
			}
			if ($.inArray(val, arr1) < 0) {
				t.id = val;
				t.opt = "add";
				str.push(t);
			}
		}
	});
	return str;
}
/**
 * 消息提示
 */
Fm.msg = function(data) {
	if (data.info) {
		if (data.theme) {
			top.$.zui.messager.show(data.info, {
				placement : 'top'
			});
		} else {
			top.Dg.z.msg(data.info);
		}
	}
};
/**
 * 消息提示
 */
Fm.tip = function(msg) {
	layer.msg(msg, {
		icon : 5
	});
};
Fm.getStorage = function() {
	return $.sessionStorage;
}
/**
 * 本地存储
 */
Fm.set = function(key, value) {
	Fm.getStorage().set(key, value);
}
/**
 * 本地存储
 */
Fm.get = function(key, defval) {
	var v = Fm.getStorage().get(key);
	if (Fm.isNN(v)) {
		return v;
	} else {
		if (defval) {
			return defval;
		}
	}
}

Fm.onerror = function(url) {
	var img = event.srcElement;
	img.src = url;
	img.onerror = null;
}