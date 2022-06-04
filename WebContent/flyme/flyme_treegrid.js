/**
 * @description:PQGRID封装
 * @version:1.0
 * @author:flyme
 * @data:2013-8-5
 */
Tg={
	z:top.window.Flyme,
	id:"",
	idField:"id",
	treeField:"name",
	rowKey:"-1",
	url:"o_data.do",
	addurl : "add_turn.do",
	editurl : "edit_turn.do",
	delurl : "delete_sub.do",
	detailurl : "detail_turn.do",
	title:"数据列表",
	freezeCols:5,
	colM:[],
	pageSize:10,
	topVisible:true,
	param:{
		searchField:"",
		searchString:""
	}
};
Tg.LoadTreeGrid=function(){
	$("#"+Tg.id+"Grid").treegrid({
		columns:Tg.colM,
		title:Tg.title,
		url:Tg.url,
		idField:Tg.idField,
		treeField:Tg.treeField,
		pagination:true,
		rownumbers:true,
		fitColumns:true,
		fit:true,
		onClickRow:function(rowData){
			Tg.rowKey=rowData.id;// 选中记录主键值
		}

	});
	Tg.pqGridResize(Tg.id,96);
};
// 根据rowCode获取选择行的值
Tg.GetPqGridRowValue=function(id,field){
	if(Tg.rowKey!=-1){
		var rowDate=$("#"+id+"Grid").treegrid("getSelected");
		return rowDate[field];
	}else{
		return null;
	}
};

Tg.getSelections = function(field) {
	var ids = [];
	var rows = $("#" + Tg.id + "Grid").datagrid("getSelections");
	for(var i=0;i<rows.length;i++){
		ids.push(rows[i][field]);
	}
	return ids.join(",");
};

// 重置表格大小
Tg.pqGridResize=function(id,lose_height){

	$("#"+id+"Grid").treegrid('resize',{
		width:function(){
			return document.body.clientWidth;
		},
		height:function(){
			return document.body.clientHeight;
		}
	});
};

/**
 * 检验是否选择数据
 */
Tg.checkObj = function(id) {
	var isOK = true;
	if (id == undefined || id == "") {
		isOK = false;
		Tg.z.tip("您未选中任何一条记录！", 4000, 300, "error");
	} else if (id.split(",").length > 5) {
		isOK = false;
		Tg.z.tip("只能选择一条记录！", 4000, 300, "error");
	}
	return isOK;
};
Tg.add = function() {
	top.Dg.url = Tg.id + "/" + Tg.addurl;
	top.Dg.title = Tg.title + "添加";
	top.Dg.openDialog();
};
Tg.edit = function() {
	top.Dg.title = Tg.title + "修改";
	if (Tg.checkObj(Tg.rowKey)) {
		top.Dg.url = Tg.id + "/" + Tg.editurl + "?id=" + Tg.rowKey;
		top.Dg.openDialog();
	}
};
Tg.del = function() {
	top.Dg.gridType="Tg";
	if (Tg.checkObj(Tg.rowKey)) {
		top.Dg.url = Tg.id + "/" + Tg.delurl;
		top.Dg.deleteRow(Tg.rowKey, Tg.id);
	}
};
Tg.detail = function() {
	top.Dg.title = Tg.title + "详细信息";
	if (Tg.checkObj(Tg.rowKey)) {
		top.Dg.url = Tg.id + "/" + Tg.detailurl + "?id=" + Tg.rowKey;
		top.Dg.openDialog();
	}
};
Tg.winResize = function(element, height) {
	resizeU();
	$(window).resize(resizeU);
	function resizeU() {
		Tg.GridResize(element, height);
	}
};
Tg.GridResize = function(id, lose_height) {
	$("#" + id + "Grid").datagrid('resize', {
		width : "100%",
		height : $(window).height() - lose_height
	});
};
Tg.search = function() {
	var queryParams = $("#" + Tg.id + "Grid").datagrid("options").queryParams;
	$.extend(queryParams, Tg.param);
	$("#" + Tg.id + "Grid").datagrid('reload');

};
Tg.formatter = function(row, pojo, field) {
	var rowData = eval("row." + pojo);
	if (rowData) {
		return eval("rowData." + field);
	} else {
		return "-";
	}
};
Tg.refresh=function(id){
	Tg.z.refreshIframe(top.Current_iframeID());
};
Tg.close=function(id){
	top.closeTab(top.Current_iframeID());
};
$(document).ready(function(){
});
