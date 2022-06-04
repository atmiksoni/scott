<#--
input
-->
<#macro layinput id="" readonly="" label="" type="" name="" value="" default="" remark="" class="layui-input" width="" min="" max="" datarule="" datamsgrequired="" placeholder="">
<label class="layui-form-label"><b>${label}：</b>&nbsp;&nbsp;</label>
<div class="layui-input-inline" <#rt/>
<#if width!=""> style="width:${width}px;"</#if>>
<input<#rt/>
<#if id!=""> id="${id}"</#if><#rt/>
<#if name!=""> name="${name}"</#if><#rt/>
<#if readonly!=""> readonly="${readonly}"</#if><#rt/>
<#if datarule!=""> data-rule="${datarule}"</#if><#rt/>
<#if datamsgrequired!=""> data-msg-required="${datamsgrequired}"</#if><#rt/>
<#if min!=""> min:${min}</#if><#rt/>
<#if max!=""> max:${max}</#if><#rt/>
<#if placeholder!=""> placeholder="${placeholder}"</#if><#rt/>
<#if value!=""> value="${value}"</#if><#rt/>
<#if value=="" && default!=""> value="${default}"</#if><#rt/>
<#if class!=null && type!="file"> class="${class}"</#if><#rt/>
<#if type!=""> type="${type}"</#if><#rt/>
<#if placeholder!=""> placeholder="${placeholder}"</#if><#rt/>
<#if placeholder=="" && datarule!=""> placeholder="${label}"</#if><#rt/>
<#if type=="file"> class="layui-upload-file"</#if><#rt/>
/><#rt/>
</div>
<div class="layui-form-mid layui-word-aux"><#if datarule?index_of("required")!=-1> <span class="red">*</span></#if></div><#rt/>
</#macro>