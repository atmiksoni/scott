﻿/**
 * @description:Dialog封装
 * @version:1.0
 * @author:flyme
 * @data:2013-8-5
 */
Dg = {
	z : window.Flyme,
	gridType : "Dd",// 命名空间
	id : "dialog",
	title : '',
	url : "",
	parentDg : "",
	index : 0,
	namespace : "",
	closeBtn : 1,
	width : 600,
	height : 350,
	w : '600px',
	h : '350px',
	cancel : true,
	delmsg : '删除后将不能恢复'
};
Dg.open2 = function(options) {
	var btn = ['确定','关闭']
	var url = options.url;
	var datagrid = options.datagrid;
	options.params ? url = Dg.initUrl(url, options.params) : url = url;
	options.mybtn ? btn = options.mybtn : btn = btn;
	options.newbtn ? btn = options.newbtn : btn = btn;
	options.w ? Dg.w = options.w : Dg.w = Dg.w;
	options.h ? Dg.h = options.h : Dg.h = Dg.h;
	options.closeBtn ? Dg.closeBtn = options.closeBtn : Dg.closeBtn = Dg.closeBtn;
	var content = url;
	options.content = content;
	var config = {
		type : 2,
		closeBtn : Dg.closeBtn,
		btn : btn,
		shift : 2,
		scrollbar : false,
		yes : function(index, layero) {
			var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			// parent.$(".layui-layer-btn0").unbind();
			iframeWin.submit(datagrid, layer, index, options.callback);
			return false;
		},
		success : function(layero, index) {
			Dg.index = index;
			Dg.parentDg = datagrid;
			/** 向弹出页面传递参数 */
			if (options.setParams) {
				var obj = options.setParams;
				for ( var key in obj) {
					parent.layer.getChildFrame('#' + key, index).val(obj[key]);
				}
			}
		},
		area : [ Dg.w, Dg.h ],
		fix : true,
		shade : 0.5,
		maxmin : true,
		resize : true,
		opacity : 0.7
	};
	$.extend(config, options);
	var a = layer.open(config);
	return a;

};
Dg.open = function(options) {
	var btn = [ '确定', '关闭' ]
	var url = options.url;
	var dg = options.datagrid;
	options.params ? url = Dg.initUrl(url, options.params) : url = url;
	options.mybtn ? btn = options.mybtn : btn = btn;
	options.newbtn ? btn = options.newbtn : btn = btn;
	options.w ? Dg.w = options.w : Dg.w = Dg.w;
	options.h ? Dg.h = options.h : Dg.h = Dg.h;
	options.closeBtn ? Dg.closeBtn = options.closeBtn : Dg.closeBtn = Dg.closeBtn;
	var content = url;
	options.content = content;
	var config = {
		type : 2,
		closeBtn : Dg.closeBtn,
		btn : btn,
		shift : 2,
		scrollbar : false,
		yes : function(index, layero) {
			var iframe = parent.window[layero.find('iframe')[0]['name']];
			iframe.callback(dg, index, layer, layero);
			return false;
		},
		success : function(layero, index) {
			Dg.index = index;
			/** 向弹出页面传递参数 */
			if (options.setParams) {
				var obj = options.setParams;
				for ( var key in obj) {
					parent.layer.getChildFrame('#' + key, index).val(obj[key]);
				}
			}
		},
		area : [ Dg.w, Dg.h ],
		fix : true,
		shade : 0.5,
		maxmin : true,
		opacity : 0.7
	};
	$.extend(config, options);
	var a = layer.open(config);
	return a;

};
Dg.openprint = function(options) {
	var btn = [ '打印', '关闭' ]
	var url = options.url;
	options.params ? url = Dg.initUrl(url, options.params) : url = url;
	options.mybtn ? btn = options.mybtn : btn = btn;
	options.newbtn ? btn = options.newbtn : btn = btn;
	options.w ? Dg.w = options.w : Dg.w = Dg.w;
	options.h ? Dg.h = options.h : Dg.h = Dg.h;
	options.closeBtn ? Dg.closeBtn = options.closeBtn : Dg.closeBtn = Dg.closeBtn;
	options.content = url;
	var config = {
		type : 2,
		closeBtn : Dg.closeBtn,
		btn : btn,
		shift : 2,
		scrollbar : false,
		yes : function(index, layero) {
			var iframe = parent.window[layero.find('iframe')[0]['name']];
			iframe.MyPreview();
			layer.close(index);
		},
		success : function(layero, index) {
			Dg.index = index;
			if (options.setParams) {
				var obj = options.setParams;
				for ( var key in obj) {
					parent.layer.getChildFrame('#' + key, index).val(obj[key]);
				}
			}
		},
		area : [ Dg.w, Dg.h ],
		fix : true,
		shade : 0.5,
		maxmin : true,
		opacity : 0.7
	};
	$.extend(config, options);
	var a = layer.open(config);
	return a;

};
Dg.dialognobtn = function(options) {
	var url = options.url;
	options.params ? url = Dg.initUrl(url, options.params) : url = url;
	options.w ? Dg.w = options.w : Dg.w = Dg.w;
	options.h ? Dg.h = options.h : Dg.h = Dg.h;
	options.closeBtn ? Dg.closeBtn = options.closeBtn : Dg.closeBtn = Dg.closeBtn;
	var content = url;
	options.content = content;
	var config = {
		type : 2,
		closeBtn : Dg.closeBtn,
		shift : 2,
		scrollbar : false,
		area : [ Dg.w, Dg.h ],
		fix : true,
		success : function(layero, index) {
			Dg.index = index;
			if (options.params) {
				var body = layer.getChildFrame('body', index);
				var iframeWin = parent.window[layero.find('iframe')[0]['name']];
				if (iframeWin.myparams) {
					$.extend(iframeWin.myparams, options.params);
				}
			}
		},
		shade : 0.5,
		maxmin : true,
		opacity : 0.7
	};
	$.extend(config, options);
	var a = layer.open(config);
	return a;

};
Dg.video = function(url) {
	layer.open({
		type : 2,
		area : [ '630px', '360px' ],
		shade : 0.8,
		closeBtn : 1,
		maxmin : true,
		shadeClose : true,
		content : url
	});
}
Dg.search = function(options) {
	var btn = [ '确定', '关闭' ]
	var url = options.url;
	options.params ? url = Dg.initUrl(url, options.params) : url = url;
	options.mybtn ? btn = options.mybtn : btn = btn;
	options.newbtn ? btn = options.newbtn : btn = btn;
	options.w ? Dg.w = options.w : Dg.w = Dg.w;
	options.h ? Dg.h = options.h : Dg.h = Dg.h;
	options.closeBtn ? Dg.closeBtn = options.closeBtn : Dg.closeBtn = Dg.closeBtn;

	var content = url;
	options.content = content;
	var config = {
		type : 2,
		closeBtn : Dg.closeBtn,
		btn : btn,
		shift : 2,
		scrollbar : false,
		yes : function(index, layero) {
			var params = Dg.getParent().myparams;
			var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			iframeWin.initparams(params);
			// Dg.getParent().yishou.search(params);
			options.dg.search(params);
			layer.close(index);
		},
		success : function(layero, index) {
			Dg.index = index;
			if (options.setParams) {
				var obj = options.setParams;
				for ( var key in obj) {
					parent.layer.getChildFrame('#' + key, index).val(obj[key]);
				}
			}
		},
		area : [ Dg.w, Dg.h ],
		fix : true,
		shade : 0.5,
		maxmin : true,
		opacity : 0.7
	};
	$.extend(config, options);
	var a = layer.open(config);
	return a;

};
Dg.getParent = function() {
	var n = top.Current_iframe().contentWindow.length;
	if (n > 0) {
		return top.Current_iframe().contentWindow[1];
	} else {
		try {
			return top.Current_iframe().contentWindow;
		} catch (e) {
		}
	}
};

