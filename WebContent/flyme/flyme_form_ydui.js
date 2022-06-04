VF = {
	id : "form",
	action : "save.do",
	method : "post",
	beforeCheck : "",
	beforeSubmit : function(curform) {
		return true;
	},
	beforeCheck : function(curform) {
		return true;
	},
	callback : function(date) {
	}
}
VF.callbackBefore = function(data) {
	if (data.code ==100) {
		VF.callback(data);
	}
	if (data.info) {
		$yui.toast(data.info);
	}
}
VF.Validform = function() {
	$("#" + VF.id).validator({
		focusCleanup : true,
		timely : 0,
		stopOnError : true,
		beforeSubmit : function(curform) {
			return VF.beforeSubmit(curform);
		},
		msgMaker : false, // 不要自动生成消息
		invalid : function(form, errors) {
			var html = '';
			$.map(errors, function(msg) {
				html += msg;
			});
			if (html != '') {
				$yui.toast(html);
			}
		},
		valid : function(form) {
			var me = this;
			me.holdSubmit();
			var params = $(form).serialize();
			var data = $(form).serializeArray();
		/*	var openId = $mui.getOpenId();*/
			var config = {
				url : form.action,
				type : 'POST',
				dataType : 'json',
				data : data,
				type : "POST",
				
				success : function(data) {
					me.holdSubmit(false);
					VF.callbackBefore(data);
				}
			}
			var headers = {};
//			headers["sc_api"] = base64_encode('userId' + '/' + openId + '/' + Math.round(new Date().getTime() / 1000));
//			config.headers = headers;
			$.ajax(config);
		}
	});
	$(".btn_sub").on("click", function() {
		$("#" + VF.id).trigger("submit");
	});
}