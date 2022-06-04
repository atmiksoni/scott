wxApi = {
	jsurl : "http://res.wx.qq.com/open/js/jweixin-1.0.0.js",
	jsapi : "/weixin/jsapi.wx",
	getScript : function(option) {
		var script = document.createElement("script");
		script.type = "text/javascript";
		script.src = this.jsurl;
		script.charset = option.charset || "UTF-8";
		script.onload = script.onreadystatechange = function() {
			if (!this.readyState || this.readyState == "loaded" || this.readyState == "complete") {
				this.onload = this.onreadystatechange = null;
				option.callback && option.callback();
			}
		}
		document.getElementsByTagName("head")[0].appendChild(script);
	},
	wxConfig : function(option) {
		$.post(this.jsapi, {
			url : window.location.href
		}, function(data) {
			wx.config({
				debug : false,
				appId : data.appId,
				timestamp : data.timestamp,
				nonceStr : data.nonceStr,
				signature : data.signature,
				jsApiList : [ 'checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo', 'hideMenuItems', 'showMenuItems', 'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem', 'translateVoice', 'startRecord', 'stopRecord', 'onRecordEnd', 'playVoice', 'pauseVoice', 'stopVoice', 'uploadVoice', 'downloadVoice', 'chooseImage', 'previewImage', 'uploadImage', 'downloadImage', 'getNetworkType', 'openLocation', 'getLocation', 'hideOptionMenu', 'showOptionMenu', 'closeWindow', 'scanQRCode', 'chooseWXPay', 'openProductSpecificView', 'addCard', 'chooseCard', 'openCard' ]
			});
			option.callback && option.callback();
		}, 'json');
	},
	init : function(func) {
		if (navigator.userAgent.toLowerCase().match(/micromessenger/i) != "micromessenger") {
			return;
		}
		wxApi.getScript({
			callback : function() {
				wxApi.wxConfig({
					callback : function() {
						wx.ready(function() {
							func && func();
						});
						wx.error(function(res) {

						});
					}
				})
			}
		})
	},
	uploadImage : function(localIds, config) {
		var localId = localIds.pop();
		wx.uploadImage({
			localId : localId,
			isShowProgressTips : 1,
			success : function(res) {
				config.callback && config.callback(res, localId);
				if (localIds.length > 0) {
					wxApi.uploadImage(localIds, config);
				}
			}
		});
	},
	choosePay : function(data, func) {
		wx.chooseWXPay({
			timestamp : data.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
			nonceStr : data.nonceStr, // 支付签名随机串，不长于 32 位
			package : data.packageWithPrepayId, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
			signType : data.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
			paySign : data.paySign, // 支付签名
			success : function(res) {
				func && func(res);
			}
		});
	},
	chooseImage : function(option) {
		var config = {
			count : 1,
			callback : function(res, localIds) {
			}
		}
		$.extend(config, option);
		wx.chooseImage({
			count : config.count,
			sizeType : [ 'original', 'compressed' ],
			sourceType : [ 'album', 'camera' ],
			success : function(res) {
				wxApi.uploadImage(res.localIds, config);
			}
		});
	},
	onMenuShareTimeline : function(title, link, img, ok, canle) {
		wx.onMenuShareTimeline({
			title : title, // 分享标题
			link : link, // 分享链接
			imgUrl : img, // 分享图标
			success : function() {
				ok();
			},
			cancel : function() {
				canle();
			}
		});
	}

}