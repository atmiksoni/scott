/* 总应收金额 */
var receivable;
/* 总优惠金额 */
var privilege;
/* 总滞纳金 */
var latefee;
/* 本次应收 */
var this_receivable;
/* 本次实收 */
var paymoney;
/* 总结余存入综合账户 */
var surplus;
/* 综合费用应收金额 */
var pmc_receivable;
/* 不抵扣费用应收金额 */
var no_receivable;
/* 不使用账户优惠金额 */
var no_this_privilege;
/* 不使用账户滞纳金 */
var no_this_latefee;
/* 综合费用本次应收金额 */
var pmc_this_receivable;
/* 综合费用本次优惠金额 */
var pmc_this_privilege;
/* 综合费用本次滞纳金 */
var pmc_this_latefee;
/* 水费应收金额 */
var water_receivable;
/* 水费本次应收金额 */
var water_this_receivable;
/* 水费本次优惠金额 */
var water_this_privilege;
/* 水费本次滞纳金 */
var water_this_latefee;
/* 电费应收金额 */
var electric_receivable;
/* 电费本次应收金额 */
var electric_this_receivable;
/* 电费本次优惠金额 */
var electric_this_privilege;
/* 电费本次滞纳金 */
var electric_this_latefee;
/* 不抵扣费项交款金额 */
var no_paymoney;
/* 综合费用交款金额 */
var pmc_paymoney;
/* 水费交款金额 */
var water_paymoney;
/* 电费交款金额 */
var electric_paymoney;
/* 综合费本次用结余 */
var pmc_surplus;
/* 水费本次结余 */
var water_surplus;
/* 电费本次结余 */
var electric_surplus;
var houseId;
var sdigits = "";
var oldsd = "-10";
var pmcqf;
var pmcbalStatus = 0;// 综合账户状态
var warterbalStatus = 0;// 水费账户状态
var electricbalStatus = 0;// 电费账户状态
var pmcbal;// 综合账户余额
var warterbal;// 水费账户余额
var electricbal;// 电费账户余额
var disabled = true;// 交款金额输入框禁用状态
var tag = true;// 是否关闭全局精确度
var hasPmc = false;
var hasEle = false;
var hasWater = false;
var ftl = "";
ftl += "<div style='float:left'>本次费用:【应收金额：<span id='receivable'  class='money'>0.00</span>";
ftl += "<span class='spanfont'> 优惠金额：<span id='privilege' class='money'>0.00</span></span>";
ftl += "<span class='spanfont'> 滞纳金：<span id='slatefee' class='money'>0.00</span></span>";
ftl += "<span class='spanfont'> 本次应收：<span id='this_receivable' class='money'>0.00</span>】</span>";
ftl += "</div>";
ftl += "<div style='float:right'>";
ftl += "{{if enable(balance.pmcbalStatus)}}";
ftl += "【综合账户:<span id='pmcsurplus' class='money'>0.00</span>";
ftl += "{{/if}}";
ftl += "{{if enable(balance.warterbalStatus)}}";
ftl += "<span class='spanfont'>水费:<span id='watersurplus' class='money'>0.00</span></span>";
ftl += "{{/if}}";
ftl += "{{if enable(balance.electricbalStatus)}}";
ftl += "<span class='spanfont'>电费:<span id='electricsurplus' class='money'>0.00</span></span>";
ftl += "{{/if}}";
ftl += "{{if enable(balance.pmcbalStatus)}}";
ftl += "】";
ftl += "{{/if}}";
ftl += "</div>";
function initTitle(data) {
	houseId = data.houseId;
	pmcqf = data.pmcqf;
	if (data.sdigits != "-1" && houseId != "") {
		sdigits = data.sdigits;
		tag = false;
	}
	pmcbalStatus = data.pmcbalStatus;
	warterbalStatus = data.warterbalStatus;
	electricbalStatus = data.electricbalStatus;
	pmcbal = data.pmcbal;
	warterbal = data.warterbal;
	electricbal = data.electricbal;
	var render = template.compile(ftl);
	template.helper('enable', function(v) {
		if (v) {
			return true;
		} else {
			return false;
		}
	});
	return render({
		balance : data
	});

}
/**
 * 初始化计算元素
 */
