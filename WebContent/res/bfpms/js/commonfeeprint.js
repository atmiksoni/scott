var BASE_PAGER_HEIGHT = 93;// 纸张高度
var BASE_PAGER_WEIGHT = 200;// 纸张宽度
var TABLE_HEIGHT = 145;// table高度
var TABLE_HEAD = 25;// 标题高度
var TABLE_TR = 20;// table行高度
var TITLE_LEFT = 50;// 标题距离左边距
var TITLE_TOP = 15;// 标题距离顶边距
var UNIT = "mm";
var houseNumber;
function commonfeeprint(params, url) {
  LODOP = getLodop();
  top.Dg.z.ajax(url, params, function(data) {
    if (data.length) {
      LODOP.PRINT_INIT(data[0].title + "收款收据" + "_" + 1);
      initPage(data[0], 0);
    } else {
      LODOP.PRINT_INIT(data.title + "收款收据" + "_" + 1);
      initPage(data, 0);
    }
    LODOP.SET_PRINT_PAGESIZE(1, BASE_PAGER_WEIGHT + UNIT, BASE_PAGER_HEIGHT + UNIT, "CreateCustomPage");
    LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE", true);// 预览窗口禁止最小化，并始终最前

    LODOP.PREVIEW();
  });
}
function batchprint(params, url) {
  LODOP = getLodop();
  top.Dg.z.ajax(url, params, function(data) {
    if (data.length > 0) {
      LODOP.PRINT_INIT(data.title + "收款收据" + "_" + 1);
      $(data).each(function(i, v) {
        initPage(v, i);
      });
      LODOP.SET_PRINT_PAGESIZE(1, BASE_PAGER_WEIGHT + UNIT, BASE_PAGER_HEIGHT + UNIT, "CreateCustomPage");
      LODOP.SET_SHOW_MODE("PREVIEW_NO_MINIMIZE", true);// 预览窗口禁止最小化，并始终最前

      LODOP.PREVIEW();
    }
  });
}
function initPage(data, i) {
  var printhtml = "";
  LODOP.NewPage();
  initData(data);
  if (data.houseNumber == null) {
    houseNumber = "";
  } else {
    houseNumber = data.houseNumber;
  }

  LODOP.ADD_PRINT_TEXT(TITLE_TOP, TITLE_LEFT + UNIT, 500, 20, data.title + "收款收据");
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  var titlesize = 18;
  if (data.title.length > 16) {
    titlesize = 16;
  }
  LODOP.SET_PRINT_STYLEA(0, "FontSize", titlesize);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");
  LODOP.SET_PRINT_STYLEA(0, "ItemType", 1);
  LODOP.SET_PRINT_STYLEA(0, "Bold", 1);

  LODOP.ADD_PRINT_TEXTA("t01", 5, 560, 200, 20, data.printNo);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t02", 60, 55, 300, 20, data.communityName);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t03", 60, 510, 300, 20, "收款日期:" + data.createDate);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 10);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t04", 116, 150, 600, 20, houseNumber + "  " + data.userName);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t05", 150, 150, 300, 20, data.bigAmount);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t06", 183, 150, 600, 20, data.content);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t07", 150, 550, 200, 20, "￥ " + data.amount);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t08", 218, 150, 400, 20, data.remark);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 12);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t09", 250, 70, 300, 20, "单位盖章");
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  LODOP.ADD_PRINT_TEXTA("t10", 250, 510, 300, 20, "收款人:" + data.tollUser);
  LODOP.SET_PRINT_STYLEA(0, "FontName", "宋体");
  LODOP.SET_PRINT_STYLEA(0, "FontSize", 9);
  LODOP.SET_PRINT_STYLEA(0, "FontColor", "#000000");

  printhtml = "<table  style='border: 3px double #000000;' height='210' width='620'>";
  printhtml += "<tr>";
  printhtml += "<td><span style='font-weight:bold;text-indent:35px;'>今收到 </span>";
  printhtml += "<hr align='right' style='border: 1px solid #000000;width:520;background-color:#000000;height:1px;'/>";
  printhtml += "<span style='font-weight:bold;text-indent:35px;'>人民币 </span>";
  printhtml += "<hr align='right' style='border: 1px solid #000000;width:520;background-color:#000000;height:1px;'/>";
  printhtml += "<span style='font-weight:bold;text-indent:35px;letter-spacing:13px;'>系付 </span></b>";
  printhtml += "<hr align='right' style='border: 1px solid #000000;width:520;background-color:#000000;height:1px;'/>";
  printhtml += "<span style='font-weight:bold;text-indent:35px;letter-spacing:13px;'>备注 </span></b>";
  printhtml += "<hr align='right' style='border: 1px solid #000000;width:520;background-color:#000000;height:1px;'/>";
  printhtml += "</td>";
  printhtml += "</tr>";
  printhtml += "</table>";
  LODOP.ADD_PRINT_TABLE(TITLE_TOP * 5, 50, 700, TABLE_HEIGHT, printhtml);
  LODOP.SET_PREVIEW_WINDOW(1, 2, 1, 800, 600, data.title + ".打印收据");
}
function initData(data) {
  var fontlength = parseInt(data.title.length) + 4;
  var fontwidth = 7.381 * fontlength;
  TITLE_LEFT = 100 - (fontwidth / 2);
}
