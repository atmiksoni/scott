Ec = function(id) {
	var me = this;
	me.id = id;
	me.chart = "";
	me.title = "";
	me.subtext = "";
	me.init = function() {
		myChart = echarts.init(document.getElementById(me.id));
		myChart.showLoading({
			text : '正在努力的读取数据中...',
		});
		me.chart = myChart;
	};
	me.setOption = function(data) {
		var option = {
		    title : {
		        text : me.title,
		        subtext : me.subtext
		    },
		    tooltip : {
			    trigger : 'axis'
		    },
		    legend : {
			    data : data.lData
		    },
		    grid:{height:'60%'},
		    toolbox : {
		        show : true,
		        feature : {
		            mark : {
			            show : false
		            },
		            dataView : {
		                show : true,
		                readOnly : false
		            },
		            magicType : {
		                show : true,
		                type : [ 'line', 'bar' ]
		            },
		            restore : {
			            show : true
		            },
		            saveAsImage : {
			            show : true
		            }
		        }
		    },
		    calculable : false,
		    xAxis : [ {
		        type : 'category',
		        height : 100,
		        axisLabel : {
		            interval : 0,
		            formatter : function(val) {
			            return val.split("").join("\n");
		            }

		        },
		        data : data.xData
		    } ],
		    yAxis : [ {
		    	splitNumber:1000,
			    type : 'value'
		    } ],
		    series : data.series
		};

		me.chart.hideLoading();
		me.chart.clear();
		me.chart.setOption(option);

	};
}