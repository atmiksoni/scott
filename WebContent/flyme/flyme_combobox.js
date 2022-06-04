Cb = function(id) {
	var me = this;
	me.id = id;
	me.z = top.window.Flyme;
	me.panelHeight = "auto";
	me.width = "130";
	me.height = "22";
	me.url = "";
	me.textField = "name";
	me.valueField = "id";
	me.editable = false;
	me.showDefault = false;
	me.defaultText = "";
	me.queryParams = {};
	me.multiple = false;
	me.required = false;
	me.isFirstLoad = true;
	me.onSelect = function(record) {
	};
	me.onLoadSuccess = function(data) {
	};
	me.LoadCombo = function() {
		var config = {
		    textField : me.textField,
		    valueField : me.valueField,
		    panelHeight : me.panelHeight,
		    required : me.required,
		    editable : me.editable,
		    multiple : me.multiple,
		    queryParams : me.queryParams,
		    width : me.width,
		    height : me.height,
		    onLoadSuccess : function(data) {
			    if (me.isFirstLoad && me.showDefault) {
				    me.isFirstLoad = false;
				    var tmp = [ {
				        name : "----请选择" + me.defaultText + "----",
				        id : ""
				    } ];
				    tmp = tmp.concat(data);
				    me.loadData(tmp);
			    }
			    me.onLoadSuccess(data);
		    },
		    onSelect : function(record) {
			    $("#" + id).val(record.id);
			    me.onSelect(record);
		    },

		    onShowPanel : function() {
			    $("#" + id).next("span").css("border-bottom-width", "0px");
		    },
		    onHidePanel : function() {
			    $("#" + id).next("span").css("border-bottom-width", "1px");
		    }
		}
		if (me.url != "") {
			config.url = me.url;
		}
		$("#" + id).combobox(config);
	};
	me.getValues = function() {
		return $("#" + me.id).combobox("getValues");
	};
	me.getValue = function() {
		return $("#" + me.id).combobox("getValue");
	};
	me.getText = function() {
		return $("#" + me.id).combobox("getText");
	};
	me.remove = function() {
		return $("#" + me.id).combobox("setValue", "").combobox("setText", "");
	};
	me.loadData = function(data) {
		$("#" + me.id).combobox('loadData', data);
	};
	return me;
}