<input type="radio"<#rt/>
 value="${rkey}"<#rt/>
 <#if layfilter!=""> lay-filter="${layfilter}"</#if><#rt/>
 <#if name!=""> name="${name}"</#if><#rt/>
 <#if id!=""> id="${id}"</#if><#rt/>
 title="${rvalue}"<#rt/>
<#if index==0 && datarule!=""> data-rule="${datarule}"</#if><#rt/>
<#if (rkey?string=="" && (!value?? || value?string=="")) || (value?? && value?string!="" && value?string==rkey?string)> checked="checked"</#if><#rt/>
/>
<#if hasNext> 
</#if>