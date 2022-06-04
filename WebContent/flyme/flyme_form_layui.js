VF = {
	id : "form",
	action : "",
	method : "post",
	beforeCheck : "",
	layer:"",
	index:"",
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
	if (data.info) {
		$layui.toast(data.info);
	}
	if (data.code ==100) {
		VF.callback(data,VF.layer,VF.index);
		if(VF.layer){
			VF.layer.close(VF.index);
		}
		return;
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
				$layui.toast(html);
			}
		},
		valid : function(form) {
			var me = this;
			me.holdSubmit();
			var params = $(form).serialize();
			var data = $(form).serializeArray();
			var token = $layui.getKey("token");
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
			headers["token"] = token;
			config.headers = headers;
			$.ajax(config);
		}
	});
	$(".btn_sub").on("click", function() {
		$("#" + VF.id).trigger("submit");
	});
}
/** 弹出框自带按钮提交表单 */
function submit(layer, index, callback) {
	if (callback) {
		VF.callback = callback;
	}
	VF.layer=layer;
	VF.index=index;
	$("#" + VF.id).trigger("submit");
}
/** 初始化 */
function init(option, iframeWin){
	if(option.beforeSubmit){
		/*此方法可在 父页面获取表单元素$("#id",curform).val()*/
		VF.beforeSubmit=option.beforeSubmit;
	}
}