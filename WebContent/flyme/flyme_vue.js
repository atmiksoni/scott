MyVue = function(options) {
	var me = this;
	var page = 1;
	var url = options.url;
	var id = options.id;
	var infiniteScroll = options.infiniteScroll == true ? options.infiniteScroll : false;
	var pageSize = options.pageSize ? options.pageSize : 5;
	var pageCount = 0;
	me.getData = function(params) {
		var result;
		var data = {
			page : page,
			rows : pageSize
		};
		
		if (options.params) {
			$.extend(data, options.params);
		}
		if (params) {
			$.extend(data, params);
		}
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			cache : false,
			async : false,
			success : function(data) {
				result = data;
				pageCount += data.pageCount;
			}
		});
		return result;
	}
	var config = {
		el : '#' + id,
		mounted : function() {
			if (infiniteScroll == false) {
				var data = me.getData();
				this.items = data.rows;
				this.params = data.params;
			}
			if (options.mymounted) {
				options.mymounted && options.mymounted(this);
			}
		},
		data : {
			items : [],
			params : {}
		}
	};
	if (options.mydata) {
		$.extend(config.data, options.mydata);
	}
	options.methods.refresh = function() {
		var data = me.getData({
			rows : pageSize,
			page : 1
		});
		vue.items = data.rows;
		vue.params = data.params;
	}
	options.methods.reload = function(params) {
		params.page = 1;
		var data = me.getData(params);
		vue.items = data.rows;
		vue.params = data.params;
	}
	$.extend(config, options);
	var vue = new Vue(config);
	if (infiniteScroll == true) {
		$('#' + id).infiniteScroll({
			pageSize : 5,
			binder : '#' + id,
			initLoad : true,
			doneTxt : '----------我是有底线的---------',
			loadingHtml : '加载中...',
			loadListFn : function() {
				var def = $.Deferred();
				var data = me.getData();
				var pageCount = data.pageCount;
				if (pageCount > 0) {
					vue.items = vue.items.concat(data.rows);
					vue.params = data.params;
					def.resolve(data.rows);
					++page;
				} else {
					def.resolve([ {} ]);
				}
				return def.promise();
			}
		});
	}
	vue.getData = me.getData;
	
	return vue;
}