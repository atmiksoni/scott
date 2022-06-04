(function(yui, $yui) {

	/**
	 * 文本框校验
	 */
	$yui.textValid = function(text, alertMsg) {
		if (!text.trim()) {
			$yui.toast(alertMsg);
			return false;
		} else {
			return true;
		}

	};
	/**
	 * 数字校验
	 */
	$yui.numValid = function(num, alertMsg) {
		var pattern = /^[0-9]*[0-9][0-9]*$/;
		var flag = pattern.test(num);
		if (!flag) {
			$yui.toast(alertMsg);
			return false;
		} else {
			return true;
		}
	};
	/**
	 * 手机号校验
	 */
	$yui.mobileValid = function(mobile, alertMsg) {
		var pattern = /^1[3|5|7|8][0-9]\d{4,8}$/;
		var flag = pattern.test(mobile);
		if (!flag) {
			$yui.toast(alertMsg);
			return false;
		} else {
			return true;
		}
	};
	/**
	 * 邮箱校验
	 */
	$yui.emailValid = function(email, alertMsg) {
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
		var flag = reg.test(email);
		if (!flag) {
			$yui.toast(alertMsg);
			return false;
		} else {
			return true;
		}
	};
	/**
	 * 浮点数校验
	 */
	$yui.floatValid = function(num, alertMsg, type) {
		var pattern;
		if (!type || type == 1) {
			pattern = /^-?\d+[\.\d]?\d{0,1}$/; // 保留一位小数
		} else {
			pattern = /^-?\d+[\.\d]?\d{0,2}$/; // 保留二位小数
		}
		var flag = pattern.test(num);
		if (!flag) {
			$yui.toast(alertMsg);
			return false;
		} else {
			return true;
		}
	};

}(YDUI, $yui));
