KISSY.use('plug/calendar/index', function(KISSY, Calendar) {
	var triggerNode=[];
	$("input[mytype=calendar]").each(function(index,item){
		triggerNode.push("#"+item.id);
	});
	var calendar = new Calendar({
		triggerNode : triggerNode.join(","),
		isSelect:true,
		count : 1
	});
});