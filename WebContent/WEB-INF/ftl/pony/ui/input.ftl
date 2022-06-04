<#--
input
-->
<#macro input id="" name="" value="" class="txtInput" width="160" myclass="" min="" max="" precision="" prefix="" suffix="">
<input<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#if name!=""> name="${name}"</#if><#rt/>
<#if value!=""> value="${value}"</#if><#rt/>
<#if class!=null && myclass==""> class="${class}"</#if><#rt/>
<#if myclass!=""> class="${myclass}" <#rt/>
data-options="width:${width}<#rt/>
<#if min!="">,min:${min}</#if><#rt/>
<#if max!="">,max:${max}</#if><#rt/>
<#if precision!="">,precision:${precision}</#if><#rt/>
<#if prefix!="">,prefix:'${prefix}'</#if><#rt/>
<#if suffix!="">,suffix:${suffix}</#if><#rt/>
"<#rt/>
</#if><#rt/>
/><#rt/>
</#macro>