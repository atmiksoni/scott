Dd = function(id) {
	var me = this;
	me.z = top.window.Flyme;
	me.tag = true;
	me.id = id;
	me.namespace = $("#" + me.id).attr("namespace");
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
	me.url = "list_data.do";
	me.addurl = "add_turn.do";
	me.editurl = "edit_turn.do";
	me.delurl = "delete_sub.do";
	me.detailurl = "detail_turn.do";
	me.pagination = true;
	me.fcolM = [ [] ];
	me.colM = [ [] ];
	me.field = [];
	me.grid = "";
	me.jsonfield = [];
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
	me.onAfterEdit = function(index, row, changes) {
	};
	me.onLoadSuccess = function(data) {
	};
	me.onBeforeLoad = function(param) {
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
			}

		}
		if (me.toolbar != '') {
			config.toolbar = me.toolbar;
		}
		me.grid = $("#" + me.id).datagrid(config);

	};
	me.add = function(params) {
		top.Dg.namespace = me.namespace;
		top.Dg.url = me.addurl;
		top.Dg.title = me.title + "添加";
		top.Dg.openDialog(params);
	};
	me.open = function(options) {
		var namespace = me.namespace;
		if (options.namespace) {
			namespace = options.namespace;
		}
		if (options.checkurl) {
			checkurl = top.baseUrl + "/" + namespace + "/" + options.checkurl;
			top.Dg.z.ajax(checkurl, options.params, function(data) {
				if (data.code ==100) {
					top.Dg.open(options);
				}
			});
		} else {
			options.url = top.baseUrl + "/" + namespace + "/" + options.url;
			top.Dg.open(options);
		}
	};

	me.opentimeline = function(options) {
		if (!options.namespace) {
			options.namespace = me.namespace;
		}
		if (options.checkurl) {

			top.Dg.z.ajax(options.namespace + "/" + options.checkurl, myparams, function(data) {
				if (data.code ==100) {
					top.Dg.open(options);
				}
			});
		} else {
			top.Dg.opentimeline(options);
		}
	};

	me.edit = function(params) {
		top.Dg.namespace = me.namespace;
		top.Dg.title = me.title + "修改";
		if (me.checkObj(me.rowKey)) {
			top.Dg.url = me.editurl;
			var temp = {
				id : me.rowKey
			};
			if (params) {
				$.extend(temp, params);
			}
			top.Dg.openDialog(temp);
		}
	};
	me.update = function(options) {
		options.namespace = me.namespace;
		if (me.checkObj(me.rowKey)) {
			var params = options.params;
			var temp = {
				id : me.rowKey
			};
			if (params) {
				$.extend(temp, params);
			}
			options.params = temp;
			top.Dg.open(options);
		}
	};
	me.del = function(params) {
		top.Dg.namespace = me.namespace;
		if (me.checkObj(me.rowKey)) {
			top.Dg.url = me.delurl;
			var temp = {
				id : me.rowKey
			};
			if (params) {
				$.extend(temp, params);
			}
			top.Dg.deleteRow(me.id, temp);
		}
	};
	me.remove = function(options) {
		if (me.checkObj(me.rowKey)) {
			var params = options.params;
			var temp = {
				id : me.rowKey
			};
			if (params) {
				$.extend(temp, params);
			}
			options.params = temp;
			options.namespace = me.namespace;
			top.Dg.remove(options);
		}
	};
	me.confirm = function(options) {
		alert(1);
		if (me.checkObj(me.rowKey)) {
			var params = options.params;
			var temp = {
				id : me.rowKey
			};
			if (params) {
				$.extend(temp, params);
			}
			options.params = temp;
			options.namespace = me.namespace;
			top.Dg.confirm(options);
		}
	};
	/* 批量删除 */
	me.batchdel = function(options) {
		var ids = options.params.ids;
		if (me.isNN(ids)) {
			options.namespace = me.namespace;
			top.Dg.remove(options);
		}
	};
	me.detail = function(params) {
		top.Dg.title = me.title + "详细信息";
		if (me.checkObj(me.rowKey)) {
			var temp = {
				id : me.rowKey
			};
			if (params) {
				$.extend(temp, params);
			}
			top.Dg.url = me.detailurl;
			top.Dg.openDialog(temp);
		}
	};
	me.winResize = function(element, height) {
		resizeU();
		$(window).resize(resizeU);
		function resizeU() {
			me.GridResize(element, height);
		}
	};
	me.GridResize = function(id, lose_height) {
		$("#" + id).datagrid('resize', {
			width : "100%",
			height : $(window).height() - lose_height
		});
	};
	me.getSelected = function(field) {
		if (me.rowKey != -1) {
			var rowData = $("#" + me.id).datagrid("getSelected");
			return eval("rowData." + field);
		} else {
			return null;
		}
	};
	me.search = function(param) {
		$.extend(me.defaultparam, param);
		$("#" + me.id).datagrid('load', me.defaultparam);
		me.rowKey = "";
		me.rowIndex = "";
	};
	me.formatter = function(row, pojo, field) {
		var rowData = eval("row." + pojo);
		if (rowData) {
			return eval("rowData." + field);
		} else {
			return "-";
		}
	};
	me.getSelections = function(field) {
		var ids = [];
		var rows = $("#" + me.id).datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		return ids.join(",");
	};
	me.getChecked = function(field) {
		var ids = [];
		var rows = $("#" + me.id).datagrid("getChecked");
		for (var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		return ids.join(",");
	};
	me.getSelectedRows = function() {
		var rows = $("#" + me.id).datagrid("getSelections");
		return rows;
	};
	me.getRows = function() {
		var rows = $("#" + me.id).datagrid("getRows");
		return rows;
	};
	me.getSelectionsArray = function(field) {
		var ids = [];
		var rows = $("#" + me.id).datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		return ids;
	};
	me.checkObj = function(id) {
		var isOK = true;
		if (id == undefined || id == "") {
			isOK = false;
			me.z.tip("您未选中任何一条记录！", 4000, 300, "error");
		} else if (id.split(",").length > 1) {
			isOK = false;
			me.z.tip("只能选择一条记录！", 4000, 300, "error");
		}
		return isOK;
	};
	me.isNN = function(id) {
		var isOK = true;
		if (id == undefined || id == "") {
			isOK = false;
			me.z.tip("您未选中任何一条记录！", 4000, 300, "error");
		}
		return isOK;
	};
	me.refresh = function() {
		$("#" + me.id).datagrid("reload");
		me.rowKey = "";
		me.rowIndex = "";
	};
	me.deleteRow = function() {
		$("#" + me.id).datagrid("deleteRow", me.rowIndex);
		me.rowKey = "";
		me.rowIndex = "";
	};
	me.refreshIframe = function() {
		me.z.refreshIframe(top.Current_iframeID());
	};
	me.setTitle = function(title) {
		$("#" + me.id).datagrid("getPanel").panel("setTitle", me.title + "-" + title);
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
	me.close = function(id) {
		top.closeTab(top.Current_iframeID());
	};
	me.getParentTab = function() {
		var tabId = me.iframeId.split("_child_");
		return top.getTabById(tabId[0]);
	};
	me.getPager = function() {
		var pager = $("#" + me.id).datagrid().datagrid('getPager');
		return pager;
	};
	me.showColumn = function(field) {
		$("#" + me.id).datagrid("showColumn", field);
	}
	me.hideColumn = function(field) {
		$("#" + me.id).datagrid("hideColumn", field);
	}
	me.addEditor = function(field, type) {
		$("#" + me.id).datagrid('addEditor', {
			field : field,
			editor : {
				type : type
			}
		});
	}
	me.removeEditor = function(field) {
		$("#" + me.id).datagrid('removeEditor', field);
	}
	me.formatter = function(row, pojo, field) {
		var rowData = eval("row." + pojo);
		if (rowData) {
			return eval("rowData." + field);
		} else {
			return "-";
		}
	};
	me.enableDnd = function() {
		$("#" + me.id).datagrid('enableDnd');
	}

	return me;
}
$.extend($.fn.datagrid.methods, {
	keyCtr : function(jq, field) {
		return jq.each(function() {
			var grid = $(this);
			grid.datagrid('getPanel').panel('panel').attr('tabindex', 1).bind('keydown', function(e) {
				switch (e.keyCode) {
				case 38: // up
					var selected = grid.datagrid('getSelected');
					if (selected) {
						var index = grid.datagrid('getRowIndex', selected);
						grid.datagrid('selectRow', index - 1).datagrid('editCell', {
							index : index - 1,
							field : field
						});
						grid.datagrid('endEdit', index - 1);
						// $("input.datagrid-editable-input").select();
						// $(e.target).textbox('textbox').focus();
					} else {
						var rows = grid.datagrid('getRows');
						grid.datagrid('selectRow', rows.length - 1);
					}
					break;
				case 40: // down
					var selected = grid.datagrid('getSelected');
					if (selected) {
						var index = grid.datagrid('getRowIndex', selected);
						grid.datagrid('selectRow', index + 1).datagrid('editCell', {
							index : index + 1,
							field : field
						});
						grid.datagrid('endEdit', index);
						// $("input.datagrid-editable-input").select();
						// $(e.target).textbox('textbox').focus();
					} else {
						grid.datagrid('selectRow', 0);
					}
					break;
				case 13: // 回车
					var selected = grid.datagrid('getSelected');
					if (selected) {
						var index = grid.datagrid('getRowIndex', selected);
						grid.datagrid('selectRow', index + 1).datagrid('editCell', {
							index : index + 1,
							field : field
						});
						grid.datagrid('endEdit', index);
						$("input.datagrid-editable-input").select();
					} else {
						grid.datagrid('selectRow', 0);
					}
					break;
				}
			});
		});
	},

	addEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item.field);
				e.editor = item.editor;
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param.field);
			e.editor = param.editor;
		}
	},
	removeEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item);
				e.editor = {};
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param);
			e.editor = {};
		}
	},

	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			var ed = $(this).datagrid('getEditor', param);
			if (ed) {
				if ($(ed.target).hasClass('textbox-f')) {
					$(ed.target).textbox('textbox').select();
					$(ed.target).textbox('textbox').focus();
				} else {
					$(ed.target).focus();
					$(ed.target).select();
				}
			}
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	},
	enableCellEditing : function(jq) {
		return jq.each(function() {
			var dg = $(this);
			var opts = dg.datagrid('options');
			opts.oldOnClickCell = opts.onClickCell;

			opts.onClickCell = function(index, field) {

				if (opts.editIndex != undefined) {
					if (dg.datagrid('validateRow', opts.editIndex)) {
						dg.datagrid('endEdit', opts.editIndex);
						opts.editIndex = undefined;
					} else {
						return;
					}
				}
				dg.datagrid('selectRow', index).datagrid('editCell', {
					index : index,
					field : field
				});
				opts.editIndex = index;
				opts.oldOnClickCell.call(this, index, field);
			}
		});
	}
});
// 加法函数
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}
// 给Number类型增加一个add方法，，使用时直接用 .add 即可完成计算。
Number.prototype.add = function(arg) {
	return accAdd(arg, this);
};

// 减法函数
function Subtr(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	// last modify by deeka
	// 动态控制精度长度
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 给Number类型增加一个sub方法，，使用时直接用 .sub 即可完成计算。
Number.prototype.sub = function(arg) {
	return Subtr(this, arg);
};

// 乘法函数
function accMul(arg1, arg2) {
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch (e) {
	}
	try {
		m += s2.split(".")[1].length;
	} catch (e) {
	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}
// 给Number类型增加一个mul方法，使用时直接用 .mul 即可完成计算。
Number.prototype.mul = function(arg) {
	return accMul(arg, this);
};

// 除法函数
function accDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch (e) {
	}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch (e) {
	}
	with (Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}
// 给Number类型增加一个div方法，，使用时直接用 .div 即可完成计算。
Number.prototype.div = function(arg) {
	return accDiv(this, arg);
};
