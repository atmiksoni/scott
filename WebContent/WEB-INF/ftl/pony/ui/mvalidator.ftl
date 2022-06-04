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
<#macro mvalid 
	id="form" 
	action=""
	class="form"
	method="post" 
	beforeCheck="" 
	beforeSubmit="" 
	callback="" 
>
<form id="${id}" class="${class}" action="${action}" method="post">
<#nested/>
</form>
<script type="text/javascript" src="${base}/plug-in/validator/js/jquery.validator.js"></script>
<script src="${base}/plug-in/validator/local/zh-CN.js"></script>
<script src="${base}/flyme/flyme_form_ydui.js?version=${version}" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    VF.id="${id}";
    VF.Validform();
});
</script>
</#macro>
