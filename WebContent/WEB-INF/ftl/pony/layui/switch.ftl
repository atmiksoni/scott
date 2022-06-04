<#--开关-->
<#macro switch label="" name="" value="" disabled="" forhide="" text="" disable=false>
<label class="layui-form-label"><b>${label}：</b></label>
<div class="layui-input-inline">
    <input type="hidden" name="${name}" id="${name}" value="${value}">
	<input type="checkbox" 
	<#if value==1> checked</#if><#rt/>
	name="${name}Switch" lay-skin="switch" lay-filter="${name}" value="${value}" <#if text!=""> lay-text="${text}"</#if>  <#if disabled!="">disabled</#if>>
</div>
<script>
      layui.use(['form'], function(){
        var form = layui.form
		form.on('switch(${name})', function(data) {
			if (data.elem.checked) {
				$("#${name}").val(1);
				<#if forhide!="">
				$("${forhide}").removeClass("layui-hide");
				</#if>
			} else {
			    $("#${name}").val(0);
			    <#if forhide!="">
				$("${forhide}").addClass("layui-hide");
				</#if>
			}
		<#if value=="1" && forhide!="">
		  $("${forhide}").removeClass("layui-hide");
		</#if>
		<#if value=="0" && forhide!="">
		  $("${forhide}").addClass("layui-hide");
		</#if>
	});
		});
</script>
</#macro>