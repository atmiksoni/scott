ArtTemp = function(buildingFloorCharts) {
	var me = this;
	var f = "";
	f = '{{each buildingFloorCharts as floor }}';
	f += '<tr><td width="100px" style="background-color:#ffffff;color: #1853A1;"><a href="#" style="font-size:16px;">{{floor.buildingFloorName}} <input class="checkfloor" style="vertical-align:middle;" id="{{floor.buildingFloorNo}}" type="checkbox"></td>';
	f += '{{each floor.buildingUnitCharts as unit}}';
	f += '{{each unit.houseCharts as house index}}';
	f += '<td unitname="{{unit.buildingUnitNo}}" class="HouseList" style="background-color: {{check(house)}};{{if index%(unit.houseCharts.length-1)==0 && index>0}}border-right-color:#0003FF{{/if}}">';
	f += '{{if flag(house)}}';
	f += '{{if isFeestandard(house)}}';
	f += '<div floor="floor{{floor.buildingFloorNo}}" unit="unit{{unit.buildingUnitNo}}" class="{{checkbutton(house)}}" >';
	f += '<div class="{{triangle(house)}}">';
	f += '<div id="{{house.houseId}}" class="checktext">';
	f += '{{/if}}';
	f += '{{/if}}';
	f += '{{if isfeeReportDetailsState(house)}}';
	f += '<b style="color:#000000;font-size:12px;">￥</b>';
	f += '{{/if}}';
	f += '&nbsp;<a href="#" title="{{house.houseOwner}}" class="housetip" style="color:white;font-size:15px;font-weight:bold">{{house.houseNum}}</a>';
	f += '{{if flag(house)}}';
	f += '{{if isFeestandard(house)}}';
	f += '</div></div></div>';
	f += '{{/if}}';
	f += '{{/if}}';
	f += '</td>';
	f += '{{/each}}';
	f += '{{/each}}';
	f += '<td></td>';
	f += '</tr>';
	f += '{{/each}}';
	me.render = function() {
		var render = template.compile(f);
		template.helper('flag', function(house) {
			if (house.houseNum == "无") {
				return false;
			}
			return true;
		});

		template.helper('check', function(house) {
			cls = "#917430";
			if (house.feeItemState == 1) {
				cls = "#536bac";
			}
			if (house.houseNum == "无") {
				cls = "#7b8587";
			}
			return cls;
		});

		template.helper('isFeestandard', function(house) {
			// 如果未分配收费标准
			if (house.feeItemState == 0) {
				return true;
			}
			// 如果已经分配收费标准 且此收费标准与待分配收费标准不同则不显示div
			if (house.feeItemState == 1 && house.feeStandardState == 1) {
				return true;
			}
			// 如果已经分配收费标准 且此收费标准与待分配收费标准相同则显示div
			if (house.feeItemState == 1 && house.feeStandardState == 0) {
				return false;
			}
		});

		template.helper('isfeeReportDetailsState', function(house) {
			// 如果已生成收费记录
			if (house.feeReportDetailsState == 1) {
				return true;
			}
			// 如果未生成收费记录
			if (house.feeReportDetailsState == 0) {
				return false;
			}
		});

		template.helper('checkbutton', function(house) {
			var check = "checkbuttonNo";
			if (house.feeStandardState == 1) {
				check = "checkbuttonOk";
			}
			return check + " panelcheck";
		});

		template.helper('triangle', function(house) {
			var check = "triangleNo";
			if (house.feeStandardState == 1) {
				check = "triangleOk";
			}
			return check;
		});

		return render({
			buildingFloorCharts : buildingFloorCharts
		});
	};
}
function initHouseChart(url, params) {
	var hasHouseStr;
	$("#unit-thead,#showHouses").html("");
	top.Dg.z.ajax(url, params, function(data) {
		if (data.code ==100) {
			$("#houseTable").css('width', data.width);
			initHtml(data.buildingFloorCharts);
			hasHouseStr = data.houseIds;
		}
	});
	return hasHouseStr;
}
function initHtml(buildingFloorCharts) {
	var feestandardAllot = new ArtTemp(buildingFloorCharts);
	$("#showHouses").html(feestandardAllot.render());
	$('.housetip').tooltip({
	    position : 'left',
	    onShow : function() {
		    $(this).tooltip('tip').css({
		        backgroundColor : '#fff',
		        borderColor : '#666'
		    });
	    }
	});
	// 单个选择
	var has = [];
	$(".panelcheck").on('click', function() {
		if (!$(this).hasClass('checkbuttonOk')) {
			triangleOk(this);
		} else {
			triangleNo(this);
		}
	});
	// 设置宽度
	var width = $(".HouseList").css('width');
	$(".HouseList").css('width', "500px;");

	// 生成单元
	var lists = $('.unit-the-table tr').eq(0);
	$.each(lists, function() {
		var tds = $(this).find('td[unitname]');
		var names = {};
		$.each(tds, function() {
			var unitname = $(this).attr('unitname');
			names[unitname] = names[unitname] ? names[unitname] + 1 : 1;
		});
		var html = '<tr><th width="70" id="all" class="fang_th">全选  <input class="checkfloor"  style="vertical-align:middle;" id="checkAll" type="checkbox"></th>';
		$.each(names, function(i, n) {
			html += '<th colspan="' + n + '" class="fang_th">' + i + '单元 <input class="checkunit"  style="vertical-align:middle;" id=' + i + ' type="checkbox"></th>';
		});
		$('#unit-thead').html(html);
	});

	// 楼层全选
	$(".checkfloor").on('click', function() {
		var id = "floor" + $(this).attr("id");
		if ($(this).is(":checked")) {
			var obj = $("div[floor='" + id + "']");
			obj.each(function() {
				triangleOk(this);
			});
		} else {
			var obj = $("div[floor='" + id + "']");
			obj.each(function() {
				triangleNo(this);
			});
		}

	});

	// 单元全选
	$(".checkunit").on('click', function() {
		var id = "unit" + $(this).attr("id");
		var obj = $("div[unit='" + id + "']");
		if ($(this).is(":checked")) {
			obj.each(function() {
				triangleOk(this);
			});
		} else {
			obj.each(function() {
				triangleNo(this);
			});
		}
	});
	// 全选
	$("#checkAll").on('click', function() {
		var obj = $("div[unit]");
		if ($(this).is(":checked")) {
			obj.each(function() {
				triangleOk(this);
			});
		} else {
			obj.each(function() {
				triangleNo(this);
			});
		}
	});

}
function triangleOk(obj) {
	$(obj).parent().css("background-color", "#536bac");
	$(obj).attr('class', 'checkbuttonOk panelcheck');
	$(obj).find('.triangleNo').attr('class', 'triangleOk');
}
function triangleNo(obj) {
	$(obj).parent().css("background-color", "#917430");
	$(obj).attr('class', 'checkbuttonNo panelcheck');
	$(obj).find('.triangleOk').attr('class', 'triangleNo');
}
/* 获取选中房间ID */
function getCheckedHouseId() {
	var houseIds = [];
	$(".checkbuttonOk").each(function() {
		houseIds.push($(this).find(".checktext").attr("id"));
	});
	return houseIds;
}