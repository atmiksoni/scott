/**
 * ComboGrid
 */
Cg = function(id) {
	var me = this;
	me.z = top.window.Flyme;
	me.id = id;
	me.namespace = $("#" + me.id).attr("namespace");
	me.idField = "";
	me.textField = "name";
	me.title = "";
	me.disabled = false;
	me.rowKey = "-1";
	me.url = "";
	me.freezeCols = 5;
	me.colM = [];
	me.field = [];
	me.jsonfield = [];
	me.pageSize = 10;
	me.topVisible = true;
	me.onClickRow = function(rowIndex, rowData) {
	};
	me.onBeforeLoad = function(param) {
	};
	me.param = {};
	me.defaultparam = {
	    jsonfields : "",
	    sqlfields : ""
	};
	me.LoadComBoGrid = function() {
		me.initColumnFields();
		me.defaultparam.jsonfields = me.jsonfield.join(",");
		me.defaultparam.sqlfields = me.field.join(",");
		var config = {
		    columns : me.colM,
		    panelWidth : 450,
		    mode : "remote",
		    width : 200,
		    url : me.url,
		    idField : me.idField,
		    fitColumns : true,
		    pagination : true,
		    title : me.title,
		    disabled : me.disabled,
		    queryParams : me.defaultparam,
		    textField : me.textField,
		    onClickRow : function(rowIndex, rowData) {
			    me.onClickRow(rowIndex, rowData);
		    },
		    onBeforeLoad : function(param) {
			   // $.extend(param, me.param);
			    me.onBeforeLoad(param);
		    },
		    onShowPanel : function() {
			    /* $(".panel-body-noheader").css("border-top-width", "1px"); */
			    $(".combo").css("border-bottom-width", "0px");
		    },
		    onHidePanel : function() {
			    $(".combo").css("border-bottom-width", "1px");
		    }
		}
		$("#" + me.id).combogrid(config);
	};
	me.initColumnFields = function() {
		var fields = [];
		var jsonfields = [];
		$(me.colM).each(function(index, colMs) {
			$(colMs).each(function(index, item) {
				if (item.field != "ck" && !item.sumfield) {
					fieldName = item.field;
					if (fieldName.indexOf("_") > -1) {
						fieldName = fieldName.split("_")[1];
					}
					if (!item.sqlfield) {
						fields.push(item.alias + "." + fieldName);
					}
					if (item.Pk) {
						me.idField = item.field;
					}
					jsonfields.push(item.field);
				}
				if (item.title == "主键" || item.title == "PK") {
					me.idField = item.field;
				}
			});
		});
		me.field = fields;
		me.jsonfield = jsonfields;
	};
	me.search = function(param) {
		$.extend(me.defaultparam, param);
		$("#" + me.id).combogrid('grid').datagrid("reload");
	};
	me.formatter = function(row, pojo, field) {
		var rowData = eval("row." + pojo);
		if (rowData) {
			return eval("rowData." + field);
		} else {
			return "-";
		}
	};
}