Dg.initUrl = function(url, params) {
	var timestamp = new Date().getTime();
	if (params) {
		$.each(params, function(k, v) {
			url += Dg.getTag(url) + k + "=" + v;
		});
	}
	return url;
};
Dg.getTag = function(url) {
	var tag = "?";
	if (url.indexOf("?") > -1) {
		tag = "&"
	}
	return tag;
};
/**
 * 描述：删除选择的记录
 */
Dg.deleteRow = function(tableId, params) {
	alert(2);
	Dg.confirm(Dg.delmsg, function(y) {
		if (y) {
			var url = tableId + "/" + Dg.url;
			Dg.z.ajax(url, params, function(data) {
				if (data.code == 100) {
					var n = top.$('#' + top.Current_iframeID())[0].contentWindow.length;
					if (n > 0) {
						top.Current_iframe().contentWindow[1].eval(tableId).refresh();
					} else {
						top.Current_iframe().contentWindow.eval(tableId).refresh();
					}
				} else {
				}
			});
		}
	});
};
/**
 * 描述：删除选择的记录
 */
Dg.remove = function(options) {
	alert(1);
	console.log(options);
	var id = options.namespace;
	Dg.confirm(Dg.delmsg, function(y) {
		if (y) {
			var url = id + "/" + options.url;
			Dg.z.api(url, options.params, function(data) {
				if (data.code == 100) {
					var n = top.$('#' + top.Current_iframeID())[0].contentWindow.length;
					if (n > 0) {
						alert(n);
						top.Current_iframe().contentWindow[1].eval(id).refresh();
					} else {
						top.Current_iframe().contentWindow.eval(id).refresh();
					}
				} else {
				}
			});
		}
	});
};
/**
 * 描述:警告提示 msg: 显示消息 callBack：函数
 */
