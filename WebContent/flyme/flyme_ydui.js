(function(yui, $yui) {
	var dialog = yui.dialog;
	if (yui.device.isWeixin) {
		// alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');
		// var opened = window.open('about:blank', '_self');
		// opened.opener = null;
		// opened.close();
	}
	$yui.getdom = function(id) {
		return document.getElementById(id);
	};
	$yui.getVal = function(id) {
		return document.getElementById(id).value;
	};
	var time = 0;
	$yui.toast = function(msg) {
		var now = new Date().getTime();
		if (now - time > 2000) {
			time = now;
			dialog.toast(msg, 'none', 1000);
		}
	};
	// 原生alert
	$yui.alert = function(alertMsg) {
		dialog.alert(alertMsg);
	};
	$yui.loading = function(alertMsg) {
		dialog.loading.open(alertMsg);
	};
	$yui.loadingclose = function() {
		dialog.loading.close();
	};
	$yui.confirm = function(options) {
		var title = options.title ? options.title : "";
		var ok = options.ok;
		var params = {};
		if (options && options.params) {
			$.extend(params, options.params);
		}
		if (!$.isFunction(ok)) {
			options.ok = function() {
				$yui.ajax(ok, params, function(data) {
					if (data.info) {
						$yui.toast(data.info);
					}
					if (data.code ==100) {
						options.callback && options.callback(data);
					}
				});
			}
		}
		dialog.confirm(title, options.content, options.ok);
	}
	$yui.getKey = function(key) {
		return localStorage.getItem(key);
	};
	$yui.setKey = function(key, value) {
		localStorage.setItem(key, value);
	};

	$yui.removeKey = function(key) {
		localStorage.removeItem(key);
	};
	$yui.removeUser = function() {
		localStorage.removeItem('$users');
	};
	$yui.setUser = function(user) {
		localStorage.setItem('$users', JSON.stringify(user));
		$yui.setOpenId(user.openId);
	};
	$yui.getUser = function() {
		return JSON.parse(localStorage.getItem('$users') || '[]');
	};
	$yui.setOpenId = function(openId) {
		localStorage.setItem('openId', openId);
	};
	$yui.getOpenId = function() {
		return localStorage.getItem('openId');
	};
	/** 跳转链接 */
	$yui.href = function(url, params) {
		url = initParams(url, params);
		console.log("homeSrc:" + url);
		window.location.href = url;
	};
	$yui.ajax = function(url, data, callback) {
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			cache : false,
			async : false,
			success : function(data) {
				if (data.info) {
					$yui.toast(data.info);
				}
				callback && callback(data);
			}
		});
	};
	/* 带header的ajax */
	$yui.ajax2 = function(options) {
		var openId = $yui.getOpenId();
		var user = $yui.getUser();
		var config = {
			url : options.url,
			dataType : 'json',
			type : 'POST',
			data : {},
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			async : false,
			beforeSend : function() {
				if (options.needOpenId) {
					if (!openId) {
						top.$.toptip("微信授权失败", "error");
						return false;
					}
				}
				options.beforeSend && options.beforeSend();
			},
			success : function(data) {
				if (data.info) {
					top.$.toast(data.info, "text");
				}
				if (data.code ==100) {
					options.success && options.success(data);
				} else {
					options.fail && options.fail(data);
				}
			},
			error : function(xhr, type, errorThrown) {
				if (type == "timeout" || type == "error") {
					$yui.toast("网络繁忙，请稍后再试");
				} else {
					$yui.toast(type);
				}
				options.error && options.error();
			}
		};
		if (options.data) {
			$.extend(config.data, options.data);
		}
		var headers = {};
		var userId = "";
		if (user != '' && user != null) {
			userId = user.userId;
		}
		headers["sc_api"] = base64_encode(userId + '/' + openId + '/'
				+ Math.round(new Date().getTime() / 1000));
		config.headers = headers;
		yui.ajax(config);

	};

}(YDUI, window.$yui = {}));
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
			string += base64EncodeChars.charAt(((c1 & 0x3) << 4)
					| ((c2 & 0xF0) >> 4));
			string += base64EncodeChars.charAt((c2 & 0xF) << 2);
			string += "=";
			break;
		}
		c3 = str.charCodeAt(i++);
		string += base64EncodeChars.charAt(c1 >> 2);
		string += base64EncodeChars.charAt(((c1 & 0x3) << 4)
				| ((c2 & 0xF0) >> 4));
		string += base64EncodeChars.charAt(((c2 & 0xF) << 2)
				| ((c3 & 0xC0) >> 6));
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
