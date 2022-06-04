<#macro laydate id="" name=""  label="" width="200" value="" datarule="" endvalue="" format="YYYY-MM-DD hh:mm:ss" istime=false min="" max="5000" endId="" endlabel="">
<div class="layui-inline">
<label class="layui-form-label"><b>${label}：</b></label>
<div class="layui-input-inline" <#rt/>
<#if width!=""> style="width:${width}px;"</#if>>
	<input type="text"  id="${id}" name="${name}" class="layui-input laydate-icon" value="${value}" <#if datarule!=""> data-rule="${datarule}"</#if>>
</div>
</div>
<#if endId!="">
<div class="layui-inline">
	<label class="layui-form-label">${endlabel}：</label>
	<div class="layui-input-inline" style="width:200px;">
		<input type="text" name="${endId}" id="${endId}" class="layui-input laydate-icon" value="${endvalue}">
	</div>
</div>
</#if>
<script>
  var ${id} = {
   <#if min!="">min: laydate.now(${min}),</#if>
   <#if max!="">max: laydate.now(${max}),</#if>
   istoday: true,
   <#if istime>istime:true,</#if><#rt/>
   format:"${format}",
   choose: function(datas){
      <#if endId!="">
      ${endId}.min = datas; 
      ${endId}.start =datas;
      </#if>
   }
  };
  <#if endId!="">
   var ${endId} = {
    min: laydate.now(${min}),
    <#if max!="">max: laydate.now(${max}),</#if>
    istoday: true,
    <#if istime>istime:true,</#if><#rt/>
    format:"${format}",
    choose: function(datas){
      ${id}.max = datas;
    }
  };
  </#if>
  $('#${id}').click(function(){
    ${id}.elem = this;
    laydate(${id});
  });
  <#if endId!="">
  $('#${endId}').click(function(){
    ${endId}.elem = this;
    ${endId}.format="${format}";
    laydate(${endId});
  });
  </#if>
</script>
</#macro>