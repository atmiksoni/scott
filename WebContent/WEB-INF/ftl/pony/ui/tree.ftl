<#--
描述:ztree生成
tId:ztree ID
oId:后台取值隐藏域Id 如果配置oId树形菜单将以弹出层的形式依附于该控件
oName:后台取值隐藏域name 
url:数据请求地址
w:树形菜单宽度
h:树形菜单高度
autoParam:提交后台的参数名
onClick:onClick回调函数
-->
<#macro ztree tId="ztree" oId="" oName="" url="" tValue="" oValue="" autoParam="'id'" w="120" h="150" onClick="" beforeClick="" view=false addDiyDom=false checkbox=false btn=true>
<div id="${oId}Div" style="display:none;position:absolute;">
</#if>
<ul id="${tId}ztree" class="ztree" style="width:${w}px;height:${h}px"></ul>
<#if oId!="">
</div>
</#if>
<script type="text/javascript">
Fm.loadJc(['../../plug-in/ztree/myplug/ztreeplug.js']);
		var ${tId}setting = {
		    <#if checkbox>
			check: {
   			  enable: true,
   			  chkboxType: { "Y": "", "N": "" }
  			},
  			</#if>
  			<#if view>
			view: {
 				<#if addDiyDom>
 				 addDiyDom:addDiyDom
 				</#if>
  			},
  			</#if>
			async: {
				enable : true,
				dataType :"JSON",
				url:"../${url}",
				autoParam:[${autoParam}]
			},
			callback: {
				onClick:function(e,treeId,treeNode)
				{
				   <#if onClick!="" && oId!="">
				    ${onClick}(e,treeId,treeNode,'${oId}');
				   </#if>
				    <#if onClick!="">
				    ${onClick}(e,treeId,treeNode);
				   </#if>
				}
				<#if beforeClick!="">
				    ,beforeClick:${beforeClick}
			    </#if>
			}
	    };
		$(function(){
		    Fm.initZtree("${tId}ztree",${tId}setting);
		});
		
</script>
</#macro>
