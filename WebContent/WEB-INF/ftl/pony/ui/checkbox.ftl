<#--
<input type="checkbox"/>
-->
<#macro checkbox
	list listKey="" listValue="" value="" rowslength=5
	label="" noHeight="false" datarule="" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	id="" name="" class="" style="" size="" title="" disabled="" tabindex="" accesskey=""
	onclick="" ondblclick="" onmousedown="" onmouseup="" onmouseover="" onmousemove="" onmouseout="" onfocus="" onblur="" onkeypress="" onkeydown="" onkeyup="" onselect="" onchange=""
	>
<#if list?is_sequence>
	<#if listKey!="" && listValue!="">
		<#list list as item>
			<#local rkey=item[listKey]>
			<#local rvalue=item[listValue]>
			<#local index=item_index>
			<#local hasNext=item_has_next>
			<#include "ftl/pony/ui/checkbox-item.ftl"><#t/>
			<#if item_index%rowslength==rowslength-1><br></#if>
		</#list>
	<#else>
		<#list list as item>
			<#local rkey=item>
			<#local rvalue=item>
			<#local index=item_index>
			<#local hasNext=item_has_next>
			<#include "ftl/pony/ui/checkbox-item.ftl"><#t/>
		</#list>
	</#if>
<#else>
	<#list list?keys as key>
		<#local rkey=key/>
		<#local rvalue=list[key]/>
		<#local index=key_index>
		<#local hasNext=key_has_next>
		<#include "ftl/pony/ui/checkbox-item.ftl"><#t/>
	</#list>
</#if>
</#macro>