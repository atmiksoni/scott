ArtTemp = function(carPortAreaCharts) {
	var me = this;
	me.ftl = '{{each carPortAreaCharts as carPortArea ii}}' 
	  + '<tr>' 
	  + '<td rowspan="{{ceil(carPortArea.carPortCharts.length)}}" width="50px" style="background-color:#1874CD;color: #FFFFFF;">{{carPortArea.carPortAreaNo}}</td>' 
	  + '{{each carPortArea.carPortCharts as carPort index}}' 
	  + '{{if index % 10==0 && index>10}}<tr>{{/if}}'
	  + '<td style="background-color: {{check(carPort)}}">' 
	  + ' {{if warning(carPort)}}<img src="../../res/theme/default/images/m.gif">{{/if}} <a href="#" id="{{carPort.carPortId}}" status="{{getStatus(carPort)}}" style="color:white;font-size:15px;font-weight:bold">{{carPort.carPortNo}}{{sell(carPort)}}</a>' 
	  + '</td>' 
	  + '{{if index % 10==9 }}</tr>{{/if}}' 
	  + '{{/each}}' 
	  + '</tr>' 
	  + '{{/each}}';
	me.render = function() {
		var render = template.compile(me.ftl);
		template.helper('check', function(carPort) {
			var status = carPort.status;
			var carPortType = 2;
			var dayNum = carPort.dayNum;
			var begindayNum = carPort.begindayNum;
			if (!top.Dg.z.isNN(dayNum)) {
				dayNum = 0;
			}
			if (status == 0) {
				cls = "#1E90FF";
			}
			if(begindayNum>0){
				cls = "#43CD80";
			}
			if (status == 1) {
				if (dayNum > 5) {
					cls = "#23b628";
				}
				if (dayNum == 0) {
					cls = "#060606";
				}
				if (dayNum <= 5 && dayNum > 0) {
					cls = "#8B0000";
				}
			}
			if (status == 10) {
				cls = "#917430";
			}

			return cls;
		});
		template.helper('ceil', function(size) {
			return Math.ceil(size / 10);
		});

		template.helper('warning', function(carPort) {
			var result = false;
			var dayNum = carPort.dayNum;
			if (dayNum <= 5 && dayNum > 0) {
				result = true;
			}
			return result;
		});

		template.helper('getStatus', function(carPort) {
			var status = carPort.status;
			var dayNum = carPort.dayNum;
			if (status == 1) {
				if (dayNum <= 5 && dayNum > 0) {

				}
			}
			return status;
		});

		template.helper('sell', function(carPort) {
			var carPortType = 1;
			if (carPortType == 2) {
				return '(已售)';
			}
			return '';
		});

		return render({
			carPortAreaCharts : carPortAreaCharts
		});
	};
}