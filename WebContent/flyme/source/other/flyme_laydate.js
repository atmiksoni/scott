layDate = function(options) {
	var me = this;
	me.config = {
		format : "YYYY-MM-DD",
		festival : true, // 是否显示节日
		isclear : true,
		fixed : false,
		zIndex : 99999999999999,
		istoday : true
	}
	$.extend(me.config, options);
	laydate(me.config);
	return me.config;
}