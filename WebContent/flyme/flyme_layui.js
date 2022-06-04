(function(layui, $layui) {
	var dialog = layui.layer;
	$layui.getdom = function(id) {
		return document.getElementById(id);
	};
	$layui.getVal = function(id) {
		return document.getElementById(id).value;
	};
	var time = 0;
	$layui.toast = function(msg) {
		var now = new Date().getTime();
		if (now - time > 2000) {
			time = now;
			dialog.msg(msg);
		}
	};
	$layui.loading = function(alertMsg) {
		dialog.load();
	};
	$layui.dialog = function(options) {
		var config = {
			type : 2,
			title : false,
			closeBtn : 1,
			shadeClose : true,
			area : [ '500px', '450px' ],
			shade : 0.5,
			success : function(layero, index) {
				options.success && options.success();
			},
			content : options.content
		}
		$.extend(config, options);
		dialog.open(config);
	};
	$layui.loadingclose = function() {
		dialog.closeAll('loading');
	};
	$layui.confirm = function(options) {
		var title = options.title ? options.title : "";
		var ok = options.ok;
		var params = {};
		if (options && options.params) {
			$.extend(params, options.params);
		}
		if (!$.isFunction(ok)) {
			options.ok = function() {
				$layui.ajax(ok, params, function(data) {
					if (data.info) {
						$layui.toast(data.info);
					}
					if (data.code == 100) {
						options.callback && options.callback(data);
					}
				});
			}
		}
		dialog.confirm(title, options.content, options.ok);
	}
	$layui.getKey = function(key) {
		return localStorage.getItem(key);
	};
	$layui.setKey = function(key, value) {
		localStorage.setItem(key, value);
	};

	$layui.removeKey = function(key) {
		localStorage.removeItem(key);
	};
	$layui.removeUser = function() {
		localStorage.removeItem('$users');
	};
	$layui.setUser = function(user) {
		localStorage.setItem('$users', JSON.stringify(user));
	};
	$layui.getUser = function() {
		return JSON.parse(localStorage.getItem('$users') || '[]');
	};
	$layui.getMsg = function(id) {
		return JSON.parse(localStorage.getItem(id) || '{}');
	};
	$layui.getMsgByKey = function(targetId, id) {
		var msgObj = JSON.parse(localStorage.getItem(id) || '{}');
		var msg = msgObj[targetId] || {};
		return msg;
	};
	$layui.select = function(options) {
		var config = {
			type : 2,
			title : "",
			area : [ '830px', '500px' ],
			content : options.url,
			success : function(layero, index) {
				var body = layer.getChildFrame('body', index);
				var iframeWin = parent.window[layero.find('iframe')[0]['name']];
				iframeWin.init(options, iframeWin);
			}
		}
		$.extend(config, options);
		layer.open(config);
	};
	$layui.open = function(options) {
		var config = {
			type : 2,
			title : "",
			area : [ '830px', '500px' ],
			beforeSubmit : function() {

			},
			content : options.url,
			btn : [ '确定', '关闭' ],
			yes : function(index, layero) {
				var iframeWin = parent.window[layero.find('iframe')[0]['name']];
				iframeWin.submit(layer, index, options.callback);
				return false;
			},
			success : function(layero, index) {
				var body = layer.getChildFrame('body', index);
				var iframeWin = parent.window[layero.find('iframe')[0]['name']];
				iframeWin.init(options, iframeWin);
			}
		}
		$.extend(config, options);
		layer.open(config);
	};
	/** 跳转链接 */
	$layui.href = function(url, params) {
		url = initParams(url, params);
		console.log("homeSrc:" + url);
		window.location.href = url;
	};
	$layui.ajax = function(url, data, callback) {
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			cache : false,
			async : false,
			headers : {
				'token' : $layui.getKey("token")
			},
			success : function(data) {
				var result;
				if ($.type(data) == 'object') {
					result = data;
				} else {
					result = $.parseJSON(data);
				}
				if (result.info) {
					$layui.toast(result.info);
				}
				callback && callback(result);
			}
		});
	};
	$layui.simpajax = function(url, data, callback) {
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			cache : false,
			async : false,
			success : function(data) {
				callback && callback(data);
			}
		});
	};
	/* 带header的ajax */
	$layui.ajax2 = function(options) {
		var config = {
			url : options.url,
			dataType : 'json',
			type : 'POST',
			data : {},
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			async : false,
			beforeSend : function() {
				options.beforeSend && options.beforeSend();
			},
			success : function(data) {
				var result;
				if ($.type(data) == 'object') {
					result = data;
				} else {
					result = $.parseJSON(data);
				}
				if (result.info) {
					$layui.toast(result.info);
				}
				if (result.code == 100) {
					options.success && options.success(result);
				} else {
					options.fail && options.fail(result);
				}
			},
			error : function(xhr, type, errorThrown) {
				if (type == "timeout" || type == "error") {
					$layui.toast("网络繁忙，请稍后再试");
				}
				options.error && options.error();
			}
		};
		if (options.data) {
			$.extend(config.data, options.data);
		}
		var headers = {};
		headers["token"] = $layui.getKey("token");
		config.headers = headers;
		layui.ajax(config);

	};

}(layui, window.$layui = {}));
function base64_encode(str) {
	var c1, c2, c3;
	var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	var i = 0, len = str.length, string = '';

	while (i < len) {
		c1 = str.charCodeAt(i++) & 0xff;
		if (i == len) {
			string += base64EncodeChars.charAt(c1 >> 2);
			string += base64EncodeChars.charAt((c1 & 0x3) << 4);
			string += "==";
			break;
		}
		c2 = str.charCodeAt(i++);
		if (i == len) {
			string += base64EncodeChars.charAt(c1 >> 2);
			string += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
			string += base64EncodeChars.charAt((c2 & 0xF) << 2);
			string += "=";
			break;
		}
		c3 = str.charCodeAt(i++);
		string += base64EncodeChars.charAt(c1 >> 2);
		string += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
		string += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
		string += base64EncodeChars.charAt(c3 & 0x3F)
	}
	return string
}
function initUrl(url) {
	var userId = $ydui.getUser().userId;
	var openId = $ydui.getOpenId();
	var params = {
		userId : userId,
		openId : openId
	}
	if (params) {
		$.each(params, function(k, v) {
			if (v != null && v != "" && v != undefined) {
				url += getTag(url) + k + "=" + v;
			}
		});
	}
	return url;
};
function initParams(url, params) {
	if (params) {
		$.each(params, function(k, v) {
			if (v != null && v != "" && v != undefined) {
				url += getTag(url) + k + "=" + v;
			}
		});
	}
	return url;
};
function getTag(url) {
	var tag = "?";
	if (url.indexOf("?") > -1) {
		tag = "&"
	}
	return tag;
};
function isFalse(v) {
	var tag = false;
	if (v == false || v == "false" || v == "0") {
		tag = true;
	}
	return tag;
};
function isTrue(v) {
	var tag = false;
	if (v == true || v == "true" || v == "1") {
		tag = true;
	}
	return tag;
};
function nofind() {
	var img = event.srcElement;
	img.src = "../res/weixin/img/usercenter/images/wode_user_touxinag.png";
	img.onerror = null;
}
function noimg(imgurl) {
	var img = event.srcElement;
	img.src = "../../res/theme/common/images/" + imgurl;
	img.onerror = null;
}
