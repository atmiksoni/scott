$.extend($.fn.tree.defaults, {
	loadFilter : function(treedata, parent) {
		var data = (treedata.data) ? treedata.data : treedata;
		var checkeds = treedata.checkeds;
		var state = treedata.state;
		init(checkeds, this);
		var idFiled = "id", textFiled = "name", parentField = "parentId";
		if (parentField) {
			var treeData = [], tmpMap = [];
			$(data).each(function(i, value) {
				tmpMap[data[i][idFiled]] = value;
			});
			$(data).each(function(i, value) {
				if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children']) {
						tmpMap[data[i][parentField]]['children'] = [];
					}
					data[i]['text'] = data[i][textFiled];
					tmpMap[data[i][parentField]]['children'].push(value);
				} else {
					data[i]['text'] = data[i][textFiled];
					data[i]['state'] = state;
					treeData.push(data[i]);
				}
			});
			return treeData;
		}
		return data;
	}
});
function init(checkeds, tree) {
	if (checkeds) {
		var opt = $(tree).data().tree.options;
		opt.checkeds = checkeds;
	}
};
var Tree = function() {
	var me = this;
	me.treeId = "";
	me.url = "list_treedata.do";
	me.queryParams = {

	};
	me.dosearch = true;
	me.onlyLeafCheck = false;
	me.checkbox = false;
	me.rowKey = "";
	me.scroll = true;
	me.removeMenu="";
	me.menu = "";
	me.menuShow="";
	me.animate = true;
	me.expand = false;
	me.search = function() {
	};
	me.onBeforeLoad = function(node, param) {
	};
	me.onClick = function(node) {
	};
	me.onCheck = function(node) {
		if (me.dosearch == true) {
			me.search();
		}
	};
	me.formatter = function(node) {
		return node.name;
	};
	me.onLoadSuccess = function(node, data) {
	};
	me.initTree = function(id) {
		me.treeId = id;
		me.queryParams.pojoName = "";
		me.queryParams.name = "";
		me.queryParams.code = "";
		me.queryParams.attrs = "";
		me.queryParams.parentId = "";
		$("#" + me.treeId).tree({
			url : me.url,
			animate : me.animate,
			queryParams : me.queryParams,
			checkbox : me.checkbox,
			onlyLeafCheck : me.onlyLeafCheck,
			onCheck : me.onCheck,
			lines : true,
			formatter : me.formatter,
			onContextMenu : function(e, node) {
				e.preventDefault();
				$(this).tree('select', node.target);
				$('#' +node.pojoName).menu('show', {
						left : e.pageX,
						top : e.pageY
				});
				
			},
			onBeforeLoad : function(node, param) {
				me.onBeforeLoad(node, param);
				$.extend(param, me.queryParams);
			},
			onBeforeExpand : function(node) {
				var parentNode = $("#" + me.treeId).tree('getParent', node.target);
				var parentId = "";
				if (parentNode != null) {
					parentId = parentNode.id;
				}
				var params = {
					name : node.name,
					pojoName : node.pojoName,
					code : node.code,
					attrs : node.attrs,
					parentId : parentId
				}
				/** 点击节点向后台传递参数 */
				$.extend(me.queryParams, params);
			},
			onClick : function(node) {
				me.onClick(node);
				if (me.checkbox) {
					if (!node.checked) {
						me.checkNode(node);
					} else {
						me.unCheckNode(node);
					}
				}
			},
			onSelect : function(node) {
				me.rowKey = node.id;
				$(this).tree('expand', node.target);
			},
			onLoadSuccess : function(node, data) {
				me.rowKey = "";
				me.onLoadSuccess(node, data);
				if (me.expand == true) {
					me.expandAll();
				}
				if (me.scroll) {
					niceScroll(".ztree");
				}
			}
		});
	};
	me.getSelected = function() {
		return $("#" + me.treeId).tree("getChecked");
	};
	me.getNode = function() {
		return $("#" + me.treeId).tree("getSelected");
	};
	me.expandAll = function() {
		me.dosearch = false;
		$("#" + me.treeId).tree('expandAll');
		me.dosearch = true;
	};
	me.reload = function() {
		$("#" + me.treeId).tree('reload', $("#" + me.treeId).tree('getRoot').target);
	};
	me.remove = function(options) {
		top.Dg.confirm(options.msg, function(layer, index) {
			Fm.ajax(options.url, options.params, function(data) {
				if (data.code ==100) {
					var node = $("#" + me.treeId).tree('getSelected');
					$("#" + me.treeId).tree('remove', node.target);
					top.Dg.z.msg(data);
					layer.close(index);
                    //options.doCallback && options.doCallback();
				}
			});
		});
	};
	me.getCheckedIds = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked', 'indeterminate' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			s.push(nodes[i].id);
		}
		return s;
	};
	me.getCodes = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			s.push(nodes[i].code);
		}
		return s;
	};
	me.getChildChecked = function(property) {
		if (Fm.isEM(property)) {
			property = "id";
		}
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked', 'indeterminate' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			if ($('#' + me.treeId).tree('isLeaf', nodes[i].target)) {
				s.push(nodes[i][property]);
			}
		}
		return s;
	};
	me.getCheckedNames = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked', 'indeterminate' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			s.push(nodes[i].name);
		}
		return s;
	};
	me.getChildCheckedNames = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked', 'indeterminate' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			if ($('#' + me.treeId).tree('isLeaf', nodes[i].target)) {
				s.push(nodes[i].name);
			}
		}
		return s;
	};
	me.getCheckedId = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			s.push(nodes[i].id);
		}
		return s;
	};
	me.getChildCheckedId = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked', 'indeterminate' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			if ($('#' + me.treeId).tree('isLeaf', nodes[i].target)) {
				s.push(nodes[i].id);
			}
		}
		return s;
	};
	me.getParent = function(node) {
		return $("#" + me.treeId).tree("getParent", node.target);
	};
	me.getCheckedCon = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			var con = [];
			con.push(nodes[i].id);
			con.push(nodes[i].pojoName);
			s.push(con);
		}
		return s;
	};
	me.getUnCheckedNodes = function() {

		return $('#' + me.treeId).tree('getChecked', 'unchecked');
	};
	me.getCheckedNodes = function() {
		return $('#' + me.treeId).tree('getChecked', 'checked');
	};
	me.checkNode = function(node) {
		$('#' + me.treeId).tree('check', node.target);
	};
	me.isLeaf = function(node) {
		return $('#' + me.treeId).tree('isLeaf', node.target)
	};
	me.checkAll = function() {
		me.dosearch = false;
		var nodes = $('#' + me.treeId).tree('getRoots');
		for (var i = 0; i < nodes.length; i++) {
			me.checkNode(nodes[i]);
		}
		me.dosearch = true;
	}
	me.unCheckAll = function() {
		me.dosearch = false;
		var nodes = me.getCheckedNodes();
		for (var i = 0; i < nodes.length; i++) {
			me.unCheckNode(nodes[i]);
		}
		me.dosearch = true;
	};
	me.unCheckNode = function(node) {
		$('#' + me.treeId).tree('uncheck', node.target);
	};
	me.getOptions = function() {
		return $('#' + me.treeId).tree('options');
	};
	me.loader = function() {
		$("#" + me.treeId).tree('reload');
	};
	me.append = function(newNode) {
		var data = [];
		data.push(newNode);
		var node = $("#" + me.treeId).tree('getSelected');
		$("#" + me.treeId).tree('append', {
			parent : (node ? node.target : null),
			data : data
		});
	};
	me.bindBtn = function(id) {
		$("#" + id).linkbutton({
			iconCls : 'icon-uncheck',
			plain : true,
			onClick : function() {
				var iconCls = $(this).linkbutton("options").iconCls;
				if (iconCls == "icon-uncheck") {
					$(this).linkbutton({
						text : "反选",
						iconCls : "icon-check"
					});
					me.checkAll();
				} else {
					$(this).linkbutton({
						text : "全选",
						iconCls : "icon-uncheck"
					});
					me.unCheckAll();
				}
				if (me.dosearch == true) {
					me.search();
				}
			}
		});
	};
	return me;
};