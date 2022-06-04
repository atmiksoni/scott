/**
 * @description:Dialog封装
 * @version:1.0
 * @author:flyme
 * @data:2013-8-5
 */
Dg = {
	z : window.Flyme,
	gridType : "Dd",// 命名空间
	id : "dialog",
	title : '操作提示',
	index : 0,
	url : "",
	namespace : "",
	width : '600px',
	height : '350px',
	cancel : true,
	delmsg : '删除后将不能恢复'
};
Dg.open = function(options) {
	var btn = [ '确定', '关闭' ]
	var url = options.url;
	options.params ? url = Dg.initUrl(url, options.params) : url = url;
	options.mybtn ? btn = options.mybtn : btn = btn;
	options.newbtn ? btn = options.newbtn : btn = btn;
	options.width ? Dg.width = options.width : Dg.width = Dg.width;
	options.height ? Dg.height = options.height : Dg.height = Dg.height;
	var content = url;
	options.content = content;
	var config = {
		type : 2,
		closeBtn : 1,
		btn : btn,
		shift : 1,
		scrollbar : false,
		yes : function(index, layero) {
			layer.getChildFrame('#btn_sub', index).click();
			return false;
		},
		success : function(layero, index) {
			Dg.index = index;
		},
		area : [ Dg.width, Dg.height ],
		fix : true,
		shade : 0.5,
		maxmin : false,
		opacity : 0.7
	};
	$.extend(config, options);
	var a = layer.open(config);
	return a;
};
Dg.initUrl = function(url, params) {
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
};
/**
 * 描述：删除选择的记录
 */
Dg.deleteRow = function(tableId, params) {
	Dg.confirm(Dg.delmsg, function(y) {
		if (y) {
			var url = tableId + "/" + Dg.url;
			Dg.z.ajax(url, params, function(data) {
				if (data.code ==100) {
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
	var id = options.namespace;
	if (options.title) {
		Dg.title = options.title;
	}
	Dg.layerconfirm(Dg.title, Dg.delmsg, function(y, index) {
		if (y) {
			var url = options.url;
			Dg.z.ajax(url, options.params, function(data) {
				parent.eval(id).refresh();
				Dg.close(index);
			});
		}
	});
};

/**
 * 描述：ajax操作询问
 */
Dg.confirm = function(options) {
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

/**
 * 描述:警告提示 msg: 显示消息 callBack：函数
 */
Dg.layerconfirm = function(title, msg, callBack) {
	layer.confirm(msg, {
		icon : 3,
		title : title
	}, function(index) {
		callBack(index);
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
Dg.exit = function(url) {
	layer.confirm('安全退出？', {
		btn : [ '下班回家', '继续工作' ], // 按钮
		shade : false
	// 不显示遮罩
	}, function() {
		window.parent.location = 'http://localhost:8088/business/businesslogin.do';
	}, function() {
		layer.msg('继续工作', {
			shift : 6
		});
	});

};

Dg.close = function(index) {
	layer.close(index);
};

// 订单进度弹窗
Dg.opentimeline = function(options) {
	var url = options.url;
	if (options.params) {
		url = Dg.initUrl(url, options.params);
	}
	if (options.mybtn) {
		btn = btn.concat(options.mybtn);
	}
	if (options.width) {
		Dg.width = options.width;
	}
	if (options.height) {
		Dg.height = options.height;
	}
	var content = url;
	options.content = content;
	var config = {
		type : 2,
		scrollbar : false,
		yes : function(index, layero) {
			layer.getChildFrame('#btn_sub', index).click();
			return false;
		},
		success : function(layero, index) {
			Dg.index = index;
		},
		area : [ Dg.width, Dg.height ],
		fix : false, // 不固定
		maxmin : true,
		opacity : 0.7
	};
	$.extend(config, options);
	var a = layer.open(config);
	return a;

};

// 商品上传图片

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
		area : [ '750px', '600px' ],
		content : 'upload_images_turn.do'
	});
};
// 个人头像上传
Dg.uploadhead = function(config) {
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
		content : config.url
	});
};

// 广告管理图片上传
Dg.uploadslide = function(type) {
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
		content : 'upload_slide_turn.do'
	});
};

// 金币商城图片上传
Dg.uploadgift = function(type) {
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
		content : 'upload_gift_turn.do'
	});
};
// 商家图片上传
Dg.uploadutopia = function(type) {
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
		content : 'shopmanage_images_turn.do'
	});
};
// 商家信息图片上传
Dg.uploadutopia2 = function(type) {
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
		content : 'shopmanage/shopmanage_images_turn.do'
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
		content : 'profile/profile_edit_turn.do'
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
		content : 'profile/profilestaff_edit_turn.do'
	});
};
/** 密码修改 */
Dg.editpwd = function(config) {
	layer.open({
		type : 2,
		title : "密码修改",
		area : [ '630px', '360px' ],
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			layer.getChildFrame('#btn_sub', index).click();
			return false;
		},
		content : config.url
	});
};

/** 密码修改 */
Dg.edit = function(config) {
	layer.open({
		type : 2,
		title : "修改信息",
		area : [ '630px', '360px' ],
		btn : [ '确定', '关闭' ],
		yes : function(index, layero) {
			layer.getChildFrame('#btn_sub', index).click();
			return false;
		},
		content : config.url
	});
};
