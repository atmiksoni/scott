VF = {
    id : "form",
    action : "save.do",
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
}
VF.refreshparent = function() {
	parent.eval(VF.id).refresh();
}
VF.refreById = function(id) {
	parent.eval(id).refresh();
}
VF.callbackBefore = function(data) {
	
	var index = parent.layer.getFrameIndex(window.name);
	if (VF.dialog) {
		if (data.code ==100) {
			top.Dg.z.tip(data.info,'success','成功提示');
			
		} else {
			top.Dg.z.tip(data.info,'error','错误提示');
		}
		VF.refreshparent();
		parent.layer.close(index);
	} else {
		VF.callback(data);
		parent.layer.close(index);
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