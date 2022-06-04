ArtTemp = function(buildingFloorCharts) {
  var me = this;
  var ftl = '';
  ftl += '{{each buildingFloorCharts as floor}}';
  ftl += '<tr><td width="70px" style="background-color:#ffffff;color: #1853A1;">';
  ftl += '{{floor.buildingFloorName}}(<input class="checkfloor" style="vertical-align:middle;" id="{{floor.buildingFloorNo}}" type="checkbox">)</td>';
  ftl += '{{each floor.buildingUnitCharts as unit}}';
  ftl += '{{each unit.houseCharts as house}}';
  ftl += '<td unitname="{{unit.buildingUnitNo}}" class="HouseList" style="background-color: {{check(house)}};">';
  ftl += '{{if flag(house)}}';
  ftl += '<div floor="floor{{floor.buildingFloorNo}}" unit="unit{{unit.buildingUnitNo}}" class="checkbuttonNo panelcheck"><div class="triangleNo"><div id="{{house.houseId}}" class="checktext">';
  ftl += '{{/if}}';
  ftl += '<a  href="#" id="{{house.houseId}}" title="业主：{{owner(house)}}&#10;电话：{{phone(house)}}" style="color:white;font-size:15px;font-weight:bold"><span>{{house.houseNum}}{{if issold(house)}}{{/if}}{{if isjoin(house)}} <img style="border:0px;vertical-align:middle;margin-top:-2px;" src="../../res/theme/default/images/join.png">{{/if}}</span></a>';
  ftl += '{{if flag(house)}}';
  ftl += '</div></div></div>';
  ftl += '{{/if}}';
  ftl += '</td>{{/each}}{{/each}}<td></td>';
  ftl += '</tr>{{/each}}';
  me.ftl = ftl;
  me.render = function() {
    var render = template.compile(me.ftl);
    template.helper('check', function(house) {
      var joinState = house.joinState;
      var soldState = house.soldState;
      if (house.houseNum == "无") {
        cls = "#D3D3D3";
      }
      if (soldState == 0) {// 未售
        cls = "#7b8587";
      }
      if (soldState == 1) {// 已售
        cls = "#B22222";
      }
      if (soldState == 2 && joinState == 0) {// 已装修
        cls = "#B38D05";
      }
      if (soldState == 2 && joinState == 1) {// 已入住
          cls = "#23b628";
       }
      return cls;
    });
    template.helper('flag', function(house) {
      if (house.houseNum == "无") {
        return false;
      }
      return true;
    });
    template.helper('isjoin', function(house) {
      var joinState = house.joinState;
      if (joinState == 1) {
        return true;
      }
      return false;
    });
    template.helper('issold', function(house) {
      var soldState = house.soldState;
      if (soldState==0) {
        return true;
      }
      return false;
    });
    template.helper('owner', function(house) {
      owner = "暂无";
      if (house.houseOwner != "" && house.houseOwner != null) {
        owner = house.houseOwner;
      }
      return owner;
    });

    template.helper('phone', function(house) {
      ownerPhone = "暂无";
      if (house.ownerPhone != "" && house.ownerPhone != null) {
        ownerPhone = house.ownerPhone;
      }
      return ownerPhone;
    });

    return render({
      buildingFloorCharts : buildingFloorCharts
    });
  };
}