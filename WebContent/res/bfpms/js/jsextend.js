/**
 * toFixed 方法的修复
 */
Number.prototype.toFixed2 = function(n) {
	if (n > 20 || n < 0) {
		throw new RangeError('toFixed() digits argument must be between 0 and 20');
	}

	var number = this;

	if (isNaN(number) || number >= Math.pow(10, 21)) {
		return number.toString();
	}
	if (typeof (n) == 'undefined' || n == 0) {
		return (Math.round(number)).toString();
	}

	var result = number.toString();
	var arr = result.split('.');

	// 整数的情况
	if (arr.length < 2) {
		result += '.';
		for (var i = 0; i < n; i++) {
			result += '0';
		}
		return result;
	}

	var integer = arr[0];
	var decimal = arr[1];
	if (decimal.length == n) {
		return result;
	}
	if (decimal.length < n) {
		for (var i = 0; i < n - decimal.length; i++) {
			result += '0';
		}
		return result;
	}
	result = integer + '.' + decimal.substr(0, n);

	var last = decimal.substr(n, 1);

	// 四舍五入，转换为整数再处理，避免浮点数精度的损失
	if (parseInt(last) >= 5) {
		var x = Math.pow(10, n);
		result = (parseFloat(result) * x + 1) / x;
		result = result.toFixed(n);
	}
	return result;

};

// 加法函数
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}
// 给Number类型增加一个add方法，，使用时直接用 .add 即可完成计算。
Number.prototype.add = function(arg) {
	return accAdd(arg, this);
};

// 减法函数
function Subtr(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	// last modify by deeka
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 给Number类型增加一个sub方法，，使用时直接用 .sub 即可完成计算。
Number.prototype.sub = function(arg) {
	return Subtr(this, arg);
};

// 乘法函数
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}
// 给Number类型增加一个mul方法，使用时直接用 .mul 即可完成计算。
Number.prototype.mul = function(arg) {
	return accMul(arg, this);
};

// 除法函数
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
// 给Number类型增加一个div方法，，使用时直接用 .div 即可完成计算。
Number.prototype.div = function(arg) {
	return accDiv(this, arg);
};

/**
 * 取整
 */
function ceil(amountMoney) {
	return Math.ceil(amountMoney);
}

/**
 * 取余
 */
function getBalance(amountMoney, digits) {
	var money = Math.ceil(amountMoney) - amountMoney;
	return money.toFixed(digits);
}
function getVal(v, def) {
	var obj = "";
	if (v == undefined || v == "" || v == null) {
		obj = def;
	} else {
		obj = v;
	}
	return obj;
}
