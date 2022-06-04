<#macro layselect list label="" value="" data_target="" class="" filter=""   width="160" disabled="" multiple=""   valueField="" textField=""  id="" name="" defKey="" defVal="" datarule="">
<#if list?exists>

<label class="layui-form-label"><b>${label}：</b></label>
<div  class="layui-input-inline" <#rt/>
<#if width!=""> style="width:${width}px;"</#if>>
<select<#rt/>
<#if datarule!=""> data-rule="${datarule}" data-target="#${id}Div"</#if><#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#if filter!=""> lay-filter="${filter}"</#if><#rt/>
<#if name!=""> name="${name}"</#if><#rt/>
<#if datarule!=""> data-target="${id}Div"</#if><#rt/>
<#if class!=""> class="${class}"</#if><#rt/>
<#if disabled!=""> disabled="${disabled}"</#if><#rt/>
><#rt/>
<#if defVal!="">
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
</div>
 <div id="${id}Div" class="layui-form-mid layui-word-aux"><#if datarule?index_of("required")!=-1> <span class="red">*</span></#if></div><#rt/>
</#if>

</#macro>
