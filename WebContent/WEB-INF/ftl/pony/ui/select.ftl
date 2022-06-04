<#--
下拉框
-->
<#macro select list value="" class="" myclass="" panelHeight="150" width="160" multiple=""   valueField="" textField=""  id="" name="" defKey="" defVal="" datarule="">
<#if list?exists>
<select<#rt/>
<#if datarule!=""> data-rule="${datarule}"</#if><#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#if name!=""> name="${name}"</#if><#rt/>
<#if class!="" && myclass==""> class="${class}"</#if><#rt/>
<#if myclass!=""> class="${myclass} "<#rt/>
 data-options="width:${width},panelHeight:${panelHeight}<#rt/>
<#if multiple!="">,multiple:${multiple}</#if><#rt/>
"<#rt/>
</#if><#rt/>
><#rt/>
<#if defKey!="" || defVal!="">
	<option value="${defKey}"<#if defKey==value?string> selected="selected"</#if>>---<@s.mt code=defVal text=defVal/>---</option><#t/>
</#if>
<#if list?is_sequence>
	<#if valueField!="" && textField!="">
		<#list list as item>
			<option value="${item[valueField]}"<#if item[valueField]?string==value?string> selected="selected"</#if>>${item[textField]!}</option><#t/>
		</#list>
	<#else>
		<#list list as item>
			<option value="${item}"<#if item==value> selected="selected"</#if>>${item}</option><#t/>
		</#list>
	</#if>
<#else>
	<#list list?keys as key>
		<option value="${key}"<#if key==value?string> selected="selected"</#if>><@s.mt code=list[key] text=list[key]/></option><#t/>
	</#list>
</#if>
</select>
</#if>
</#macro>
