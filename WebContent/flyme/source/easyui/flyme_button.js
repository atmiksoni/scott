/**
 * easyui按钮封装
 * 
 * @varsion:1.0
 */
Btn = function(id) {
	var me = this;
	me.id = id;
	me.iconCls = "";
	me.menu = "";
	me.onClick = function() {
	};
	me.init = function() {
		var config = {
		    plain : true,
		    iconCls : me.iconCls,
		    onClick : function() {
			    me.onClick();
		    }
		}
		if (me.menu != "") {
			config.menu = "#" + me.menu;
			$("#" + me.id).splitbutton(config);
		} else {
			$("#" + me.id).linkbutton(config);
		}
	};
	return me;
};