function init() {
	receivable = 0.00;
	privilege = 0.00;
	latefee = 0.00;
	this_receivable = 0.00;
	paymoney = 0.00;
	surplus = 0.00;
	no_receivable = 0.00;
	no_this_privilege = 0.00;
	no_this_latefee = 0.00;
	pmc_receivable = 0.00;
	pmc_this_receivable = 0.00;
	pmc_this_privilege = 0.00;
	pmc_this_latefee = 0.00;
	water_receivable = 0.00;
	water_this_receivable = 0.00;
	water_this_privilege = 0.00;
	water_this_latefee = 0.00;
	electric_receivable = 0.00;
	electric_this_receivable = 0.00;
	electric_this_privilege = 0.00;
	electric_this_latefee = 0.00;
	no_paymoney = 0.00;
	pmc_paymoney = 0.00;
	water_paymoney = 0.00;
	electric_paymoney = 0.00;
	pmc_surplus = 0.00;
	water_surplus = 0.00;
	electric_surplus = 0.00;
}
/** 计算费用入口 */
function feeCount() {
	var mymin = 0;
	init();
	getPaymoney();
	if (this_receivable > 0) {
		disabled = false;
	} else {
		disabled = false;
	}
	if (!pmcqf) {
		mymin = paymoney;
	}
	if (top.Dg.z.isFalse(pmcbalStatus)) {
		disabled = true;
	}
	$("#paymoney").numberbox({
		min : mymin,
		disabled : disabled
	});
	/** 实收金额 */
	$("#paymoney").numberbox("setValue", paymoney.toFixed2(sdigits));
	/** 本次应收 */
	$("#this_receivable").html(this_receivable.toFixed2(sdigits));
	/** 应收金额 */
	$("#receivable").html(receivable.toFixed2(2));
	/** 优惠金额 */
	$("#privilege").html(privilege.toFixed2(2));
	/** 滞纳金 */
	$("#slatefee").html(latefee.toFixed2(2));
	var rows = feereportdetails.getSelectedRows();
	if (rows.length > 0 && receivable > 0) {
		$("#paymoneybtn,#createbills,#youhui,#feedel,#delyouhui").linkbutton("enable");
		if (rows.length > 1) {
			$("#latefee").linkbutton("disable");
		} else {
			$("#latefee").linkbutton("enable");
		}
	} else {
		$("#youhui,#feedel,#paymoneybtn,#createbills,#delyouhui,#latefee").linkbutton("disable");
	}
}
/**
 * 金额计算
 */
