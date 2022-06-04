<#macro listtext list label="" value="" remark="" valueField="" textField="">
<label class="layui-form-label"><b>${label}：</b></label>
<div class="layui-form-mid" style="padding-top: 10px;width:80px;">
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
</div>
<#if remark!=""> <div class="layui-form-mid layui-word-aux">${remark}</div></#if><#rt/>
</#macro>