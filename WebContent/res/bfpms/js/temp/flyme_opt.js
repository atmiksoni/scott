ArtTemp = function(fun,value,obj) {
	var me = this;
	var data = JSON2.stringify(obj);
	me.ftl = value;
	me.render = function() {
		var render = template.compile(me.ftl);
		return render({
		    fun : fun,
		    obj : data
		});

	};
}