<#--资源文件引用模板--> <#macro resource types> <#list types?split(",") as t> <#if t?lower_case=="jquery">
<script src="${base}/plug-in/jquery/jquery-1.11.1.min.js?version=${version}"></script>
<script src="${base}/plug-in/jquery/plugins/json2/json2.js?version=${version}"></script>
<script src="${base}/plug-in/jquery/plugins/jqstorage/jquery.storageapi.min.js?version=${version}"></script>
<script src="${base}/flyme/flyme_main.js?version=${version}" config="debug:yes"></script>
<script src="${base}/flyme/flyme_util.js?version=${version}"></script>
</#if>  
<#if t?lower_case=="jqueryui">
<script src="${base}/plug-in/jquery-ui/js/jquery-ui.min.js?version=${version}"></script>
</#if> <#if t?lower_case=="easyui">
<link rel="stylesheet" href="${base}/flyme/common/css/common.css" />
<link rel="stylesheet" type="text/css" href="${base}/plug-in/easyui/themes/bootstrap/easyui.css?version=${version}" />
<link rel="stylesheet" type="text/css" href="${base}/plug-in/easyui/themes/icon.css?version=${version}" />
<link rel="stylesheet" type="text/css" href="${base}/plug-in/easyui/themes/color.css?version=${version}" />
<script src="${base}/plug-in/easyui/jquery.easyui.min.js?version=${version}"></script>
<script src="${base}/plug-in/easyui/locale/easyui-lang-zh_CN.js?version=${version}"></script>
</#if> <#if t?lower_case=="combogrid">
<script src="${base}/flyme/source/easyui/flyme_combogrid.js?version=${version}"></script>
</#if> <#if t?lower_case=="combo">
<script src="${base}/flyme/flyme_combobox.js?version=${version}"></script>
</#if> <#if t?lower_case=="anydatagrid">
<script src="${base}/flyme/source/easyui/flyme_datagrid.js?version=${version}"></script>
<script src="${base}/flyme/source/easyui/flyme_button.js?version=${version}"></script>
<script src="${base}/plug-in/easyui/extends/datagrid-tip.js?version=${version}"></script>
</#if> <#if t?lower_case=="dgdnd">
<script src="${base}/plug-in/easyui/extends/datagrid-dnd.js?version=${version}"></script>
</#if> <#if t?lower_case=="cellediting">
<script src="${base}/plug-in/easyui/extends/datagrid-cellediting.js?version=${version}"></script>
</#if> <#if t?lower_case=="searchbox">
<script src="${base}/flyme/source/easyui/flyme_searchbox.js?version=${version}"></script>
</#if> <#if t?lower_case=="tip">
<script src="${base}/plug-in/easyui/extends/datagrid-tip.js?version=${version}"></script>
</#if> <#if t?lower_case=="nicescroll">
<script src="${base}/plug-in/nicescroll/js/jquery.nicescroll.js?version=${version}"></script>
</#if> <#if t?lower_case=="tree">
<script src="${base}/flyme/source/easyui/flyme_tree.js?version=${version}"></script>
</#if> <#if t?lower_case=="combotree">
<script src="${base}/flyme/source/easyui/flyme_combotree.js?version=${version}"></script>
</#if> <#if t?lower_case=="lodop">
<script src="${base}/plug-in/lodop/LodopFuncs.js?version=${version}"></script>
</#if> <#if t?lower_case=="calendar">
<script src="${base}/flyme/source/other/flyme_calendar.js?version=${version}"></script>
</#if> <#if t?lower_case=="laydate">
<script src="${base}/plug-in/layer/laydate/laydate.js?version=${version}"></script>
<script src="${base}/flyme/source/other/flyme_laydate.js?version=${version}"></script>
</#if> <#if t?lower_case=="zui">
<script src="${base}/plug-in/webui/zui/js/zui.js"></script>
<link rel="stylesheet" href="${base}/plug-in/webui/zui/css/zui.css" type="text/css" media="screen" />
<link rel="stylesheet" href="${base}/plug-in/webui/zui/theme/default/style.css" type="text/css" media="screen" />
</#if> <#if t?lower_case=="picker">
<script src="${base}/plug-in/DatePicker/jquery.DatePicker.min.js?version=${version}"></script>
</#if> <#if t?lower_case=="artemp">
<script src="${base}/plug-in/artTemplate/template.js?version=${version}"></script>
</#if> <#if t?lower_case=="roletemplate">
<script src="${base}/plug-in/artTemplate/template.js?version=${version}"></script>
<script src="${base}/plug-in/artTemplate/temp/flyme_role_template.js?version=${version}"></script>
</#if> <#if t?lower_case=="ueditor">
<script src="${base}/plug-in/ueditor/ueditor.config.js?version=${version}"></script>
<script src="${base}/plug-in/ueditor/ueditor.all.js?version=${version}"></script>
<script src="${base}/flyme/source/other/flyme_ueditor.js?version=${version}"></script>
</#if> <#if t?lower_case=="allueditor">
<script src="${base}/plug-in/ueditor/ueditor.config2.js?version=${version}"></script>
<script src="${base}/plug-in/ueditor/ueditor.all.min.js?version=${version}"></script>
</#if> 
<#if t?lower_case=="echarts">
<script src="${base}/plug-in/echarts/esl.js?version=${version}"></script>
</#if> 
<#if t?lower_case=="echarts2">
<script src="${base}/plug-in/echarts/echarts-plain.js?version=${version}"></script>
</#if> 
<#if t?lower_case=="layer">
<script src="${base}/plug-in/layer/layer.js?v=2.0"></script>
</#if> <#if t?lower_case=="webuploader">
<link rel="stylesheet" type="text/css" href="${base}/plug-in/webuploader/css/webuploader.css">
<link rel="stylesheet" type="text/css" href="${base}/plug-in/webuploader/css/demo.css">
<link rel="stylesheet" type="text/css" href="${base}/plug-in/webuploader/css/style.css">
<script src="${base}/plug-in/webuploader/js/webuploader.js"></script>
<script src="${base}/plug-in/webuploader/js/imgupload.js"></script>
</#if> <#if t?lower_case=="toastr">
<script src="${base}/plug-in/jquery/plugins/toastr/js/toastr.min.js"></script>
<link href="${base}/plug-in/jquery/plugins/toastr/css/toastr.min.css" rel="stylesheet">
</#if> <#if t?lower_case=="sockjs">
<script src="${base}/plug-in/sockjs/sockjs.min.js"></script>
</#if> <#if t?lower_case=="cityselect">
<script src="${base}/plug-in/jquery/plugins/citySelect/jquery.cityselect.js"></script>
</#if> <#if t?lower_case=="fullcalendar">
<script src="${base}/plug-in/jquery/plugins/fullcalendar/js/fullcalendar.min.js"></script>
<script src="${base}/plug-in/jquery/plugins/fullcalendar/js/jquery.fancybox-1.3.1.pack.js"></script>
<script src="${base}/plug-in/jquery/plugins/fullcalendar/js/jquery.form.min.js"></script>
<link rel="stylesheet" href="${base}/plug-in/jquery/plugins/fullcalendar/css/fancybox.css" />
<link rel="stylesheet" href="${base}/plug-in/jquery/plugins/fullcalendar/css/fullcalendar.css" />
<link rel="stylesheet" href="${base}/plug-in/jquery/plugins/fullcalendar/css/jquery-ui.css" />
<link rel="stylesheet" href="${base}/plug-in/jquery/plugins/fullcalendar/css/btn.css" />
</#if> 
<#if t?lower_case=="weiui">
<link rel="stylesheet" href="${base}/plug-in/webui/jqweiui/lib/weui.min.css">
<link rel="stylesheet" href="${base}/plug-in/webui/jqweiui/css/jquery-weui.min.css?v=${version}">
<script src="${base}/plug-in/webui/jqweiui/lib/fastclick.js"></script>
<script src="${base}/plug-in/webui/jqweiui/js/jquery-weui.js"></script>
</#if>
</#list> </#macro>