Dg.confirm = function(msg, callBack) {
	layer.confirm(msg, {
		btn : [ '确定', '取消' ]
	}, function(index) {
		callBack(layer, index);
	}, function() {
	});
};
/**
 * 描述：ajax操作询问
 */
Dg.confirmajax = function(options) {
	var config = {
		title : "提示信息",
		content : "",
		url : "",
		success : function(index) {
			options.success(index);
		}
	}
	$.extend(config, options);
	layer.confirm(config.content, {
		icon : 3,
		title : config.title
	}, config.success);

};
Dg.msg = function(msg) {
	layer.msg(msg);
};
Dg.tips = function(msg, id) {
	layer.tips(msg, '.' + id, {
		tips : 3
	});
};
/**
 * 描述:警告提示 msg: 显示消息 callBack 回掉函数
 */
Dg.layerconfirm = function(title, msg, callback) {
	layer.confirm(msg, {
		icon : 3,
		title : title,
		btn:['confrim','close']
	}, function(index) {
		callback(index);
	});
};
/**
 * 描述:警告提示 msg: 显示消息 callBack 回掉函数 增加btn 可配置
 */
Dg.layerconfirm2 = function(title,btn, msg, callback) {
	var nbtn = ['确定','关闭']
	btn=btn==''||btn==null ||btn=="undefined"?nbtn:btn
	layer.confirm(msg, {
		icon : 3,
		title : title,
		btn:btn
	}, function(index) {
		callback(index);
	});
};
/**
 * 描述:普通表格AJAX删除
 */
Dg.del = function(msg, rowKey, url, callback) {
	var parames = {
		id : rowKey
	};
	var config = {
		title : "提示信息",
		content : msg,
		ok : function(index) {
			Dg.z.ajax2(url, parames, function(data) {
				if (data.code == 100) {
					if (callback) {
						callback(index);
					}
					window.location.replace(location);
				}
				Dg.msg(data.info);
			});
		}
	}
	Dg.layerconfirm(config.title, config.content, config.ok);

};
/**
 * 描述:带询问的AJAX操作
 */
Dg.cfa = function(msg, url, params, callback) {
	var config = {
		title : "操作提示",
		content : msg,
		ok : function(index) {
			Dg.z.ajax2(url, params, function(data) {
				if (data.code == 100) {
					if (callback) {
						callback(index);
					}
				}
				layer.close(index);
			});
		}
	}
	Dg.layerconfirm(config.title, config.content, config.ok);
};
/**
 * 描述:普通表格AJAX删除
 */
Dg.ajax = function(url, id) {
	var parames = {
		id : id
	};
	Dg.z.ajax2(url, parames, function(data) {
		if (data.code == 100) {
			window.location.replace(location);
		}
		Dg.msg(data.info);
	});
};
Dg.initParams = function(params) {
	var timestamp = new Date().getTime();
	if (params) {
		Dg.url += "?time=" + timestamp;
		var temp = "";
		$.each(params, function(k, v) {
			temp += "&" + k + "=" + v;
		});
		Dg.url += temp;
	}
	return Dg.url;
};
/**
 * 退出确认框
 */
Dg.exit = function(msg,title,btn1,btn2) {
	layer.confirm(msg, {
		title : title,
		btn : [ btn1, btn2 ]
	}, function(index) {
		layer.close(index);
	}, function() {
		window.parent.location = top.baseUrl + '/logout.do';
	});
};
function CloseWebPage() {
	if (navigator.userAgent.indexOf("MSIE") > 0) {
		if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
			window.opener = null;
			window.close();
		} else {
			window.open('', '_top');
			window.top.close();
		}
	} else if (navigator.userAgent.indexOf("Firefox") > 0) {
		window.location.href = 'about:blank ';
	} else {
		window.opener = null;
		window.open('', '_self', '');
		window.close();
	}
}
Dg.close = function() {
	layer.close(Dg.index);
};
// 商家信息图片上传
Dg.uploadimg = function(option) {
	parent.layer.open({
		type : 2,
		name : "3ss",
		title : "图片上传",
		btn : [ '上传', '关闭' ],
		yes : function(index, layero) {
			var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			iframeWin.upload();
			return false;
		},
		scrollbar : false,
		area : [ '600px', '400px' ],
		content : option.url
	});
};

