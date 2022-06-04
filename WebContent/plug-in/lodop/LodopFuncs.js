var CreatedOKLodop7766 = null;

/* 判断是否需要安装CLodop云打印服务器 */
function needCLodop() {
	try {
		return true;
		//return installClodop();
	} catch (err) {
		return true;
	}
}
function installClodop() {
	var ua = navigator.userAgent;
	if (ua.match(/Windows\sPhone/i) != null) {
		return true;
	}
	if (ua.match(/iPhone|iPod/i) != null) {
		return true;
	}
	if (ua.match(/Android/i) != null) {
		return true;
	}
	if (ua.match(/Edge\D?\d+/i) != null) {
		return true;
	}
	if (ua.match(/QQBrowser/i) != null) {
		return false;
	}
	var verTrident = ua.match(/Trident\D?\d+/i);
	var verIE = ua.match(/MSIE\D?\d+/i);
	var verOPR = ua.match(/OPR\D?\d+/i);
	var verFF = ua.match(/Firefox\D?\d+/i);
	var x64 = ua.match(/x64/i);
	if ((verTrident == null) && (verIE == null) && (x64 !== null)) {
		return true;
	} else if (verFF !== null) {
		verFF = verFF[0].match(/\d+/);
		if (verFF[0] >= 42) {
			return true;
		}
	} else if (verOPR !== null) {
		verOPR = verOPR[0].match(/\d+/);
		if (verOPR[0] >= 32) {
			return true;
		}
	} else if ((verTrident == null) && (verIE == null)) {
		var verChrome = ua.match(/Chrome\D?\d+/i);
		if (verChrome !== null) {
			verChrome = verChrome[0].match(/\d+/);
			if (verChrome[0] >= 42) {
				return true;
			}
		}
	}
}
/* 页面引用CLodop云打印必须的JS文件 */
if (needCLodop()) {
	var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
	var oscript = document.createElement("script");
	oscript.src = "http://127.0.0.1:8000/CLodopfuncs.js?priority=1";
	head.insertBefore(oscript, head.firstChild);
	/*
	 * 本机浏览器的后补端口8001： oscript = document.createElement("script"); oscript.src =
	 * "http://www.zhiwanjia.com/CLodopfuncs.js?priority=2";
	 * head.insertBefore(oscript, head.firstChild);
	 */
};

