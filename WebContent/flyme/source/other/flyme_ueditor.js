window.UEDITOR_HOME_URL = "/plug-in/ueditor/";
var Ue = function(id) {
	var me = this;
	me.height = "200";
	me.width = "700";
	me.params = {};
	me.init = function() {
		var ue = UE.getEditor(id, {
			initialFrameWidth : me.width,
			initialFrameHeight : me.height,
			allowDivTransToP : false,
			elementPathEnabled : false,
			enterTag : 'br',
			
			toolbars : [ [ 'fullscreen', 'source','undo', 'redo', '|', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|', 'rowspacingtop', 'rowspacingbottom', 'lineheight', '|', 'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|', 'directionalityltr', 'directionalityrtl', 'indent', '|', 'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|', 'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|', 'simpleupload', 'insertimage', 'emotion',
							 'pagebreak',   'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|', 'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts'] ],
			enableAutoSave : false,
			UEDITOR_HOME_URL : "/plug-in/ueditor/",
			serverUrl : "../ueditor/config.do"
		});
		ue.ready(function() {
			ue.execCommand('serverparam', me.params);
		});
		/** 去除默认P */
		ue.addListener("focus", function(type, event) {
			if ($.trim(ue.getContent()) == "") {
				ue.setContent("");
			}
		});
		UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
		UE.Editor.prototype.getActionUrl = function(action) {
			if (action == 'uploadimage' || action == 'uploadfile') {
				return '/admin/ueditor/upload_file.do';
			} else {
				return this._bkGetActionUrl.call(this, action);
			}
		};
	}
	return me;
};
