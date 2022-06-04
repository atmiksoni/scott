ArtTemp = function(buildingFloorCharts) {
	var me = this;
	me.ftl = '{{each buildingFloorCharts as floor}}<tr><td width="50px" style="background-color:#ffffff;color: #1853A1;">{{floor.buildingFloorName}}</td>'
		+'{{each floor.buildingUnitCharts as unit}}'
		+'{{each unit.houseCharts as house}}'
		+'<td unitname="{{unit.buildingUnitNo}}" class="HouseList" style="background-color: {{check(house)}}">'
		+'{{if isFeestandard(house)}}<div class="{{checkbutton(house)}} panelcheck"><div class="{{triangle(house)}}"><div id="{{house.houseId}}" class="checktext">{{/if}}<a href="#" style="color:white;font-size:15px;font-weight:bold">{{house.houseNum}}</a>{{if isFeestandard(house)}}</div></div></div>{{/if}}'
		+'</td>{{/each}}{{/each}}<td></td>'
		+'</tr>{{/each}}';
	me.render = function() {
		var render = template.compile(me.ftl);
		template.helper('check', function(house) {
			cls = "#917430";
			if (house.feeItemState == 1) {
				cls = "#000EFF";
			}
			return cls;
		});
		
		template.helper('isFeestandard', function(house) {
			//如果未分配收费标准
			if (house.feeItemState==0){
				return true;
			}
			//如果已经分配收费标准 且此收费标准与待分配收费标准不同则不显示div
			if (house.feeItemState==1 && house.feeStandardState == 1) {
				return true;
			}
			//如果已经分配收费标准 且此收费标准与待分配收费标准相同则显示div
			if (house.feeItemState==1 && house.feeStandardState == 0) {
				return false;
			}
		});
		
		template.helper('checkbutton', function(house) {
			var check = "checkbuttonNo";
			if (house.feeStandardState==1){
				check = "checkbuttonOk";
			}
			return check;
		});
		
		template.helper('triangle', function(house) {
			var check = "triangleNo";
			if (house.feeStandardState==1){
				check = "triangleOk";
			}
			return check;
		});
		
		return render({
			buildingFloorCharts : buildingFloorCharts
		});
	};
}