/* 获取LODOP对象的主过程 */
function getLodop(oOBJECT, oEMBED) {
	var strHtmInstall = "<a href='../../plug-in/lodop/install_lodop32.exe' style='font-size:14px;color:#FF00FF' target='_self'>点击安装打印控件</a>,安装后刷新页面";
	var strHtmUnInstall = "<a href='../../plug-in/lodop/uninstall_lodop32.exe' style='font-size:14px;color:#FF00FF' target='_self'>卸载打印控件</a>,卸载后刷新页面";
	var strHtmUpdate = "<a href='../../plug-in/lodop/install_lodop32.exe' style='font-size:14px;color:#FF00FF' target='_self'>升级打印控件</a>,升级后请重新进入";
	var strHtm64_Install = "<a href='../../plug-in/lodop/install_lodop64.exe' style='font-size:14px;color:#FF00FF' target='_self'>点击安装打印控件</a>,安装后刷新页面</font>";
	var strHtm64_Update = "<a href='../../plug-in/lodop/install_lodop64.exe' style='font-size:14px;color:#FF00FF'  target='_self'>升级打印控件</a>,升级后请重新进入</font>";
	var strCLodopInstall = "<a href='../../plug-in/lodop/CLodopPrint_Setup_for_Win32NT.exe' style='font-size:14px;color:#FF00FF' target='_self'>点击安装CLodop</a>,安装后刷新页面。</font>";
	var strCLodopUpdate = "<a href='../../plug-in/lodop/CLodopPrint_Setup_for_Win32NT.exe' style='font-size:14px;color:#FF00FF' target='_self'>执行升级CLodop</a>,升级后刷新页面。</font>";
	var LODOP;
	try {
		var isIE = (navigator.userAgent.indexOf('MSIE') >= 0) || (navigator.userAgent.indexOf('Trident') >= 0);
		if (needCLodop()) {
			try {
				LODOP = getCLodop();
			} catch (err) {
			}
			if (!LODOP && document.readyState !== "complete") {
				top.Dg.z.toastr(strCLodopInstall, 'error', '');
				return;
			}
			if (!LODOP) {
				var a = getLp();
				if (a.VERSION) {
					top.Dg.z.toastr(strHtmUnInstall, 'error', '');
				} else {
					top.Dg.z.toastr(strCLodopInstall, 'error', '');
				}
				return;
			} else {

				if (CLODOP.CVERSION < "2.0.5.3") {
					if (isIE) {
						top.Dg.z.toastr(strCLodopUpdate, 'error', '');
					} else {
						top.Dg.z.toastr(strCLodopUpdate, 'error', '');
					}
				}
				if (oEMBED && oEMBED.parentNode) {
					oEMBED.parentNode.removeChild(oEMBED);
				}
				if (oOBJECT && oOBJECT.parentNode) {
					oOBJECT.parentNode.removeChild(oOBJECT);
				}
			}
		} else {
			var is64IE = isIE && (navigator.userAgent.indexOf('x64') >= 0);
			/* 如果页面有Lodop就直接使用，没有则新建 */
			if (oOBJECT != undefined || oEMBED != undefined) {
				if (isIE) {
					LODOP = oOBJECT;
				} else {
					LODOP = oEMBED;
				}
			} else if (CreatedOKLodop7766 == null) {
				LODOP = document.createElement("object");
				LODOP.setAttribute("width", 0);
				LODOP.setAttribute("height", 0);
				LODOP.setAttribute("style", "position:absolute;left:0px;top:-100px;width:0px;height:0px;");
				if (isIE) {
					LODOP.setAttribute("classid", "clsid:2105C259-1E0C-4534-8141-A753534CB4CA");

				} else {
					LODOP.setAttribute("type", "application/x-print-lodop");
				}
				document.documentElement.appendChild(LODOP);
				CreatedOKLodop7766 = LODOP;
			} else
				LODOP = CreatedOKLodop7766;
			/* Lodop插件未安装时提示下载地址 */
			if ((LODOP == null) || (typeof (LODOP.VERSION) == "undefined")) {
				if (is64IE) {
					msg = strHtm64_Install;
				} else if (isIE) {
					msg = strHtmInstall;
				} else {
					msg = strHtmInstall;
				}
				top.Dg.z.toastr(msg, 'error', '');
				return;
			}

		}
		if (LODOP.VERSION < "6.2.0.4") {
			if (needCLodop()) {
				msg = strCLodopUpdate;
			} else if (is64IE) {
				msg = strHtm64_Update;
			} else if (isIE) {
				msg = strHtmUpdate;
			} else {
				msg = strHtmUpdate;
			}
			top.Dg.z.toastr(msg, 'error', '');
			return LODOP;
		}
		return LODOP;
	} catch (err) {
		alert("getLodop出错:" + err);
	}
}
function getLp() {
	var LODOP;
	try {
		var isIE = (navigator.userAgent.indexOf('MSIE') >= 0) || (navigator.userAgent.indexOf('Trident') >= 0);
		var is64IE = isIE && (navigator.userAgent.indexOf('x64') >= 0);
		/* 如果页面有Lodop就直接使用，没有则新建 */
		if (CreatedOKLodop7766 == null) {
			LODOP = document.createElement("object");
			LODOP.setAttribute("width", 0);
			LODOP.setAttribute("height", 0);
			LODOP.setAttribute("style", "position:absolute;left:0px;top:-100px;width:0px;height:0px;");
			if (isIE) {
				LODOP.setAttribute("classid", "clsid:2105C259-1E0C-4534-8141-A753534CB4CA");

			} else {
				LODOP.setAttribute("type", "application/x-print-lodop");
			}
			document.documentElement.appendChild(LODOP);
			CreatedOKLodop7766 = LODOP;
		} else {
			LODOP = CreatedOKLodop7766;
		}
		return LODOP;
	} catch (err) {
		alert("getLodop出错:" + err);
	}
}
