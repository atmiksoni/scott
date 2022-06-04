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
<#macro valid 
	id="form" 
	ui="easyui"
	action=""
	layui=false
	class="form"
	method="post" 
	beforeCheck="" 
	beforeSubmit="" 
	callback="" 
	label="tdl" 
	tiptype="2" 
	timely=2
	msgClass="n-right",
	msgStyle="",
	ajaxPost=true 
	msgMaker=true,
	btnSubmit=false 
	dialog=true 
	enctype=false
>
<#if layui>
<link rel="stylesheet" href="${base}/plug-in/webui/layui/css/layui.css" media="all">
<link rel="stylesheet" href="${base}/plug-in/webui/layui/mycss/mylayui.css" media="all">
<script src="${base}/plug-in/webui/layui/layui.js" charset="utf-8"></script>
<script src="${base}/flyme/flyme_layui.js?version=${version}" type="text/javascript"></script>
</#if>
<link rel="stylesheet" href="${base}/flyme/common/css/common.css" />
<#if layui>
<link rel="stylesheet" href="${base}/plug-in/validator/jquery.validator.css" type="text/css"></link>
</#if>
<script type="text/javascript" src="${base}/plug-in/validator/js/jquery.validator.js"></script>
<script src="${base}/plug-in/validator/local/zh-CN.js"></script>
<#if ajaxPost>
<script src="${base}/flyme/flyme_form_${ui}.js?version=${version}" type="text/javascript"></script>
<script type="text/javascript">
layui.use('form', function(){
	form = layui.form;
    VF.id="${id}";
    VF.tiptype="${tiptype}";
    VF.msgClass="${msgClass}";
    VF.dialog="${dialog}";
    VF.msgMaker="${msgMaker}";
    VF.msgStyle="${msgStyle}";
    VF.timely="${timely}";
    VF.Validform();
    <#if layui>
       form.render();
	</#if>
});
</script>
<#else>
<script type="text/javascript">
   $('#${id}').validator({
    display: function(el){
        return el.getAttribute('placeholder') || '';
    },
    msgClass : "n-right",
    theme : 'yellow_right_effect'
     <#if beforeSubmit!="">
     ,valid: function(form){
      ${beforeSubmit}(form);
     }
     </#if>
   });
</script>
</#if>
<form id="${id}" style="padding-top:5px;" class="${class}" action="${action}" method="post"<#rt/>
<#if enctype>
 enctype="multipart/form-data"<#rt/>
</#if>
>
<#if dialog>
<input type="hidden" id="btn_sub" class="btn_sub" />
</#if>
<#nested/>
</form>

</#macro>
