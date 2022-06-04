var PRINT_SIZE = 10;// 一页打印数量
var BASE_PAGER_HEIGHT = 93;// 纸张高度
var BASE_PAGER_WEIGHT = 200;// 纸张宽度
var TABLE_HEIGHT = 145;// table高度
var TABLE_HEAD = 25;// 标题高度
var TABLE_TR = 20;// table行高度
var UNIT = "mm";
var recordSize = 0;
var otherSize = 0;
var pageSize = 4;
var LODOP;
PrintTemp = function(data) {
	var me = this;
	var ftl = "";
	var pmcbalStatus = data.pmcbalStatus;
	var warterbalStatus = data.warterbalStatus;
	var electricbalStatus = data.electricbalStatus;
	var pmcAlias = "";
	var eletAlias = "";
	var waterAlias = "";
	var lastPmcBalance = "";
	var pmcBalance = "";
	var lastElectricBalance = "";
	var electricBalance = "";
	var lastWaterBalance = "";
	var waterBalance = "";
	var privilege = data.privilege;
	var privilegeAlias = "";
	var privilegeVal = "";
	var ljy = "";
	var bjy = "";
	if (top.Dg.z.isTrue(pmcbalStatus)) {
		ljy = "上次余额"
		bjy = "本次余额";
		pmcAlias = data.pmcAlias;
		lastPmcBalance = data.lastPmcBalance;
		pmcBalance = data.pmcBalance;
	}
	if (top.Dg.z.isTrue(warterbalStatus)) {
		ljy = "上次余额"
		bjy = "本次余额";
		waterAlias = data.waterAlias;
		lastWaterBalance = data.lastWaterBalance;
		waterBalance = data.waterBalance;
	}
	if (top.Dg.z.isTrue(electricbalStatus)) {
		ljy = "上次余额"
		bjy = "本次余额";
		eletAlias = data.eletAlias;
		lastElectricBalance = data.lastElectricBalance;
		electricBalance = data.electricBalance;
	}
	if (privilege > 0) {
		privilegeAlias = "优惠";
		privilegeVal = privilege;
	}
	var bg_main = "<table border='1' cellspacing='0' cellpadding='2' style='border:1 solid #000000;border-collapse:collapse; font:13px;flont-family:微软雅黑' width='630'>";
	var bg_header = "";
	var col = 4;
	bg_header += "<tr height='20'>";
	bg_header += "<th width='130' align='center'>收费项</th>";
	bg_header += "<th width='80' align='center'>起始日期</th>";
	bg_header += "<th width='80' align='center'>截止日期</th>";
	if (data.billsType == 2 || data.billsType == 3) {
		bg_header += "<th width='70' align='center'>上次读数</th>";
		bg_header += "<th width='70' align='center'>本次读数</th>";
		col = 6;
	}
	if (data.billsType == 2) {
		bg_header += "<th width='60' align='center'>用量 </th>";
	} else {
		bg_header += "<th width='60' align='center'>数量</th>";
	}

	bg_header += "<th width='70' align='center'>收费标准</th>";

	if (data.latefees > 0) {
		bg_header += "<th width='70' align='center'>滞纳金</th>";
		if (data.billsType == 2 || data.billsType == 3) {
			col = 7;
		} else {
			col = 5;
		}
	}
	bg_header += "<th width='50' align='center'>金额</th>";
	bg_header += "</tr>";
	if (bg_header != "") {
		bg_header = "<thead>" + bg_header + "</thead>";
	}
	var tr_rows = "";
	var tfooter = "";
	tr_rows += "{{each bills.rows as row}}<tr height='15' align='center'>";
	tr_rows += "<td style='font-size:{{getSize(row.feeItemName)}}'>{{row.feeItemName}}</td>";
	tr_rows += "<td>{{row.beginDate}}</td>";
	tr_rows += "<td>{{row.endDate}}</td>";
	if (data.billsType != 1) {
		tr_rows += "<td>{{getVal(row.lastReadNum)}}</td>";
		tr_rows += "<td>{{getVal(row.readNum)}}</td>";
	}
	tr_rows += "<td>{{row.billingDegrees}}</td>";
	tr_rows += "<td>{{row.price}}</td>";
	if (data.latefees > 0) {
		tr_rows += "<td>{{row.latefees}}</td>";
	}
	tr_rows += "<td>￥{{row.amount}}</td>";
	tr_rows += "</tr>{{/each}}";
	if (top.Dg.z.isTrue(pmcbalStatus) || top.Dg.z.isTrue(warterbalStatus) || top.Dg.z.isTrue(electricbalStatus)) {
		tfooter = "<tr height='10'>";
		tfooter += "<td colspan='" + col + "' align='right'></td>";
		tfooter += "<td style='font-weight:bold' align='center'>合计</td>";
		tfooter += "<td align='center'>￥" + data.receivable + "</td>";
		tfooter += "</tr>";
		if (pmcAlias != "" || waterAlias != "" || eletAlias != "" || privilege > 0) {
			tfooter += "<tr height='10' align='center'>";
			tfooter += "<td  style='font-weight:bold' ></td>";
			tfooter += "<td style='font-weight:bold' >" + pmcAlias + "</td>";
			tfooter += "<td style='font-weight:bold' >" + waterAlias + "</td>";
			tfooter += "<td style='font-weight:bold' >" + eletAlias + "</td>";
			if (data.latefees > 0) {
				tfooter += "<td></td>";
			}
			if (data.billsType == 2 || data.billsType == 3) {
				tfooter += "<td colspan='2'></td>";
			}
			tfooter += "<td style='font-weight:bold'>优惠</td>";
			tfooter += "<td >￥" + privilege + "</td>";
			tfooter += "</tr>";
		}
		tfooter += "<tr height='10' align='center'>";
		tfooter += "<td  style='font-weight:bold' >" + ljy + "</td>";
		tfooter += "<td>" + lastPmcBalance + "</td>";
		tfooter += "<td>" + lastWaterBalance + "</td>";
		tfooter += "<td>" + lastElectricBalance + "</td>";
		if (data.latefees > 0) {
			tfooter += "<td></td>";
		}
		if (data.billsType == 2 || data.billsType == 3) {
			tfooter += "<td colspan='2'></td>";
		}
		tfooter += "<td style='font-weight:bold' >本次应收</td>";
		tfooter += "<td >￥" + data.thisreceivable + "</td>";
		tfooter += "</tr>";

		tfooter += "<tr height='10' align='center'>";
		tfooter += "<td  style='font-weight:bold' >" + bjy + "</td>";
		tfooter += "<td>" + pmcBalance + "</td>";
		tfooter += "<td>" + waterBalance + "</td>";
		tfooter += "<td>" + electricBalance + "</td>";
		if (data.latefees > 0) {
			tfooter += "<td></td>";
		}
		if (data.billsType == 2 || data.billsType == 3) {
			tfooter += "<td colspan='2'></td>";
		}
		tfooter += "<td style='font-weight:bold' align='center' >本次实收</td>";
		tfooter += "<td style='font-size:16px;'>￥" + data.takeamount + "</td>";
		tfooter += "</tr>";
	} else {
		tfooter = "<tr height='10'>";
		tfooter += "<td colspan='" + 10 + "' style='font-weight:bold' align='center'><span>合计￥" + data.receivable + "</span><span style='padding: 3px 30px 3px 60px;'>优惠￥" + privilege + "</span><span style='padding: 3px 30px 3px 30px;'>本次应收￥" + data.thisreceivable + "</span><span style='padding: 3px 0px 3px 30px;'>本次实收￥" + data.takeamount + "</span></td>";
		tfooter += "</tr>";
	}
	bg_rows = "<tbody>" + tr_rows + tfooter + "</tbody>";
	ftl = bg_main + bg_header + bg_rows + "</table>";
	me.render = function() {
		var render = template.compile(ftl);
		template.helper('getVal', function(v) {
			if (v) {
				return v;
			} else {
				return "-";
			}
		});
		template.helper('getSize', function(v) {
			if (v.length > 15) {
				return 10;
			} else {
				return 13;
			}
		});
		return render({
			bills : data
		});
	};
}
function feeprint(url, params) {
	LODOP = getLodop();
	top.Dg.z.ajax(url, params, function(data) {
		if (data.length > 0) {
			LODOP.PRINT_INIT(data[0].title + "收款收据" + "_" + 1);
			$(data).each(function(i, v) {
				CreatePage(v, i);
			});
			LODOP.SET_PREVIEW_WINDOW(1, 2, 1, 810, 530, data[0].title + ".打印收据");
			// 预览窗口禁止最小化，并始终最前
			// LODOP.PREVIEW("_blank", 770, 460);
			LODOP.PREVIEW();
		}
	});

}
function feebatchprint(data) {
	LODOP = getLodop();
	if (data.length > 0) {
		LODOP.PRINT_INIT(data[0].title + "收款收据" + "_" + 1);
		$(data).each(function(i, v) {
			CreatePage(v, i);
		});
		LODOP.SET_PREVIEW_WINDOW(1, 2, 1, 810, 530, data[0].title + ".批量打印");
		LODOP.PREVIEW();
	}
}
function feebatchprintWithURL(url, params) {
	LODOP = getLodop();
	top.Dg.z.ajax(url, params, function(data) {
		if (data.length > 0) {
			LODOP.PRINT_INIT(data[0].title + "收款收据" + "_" + 1);
			$(data).each(function(i, v) {
				CreatePage(v, i);
			});
			LODOP.SET_PREVIEW_WINDOW(1, 2, 1, 810, 530, data[0].title + ".批量打印");
			LODOP.PREVIEW();
		}
	});
}
function CreatePage(data, i) {
	LODOP.NewPage();
	LODOP.SET_PRINT_PAGESIZE(1, 2000, 930, "CreateCustomPage");
	LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE", true);//
	initTable(data);
	var pmcbalStatus = data.pmcbalStatus;
	var warterbalStatus = data.warterbalStatus;
	var electricbalStatus = data.electricbalStatus;
	LODOP.ADD_PRINT_TEXT(10, 50, 400, 20, data.title + "收款收据");
	LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
	LODOP.SET_PRINT_STYLEA(0, "Horient", 2);
	LODOP.SET_PRINT_STYLEA(0, "FontSize", data.fontSize);
	LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
	LODOP.SET_PRINT_STYLEA(0, "Bold", 1);

	LODOP.ADD_PRINT_TEXT(45, 40, 600, 20, data.communityName + "   业主:" + data.houseOwnerName + data.houseNo);
	LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

	LODOP.ADD_PRINT_TEXT(15, 560, 200, 20, data.billsNo);
	LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
	LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

	LODOP.ADD_PRINT_TEXT(45, 560, 300, 20, "开票日期:" + data.createDate);
	LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
	if (data.remark) {
		LODOP.ADD_PRINT_TEXT(TABLE_HEIGHT + 8, 50, 300, 20, "备注:" + data.remark);
		LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
		LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
	}

	LODOP.ADD_PRINT_TEXT(TABLE_HEIGHT + 8, 330, 250, 20, "大写:(" + data.amountstr + ")");
	LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

	LODOP.ADD_PRINT_TEXT(TABLE_HEIGHT + 8, 570, 300, 20, "收款人:" + data.gatherUser);
	LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
	LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
	LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

	var printhtml = "";
	pt = new PrintTemp(data);
	printhtml = pt.render();
	LODOP.ADD_PRINT_TABLE(60, 40, 700, TABLE_HEIGHT, printhtml);

}
function initTable(data) {
	var fontlength = parseInt(data.title.length) + 4;
	var fontwidth = 7.381 * fontlength;
	if (top.Dg.z.isTrue(data.pmcbalStatus) || top.Dg.z.isTrue(data.warterbalStatus) || top.Dg.z.isTrue(data.electricbalStatus)) {
		recordSize = data.rows.length + 7;
	} else {
		recordSize = data.rows.length + 4;
	}
	if (recordSize > PRINT_SIZE) {
		var pageSize = ceil(recordSize / PRINT_SIZE);
		BASE_PAGER_HEIGHT = BASE_PAGER_HEIGHT;
	}
	TABLE_HEIGHT = TABLE_TR * (parseInt(recordSize) - parseInt(otherSize)) + TABLE_HEAD;
}