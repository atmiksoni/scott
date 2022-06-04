<#-- textare --> <#macro ueditor id="" label="" width="" name="" value="" remark="" class="" placeholder="">
<label class="layui-form-label"><b>${label}：</b></label>
<div class="layui-input-block" style="margin-left:80px;border:none;">
	<textarea<#rt/> style=""
<#if id!=""> id="${id}"</#if><#rt/>
<#if name!=""> name="${name}"</#if><#rt/>
<#if placeholder!=""> placeholder="${placeholder}"</#if><#rt/>
<#if class!=null> class="${class}"</#if><#rt/>
<#if placeholder!=""> placeholder="${placeholder}"</#if><#rt/>
<#if placeholder=="" && datarule!=""> placeholder="请填写${label}"</#if><#rt/>
><#if value!="">${value}</#if><#rt/></textarea>
	<#rt/>
</div>
<#if remark!="">
<div class="layui-form-mid layui-word-aux">${remark}</div>
</#if><#rt/> </#macro>
