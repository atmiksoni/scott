<#macro text  label="" value="" remark="" width="90">
<label class="layui-form-label"><b>${label}：</b></label>
<div class="layui-form-mid" style="padding-top:9px;width:${width}px;">${value}</div>
<#if remark!=""> <div class="layui-form-mid layui-word-aux">${remark}</div></#if><#rt/>
</#macro>/