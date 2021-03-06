/**
 * 父类JS
 */
(function() {
	$(".footer").addClass('fixed');
	var _Flyme = window.Flyme;
	var scripts = document.getElementsByTagName('script'), script = scripts[scripts.length - 1], confStr = script
			.getAttribute('config'), jspath = script.hasAttribute ? script.src
			: script.getAttribute('src', 4);
	script = null;
	var uri2varName = '_'
			+ document.URL.split("#")[0].split("=")[0].replace(
					location.protocol + '//' + location.host, '').replace(
					/[^A-Za-z0-9\/]/g, '').replace(/\//g, '_');
	var z = {
		version : '0.6',
		JSLIBPATH : jspath.substr(0, jspath.lastIndexOf('/') + 1),
		Config : {
			namespace : 'window',
			context : 'backend',
			debug : 'no',
			skin : 'default'
		}
	};
	if (_Flyme && _Flyme.version === z.version
			&& _Flyme.JSLIBPATH === z.JSLIBPATH) {
		return;
	} else {
		window.Flyme = z;
	}
	z.startTime = +new Date();
	if (z.JSLIBPATH.indexOf(location.protocol + '//' + location.host + '/') == 0) {
		z.JSLIBPATH = z.JSLIBPATH.replace(location.protocol + '//'
				+ location.host, '');
	}
	z.CONTEXTPATH = z.JSLIBPATH.replace(/[^\/]+\/?$/, '');
	z.FULLCONTEXTPATH = location.protocol + '//' + location.host
			+ z.CONTEXTPATH;
	z.pageId = uri2varName;

	/** 浏览器判断* */
	var ua = navigator.userAgent.toLowerCase();
	z.isQuirks = document.compatMode === 'BackCompat';
	z.isStrict = document.compatMode === 'CSS1Compat';
	z.isWindows = /windows|win32/.test(ua);
	z.isMac = /macintosh|mac os/.test(ua);
	z.isLinux = /linux/.test(ua);
	z.isWebKit = /webkit/.test(ua);
	z.isChrome = /chrome/.test(ua);
	z.isSafari = /safari/.test(ua) && !z.isChrome;
	z.isGecko = /gecko/.test(ua);
	z.isFirefox = /firefox/.test(ua);
	z.isOpera = /opera/.test(ua) && !!window.opera;
	z.isIE = /msie/.test(ua) && !z.isOpera;
	z.ieVersion = z.isIE ? parseFloat(/msie ([\w.]+)/.exec(ua)[1]) : null;
	z.isIE9 = /msie (7|8|9)/.test(ua) && !!window.performance;
	z.isMaxthon = /maxthon/i.test(navigator.userAgent);
	z.is360se = /360se/i.test(navigator.userAgent);
	if (z.isIE9) {
		z.ieVersion = 9;
	}
	z.isIE8 = /msie (7|8)/.test(ua) && !!window.XDomainRequest;
	if (z.isIE8) {
		z.ieVersion = 8;
	}
	z.isIE7 = /msie 7/.test(ua) && !window.XDomainRequest;
	z.isIE6 = z.isIE && !window.XMLHttpRequest;
	z.isMobile = ('createTouch' in document)
			&& !('onmousemove' in document.documentElement)
			|| /(iphone|ipad|ipod|android)/.test(ua);
	z.isBorderBox = z.isIE && z.isQuirks;
	z.WHICHBUTTON = z.isIE ? {
		1 : 1,
		2 : 4,
		3 : 2
	} : (z.isWebKit ? {
		1 : 0,
		2 : 1,
		3 : 2
	} : {
		1 : 0,
		2 : 1,
		3 : 2
	});

	if (z.isIE) {
		try {
			document.execCommand("BackgroundImageCache", false, true);
		} catch (e) {
		}
	}
	document.head = document.getElementsByTagName('head')[0]
			|| document.documentElement;

	z.restricted = false;

	if (window.frameElement && window.parent) {
		var parentDocument, parentAll;
		try {
			parentDocument = window.parent.document;
			parentAll = window.parent.document.getElementsByTagName('*');
			parentDocument = parentAll = null;
		} catch (ex) {
			z.restricted = true;
		}
	}

	/**
	 * 异步加载脚本 url:js文件路径，相对于引用js框架的页面，如果要从js框架根目录开始引用需自行加上z.JSLIBPATH
	 * onsuccess:js文件加载后的回调函数
	 */
	z.loadJS = function(url, onsuccess) {
		var head = document.getElementsByTagName('head')[0]
				|| document.documentElement, script = document
				.createElement('script'), done = false;
		script.src = url;

		script.onerror = script.onload = script.onreadystatechange = function() {
			if (!done
					&& (!this.readyState || this.readyState === "loaded" || this.readyState === "complete")) {
				done = true;
				if (onsuccess) {
					onsuccess();
				}
				script.onerror = script.onload = script.onreadystatechange = null;
				head.removeChild(script);
			}
		};
		head.appendChild(script);
	};
	/**
	 * 描述:请求Ajax 带返回值 callback:回调函数
	 */
	z.ajax = function(url, data, callback) {
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			cache : false,
			async : false,
			success : function(data) {
				callback(data);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.status == 800
						|| XMLHttpRequest.status == 803) {
					alert("貌似您好久没操作了,请重新登陆吧");
					window.location = "../admin/login.do";
				}

			}
		});
	};
	var tag = true;
	/**
	 * 描述:请求Ajax 带返回值 callback:回调函数
	 */
	z.asyncAjax = function(url, data, callback) {
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			cache : false,
			beforeSend : function() {
				top.Dg.z.loading(true);
				if (!tag) {
					return false;
				}
				tag = false;
			},
			success : function(data) {
				callback(data);
				setTimeout(function() {
					top.Dg.z.loading(false);
				}, 1000);
				tag = true;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				top.Dg.z.loading(true);
				tag = true;
				if (XMLHttpRequest.status == 800
						|| XMLHttpRequest.status == 803) {
					alert("貌似您好久没操作了,请重新登陆吧");
					window.location = "../admin/login.do";
				}

			}
		});
	};
	z.confirmAjax = function(msg, url, data, callback) {
		top.Dg.confirm(msg, function() {
			z.ajax(url, data, function() {
				callback();
			});
		});
	};
	z.isNN = function(obj) {
		if ($(obj).length > 0) {
			return true
		} else {
			return false;
		}
	};
	z.checkHas = function(str1, str2) {
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
	 * 加载jsonp脚本
	 */
	z.loadJsonp = (function() {
		var seq = +(new Date());
		return function(url, onsuccess, options) {
			var funName = "z_jsonp" + seq++, callbackReplacer = options.callbackReplacer || /%callbackfun%/ig;
			window[funName] = function(data) {
				if (onsuccess) {
					onsuccess(data);
				}
				window[funName] = null;
			};
			if (callbackReplacer.test(url)) {
				url = url.replace(callbackReplacer, funName);
			} else {
				url += (/\?/.test(url) ? "&" : "?") + "callback=" + funName;
			}
			z.loadJs(url, null);
		};
	}());
	/**
	 * 加载脚本 url:js文件路径，因有加z.PATH，所以路径是相对于js框架根目录开始
	 */
	z.importJS = z.importJs = function(url) {
		if (!/^\/|^\w+\:\/\//.test(url)) {
			url = z.JSLIBPATH + url;
		}
		if (!document.body || document.readyState == 'loading') {
			document.write('<script type="text/javascript" src="' + url
					+ '"><\/script>');
		} else {
			z.loadJS(url);
		}

	};
	/**
	 * 异步加载CSS文件 url:css文件路径，相对于引用js框架的页面，如果要从js框架根目录开始引用需自行加上z.JSLIBPATH
	 */
	z.loadCSS = z.loadCss = function(url) {
		var head = document.getElementsByTagName('head')[0]
				|| document.documentElement;
		if (z.isIE) {
			document.createStyleSheet(url);
		} else {
			var e = document.createElement('link');
			e.rel = 'stylesheet';
			e.type = 'text/css';
			e.href = url;
			head.appendChild(e);
		}
	};
	/**
	 * 描述:对字符串进行编码(后台需要解码)
	 */
	z.encodeURI = function(str) {
		return encodeURI(str);
	}
	/**
	 * 描述:对字符串进行编码
	 */
	z.encodeURIComponent = function(str) {
		return encodeURIComponent(str);
	}
	/**
	 * 加载CSS文件 url:css文件路径，因有加z.PATH，所以路径是相对于js框架根目录开始
	 */
	z.importCSS = z.importCss = function(url) {
		if (!/^\/|^\w+\:\/\//.test(url)) {
			url = z.JSLIBPATH + url;
		}
		if (!document.body || document.readyState == 'loading') {
			document.write('<link rel="stylesheet" type="text/css" href="'
					+ url + '" />');
		} else {
			z.loadCSS(url);
		}

	};
	/**
	 * 添加向页面内添加一个style标签，并添加规则
	 */
	z.addStyle = function(styleElId, cssStr) {
		if (!cssStr) {
			cssStr = styleElId;
			styleElId = 'style' + (1 + z.startTime);
		}
		if (cssStr.indexOf('{') < 1 && cssStr.indexOf('}') < 1
				&& /^\S$/.test(cssStr)) {
			return z.loadCSS(cssStr);
		}
		var styleEl = document.getElementById(styleElId);
		if (!styleEl) {
			styleEl = document.createElement('style');
			styleEl.type = 'text/css';
			styleEl.id = styleElId;
			document.getElementsByTagName('head')[0].appendChild(styleEl);
		}
		if (styleEl.styleSheet) {
			styleEl.styleSheet.cssText += cssStr;
		} else {
			cssStr = document.createTextNode(cssStr);
			styleEl.appendChild(cssStr);
		}
		return styleEl;
	};
	/**
	 * 关闭当前选中tab
	 */
	z.closeTab = function() {
		var tabs_container = top.$("#tabs_container");
		$().closeTab(tabs_container.find('.selected').attr('id').substr(5));
	};
	z.addTab = function(option) {
		var tabs_container = top.$("#tabs_container");
		$().addTab(option);
	};
	/**
	 * 获取当前选择选项卡ID
	 */
	z.Current_iframeID = function() {
		var tabs_container = $("#tabs_container");
		return "tabs_" + tabs_container.find('.selected').attr('id').substr(5)
				+ "_iframe";
	};
	/**
	 * 获取当前选择选项卡ID
	 */
	z.getTabId = function() {
		var tabs_container = $("#tabs_container");
		return tabs_container.find('.selected').attr('id').substr(5)
				+ "_iframe";
	};
	z.getTabById = function(tableId) {
		return $('#' + tableId)[0];
	};
	z.Current_iframe = function() {
		return $('#' + z.Current_iframeID())[0];
	};
	z.isNull = function(v, def) {
		var obj = true;
		if (v == undefined || v == "" || v == null) {
			obj = true;
		} else {
			obj = false;
		}
		if (def) {
			obj = def;
		}
		return obj;
	};
	z.getObj = function(v, def) {
		var obj = "";
		if (v == undefined || v == "" || v == null) {
			obj = def;
		} else {
			obj = v;
		}
		return obj;
	};
	z.checkObj = function(id, msg) {
		var isOK = true;
		if (id == undefined || id == "" || id.lenght == 0) {
			isOK = false;
			this.tip(msg, 4000, 300, "error");
		}
		return isOK;
	};
	/**
	 * 刷新iframe
	 */
	z.refreshIframe = function(iframeId) {
		var myiframe = $("#" + iframeId);
		var src = myiframe.attr("src");
		myiframe.attr("src", src);
	};

	/**
	 * 顶端消息提示 msg, title
	 */
	z.tip = function(msg,type,title) {

		toastr.options = {
			closeButton : true,
			debug : false,
			progressBar : true,
			positionClass : "toast-top-center",
			onclick : null,
			showDuration : "400",
			hideDuration : "1000",
			timeOut : "7000",
			extendedTimeOut : "1000",
			showEasing : "swing",
			hideEasing : "linear",
			showMethod : "fadeIn",
			hideMethod : "fadeOut"
		}
		var $toast = toastr[type](msg,title);
	};


	/**
	 * 开启遮罩
	 */
	z.loading = function(bool) {

		if (bool) {
			$("#loading").show();
		} else {
			setInterval(z.loadinghide, 900);
		}
	};
	z.loadinghide = function() {
		if (top.$("#loading") != null) {
			top.$("#loading").hide();
		}
	};
})();