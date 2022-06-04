ArtTemp = function(buildingFloorCharts) {
	var me = this;
	var f = "";
	f += '{{each buildingFloorCharts as floor}}';
	f += '<tr>';
	f += '<td width="50px" style="background-color:#ffffff;color: #8B0000;">{{floor.buildingFloorName}}</td>';
	f += '{{each floor.buildingUnitCharts as unit}}';
	f += '{{each unit.houseCharts as house}}';
	f += '<td unitname="{{unit.buildingUnitNo}}" class="HouseList" style="background-color: {{check(house)}}">';
	f += '<a  href="#" check="{{flag(house)}}"   class="house" id="{{flag(house)}}" style="color:white;font-size:15px;font-weight:bold">{{house.houseNum}}</a>';
	f += '</td>';
	f +='{{/each}}{{/each}}';
	f +='<td></td>';
	f += '</tr>';
	f+=	'{{/each}}';
	me.render = function() {
		var render = template.compile(f);
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
			id = "";
			if (house.feeState == 1) {
				id = house.houseId;
			}
			return id;
		});

		return render({
			buildingFloorCharts : buildingFloorCharts
		});
	};
}
function initHtml(data) {
	 $('#unit-thead,#showHouses').html("");
	buildingFloorCharts = data.buildingFloorCharts;
	houseNum = data.houseNum;
	houseFeeNum = data.houseFeeNum;
	var feestandardAllot = new ArtTemp(buildingFloorCharts);
	$("#showHouses").html(feestandardAllot.render());
	$("#showHouseCount").panel("setTitle", "房间总数：" + houseNum + " &nbsp;欠费户数：<span style='color:red'>" + houseFeeNum + "</span>");
	// 生成单元信息
	var lists = $('.unit-the-table tr').eq(0);
	$.each(lists, function() {
		var tds = $(this).find('td[unitname]');
		var names = {};
		$.each(tds, function() {
			var unitname = $(this).attr('unitname');
			names[unitname] = names[unitname] ? names[unitname] + 1 : 1;
		});
		var html = '<tr><th width="40" class="fang_th">单元</th>';
		$.each(names, function(i, n) {
			html += '<th width="40" colspan="' + n + '" class="fang_th">' + i + '单元</th>';
		});
		$('#unit-thead').html(html);
	});
	$(".house").bind("mouseover", function() {
		var houseId = $(this).attr("id");
		if (houseId) {
			showtip(houseId);
		}
	});
}
function showtip(houseId) {
	Tipped.create('#' + houseId, "houseowning_fee_data.do", {
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