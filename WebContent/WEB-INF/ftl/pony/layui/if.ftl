<#macro iftext list value="" valueField="" textField="">
<#if list?exists>
<#if list?is_sequence>
	<#if valueField!="" && textField!="">
		<#list list as item>
			<#if item[valueField]?string==value?string>${item[textField]!}</#if><#t/>
		</#list>
	<#else>
		<#list list as item>
			<#if item==value>${item}</#if><#t/>
		</#list>
	</#if>
<#else>
	<#list list?keys as key>
		<#if key==value?string><@s.mt code=list[key] text=list[key]/></#if><#t/>
	</#list>
</#if>
</#if>
</#macro>