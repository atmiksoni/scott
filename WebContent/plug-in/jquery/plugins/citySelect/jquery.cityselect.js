/*

settings 参数说明
-----
url:省市数据josn文件路径
prov:默认省份
city:默认城市
dist:默认地区（县）
nodata:无数据状态
required:必选项
------------------------------ */
(function($){
	$.fn.citySelect=function(settings){
		if(this.length<1){return;};

		// 默认值
		settings=$.extend({
			url:"",
			title:"",
			prov:null,
			city:null,
			dist:null,
			nodata:null,
			required:true
		},settings);

		var box_obj=this;
		var prov_obj=box_obj.find(".prov");
		var city_obj=box_obj.find(".city");
		var dist_obj=box_obj.find(".dist");
		var prov_val=settings.prov;
		var title=settings.title;
		var disabled=settings.disabled;
		var city_val=settings.city;
		var dist_val=settings.dist;
		var select_prehtml=(settings.required) ? "" : "<option value=''>--请选择"+title+"--</option>";
		var city_json;
		
		
		// 赋值市级函数
		var cityStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
			};
			city_obj.empty().attr("disabled",true);
			//dist_obj.empty().attr("disabled",true);
			
			emptytext(city_obj);
			emptytext(dist_obj);
				
			if(prov_id<0||typeof(city_json.citylist[prov_id].child)=="undefined"){
				if(settings.nodata=="none"){
					city_obj.css("display","none");
					dist_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					city_obj.css("visibility","hidden");
					dist_obj.css("visibility","hidden");
				};
				return;
			};
			
			// 遍历赋值市级下拉列表
			temp_html=select_prehtml;
			$.each(city_json.citylist[prov_id].child,function(i,city){
				temp_html+="<option value='"+city.city.code+"'>"+city.city.name+"</option>";
			});
			city_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
			if(disabled=='true'){
				prov_obj.html(temp_html).attr("disabled",true);
				city_obj.html(temp_html).attr("disabled",true);
			}
			distStart();
		};

		// 赋值地区（县）函数
		var distStart=function(){
			var prov_id=prov_obj.get(0).selectedIndex;
			var city_id=city_obj.get(0).selectedIndex;
			if(!settings.required){
				prov_id--;
				city_id--;
			};
			//dist_obj.empty().attr("disabled",true);
			emptytext(dist_obj);
			if(prov_id<0||city_id<0||typeof(city_json.citylist[prov_id].child[city_id].child)=="undefined"){
				if(settings.nodata=="none"){
					dist_obj.css("display","none");
				}else if(settings.nodata=="hidden"){
					dist_obj.css("visibility","hidden");
				};
				return;
			};
			
			// 遍历赋值市级下拉列表
			temp_html=select_prehtml;
			$.each(city_json.citylist[prov_id].child[city_id].child,function(i,dist){
				temp_html+="<option value='"+dist.city.code+"'>"+dist.city.name+"</option>";
			});
			dist_obj.html(temp_html).attr("disabled",false).css({"display":"","visibility":""});
			if(disabled=='true'){
				//dist_obj.html(temp_html).attr("disabled",true);
			}
			
		};
		var inittext=function(obj)
		{
			//obj.parent().find("span").html(obj.find("option:selected").text());
		};
		var emptytext=function(obj)
		{
			//obj.parent().find("span").html("--请选择--");
		};
		var init=function(){
			// 遍历赋值省份下拉列表
			temp_html=select_prehtml;
			$.each(city_json.citylist,function(i,prov){
				
				temp_html+="<option value='"+prov.city.code+"'>"+prov.city.name+"</option>";
			});
			prov_obj.html(temp_html);

			// 若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
			setTimeout(function(){
				if(settings.prov!=null){
					prov_obj.val(settings.prov);
					inittext(prov_obj);
					cityStart();
					setTimeout(function(){
						if(settings.city!=null){
							city_obj.val(settings.city);
							inittext(city_obj);
							distStart();
							setTimeout(function(){
								if(settings.dist!=null){
									dist_obj.val(settings.dist);
									inittext(dist_obj);
								};
							},1);
						};
					},1);
				};
			},1);
			
			// 选择省份时发生事件
			prov_obj.bind("change",function(){
				cityStart();
			});

			// 选择市级时发生事件
			city_obj.bind("change",function(){
				distStart();
			});
			// 选择2级时发生事件
			prov_obj.bind("mousedown",function(){
				this.selectedIndex=-1
			});
			// 选择2级时发生事件
			city_obj.bind("mousedown",function(){
				this.selectedIndex=-1
			});
			// 选择3级时发生事件
			dist_obj.bind("mousedown",function(){
				this.selectedIndex=-1
			});
			
		};

		// 设置省市json数据
		if(typeof(settings.url)=="string"){
			$.getJSON(settings.url,function(json){
				city_json=json;
				init();
			});
		}else{
			city_json=settings.url;
			init();
		};
	};
})(jQuery);