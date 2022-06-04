<#--
描述:表单处理及验证
id:表单ID
action:FORM提交地址
labelClas:LABEL样式智能提示取值标识
ajaxPost:是否是AJAX方式提交
tiptype:提示信息类型
beforeSubmit：提交前处理函数
callback:提交成功后的处理函数
tipfun:提示方式函数可选alert,tip
dialog:是否是弹出框模式
btnSubmit:自定义提交按钮
-->
<#macro form id="myform" action="" method="post" tiptype="4" action="" method="post" beforeCheck="" beforeSubmit="" callback="" label="tdl" tiptype="4" ajaxPost=true btnSubmit=false dialog=false enctype=false target="">
<form id="${id}" action="${action}" method="post" 
<#if enctype>
enctype="multipart/form-data"
</#if>
>
<#nested/>
</form>
<link rel="stylesheet" href="${base}/plug-in/Validform/css/style.css" type="text/css"></link>
<script type="text/javascript" src="${base}/plug-in/Validform/js/Validform_v5.3.2.js"></script>
<script type="text/javascript" src="${base}/plug-in/Validform/js/Validform_Datatype.js"></script>
<script type="text/javascript">
$(function(){
   $("#${id}").Validform({
		<#if tiptype=="custom">
		tiptype:function(msg,o,cssctl){
			var objtip=$("#checkmsg");
			cssctl(objtip,o.type);
			objtip.text(msg);
		}
		<#else>
		tiptype:${tiptype}
		</#if>
		<#if btnSubmit>
		,btnSubmit:"#btn_sub"
		,btnReset:"#btn_reset"
   		</#if>
		,label:".${label}"
		,ignoreHidden:true
		,ajaxPost:${ajaxPost?string}
		<#if beforeSubmit!="">
		,beforeSubmit:function(curform){
		return ${beforeSubmit}(curform);
		}
		</#if>
		<#if beforeCheck!="">
		,beforeCheck:function(curform){
		return ${beforeCheck}(curform);
		}
		</#if>
		,callback:function(data)
		{
		  <#if callback!="">
		  ${callback}(data);
		  </#if>
		  <#if dialog>
		  var win = frameElement.api.opener;
		  top.Dg.alert(data.info,data.alerttype);
		  frameElement.api.close();
		  if(data.success){
		 	win.Fm.href(data.href);
		  }
		  <#else>
		   <#if ajaxPost>
		      top.Dg.alert(data.info,data.alerttype);
		      <#if target>
		      	<#if target=="_parent">
		      		parent.Fm.href(data.href);
		      	</#if>
		      	<#if target=="_top">
		      		top.Fm.href(data.href);
		      	</#if>
		      <#else>
		        if(data.success)
		        {
		      	  Fm.href(data.href);
		      	}
		      </#if>
		    </#if>
		  </#if>
		  
		}
	});
});
</script>
</#macro>
