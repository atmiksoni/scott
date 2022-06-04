/**
 * ComboGrid
 */
Cg = {
	z : top.window.Flyme,
	id : "",
	textField : "name",
	rowKey : "-1",
	url : "o_data.do",
	freezeCols : 5,
	colM : [],
	pageSize : 10,
	topVisible : true,
	onClickRow : function(rowIndex, rowData) {
	},
	param : {
		searchField : "",
		searchString : ""
	}
};
Cg.LoadComBoGrid = function() {
	$("#" + Cg.id + "Grid").combogrid({
		columns : Cg.colM,
		panelWidth : 450,
		width : 200,
		url : Cg.url,
		idField : "id",
		fitColumns : true,
		pagination : true,
		title : "请选择房间",
		fit : true,
		textField : Cg.textField,
		onClickRow : function(rowIndex, rowData) {
			Cg.onClickRow(rowIndex, rowData);
		},
		onShowPanel : function() {
			//$(".panel-body-noheader").css("border-top-width", "1px");
			$(".combo").css("border-bottom-width", "0px");
		},
		onHidePanel : function() {
			$(".combo").css("border-bottom-width", "1px");
		}
	});
};
Cg.formatter = function(row, pojo, field) {
	var rowData = eval("row." + pojo);
	if (rowData) {
		return eval("rowData." + field);
	} else {
		return "-";
	}
};
