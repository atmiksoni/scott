$.extend($.fn.textbox.methods, {
	addClearBtn : function(jq, iconCls) {
		return jq.each(function() {
			var t = $(this);
			var opts = t.textbox('options');
			opts.icons = opts.icons || [];
			opts.icons.unshift({
			    iconCls : iconCls,
			    handler : function(e) {
				    $(e.data.target).textbox('clear').textbox('textbox').focus();
				    $(this).css('visibility', 'hidden');
			    }
			});
			t.textbox();
			if (!t.textbox('getText')) {
				t.textbox('getIcon', 0).css('visibility', 'hidden');
			}
			t.textbox('textbox').bind('keyup', function() {
				var icon = t.textbox('getIcon', 0);
				if ($(this).val()) {
					icon.css('visibility', 'visible');
				} else {
					icon.css('visibility', 'hidden');
				}
			});
		});
	}
});
var Sb = function(id) {
	var me = this;
	me.id = id;
	me.prompt = "";
	me.menu = "";
	me.icon = [];
	me.searcher = function(value, name) {
	};
	me.onClickIcon = function(index) {
	};
	me.init = function() {
		$("#" + me.id).searchbox({
		    icons : me.icon,
		    menu : me.menu,
		    searcher : function(value,name) {
			    me.searcher(value, name);
		    },
		    onClickIcon : function(index) {
			    me.onClickIcon(index);
		    },
		    prompt : me.prompt
		});
		// $("#" + me.id).searchbox().textbox("addClearBtn", "icon-clear");

	};
	me.getText = function() {
		return $("#" + me.id).searchbox("getValue");
	};
	return me;
};