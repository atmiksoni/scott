ArtTemp = function(buildingFloorCharts) {
	var me = this;
	var temp = "";
	temp += '{{each buildingFloorCharts as floor}}<tr>';
	temp += '<td width="50px" style="background-color:#ffffff;color: #8B0000;">{{floor.buildingFloorName}}</td>';
	temp += '{{each floor.buildingUnitCharts as unit}}';
	temp += '{{each unit.houseCharts as house}}';
	temp += '<td unitname="{{unit.buildingUnitNo}}" class="HouseList" style="background-color: {{check(house)}}">';
	temp += '<a  href="#" class="housetip" title="{{house.houseOwner}}" {{if flag(house)}} id="{{house.houseId}}" onclick="fee_search(\'{{house.houseId}}\',\'{{house.houseNum}}\');" {{/if}} id="{{flag(house)}}" style="color:white;font-size:15px;font-weight:bold">{{house.houseNum}}</a>';
	temp += '{{if hascheckbox(house)}} <input class="radio" value="{{house.houseId}}" checked="checked" type="checkbox" name="housechart">{{/if}}';
	temp += '</td>';
	temp += '{{/each}}';
	temp += '{{/each}}';
	temp += '</tr>';
	temp += '{{/each}}';
	me.ftl = temp;
	me.render = function() {
		var render = template.compile(me.ftl);
		template.helper('check', function(house) {
			cls = "#917430";
			if (house.feeState == 1) {
				cls = "#8B0000";
			}
			if (house.houseNum == "无") {
				cls = "#7b8587";
			}
			return cls;
		});

		template.helper('flag', function(house) {
			var tag = true;
			if (house.houseNum == "无") {
				var tag = false;
			}
			return tag;
		});
		template.helper('hascheckbox', function(house) {
			var tag = false;
			if (house.feeState == 1) {
				var tag = true;
			}
			return tag;
		});

		return render({
			buildingFloorCharts : buildingFloorCharts
		});
	};
}
function initHtml(data) {
	 $('#unit-thead,#showHouses').html("");
	buildingFloorCharts = data.buildingFloorCharts;
	var feestandardAllot = new ArtTemp(buildingFloorCharts);
	$("#showHouses").html(feestandardAllot.render());
	// 生成单元信息
	var lists = $('.unit-the-table tr').eq(0);
	$.each(lists, function() {
		var tds = $(this).find('td[unitname]');
		var names = {};
		$.each(tds, function() {
			var unitname = $(this).attr('unitname');
			names[unitname] = names[unitname] ? names[unitname] + 1 : 1;
		});
		var html = '<tr><th width="70" id="all" class="fang_th">全选  <input class="checkfloor"  style="vertical-align:middle;" checked="checked" id="checkAll" type="checkbox"></th>';
		$.each(names, function(i, n) {
			html += '<th colspan="' + n + '" class="fang_th">' + i + '单元 <input class="checkunit"  style="vertical-align:middle;" id=' + i + ' type="checkbox"></th>';
		});
		$('#unit-thead').html(html);
	});
	
	
	// 全选
	$("#checkAll").on('click', function() {
		var obj = $("div[unit]");
		if ($(this).is(":checked")) {
			$('[name=housechart]:checkbox').prop("checked", true);
		} else {
			$('[name=housechart]:checkbox').prop('checked', false);
		}
	});

	$(".housetip").bind("mouseover", function() {
		var houseId = $(this).attr("id");
		if (houseId) {
			showtip(houseId);
		}
	});
}
function showtip(houseId) {
	Tipped.create('#' + houseId, "/admin/owningcount/houseowning_fee_data.do", {
	    ajax : {
	        data : {
	            houseId : houseId,
	            beginDate : myparams.beginDate,
	            endDate : myparams.endDate,
	            feeItemIds : myparams.feeItemIds
	        },
	        type : 'post'
	    },
	    skin : 'light',
	    size : 'large',
	    radius : true,
	    title : 'sfsfsf',
	    hook : 'topleft',
	    onHide : function(content, element) {
		    Tipped.clearAjaxCache();
		    Tipped.remove('#' + houseId);
	    }
	});
}

/**
 * 获取选择复选框属性值 name:复选框name
 */
function getCheckBoxVal(name, attrName) {
	var c_v = [];
	$('input[name=' + name + ']:checked').each(function() {
		c_v.push($(this).attr(attrName));
	});
	return c_v.join(",");
}
function fee_search(houseId, houseNum) {
	$("#housefee").tabs("select", 0);
	search_callback(houseId);
}