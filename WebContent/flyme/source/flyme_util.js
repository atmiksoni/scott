(function($) {
	"use strict";
	var baseUi = {
		dialogClose : function(layer, index) {
			try {
				var $IsdialogClose = top.$("#layui-layer" + index).find('.layui-layer-btn').find("#IsdialogClose");
				var IsClose = $IsdialogClose.is(":checked");
				if ($IsdialogClose.length == 0) {
					IsClose = true;
				}
				if (IsClose) {
					layer.close(index);
				}
			} catch (e) {
			}
		}
	}
	$.flyeme = baseUi;
})(window.jQuery);