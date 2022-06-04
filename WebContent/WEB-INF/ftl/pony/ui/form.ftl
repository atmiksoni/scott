<#--
描述:表单处理及验证
id:表单ID
action:FORM提交地址
label:LABEL样式智能提示取值标识
ajaxPost:是否是AJAX方式提交
tiptype:提示信息类型
beforeSubmit：提交前处理函数
callback:提交成功后的处理函数
tipfun:提示方式函数可选alert,tip
dialog:是否是弹出框模式
btnSubmit:自定义提交按钮
ignoreHidden:隐藏元素不验证
-->
<#macro form 
	id="form" 
	js="flyme_form"
	action="save.do" 
	method="post" 
	beforeCheck="" 
	beforeSubmit="" 
	callback="" 
	label="tdl" 
	tiptype="2" 
	ajaxPost=true 
	btnSubmit=false 
	dialog=true 
	enctype=false
>
<form id="${id}" action="${action}" method="post"<#rt/>
<#if enctype>
 enctype="multipart/form-data"<#rt/>
</#if>
>
<input type="hidden" id="btn_sub" class="btn_sub" />
<#nested/>
</form>
<link rel="stylesheet" href="${base}/flyme/common/css/common.css" />
<link rel="stylesheet" href="${base}/plug-in/Validform/css/style.css" type="text/css"></link>
<script type="text/javascript" src="${base}/plug-in/Validform/js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${base}/plug-in/Validform/js/Validform_Datatype.js"></script>
<script src="${base}/flyme/${js}.js?version=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    VF.id="${id}";
    VF.tiptype="${tiptype}";
    VF.dialog="${dialog}";
    VF.Validform();
});
</script>
</#macro>
