ArtTemp = function(buildingFloorCharts) {
	var me = this;
	me.ftl = '{{each buildingFloorCharts as floor}}<tr><td width="50px" style="background-color:#ffffff;color: #1853A1;">{{floor.buildingFloorName}}</td>'
		+'{{each floor.buildingUnitCharts as unit}}'
		+'{{each unit.houseCharts as house}}'
		+'<td unitname="{{unit.buildingUnitNo}}" class="HouseList" style="background-color: {{check(house)}}">'
		+'<a  href="#" id="{{flag(house)}}" {{if flag(house)}} onclick="collect(\'{{house.houseId}}\');" {{/if}} style="color:white;font-size:15px;font-weight:bold">{{house.houseNum}}</a>'
		+'</td>{{/each}}{{/each}}<td></td>'
		+'</tr>{{/each}}';
	me.render = function() {
		var render = template.compile(me.ftl);
		template.helper('check', function(house) {
			cls = "#917430";
			if (house.suggestionState == 1) {
				cls = "#b51d1a";
			}
			if(house.houseNum == "无"){
				cls = "#7b8587";
			}
			return cls;
		});
		
		template.helper('flag', function(house) {
			id = "";
			if (house.houseNum != '无') {
				id = house.houseId;
			}
			return id;
		});
		
		return render({
			buildingFloorCharts : buildingFloorCharts
		});
	};
}