function getPaymoney() {
	hasPmc = false;
	hasEle = false;
	hasWater = false;
	var rows = feereportdetails.getSelectedRows();
	if (rows.length > 0) {
		$(rows).each(function(i, row) {
			if (tag) {
				sdigits = row.feeStandard_sdigits;
				if (oldsd != sdigits && oldsd != "-10") {
					init();
					top.Dg.z.toastr("实收精确度不一致,请分批收取", 'error', '');
					return false;
				}
				oldsd = sdigits;
			}
			var balanceCode = row.feeStandard_balanceCode;
			/** 优惠金额 */
			var privilege = getVal(row.privilege, 0.00);
			/** 滞纳金 */
			var latefee = getVal(row.latefee, 0.00);

			/** 应收金额 */
			var receivable = getVal(row.receivable, 0.00);
			if (balanceCode == 'PMCBAL') {
				pmc_receivable = pmc_receivable.add(receivable);
				pmc_this_privilege = pmc_this_privilege.add(privilege);
				pmc_this_latefee = pmc_this_latefee.add(latefee);
				hasPmc = true;
			}
			if (balanceCode == 'WATERBAL') {
				if (top.Dg.z.isTrue(warterbalStatus)) {
					water_receivable = water_receivable.add(receivable);
					water_this_privilege = water_this_privilege.add(privilege);
					water_this_latefee = water_this_latefee.add(latefee);
				} else {
					pmc_receivable = pmc_receivable.add(receivable);
					pmc_this_privilege = pmc_this_privilege.add(privilege);
					pmc_this_latefee = pmc_this_latefee.add(latefee);
				}
				hasWater = true;
			}
			if (balanceCode == 'ELECTRICBAL') {
				if (top.Dg.z.isTrue(electricbalStatus)) {
					electric_receivable = electric_receivable.add(receivable);
					electric_this_privilege = electric_this_privilege.add(privilege);
					electric_this_latefee = electric_this_latefee.add(latefee);
				} else {
					pmc_receivable = pmc_receivable.add(receivable);
					pmc_this_privilege = pmc_this_privilege.add(privilege);
					pmc_this_latefee = pmc_this_latefee.add(latefee);
				}
				hasEle = true;
			}
			if (balanceCode == 'NO') {
				no_receivable = no_receivable.add(receivable);
				no_this_privilege = no_this_privilege.add(privilege);
				no_this_latefee = no_this_latefee.add(latefee);
			}
		});
		$("#paymoney").numberbox({
			precision : sdigits
		});
		/* 不抵扣费项交款金额(应收-优惠+滞纳金) */
		no_paymoney = no_receivable - no_this_privilege + no_this_latefee;
		/* 综合费用交款金额(应收-优惠+滞纳金) */
		pmc_paymoney = pmc_receivable - pmc_this_privilege + pmc_this_latefee;
		/* 水费交款金额(应收-水费优惠+滞纳金) */
		water_paymoney = water_receivable - water_this_privilege + water_this_latefee;
		/* 电费交款金额 (应收-电费优惠+滞纳金) */
		electric_paymoney = electric_receivable - electric_this_privilege + electric_this_latefee;
		/* 总优惠金额 */
		privilege = no_this_privilege.add(pmc_this_privilege).add(water_this_privilege).add(electric_this_privilege);
		/* 总滞纳金 */
		latefee = no_this_latefee.add(pmc_this_latefee).add(water_this_latefee).add(electric_this_latefee);
		/* 本次总应收金额 */
		receivable = no_receivable.add(pmc_receivable).add(water_receivable).add(electric_receivable);
		/* 计算结余 */
		initSurplus();
		/* 本次应收 */
		this_receivable = no_paymoney.add(pmc_this_receivable).add(water_this_receivable).add(electric_this_receivable);
		/* 总交款金额 */
		paymoney = no_paymoney.add(pmc_paymoney).add(water_paymoney).add(electric_paymoney);// 总交款金额
	} else {
		hasPmc = false;
		hasEle = false;
		hasWater = false;
		$("#watersurplus").html("0");
		$("#electricsurplus").html("0");
		$("#pmcsurplus").html("0");
		oldsd = "-10";
	}
}
/**
 * 计算结余金额
 */