// 上传图片
Dg.upload = function(type) {
	parent.layer.open({
		type : 2,
		title : "图片上传",
		btn : [ '上传', '关闭' ],
		yes : function(index, layero) {
			var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			iframeWin.toupload();
			return false;
		},
		scrollbar : false,
		area : [ '800px', '600px' ],
		content : 'products/upload_images_turn.do'
	});
};
// 个人头像上传
Dg.uploadhead = function(config) {
	parent.layer.open({
		type : 2,
		name : "3ss",
		title : "图片上传",
		btn : [ '裁剪', '关闭' ],
		yes : function(index, layero) {
			var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			iframeWin.toupload();
			return false;
		},
		scrollbar : false,
		maxmin : true,
		area : [ '600px', '400px' ],
		content : config.url
	});
};
// 图片上传
Dg.uploadimages = function(url) {
	parent.layer.open({
		type : 2,
		name : "3ss",
		title : "图片上传",
		btn : [ '上传', '关闭' ],
		yes : function(index, layero) {
			var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			iframeWin.toupload();
			return false;
		},
		scrollbar : false,
		area : [ '750px', '600px' ],
		content : url
	});
};

/** 商家信息修改 */
Dg.shopmanage = function(opens) {
	layer.open({
		type : 2,
		title : "商家信息修改",
		area : [ '630px', '360px' ],
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			layer.getChildFrame('#btn_sub', index).click();
			return false;
		},
		content : 'profile/profile_edit_turn.bs'
	});
};

/** 个人信息修改 */
Dg.staffmanage = function(opens) {
	layer.open({
		type : 2,
		title : "个人信息修改",
		area : [ '630px', '360px' ],
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			layer.getChildFrame('#btn_sub', index).click();
			return false;
		},
		content : 'profile/profilestaff_edit_turn.bs'
	});
};
/** 密码修改 */
Dg.editpwd = function(title,btn1,btn2) {
	Dg.open2({
		url : "/admin/account/account_pwd2_turn.do",
		title : title,
		btn : [ btn1,btn2 ],
		h : '340px',
		w : '500px'
	});
};
Dg.porlet = function(data) {
	layer.closeAll();
	layer.open({
		type : 1, // page层
		area : [ '400px', '200px' ],
		title : data.title,
		offset : 'rb', // 右下角弹出
		moveType : 0, // 拖拽风格，0是默认，1是传统拖动
		shift : 2, // 0-6的动画形式，-1不开启
		skin : 'demo-class',
		content : '<div style="padding:50px;">' + data.text + '</div>'

	});
}
/** 选择组件 */
Dg.select = function(options) {
	var urls = {};
	if (options.left) {
		urls.left = options.left;
	}
	if (options.center) {
		urls.center = options.center;
	}
	var title = "请选择";
	options.title ? title = options.title : title = title;
	var url = "../admin/component/select_index.do";
	url = Dg.initUrl(url, urls);
	layer.open({
		type : 2,
		title : title,
		area : [ '630px', '400px' ],
		content : url,
		success : function(layero, index) {
			Dg.index = index;

			var body = layer.getChildFrame('body', index);
			var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			iframeWin.initParams(options, iframeWin);

		}
	});
};

/** 展示图片 */
Dg.show = function(obj) {
	layer.open({
		type : 2,
		area : [ '1020px', '650px' ],
		fixed : false, // 不固定
		maxmin : false,
		content : 'img/show_img_turn.do?img=' + obj
	});
}

/** 展示图片 */
Dg.showImg = function(obj) {
	layer.open({
		type : 1,
		title : false,
		closeBtn : 0,
		skin : 'layui-layer-nobg', // 没有背景色
		shadeClose : true,
		content : '<img width="100%" height="100%" src="' + obj + '" />',
	});
}

/** 密码修改 */
Dg.edit = function(config) {
	layer.open({
		type : 2,
		title : "修改信息",
		area : [ '1100px', '800px' ],
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			layer.getChildFrame('#btn_sub', index).click();
			return false;
		},
		content : config.url
	});
};
