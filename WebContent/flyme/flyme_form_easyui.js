VF = {
	id : "form",
	action : "save.do",
	z : top.window.Flyme,
	method : "post",
	tiptype : "2",
	beforeCheck : "",
	timely : 0,
	parentobj : "",
	beforeSubmit : function(curform) {
		Fm.disableForm("#" + this.id);
		return true;
	},
	beforeCheck : function(curform) {
		return true;
	},
	beforeSend : function() {

	},
	/* 弹出框自定义回调 */
	callback : function(date) {
	},

	label : ".tdl",
	msgClass : "n-bottom",
	msgStyle : "",
	ajaxPost : true,
	msgMaker : true,
	btnSubmit : true,
	dialog : true,
	enctype : false
}
VF.mytiptype = function(msg, o, cssctl) {
	var objtip = $("#checkmsg");
	cssctl(objtip, o.type);
	objtip.text(msg);
};
/** 刷新指定datagrid */
VF.refreById = function(id) {
	top.getParentWin().eval(id).refresh();
}
VF.callbackBefore = function(data) {
	if (VF.dialog) {
		top.Dg.z.msg(data);
		var index = parent.layer.getFrameIndex(window.name);
		if (data.code ==100) {
			VF.callback(data);
			if (data.refrsh) {
				VF.parentobj.refresh();
			}
		}
		if (data.close == true) {
			top.$.flyeme.dialogClose(parent.layer, index);
		}
	} else {
		Dg.msg(data.info);
		VF.callback(data);
	}
}
VF.Validform = function() {
	var config = {
		focusCleanup : true,
		timely : VF.timely,
		msgClass : VF.msgClass,
		msgStyle : VF.msgStyle,
		theme : 'yellow_right_effect',
		stopOnError : true,
		display : function(element) {
			return " ";
		},
		beforeSubmit : function(curform) {
			return VF.beforeSubmit(curform);
		},
		valid : function(form) {
			var me = this;
			me.holdSubmit();
			// parent.$(".layui-layer-btn0").html("提交中..");
			$.ajax({
				url : form.action,
				type : 'POST',
				dataType : 'json',
				data : $(form).serialize(),
				beforeSend : function() {
					VF.beforeSend();
				},
				type : "POST",
				success : function(data) {
					me.holdSubmit(false);
					// 提交表单成功后，释放hold，就可以再次提交
					Fm.enableForm("#" + VF.id);
					VF.callbackBefore(data);
				}
			});
		}
	}
	if (!VF.msgMaker) {
		config.msgMaker = function(opt) {
			layer.msg(opt.msg, {
				icon : 5
			});
		}
	}
	$("#" + VF.id).validator(config);
	$(".btn_sub").on("click", function() {
		$("#" + VF.id).trigger("submit");
	});
}
/** 提交表单 */
function submit(parentobj, layer, index, callback) {
	VF.parentobj = parentobj;
	if (callback) {
		VF.callback = callback;
	}
	$("#" + VF.id).trigger("submit");
}