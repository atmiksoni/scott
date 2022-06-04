$(function() {
	$.getclientdata();
})
var clientdataItem = [];
var clientorganizeData = [];
var clientdepartmentData = [];
var clientpostData = [];
var clientroleData = [];
var clientuserGroup = [];
var clientuserData = [];
var resourceData = [];
var authorizeButtonData = [];
var authorizeColumnData = [];
var flag = false;
$.getclientdata = function() {
	$.ajax({
		url : contentPath + "get_resource.do",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			resourceData = data.resources;
			flag = data.flag;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			dialogMsg(errorThrown, -1);
		}
	});
}