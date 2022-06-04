<#--
<input type="radio"/>
-->
<#macro layradio 
	list listKey="" listValue="" value="" label="" layfilter="" datarule="" colspan="" width="100" id="" name="" class="" style=""  disabled="">
<#if list?is_sequence>
<label class="layui-form-label"><b>${label}：</b></label> 
	<#if listKey!="" && listValue!="">
		<#list list as item>
			<#local rkey=item[listKey]>
			<#local rvalue=item[listValue]>
			<#local index=item_index>
			<#local hasNext=item_has_next>
			<#include "ftl/pony/layui/radio-item.ftl"><#t/>
		</#list>
	<#else>
		<#list list as item>
			<#local rkey=item>
			<#local rvalue=item>
			<#local index=item_index>
			<#local hasNext=item_has_next>
			<#include "ftl/pony/layui/radio-item.ftl"><#t/>
		</#list>
	</#if>
<#else>
   <label class="layui-form-label"><b>${label}：</b></label> 
	<#list list?keys as key>
		<#local rkey=key/>
		<#local rvalue=list[key]/>
		<#local index=key_index>
		<#local hasNext=key_has_next>
		<#include "ftl/pony/layui/radio-item.ftl"><#t/>
	</#list>
</#if>
</#macro>