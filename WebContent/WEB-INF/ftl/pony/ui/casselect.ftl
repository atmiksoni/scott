<#--
-->
<#macro casselect id="casselect" url="" title="" name="" prov="" city="" dist="" disabled="false" required=true>
<span id="city_area">
<select class="prov" style="width:80px;" ></select> 
<select class="city"  style="width:80px;" disabled="disabled" ></select>
<select class="dist" id="${id}" name="${name}" data-rule="required;" style="width:90px;" ></select>
</span>
<script type="text/javascript">
$(function(){
	$("#city_area").citySelect({
	    url:'${url}',
		prov:"${prov}",
		city:"${city}",
		dist:"${dist}",
		title:"${title}",
		disabled:"${disabled}",
		required:${required?string}
	});
	
});
</script>
</#macro>
