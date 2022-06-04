<#--
tr
-->
<#macro tr id="" hidden="">
<tr<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#if hidden==true> style="display:none"</#if><#rt/>
><#rt/>
<#nested/>
</tr>
</#macro>