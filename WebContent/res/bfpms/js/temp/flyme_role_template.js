ArtTemp = function(alldata, hasdataString) {
	var me = this;
	me.alldata = alldata;
	me.hasdataString = hasdataString;
	me.ftl = '<div id="RoleList">' + '{{each alldata as allrole}}' + '<div class="UserRole"><div class="{{checkbutton(allrole)}} panelcheck">' + '<div id="{{allrole.roleId}}" name="{{allrole.roleName}}" code="{{allrole.roleCode}}" class="roletext"><img src="../../res/theme/default/images/role.png" />{{allrole.roleName}}</div>' + '<div class="{{triangle(allrole)}}"></div></div>{{/each}}</div>';

	me.render = function() {
		var render = template.compile(me.ftl);
		var has = hasdataString.split(',');
		template.helper('checkbutton', function(allrole) {
			cls = "rolebuttonNo";
			if ($.inArray(allrole.roleId, has) > -1) {
				cls = "rolebuttonOk";
			}
			return cls;
		});
		template.helper('triangle', function(allrole) {
			levelcls = "triangleNo";
			if ($.inArray(allrole.roleId, has) > -1) {
				levelcls = "triangleOk";
			}
			return levelcls;
		});
		return render({
			alldata : me.alldata,
			hasdataString : me.hasdataString
		});
	};
}