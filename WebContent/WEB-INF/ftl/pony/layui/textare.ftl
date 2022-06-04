<#--
textare
-->
<#macro textare id="" label=""  width=""  name="" value="" default="" remark="" class="layui-textarea"  placeholder="">
<label class="layui-form-label"><b>${label}：</b></label>
<div class="layui-input-inline" <#rt/>
<#if width!=""> style="width:${width}px;"</#if>>
<textarea<#rt/> 
<#if id!=""> id="${id}"</#if><#rt/>
<#if name!=""> name="${name}"</#if><#rt/>
<#if placeholder!=""> placeholder="${placeholder}"</#if><#rt/>
<#if class!=null> class="${class}"</#if><#rt/>
<#if placeholder!=""> placeholder="${placeholder}"</#if><#rt/>
<#if placeholder=="" && datarule!=""> placeholder="请填写${label}"</#if><#rt/>
>
<#if value!="">${value}</#if><#rt/>
<#if value=="" && default!="">${default}</#if><#rt/>
</textarea><#rt/>
</div>
<#if remark!=""> <div class="layui-form-mid layui-word-aux">${remark}</div></#if><#rt/>
</#macro>