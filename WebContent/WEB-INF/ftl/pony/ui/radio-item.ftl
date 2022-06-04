<label class='radio-inline'> 
<input type="radio"<#rt/>
 value="${rkey}"<#rt/>
<#if index==0> data-rule="${datarule}"</#if><#rt/>
<#if (rkey?string=="" && (!value?? || value?string=="")) || (value?? && value?string!="" && value?string==rkey?string)> checked="checked"</#if><#rt/>
<#include "ftl/pony/ui/common-attributes.ftl"/><#rt/>
/>
<@s.mt code=rvalue text=rvalue/>
</label>
<#if hasNext> 
</#if>