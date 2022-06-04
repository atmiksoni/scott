<#-- 内容简略显示-->
<#-- 
str:要显示的数据
num:最大显示字符数，超过这个数，显示前num个字符和...(默认，可修改)
url:超过长度以后，如果在后面加链接，带上这个 显示为带链接的 [详细]
-->
<#macro substring str="" num=10 url="">
	<#assign str=str!>
	<#if str?length<=num>
		${str}
	<#else>
		${str?substring(0,num)}...
		<#if url!=""&&url.length>0>
			<a href='${url}'>[详细]</a>
		</#if>
	</#if>
</#macro>