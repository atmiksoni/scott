Dd = function(id) {
	var me = this;
	me.z = top.window.Flyme;
	me.Dg = top.Dg;
	me.tag = true;
	me.namespace = $("#" + id).attr("namespace");
	me.idField = "id";
	me.treeField = "";
	me.title = "";
	me.rowKey = "";
	me.rowData = null;
	me.rowIndex = -1;
	me.frozen = false;
	me.onlyShowInterrupt=true;
	me.selectField = "";
	me.rownumbers = true;
	me.sortName = "";
	me.sortOrder = "asc";
	me.collapsible = false;
	me.iconCls = "icon-grid";
	me.url = "";
	me.detailsview = false;
	me.pagination = true;
	me.fcolM = [ [] ];
	me.colM = [ [] ];
	me.field = [];
	me.grid = "";
	me.jsonfield = [];
	me.groupField = "";
	me.pageSize = 15;
	me.pageList = [ 10, 15, 30, 50, 100 ];
	me.toolbar = [];
	me.singleSelect = true;
	me.showFooter = false;
	me.fitColumns = true;
	me.checkOnSelect = true;
	me.selectOnCheck = true;
	me.delUrl = me.namespace + "_del_sub.do";
	me.addUrl = me.namespace + "_add_turn.do";
	me.editUrl = me.namespace + "_edit_turn.do";
	me.detailsUrl = me.namespace + "_details_turn.do";
	me.delBtn = null;
	me.selectIndex = null;
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
				me.rowData = null;
				if (me.delBtn) {
					$("#" + me.namespace + "_del").linkbutton("disable");
				}
				$('#'+id).datagrid('doCellTip', {
					onlyShowInterrupt : me.onlyShowInterrupt,
					position : 'bottom',
					maxWidth : '200px',
					tipStyler : {
						backgroundColor : '#474747',
						borderColor : '#282828',
						color:'#ffffff',
						boxShadow : '1px 1px 3px #ffffff'
					}
				});
				if(me.selectIndex!=null){
					$('#'+id).datagrid('selectRow',me.selectIndex);
				}
				me.onLoadSuccess(data);
			},
			onLoadError : function() {
			},
			onClickRow : function(rowIndex, rowData) {
				me.rowKey = eval("rowData." + me.idField);
				me.rowIndex = rowIndex;
				me.rowData = rowData;
				me.onClickRow(rowIndex, rowData);
				if (me.delBtn) {
					$("#" + me.namespace + "_del").linkbutton("enable");
				}
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
				me.selectField = field;
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
			},
			onExpandRow : function(index, row) {
				if (me.detailsview) {
					var ddv = $(this).datagrid('getRowDetail', index).find('table.ddv');
					new Dd();
				}

			}

		}
		if (me.toolbar != '') {
			config.toolbar = me.toolbar;
		}
		if (me.delBtn != null) {
			if ($.isFunction(me.delBtn)) {
				me.toolbar.push({
					id : me.namespace + "_del",
					text : "删除",
					iconCls : "icon-remove",
					disabled : true,
					handler : function() {
						me.delBtn();
					}
				});
			} else {
				me.toolbar.push({
					id : me.namespace + "_del",
					text : "删除",
					iconCls : "icon-remove",
					disabled : true,
					handler : function() {
						me.remove();
					}
				});
			}
		}
		if (me.groupField != "") {
			config.groupField = me.groupField;
			config.view = groupview;
			config.groupFormatter = function(value, rows) {
				return me.groupFormatter(value, rows);
			};
		}
		if (me.treeField == "") {
			me.grid = $("#" + id).datagrid(config);
		} else {
			config.idField = me.idField;
			config.treeField = me.treeField;
			me.grid = $("#" + id).treegrid(config);
		}

	};
	var editIndex = undefined;
	me.append = function() {
		$("#" + id).datagrid('appendRow', {});
	};
	me.open2 = function(options) {
		var namespace = me.namespace;
		options.datagrid = me;
		if (options.namespace) {
			namespace = options.namespace;
		}
		if (options.checkurl) {
			checkurl = top.baseUrl + "/" + namespace + "/" + options.checkurl;
			me.Dg.z.ajax(checkurl, options.params, function(data) {
				if (data.code==100) {
					options.url = top.baseUrl + "/" + namespace + "/" + options.url;
					me.Dg.open2(options);
				} else {
				}
			});
		} else {
			options.url = top.baseUrl + "/" + namespace + "/" + options.url;
			me.Dg.open2(options);
		}
	};
	me.open = function(options) {
		var namespace = me.namespace;
		options.datagrid = me;
		if (options.namespace) {
			namespace = options.namespace;
		}
		if (options.checkurl) {
			checkurl = top.baseUrl + "/" + namespace + "/" + options.checkurl;
			me.Dg.z.ajax(checkurl, options.params, function(data) {
				if (data.code==100) {
					options.url = top.baseUrl + "/" + namespace + "/" + options.url;
					me.Dg.open2(options);
				} else {
				}
			});
		} else {
			options.url = top.baseUrl + "/" + namespace + "/" + options.url;
			me.Dg.open(options);
		}
	};
	me.openprint = function(options) {
		var namespace = me.namespace;
		if (options.namespace) {
			namespace = options.namespace;
		}
		if (options.checkurl) {
			checkurl = top.baseUrl + "/" + namespace + "/" + options.checkurl;
			me.Dg.z.ajax(checkurl, options.params, function(data) {
				if (data.code==100) {
					options.url = top.baseUrl + "/" + namespace + "/" + options.url;
					me.Dg.openprint(options);
				} else {
				}
			});
		} else {
			options.url = top.baseUrl + "/" + namespace + "/" + options.url;
			me.Dg.openprint(options);
		}
	};
	me.updata2 = function(options) {
		if (me.checkObj(me.rowKey)) {
			me.open2(options);
		}
	};

	me.dialog = function(options) {
		var namespace = me.namespace;
		if (options.namespace) {
			namespace = options.namespace;
		}
		if (options.checkurl) {
			checkurl = top.baseUrl + "/" + namespace + "/" + options.checkurl;
			me.Dg.z.ajax(checkurl, options.params, function(data) {
				if (data.code==100) {
					options.url = top.baseUrl + "/" + namespace + "/" + options.url;
					me.Dg.open2(options);
				} else {
				}
			});
		} else {
			options.url = top.baseUrl + "/" + namespace + "/" + options.url;
			me.Dg.dialognobtn(options);
		}
	};
	me.confirmajax = function(options) {
		var namespace = me.namespace;
		if (options.namespace) {
			namespace = options.namespace;
		}
		var config = {
			title : "提示信息",
			content : "请设置content",
			url : "",
			ok : function(index) {
				me.Dg.z.loading(true);
				url = top.baseUrl + "/" + namespace + "/" + options.url;
				var i = top.layer.msg('请稍候', {
					icon : 16,
					time : false,
					shade : 0.8
				});
				setTimeout(function() {
					me.Dg.z.ajax(url, options.params, function(data) {
						top.layer.close(i);
						if (data.code==100) {
							me.refresh();
							if (options.success) {
								options.success(index);
							}
						} else {
						}
						top.layer.close(index);
					});
				}, 500);
			}
		}
		$.extend(config, options);
		me.Dg.layerconfirm(config.title, config.content, config.ok);
	};
	/** 删除对象 */
	me.remove = function(options) {
		console.log(options);
		if (me.checkObj(me.rowKey)) {
			options && options.url ? me.delUrl = options.url : me.delUrl = me.delUrl;
			options && options.namespace ? me.namespace = options.namespace : me.namespace = me.namespace;
			options && options.content ? content = options.content : content = "确定要删除所选记录吗?";
			url = top.baseUrl + "/" + me.namespace + "/" + me.delUrl;
			var params = {
				id : me.rowKey
			};
			if (options && options.params) {
				$.extend(params, options.params);
			}
			var ntitle="删除询问?";
			options.title=options.title==''||options.title==null ||options.title=="undefined"?ntitle:options.title
			var config = {
				title : options.title,
				content : content,
				ok : function(index) {
					me.Dg.z.ajax(url, params, function(data) {
						if (data.code==100) {
							me.refresh();
							if (options && options.success) {
								options.success(index);
							}
						}
						top.layer.close(index);
					});
				}
			}
			$.extend(config, options);
			me.Dg.layerconfirm2(config.title,config.btn, config.content, config.ok);
		}
	};
	me.getSelected = function(field) {
		if (!field) {
			fiele = me.idField;
		}
		if (me.rowKey != -1) {
			var rowData = $("#" + id).datagrid("getSelected");
			return eval("rowData." + field);
		} else {
			return null;
		}
	};
	me.getSelectedRow = function() {
		if (me.rowKey != -1) {
			return $("#" + id).datagrid("getSelected");
		} else {
			return null;
		}
	};
	me.search = function(param) {
		$.extend(me.defaultparam, param);
		$("#" + id).datagrid('load', me.defaultparam);
		me.rowKey = "";
		me.rowIndex = "";
	};
	me.formatter = function(row, pojo, field) {
		console.log(row);
		console.log(pojo);
		console.log(field);
		var rowData = eval("row." + pojo);
		if (rowData) {
			return eval("rowData." + field);
		} else {
		}
	};
	me.getSelections = function(field) {
		if (!field) {
			fiele = me.idField;
		}
		var ids = [];
		var rows = $("#" + id).datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		return ids.join(",");
	};
	me.getChecked = function(field) {
		if (!field) {
			field = me.idField;
		}
		var ids = [];
		var rows = $("#" + id).datagrid("getChecked");
		for (var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		return ids.join(",");
	};
	me.getSelectedRows = function() {
		var rows = $("#" + id).datagrid("getSelections");
		return rows;
	};
	me.getRows = function() {
		var rows = $("#" + id).datagrid("getRows");
		return rows;
	};
	me.getSelectionsArray = function(field) {
		if (!field) {
			fiele = me.idField;
		}
		var ids = [];
		var rows = $("#" + id).datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			ids.push(rows[i][field]);
		}
		return ids;
	};
	me.checkObj = function(id) {
		var isOK = true;
		if (id == undefined || id == "") {
			isOK = false;
			me.Dg.msg("您未选中任何一条记录！");
		} else if (id.split(",").length > 1) {
			isOK = false;
			me.Dg.msg("您未选中任何一条记录！");
		}
		return isOK;
	};
	me.isNN = function(id) {
		var isOK = true;
		if (id == undefined || id == "") {
			isOK = false;
			me.Dg.msg("您未选中任何一条记录！");
			// me.z.tip("您未选中任何一条记录！", 4000, 300, "error");
		}
		return isOK;
	};
	me.refresh = function() {
		$("#" + id).datagrid("reload");
		me.rowKey = "";
		me.rowIndex = "";
	};
	me.deleteRow = function() {
		$("#" + id).datagrid("deleteRow", me.rowIndex);
		me.rowKey = "";
		me.rowIndex = "";
	};
	me.refreshIframe = function() {
		me.z.refreshIframe(top.Current_iframeID());
	};
	me.setTitle = function(title) {
		$("#" + id).datagrid("getPanel").panel("setTitle", me.title + "-" + title);
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
		var tabId = top.Current_iframeID().split("_child_");
		return top.getTabById(tabId[0]);
	};
	me.getPager = function() {
		var pager = $("#" + id).datagrid().datagrid('getPager');
		return pager;
	};
	me.showColumn = function(field) {
		$("#" + id).datagrid("showColumn", field);
	}
	me.hideColumn = function(field) {
		$("#" + id).datagrid("hideColumn", field);
	}
	me.addEditor = function(field, type) {
		$("#" + id).datagrid('addEditor', {
			field : field,
			editor : {
				type : type
			}
		});
	}
	me.removeEditor = function(field) {
		$("#" + id).datagrid('removeEditor', field);
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
		$("#" + id).datagrid('enableDnd');
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
						$("input.datagrid-editable-input").select();
						$(e.target).textbox('textbox').focus();
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
						$("input.datagrid-editable-input").select();
						$(e.target).textbox('textbox').focus();
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
	}
});
$.extend($.fn.datagrid.defaults.editors, {
	laydate : {
		init : function(container, options) {
			var config = "{istime: true, format:'" + options.format + "'}";
			var input = $('<input type="text" class="laydate-icon" onclick="laydate(' + config + ')" style="border:none;">').appendTo(container);
			return input;
		},
		destroy : function(target) {
			$(target).remove();
		},
		getValue : function(target) {
			return $(target).val();
		},
		setValue : function(target, value) {
			$(target).val(value);
		},
		resize : function(target, width) {
			$(target)._outerWidth(width);
		}
	},
	text : {
		init : function(container, options) {
			var input = $('<input type="text" class="datagrid-editable-input">').appendTo(container);
			return input;
		},
		destroy : function(target) {
			$(target).remove();
		},
		getValue : function(target) {
			return $(target).val();
		},
		setValue : function(target, value) {
			$(target).val(value);
		},
		resize : function(target, width) {
			$(target)._outerWidth(width);
		}
	}
});
