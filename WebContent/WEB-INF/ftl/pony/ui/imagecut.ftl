<#--图片裁剪控件-->
<#macro imgcut imgId="images" imgSrc="../../res/theme/default/images/nopicture.png" folder="folder" dialog="">
<a href="javascript:;" onclick="Dg.uploadhead({url:'${base}/admin/imageopt/upload_images_turn.do?imgId=${imgId}&folder=${folder}&dialog=${dialog}'});"><img id="${imgId}Img" width="66" height="66" src="../../${imgSrc}" ></a> 
<input type="hidden" name="${imgId}" id="${imgId}Hidden" value="${imgSrc}">
</#macro>