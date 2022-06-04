SubDd = function(grid) {
	var me = this;
	me.z = top.window.Flyme;
	me.tag = true;
	me.idField = "id";
	me.title = "";
	me.rowKey = "";
	me.rowIndex = -1;
	me.frozen = false;
	me.rownumbers = true;
	me.sortName = "";
	me.sortOrder = "asc";
	me.collapsible = false;
	me.iconCls = "icon-grid";
	me.url = "data.do";
	me.pagination = false;
	me.fcolM = [ [] ];
	me.colM = [ [] ];
	me.field = [];
	me.grid = "";
	me.jsonfield = [];
	me.groupField = "";
	me.pageSize = 15;
	me.pageList = [ 10, 15, 30, 50, 100 ];
	me.toolbar = '';
	me.singleSelect = true;
	me.rownumbers = true;
	me.showFooter = false;
	me.fitColumns = true;
	me.checkOnSelect = true;
	me.selectOnCheck = true;
	me.onClickRow = function(rowIndex, rowData) {
	};
	me.onDblClickRow = function(rowIndex, rowData) {
	};
	me.onCheck = function(rowIndex, rowData) {
	};
	me.onUncheck = function(rowIndex, rowData) {
	};
	me.onCheckAll = function(rows) {
	};
	me.onUncheckAll = function(rows) {
	};
	me.onClickCell = function(rowIndex, field, value) {
	};
	me.onBeforeEdit = function(rowIndex, rowData) {
	};
	me.onEndEdit = function(rowIndex, rowData, changes) {
	};
	me.onBeginEdit = function(rowIndex, row) {
	};
	me.gotoCell = function(param) {

	};
	me.onAfterEdit = function(index, row, changes) {
	};
	me.onLoadSuccess = function(data) {
	};
	me.onBeforeLoad = function(param) {
	};
	me.groupFormatter = function(value, rows) {
	};
	me.defaultparam = {
	    searchField : "",
	    searchString : "",
	    jsonfields : "",
	    sqlfields : ""
	};
	me.param = {};
	me.LoadDataGrid = function() {
		me.initColumnFields();
		me.defaultparam.jsonfields = me.jsonfield.join(",");
		me.defaultparam.sqlfields = me.field.join(",");
		var config = {
		    title : me.title,
		    frozenColumns : me.fcolM,
		    columns : me.colM,
		    url : me.url,
		    iconCls : me.iconCls,
		    collapsible : me.collapsible,
		    rownumbers : me.rownumbers,
		    showFooter : me.showFooter,
		    sortOrder : me.sortOrder,
		    sortName : me.sortName,
		    selectOnCheck : me.selectOnCheck,
		    singleSelect : me.singleSelect,
		    pageSize : me.pageSize,
		    pageList : me.pageList,
		    striped : true,
		    scrollbarSize : 10,
		    queryParams : me.defaultparam,
		    checkOnSelect : me.checkOnSelect,
		    fitColumns : me.fitColumns == null ? true : me.fitColumns,
		    pagination : me.pagination,
		    onBeforeLoad : function(param) {
			    me.onBeforeLoad(param);
			    $.extend(param, me.param);
			    $(".datagrid-wrap").css("border-bottom", "0px");
		    },
		    onLoadSuccess : function(data) {
			    me.onLoadSuccess(data);
		    },
		    onLoadError : function() {
		    },
		    onClickRow : function(rowIndex, rowData) {
			    me.rowKey = eval("rowData." + me.idField);
			    me.rowIndex = rowIndex;
			    me.onClickRow(rowIndex, rowData);
		    },
		    onDblClickRow : function(rowIndex, rowData) {
			    me.rowKey = eval("rowData." + me.idField);
			    me.rowIndex = rowIndex;
			    me.onDblClickRow(rowIndex, rowData);
		    },
		    onDrop : function(targetRow, sourceRow, point) {
			    me.onDrop(targetRow, sourceRow, point);
		    },
		    onCheck : function(rowIndex, rowData) {
			    me.rowKey = eval("rowData." + me.idField);
			    me.onCheck(rowIndex, rowData);
		    },
		    onUncheck : function(rowIndex, rowData) {
			    me.rowKey = eval("rowData." + me.idField);
			    me.onUncheck(rowIndex, rowData);
		    },
		    onCheckAll : function(rows) {
			    me.onCheckAll(rows);
		    },
		    onUncheckAll : function(rows) {
			    me.onUncheckAll(rows);
		    },
		    onClickCell : function(rowIndex, field, value) {
			    $("input.datagrid-editable-input").select();
			    me.onClickCell(rowIndex, field, value);
		    },
		    onBeforeEdit : function(index, row) {
			    me.onBeforeEdit(index, row);
		    },
		    onAfterEdit : function(index, row, changes) {
			    me.onAfterEdit(index, row, changes);
		    },
		    onEndEdit : function(index, row, changes) {
			    me.onEndEdit(index, row, changes);
		    },
		    onBeginEdit : function(index, row) {
			    me.onBeginEdit(index, row);
		    },
		    gotoCell : function(param) {
			    me.gotoCell(param);
		    }
		}
		if (me.toolbar != '') {
			config.toolbar = me.toolbar;
		}
		grid.datagrid(config);

	};
	me.initColumnFields = function() {
		var fields = [];
		var jsonfields = [];
		$(me.colM).each(function(index, colMs) {
			$(colMs).each(function(index, item) {
				if (item.field != "ck" && !item.sumfield) {
					fieldName = item.field;
					var index = fieldName.lastIndexOf("_");
					if (index > -1) {
						var fieldNames = fieldName.split("_");
						fieldName = fieldNames[fieldNames.length - 1];
					}
					if (item.alias) {
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
		$(me.fcolM[0]).each(function(index, item) {
			if (item.field != "ck" && !item.sumfield) {
				fieldName = item.field;
				var index = fieldName.lastIndexOf("_");
				if (index > -1) {
					var fieldNames = fieldName.split("_");
					fieldName = fieldNames[fieldNames.length - 1];
				}
				if (item.alias) {
					fields.push(item.alias + "." + fieldName);
				}
				jsonfields.push(item.field);
			}
			if (item.title == "主键" || item.title == "PK") {
				me.idField = item.field;
			}
		});
		me.field = fields;
		me.jsonfield = jsonfields;
	};

	return me;
}
