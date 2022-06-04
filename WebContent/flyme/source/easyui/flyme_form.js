Form = function(formId) {
	var me = this;
	me.formId = formId;
	me.dialog = true, me.refreshparent = function() {
		var n = top.Current_iframe().contentWindow.length;
		if (n > 0) {
			top.Current_iframe().contentWindow[1].eval(me.formId).refresh();
		} else {
			top.Current_iframe().contentWindow.eval(me.formId).refresh();
		}
	};
	me.callbackBefore = function(data) {
		if (me.dialog) {
			var win = frameElement.api.opener;
			if (data.status = "y") {
				top.Dg.z.tip(data.info, 4000, 300, 'success');
				me.refreshparent();
				me.callback(data);
				frameElement.api.close();
			} else {
				top.Dg.z.tip(data.info, 4000, 300, 'waring');
				frameElement.api.close();
			}
		} else {
			me.callback(data);
		}
	};
	me.form = function() {
		$("#" + me.formId).form({

			onSubmit : function() {

			},
			success : function(data) {
				me.callbackBefore(data);
			}
		});
	}

}