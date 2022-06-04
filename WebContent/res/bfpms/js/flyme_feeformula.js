var ue;
$(function() {
	var ifs = "<p><b>if</b>(){<br/><br/>}</p>";
	var ifelse = "<b>if</b>(){<br/><br/>}<b>else</b>{<br/><br/>}";
	var ren = "<span style='color:blue'>      return </span>";
	var jzmj = "<span style='font-size:14px;color:#CD661D'>建筑面积</span>";
	var symj = "<span style='font-size:14px;color:#CD661D'>使用面积</span>";
	var zxDate = "<span style='font-size:14px;color:#CD661D'>装修日期</span>";
	var bymj = "<span style='font-size:14px;color:#CD661D'>备用面积</span>";
	var floorNo = "<span style='font-size:14px;color:#CD661D'>楼层号</span>";
	var maxFloorNo = "<span style='font-size:14px;color:#CD661D'>最大楼层号</span>";
	var floorHouseNum = "<span style='font-size:14px;color:#CD661D'>楼层房间数</span>";
	var num = "<span style='font-size:14px;color:#CD661D'>用量</span>";
	var totalnum = "<span style='font-size:14px;color:#CD661D'>全年用量</span>";
	var halfyearyl = "<span style='font-size:14px;color:#CD661D'>半年用量</span>";
	var price = "<span style='font-size:14px;color:#CD661D'>单价</span>";
	var pubamount = "<span style='font-size:14px;color:#CD661D'>公摊金额</span>";
	var pubcount = "<span style='font-size:14px;color:#CD661D'>公摊户数</span>";
	var periodNum = "<span style='font-size:14px;color:#CD661D'>计费天数</span>";
	var join = "<span style='font-size:14px;color:#CD661D'>已入住</span>";
	var joinNum = "<span style='font-size:14px;color:#CD661D'>已入住户数</span>";
	var zxNum = "<span style='font-size:14px;color:#CD661D'>已装修户数</span>";
	var soldNum = "<span style='font-size:14px;color:#CD661D'>未装修户数</span>";
	var unjoinNum = "<span style='font-size:14px;color:#CD661D'>未入住户数</span>";
	var zx = "<span style='font-size:14px;color:#CD661D'>已装修</span>";
	var sold = "<span style='font-size:14px;color:#CD661D'>已售</span>";
	var cbzxZxNum = "<span style='font-size:14px;color:#CD661D'>抄表周期内装修户数</span>";
	var cbBeginDate = "<span style='font-size:14px;color:#CD661D'>抄表开始日期</span>";
	var cbEndDate = "<span style='font-size:14px;color:#CD661D'>抄表结束日期</span>";
	$(".easyui-accordion .easyui-linkbutton").click(function() {
		var modle = $(this).attr("id");
		var val = "";
		switch (modle) {
		case "if":
			UE.getEditor('editor').execCommand('insertHtml', ifs);
			break;
		case "ifelse":
			UE.getEditor('editor').execCommand('insertHtml', ifelse);
			break;
		case "return":
			UE.getEditor('editor').execCommand('insertHtml', ren);
			break;
		case "symj":
			UE.getEditor('editor').execCommand('insertHtml', symj);
			break;
		case "jzmj":
			UE.getEditor('editor').execCommand('insertHtml', jzmj);
			break;
		case "bymj":
			UE.getEditor('editor').execCommand('insertHtml', bymj);
			break;
		case "floorNo":
			UE.getEditor('editor').execCommand('insertHtml', floorNo);
			break;
		case "maxFloorNo":
			UE.getEditor('editor').execCommand('insertHtml', maxFloorNo);
			break;
		case "floorHouseNum":
			UE.getEditor('editor').execCommand('insertHtml', floorHouseNum);
			break;
		case "num":
			UE.getEditor('editor').execCommand('insertHtml', num);
			break;
		case "totalnum":
			UE.getEditor('editor').execCommand('insertHtml', totalnum);
			break;
		case "halfyearyl":
			UE.getEditor('editor').execCommand('insertHtml', halfyearyl);
			break;
		case "price":
			UE.getEditor('editor').execCommand('insertHtml', price);
			break;
		case "pubamount":
			UE.getEditor('editor').execCommand('insertHtml', pubamount);
			break;
		case "pubcount":
			UE.getEditor('editor').execCommand('insertHtml', pubcount);
			break;
		case "join":
			UE.getEditor('editor').execCommand('insertHtml', join);
			break;
		case "unjoinNum":
			UE.getEditor('editor').execCommand('insertHtml', unjoinNum);
			break;
		case "joinNum":
			UE.getEditor('editor').execCommand('insertHtml', joinNum);
			break;
		case "zxNum":
			UE.getEditor('editor').execCommand('insertHtml', zxNum);
			break;
		case "soldNum":
			UE.getEditor('editor').execCommand('insertHtml', soldNum);
			break;
		case "sold":
			UE.getEditor('editor').execCommand('insertHtml', sold);
			break;
		case "zx":
			UE.getEditor('editor').execCommand('insertHtml', zx);
			break;
		case "periodNum":
			UE.getEditor('editor').execCommand('insertHtml', periodNum);
			break;
		case "cbzxZxNum":
			UE.getEditor('editor').execCommand('insertHtml', cbzxZxNum);
			break;
		case "cbBeginDate":
			UE.getEditor('editor').execCommand('insertHtml', cbBeginDate);
			break;
		case "cbEndDate":
			UE.getEditor('editor').execCommand('insertHtml', cbEndDate);
			break;
		case "zxDate":
			UE.getEditor('editor').execCommand('insertHtml', zxDate);
			break;
		case "demo1":
			val = "";
			val += "<p style='font-size:13px;color:#8B8989'>/**<br>";
			val += "  小于3吨按3吨计算<br>";
			val += "  大于等于3吨按实际用量计算<br>";
			val += "*/</p><br>";
			val += "<b>if</b>(<span style='font-size:14px;color:#CD661D'>用量</span>< 3)<br>";
			val += "{<br>";
			val += "<span style='font-size:14px;color:#CD661D'>    用量</span> = 3;<br>"
			val += "}<br>"
			val += "<b>else</b><br>{<span style='color:blue'><br>  return</span> <span style='font-size:14px;color:#CD661D'>用量</span> * <span style='font-size:14px;color:#CD661D'>单价</span>;<br>}"
			UE.getEditor('editor').setContent(val, false);
			break;

		case "demo2":
			val = "";
			val += "<p style='font-size:13px;color:#8B8989'>/**<br>";
			val += "   电费公式<br>";
			val += "*/</p>";
			val += "<span style='color:blue'>return</span> <span style='font-size:14px;color:#CD661D'>用量</span> * <span style='font-size:14px;color:#CD661D'>单价</span>;"
			UE.getEditor('editor').setContent(val, false);
			break;

		case "demo3":
			val = "";
			val += "<p style='font-size:13px;color:#8B8989'>/**<br>";
			val += "    物业费计算公式<br>";
			val += "*/</p>";
			val += "<span style='color:blue'>return</span> <span style='font-size:14px;color:#CD661D'>建筑面积</span> * <span style='font-size:14px;color:#CD661D'>单价</span>;"
			UE.getEditor('editor').setContent(val, false);
			break;

		case "demo4":
			val = "";
			val += "<p style='font-size:13px;color:#8B8989'>/**<br>  阶梯水费<br>";
			val += "  0-3吨    用量为2元/吨<br>";
			val += "  3-10吨   用量为3元/吨<br>"
			val += "  10吨以上 用量为4元/吨<br>"
			val += "*/</p>";
			val += "<b>if</b>(<span style='font-size:14px;color:#CD661D'>用量</span>< 3)<br>";
			val += "{"
			val += "	<span style='color:blue'><br>      return</span> <span style='font-size:14px;color:#CD661D'>用量</span>* 2<br>";
			val += "}"
			val += "<b><br>else if</b>(<span style='font-size:14px;color:#CD661D'>用量</span>>= 3 && <span style='font-size:14px;color:#CD661D'>用量</span>< 10)<br>";
			val += "{";
			val += "	<span style='color:blue'><br>      return</span>  3 * 2 + (<span style='font-size:14px;color:#CD661D'>用量</span>-3) * 3<br>";
			val += "}<br>";
			val += "else<br>";
			val += "{<br>";
			val += "	<span style='color:blue'>      return</span>  3 * 2 + 7 * 3 + (<span style='font-size:14px;color:#CD661D'>用量</span>-10)* 4";
			val += "<br>}";
			UE.getEditor('editor').setContent(val, false);
			break;

		case "demo5":
			val = "";
			val += "<p style='font-size:13px;color:#8B8989'>/**";
			val += "<br>  电梯费计算方法是5楼以下不收费，5楼为每户10元，增加一层楼增加2元<br>";
			val += "*/</p>";
			val += "<b>if</b>(<span style='font-size:14px;color:#CD661D'>楼层号</span> >= 5)<br>";
			val += "{<br>";
			val += "	   <span style='color:blue'>return</span> 10 + (<span style='font-size:14px;color:red'>楼层号</span> - 5) * 2;<br>";
			val += "}<br>";
			val += "<b>else</b><br>";
			val += "{<br>";
			val += "	   <span style='color:blue'>return</span> 0;<br>";
			val += "}<br>";
			UE.getEditor('editor').setContent(val, false);
			break;
		case "demo6":
			val = "";
			val += "<p style='font-size:13px;color:#8B8989'>/**";
			val += "<br>  公摊电梯费系数法(1楼不摊)<br>";
			val += "*/</p>";
			val += "<span style='color:blue'>return (2*(楼层号-1)*用量*单价)/(最大楼层号*(最大楼层号-1)*楼层房间数)</span> ";
			val += "}<br>";
			UE.getEditor('editor').setContent(val, false);
			break;
		}
	});
	$('#save').linkbutton({
		iconCls : 'icon-save',
		onClick : function() {
			var feeItemId = $("#feeItemId").val();
			var feeFormulaName = $("#feeFormulaName").val();
			var contentTxt = UE.getEditor('editor').getContentTxt();
			var content = UE.getEditor('editor').getContent();
			if (top.Dg.z.checkObj(feeFormulaName, "请填写公式名称")) {
				var params = {
					feeItemId : feeItemId,
					feeFormulaName : feeFormulaName,
					feeFormulaContent : contentTxt,
					feeFormulaFullContent : content
				}
				top.Dg.z.ajax("feeformula/add_feeformula_save.do", params, function(data) {
					refreshParentIframe();
					top.closeTab(top.Current_iframeID());
				});
			}

		}
	});
	$('#update').linkbutton({
		iconCls : 'icon-save',
		onClick : function() {
			var feeItemId = $("#feeItemId").val();
			var feeFormulaId = $("#feeFormulaId").val();
			var feeFormulaName = $("#feeFormulaName").val();
			var contentTxt = UE.getEditor('editor').getContentTxt();
			var content = UE.getEditor('editor').getContent();
			if (top.Dg.z.checkObj(feeFormulaName, "请填写公式名称")) {
				var params = {
					feeFormulaId : feeFormulaId,
					feeFormulaName : feeFormulaName,
					feeFormulaContent : contentTxt,
					feeFormulaFullContent : content
				}
				top.Dg.z.ajax("feeformula/edit_feeformula_sub.do", params, function(data) {
					refreshParentIframe();
					top.closeTab(top.Current_iframeID());
				});
			}

		}
	});

	function refreshParentIframe() {
		var feeItemId = $("#feeItemId").val();
		var parentId = "tabs_feeformula" + feeItemId + "_iframe";
		var iframe = top.$("#" + parentId).attr("id");
		if (iframe) {
			top.Dg.z.refreshIframe(parentId);
		} else {
			top.$("#tabs_24_iframe")[0].contentWindow.eval("feeformula").refresh();
		}
	}
});