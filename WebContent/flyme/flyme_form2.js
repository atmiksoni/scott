VF = {
	id : "form",
	action : "save.do",
	z : top.window.Flyme,
	method : "post",
	tiptype : "2",
	beforeCheck : "",
	beforeSubmit : function(curform) {
		return true;
	},
	beforeCheck : function(curform) {
		return true;
	},
	callback : function(date) {

	},
	label : ".tdl",
	ajaxPost : true,
	btnSubmit : true,
	dialog : true,
	enctype : false
}
VF.mytiptype = function(msg, o, cssctl) {
	var objtip = $("#checkmsg");
	cssctl(objtip, o.type);
	objtip.text(msg);
};
VF.refreshparent = function() {
	if (VF.getDdById(VF.id)) {
		VF.getDdById(VF.id).refresh();
	}
};
VF.refreById = function(id) {
	VF.getDdById(id).refresh();
}
VF.getDdById = function(id) {
	return VF.getParent().eval(id);
};
VF.getParent = function() {
	var n = top.Current_iframe().contentWindow.length;
	if (n > 0) {
		return top.Current_iframe().contentWindow[1];
	} else {
		try {
			return top.Current_iframe().contentWindow;
		} catch (e) {
		}
	}
};
VF.refreshparent2 = function() {
	top.Current_iframe().contentWindow[0].meterfee.refresh("meterfee");
};
VF.callbackBefore = function(data) {
	var index = parent.layer.getFrameIndex(window.name);
	if (VF.dialog) {
		if (data.code == 100) {
			top.Dg.z.toastr(data.info, 'success', '');
			VF.refreshparent();
			VF.callback(data);
			parent.layer.close(index);
		} else {
			top.Dg.z.toastr(data.info, 'error', '');
			if (data.close) {
				parent.layer.close(index);
			}
		}
	} else {
		VF.callback(data);
	}
}
VF.Validform = function() {
	var setting = {
		tiptype : VF.tiptype,
		label : VF.label,
		ignoreHidden : true,
		ajaxPost : VF.ajaxPost,
		beforeSubmit : function(curform) {
			return VF.beforeSubmit(curform);
		},
		beforeCheck : function(curform) {
			return VF.beforeCheck(curform);
		},
		callback : function(data) {
			VF.callbackBefore(data);
		}
	};
	if (VF.btnSubmit) {
		setting.btnSubmit = "#btn_sub";
		setting.btnReset = "#btn_reset";
	}
	if (VF.tiptype == "custom") {
		setting.tiptype = function(msg, o, cssctl) {
			var objtip = $("#checkmsg");
			cssctl(objtip, o.type);
			objtip.text(msg);
		}
	}
	if (VF.tiptype == "toptip") {
		setting.tiptype = function(msg, o, cssctl) {
			if (!o.obj.is("form")) {
				var objtip = o.obj.parents("td").find(".vtip");
				cssctl(objtip, o.type);
				objtip.text(msg);
				var infoObj = o.obj.parents("td").find(".info");
				if (o.type == 2) {
					infoObj.fadeOut(200);
				} else {
					if (infoObj.is(":visible")) {
						return;
					}
					var left = o.obj.offset().left, top = o.obj.offset().top;

					infoObj.css({
						left : left + 170,
						top : top - 45
					}).show().animate({
						top : top - 35
					}, 200);
				}

			}
		}
	}
	$("#" + VF.id).Validform(setting);
}