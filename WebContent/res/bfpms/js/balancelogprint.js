function balancelogprint(params) {
	LODOP = getLodop(document.getElementById("LODOP"), document.getElementById("LODOP_EM"));
	var BASE_PAGER_HEIGHT = 93;// 纸张高度
	var BASE_PAGER_WEIGHT = 200;// 纸张宽度
	var TABLE_HEIGHT = 145;// table高度
	var TABLE_HEAD = 25;// 标题高度
	var TABLE_TR = 20;// table行高度
	var TITLE_LEFT = 50;// 标题距离左边距
	var TITLE_TOP = 15;// 标题距离顶边距
	var UNIT = "mm";
	top.Dg.z.ajax("balancelog/print_balancelog_sub.do", params, function(data) {
		init(data);
		LODOP.PRINT_INIT(data.title + "收款收据" + "_" + 1);
		LODOP.SET_PRINT_PAGESIZE(1, BASE_PAGER_WEIGHT + UNIT, BASE_PAGER_HEIGHT + UNIT, "");
		LODOP.ADD_PRINT_TEXT(TITLE_TOP, TITLE_LEFT + UNIT, 500, 20, data.title + "收款收据");
		LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
		  var  titlesize=18;
		  if(data.title.length>16){
			  titlesize=16;
		  }
		LODOP.SET_PRINT_STYLEA(0, "FontSize", titlesize);
		LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
		LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
		LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
		
		LODOP.ADD_PRINT_TEXT(50, 520, 200, 20,   data.printNo);
		LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
		LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
		LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
		
		LODOP.ADD_PRINT_TEXT(265, 50, 300, 20, "收款人:" + data.gatherUser);
		LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
		LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
		LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
		
		LODOP.ADD_PRINT_TEXT(50, 330, 300, 20, data.createDate);
		LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
		LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
		LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
		
		LODOP.ADD_PRINT_TEXT(50, 50, 300, 20, data.communityName);
		LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
		LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
		LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
		
		LODOP.ADD_PRINT_TEXT(265, 520, 300, 20, "收款单位(盖章有效)");
		LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
		LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
		LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
		
		LODOP.ADD_PRINT_TEXT(150, 520, 200, 20, "￥  " + data.money);
		LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
		LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
		LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
		LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
		var printhtml = "<table border='1' cellspacing='0' cellpadding='2' style='border:1 solid #000000;border-collapse:collapse;' height='180' width='600'>" +
				"<tr>" +
				"<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>今收到&nbsp;&nbsp;</b>"+ 
				getHouseNo(data.houseNo)+getPeopleName(data.peopleName)+
				"<hr align='right' style='border:0;width:495;background-color:#000000;height:1px;'/>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>人民币&nbsp;&nbsp;</b>"+data.wordsMoney+"<hr align='right' style='border:0;width:510;background-color:#000000;height:1px;'/>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>系&nbsp;&nbsp;&nbsp;&nbsp;付&nbsp;&nbsp;</b>"+data.department+"<hr align='right' style='border:0;width:510;background-color:#000000;height:1px;'/>" +
				"</td>" +
				"</tr>" +
				"</table>";
		LODOP.ADD_PRINT_TABLE(TITLE_TOP * 5, 50, 700, TABLE_HEIGHT, printhtml);
		LODOP.SET_PREVIEW_WINDOW(1,2,1,800,600,data.title+".打印收据");
		//LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE",true);//预览窗口禁止最小化，并始终最前	
		LODOP.PREVIEW();
	});
	
	function init(data) {
		var fontlength = parseInt(data.title.length) + 4;
		var fontwidth = 7.381 * fontlength;
		TITLE_LEFT = 100 - (fontwidth / 2);
	}
	
	function getHouseNo(houseNo){
		if(houseNo!="" && houseNo !=null){
			return houseNo+"&nbsp;&nbsp;";
		}
		return "";
	}
	
	function getPeopleName(peopleName){
		if(peopleName!="" && peopleName !=null){
			return peopleName;
		}
		return "";
	}
}