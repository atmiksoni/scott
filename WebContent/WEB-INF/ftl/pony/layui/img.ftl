<#macro img  label="" src="" remark="">
<label class="layui-form-label">${label}：</label>
<div class="layui-input-inline" style="padding-top: 10px;"><img src="${src}" width="50%" height="50%"></div>
<#if remark!=""> <div class="layui-form-mid layui-word-aux">${remark}</div></#if><#rt/>
</#macro>