function initSurplus() {

	/* 启用水费账户余额 */
	if (top.Dg.z.isTrue(warterbalStatus) && top.Dg.z.isTrue(hasWater)) {
		/* 如果余额大于本次交款金额 */
		if (warterbal >= water_paymoney) {
			water_surplus = (warterbal - water_paymoney).toFixed2(sdigits);
			/* 水费本次应收 */
			water_this_receivable = 0.00;
			water_paymoney = 0.00;/* 本次水费不用交款 */
		} else {
			/* 扣除水费账户余额 */
			var money = (water_paymoney - warterbal).toFixed2(sdigits);
			/* 水费本次应收 */
			water_this_receivable = money;
			/* 水费本次结余 */
			water_surplus = getBalance(money, sdigits);
			water_paymoney = ceil(money);/* 水费本次实收 */
		}
		$("#watersurplus").html(water_surplus);
	} else {
		/** 未启用账户则本次应收=交款金额 */
		water_this_receivable = water_paymoney;
	}

	/* 启用电费账户余额 */
	if (top.Dg.z.isTrue(electricbalStatus) && top.Dg.z.isTrue(hasEle)) {
		if (electricbal >= electric_paymoney) {
			electric_surplus = (electricbal - electric_paymoney).toFixed2(sdigits);
			electric_paymoney = 0.00;
			electric_this_receivable = 0.00;
		} else {
			var money = (electric_paymoney - electricbal).toFixed2(sdigits);
			electric_surplus = getBalance(money, sdigits);
			electric_this_receivable = money;
			electric_paymoney = ceil(money);
		}
		$("#electricsurplus").html(electric_surplus);
	} else {
		electric_this_receivable = electric_paymoney;
	}

	/* 启用综合账户余额 */
	if (top.Dg.z.isTrue(pmcbalStatus) && top.Dg.z.isTrue(hasPmc)) {
		if (pmcbal >= pmc_paymoney) {
			pmc_surplus = (pmcbal - pmc_paymoney).toFixed2(sdigits);
			pmc_this_receivable = 0.00;
			pmc_paymoney = 0.00;
		} else {
			var money = (pmc_paymoney - pmcbal).toFixed2(sdigits);
			pmc_surplus = getBalance(money, sdigits);
			pmc_this_receivable = money;
			pmc_paymoney = ceil(money);
		}
		/** 水费结余到综合账户 */
		if (top.Dg.z.isFalse(warterbalStatus) && top.Dg.z.isTrue(pmcbalStatus)) {
			pmc_surplus = ((parseFloat(pmc_surplus) + parseFloat(water_surplus))).toFixed2(sdigits);
		}
		/** 电费费结余到综合账户 */
		if (top.Dg.z.isFalse(electricbalStatus) && top.Dg.z.isTrue(pmcbalStatus)) {
			pmc_surplus = ((parseFloat(pmc_surplus) + parseFloat(electric_surplus))).toFixed2(sdigits);
		}
		$("#pmcsurplus").html(pmc_surplus);

	} else {
		pmc_this_receivable = pmc_paymoney;
	}

}
/**
 * 收取金额发生变化
 */
function receivableOnChange(nv, ov) {
	var change = parseFloat(nv) - parseFloat(paymoney);
	var pmcsurplus = change.add(pmc_surplus);
	$("#pmcsurplus").html(pmcsurplus.toFixed2(sdigits));
}
/**
 * 收费提交
 */
function paymoneycomfirm(url) {
	var feeReportDetailsIds = feereportdetails.getSelectionsArray("feeReportDetailsId");
	var paymoney = $("#paymoney").numberbox("getValue");
	var myparams = {
		feeReportDetailsIds : feeReportDetailsIds.join(","),
		paymoney : paymoney,
		houseId : houseId,
		hasPmc : hasPmc,
		hasEle : hasEle,
		hasWater : hasWater
	};
	feereportdetails.open2({
		title : "温馨提示,请仔细核对缴费信息",
		maxmin : false,
		url : url,
		w : '550px',
		h : '300px',
		newbtn : [ '收费', '收费并打印' ],
		btn2 : function(index) {
			parent.layer.getChildFrame('#isprint', index).val(1);
			parent.layer.getChildFrame('#btn_sub', index).click();
			return false;
		},
		params : myparams
	});
}
/**
 * 创建为支付票据
 */
function createbills(url) {
	var feeReportDetailsIds = feereportdetails.getSelectionsArray("feeReportDetailsId");
	var paymoney = $("#paymoney").numberbox("getValue");
	var myparams = {
		feeReportDetailsIds : feeReportDetailsIds.join(","),
		paymoney : paymoney,
		isprint : 0,
		houseId : houseId,
		hasPmc : hasPmc,
		hasEle : hasEle,
		hasWater : hasWater
	};
	top.Dg.cfa("该操作创建账单会推送给业主微信端进行支付!", url, myparams, function() {
		feereportdetails.refresh();
	});
}
/* 批量收费 */
function batchpay() {
	alert(3);
}