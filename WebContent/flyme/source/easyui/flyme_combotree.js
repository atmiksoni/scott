﻿$.extend($.fn.tree.defaults, {
    loadFilter : function(treedata, parent) {
	    var data = (treedata.data) ? treedata.data : treedata;
	    var checkeds = treedata.checkeds;
	    var state=treedata.state;
	    init(checkeds,this);
	    var idFiled = "id", textFiled = "name", parentField = "parentId";
	    if (parentField) {
		    var treeData = [], tmpMap = [];
		    $(data).each(function(i, value) {
			    tmpMap[data[i][idFiled]] = value;
		    });
		    $(data).each(function(i, value) {
			    if (tmpMap[data[i][parentField]] && data[i][idFiled] != data[i][parentField]) {
				    if (!tmpMap[data[i][parentField]]['children'])
					    tmpMap[data[i][parentField]]['children'] = [];
				    data[i]['text'] = data[i][textFiled];
				    if ($.inArray(data[i][idFiled], checkeds) > -1) {
					    data[i]['checked'] = true;
				    }
				    tmpMap[data[i][parentField]]['children'].push(value);
			    } else {
				    data[i]['text'] = data[i][textFiled];
				    data[i]['state'] = state;
				    if ($.inArray(data[i][idFiled], checkeds) > -1) {
					    data[i]['checked'] = true;
				    }
				    treeData.push(data[i]);
			    }
		    });
		    return treeData;
	    }
	    return data;
    },
    loader : function(param, success, error) {
	    var opts = $(this).tree("options");
	    if (!opts.url) {
		    return false;
	    }
	    if (opts.queryParams) {
		    param = $.extend({}, opts.queryParams, param);
	    }
	    $.ajax({
	        type : opts.method,
	        url : opts.url,
	        data : param,
	        dataType : "json",
	        success : function(data) {
		        success(data);
	        },
	        error : function() {
		        error.apply(this, arguments);
	        }
	    });
    },
    queryParams : {}
});
function init(checkeds,tree) {
	if (checkeds) {
		var opt = $(tree).data().tree.options;
		opt.checkeds = checkeds;
	}
};
$.extend($.fn.tree.methods, {
	setQueryParams : function(jq, params) {
		return jq.each(function() {
			$(this).tree("options").queryParams = params;
		});
	}
});
var ComboTree = function() {
	var me = this;
	me.treeId = "";
	me.url = "list_treedata.do";
	me.onlyLeafCheck = false;
	me.checkbox = false;
	me.onClick = function(node) {
	};
	me.onLoadSuccess = function(node, data) {
	};
	me.init = function(id) {
		me.treeId = id;
		$("#" + me.treeId).combotree({
		    url : me.url,
		    checkbox : me.checkbox,
		    onlyLeafCheck : me.onlyLeafCheck,
		    onBeforeExpand : function(node) {
			    $("#" + me.treeId).tree("setQueryParams", {
			        name : node.name,
			        pojoName : node.pojoName
			    });
		    },
		    onClick : function(node) {
			    me.onClick(node);
		    },
		    onLoadSuccess : function(node, data) {
			    me.onLoadSuccess(node, data);
		    }
		});
		niceScroll("body");
	};
	me.getSelected = function() {
		return $("#" + me.treeId).tree("getChecked");
	};
	me.expandAll = function() {
		$("#" + me.treeId).tree('expandAll');
	};
	me.getCheckedIds = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked', 'indeterminate' ]);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			s.push(nodes[i].id);
		}
		return s;
	};
	me.getCheckedId = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked']);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			s.push(nodes[i].id);
		}
		return s;
	}
	me.getCheckedCon = function() {
		var nodes = $("#" + me.treeId).tree('getChecked', [ 'checked']);
		var s = [];
		for (var i = 0; i < nodes.length; i++) {
			var con = [];
			con.push(nodes[i].id);
			con.push(nodes[i].pojoName);
			s.push(con);
		}
		return s;
	}
	return me;
};