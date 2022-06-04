<#--
上传组件
-->
<#macro upload list="" value=""  btnId="" id="" name="" fileType="" inputId="" text="选择文件" pathField="realpath" titleField="" idField="" delurl="" delClick="">
<#if btnId!="">
<br>
<div id="${btnId}">${text}</div>
</#if>
<div id="${id}" class="uploader-list">
<#if list?exists>
<#if list?is_sequence>
	<#list list as item>
      <div id="${id}WU_FILE_${item_index}" class="file-item thumbnail">
      <div class="file-panel" style="background: #01B7EE;opacity:0.8;"><span class="cancel" onclick="Fm.deleteFiles(this,'${delurl}?fileId=${item[idField]}','${item[idField]}','${inputId}');">删除</span></div>
      <img class="go" style="cursor: pointer" onclick="top.Dg.imageview();" height="120" width="120" src="${base}/${item[pathField]}">
      <#if titleField!=""> <div class="info">${item[titleField]}</div></#if>
       </div><#t/>
	</#list>
</#if>
</#if>
<#if value!="">
  <div id="${id}WU_FILE_0" class="file-item thumbnail">
  	  <div class="file-panel" style="background: #01B7EE;opacity:0.8;"><span class="cancel" onclick="Fm.deleteFile(this,'${delurl}?fileId=${value}','${pathField}','${inputId}');">删除</span></div>
      <#if fileType=="">
      	<img class="go" style="cursor:pointer"  height="100px" width="100px" src="${base}/${value}">
	  </#if>
	  <#if fileType!="">
	  	<br>
	  	<br>
      	<p class="imgWrap">不能预览</p>
      	<p class="title" style="max-width:100px;">${value}</p>
	  </#if>
      </div>
</#if>
<#if name!="">
<input type="hidden" class="txtInput" id="${name}Hidden"  name="${name}" value="${value}"/>
</#if>
</div>
</#macro>
