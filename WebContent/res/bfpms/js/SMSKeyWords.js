/*需要过滤的关键字*/
var SMS_KEY_WORD = [ "发票", "票据", "抽奖", "名师", "开班", "特邀", "户型", "招生", "产权", "精装", "别墅", "地产" ];
/* 手机号校验正则表达式 */
var pattern_mobile = /^((13[0-9]|15[0-9]|18[0-9]|14[1|3|5|7|8|9]|17[6,7,8])\d{8})|((170[5,9,0,7,8])\d{7})$/;
var pattern_mobile_yd = /^((13[4|5|6|7|8|9]|15[0|1|2|4|7|8|9]|18[2|3|4|7|8]|14[1|3|7|8|9]|17[8])\d{8})|((170[5])\d{7})$/;
var pattern_mobile_lt = /^((13[0|1|2]|15[5|6]|18[5|6]|14[5]|17[6])\d{8})|((170[9,7,8])\d{7})$/;
var pattern_mobile_dx = /^((13[3]|15[3]|18[0|1|9]|17[7])\d{8})|((170[0])\d{7})$/;
function CheckSMSKeyWords(str) {
  var tips = "OK";
  var index = -1;
  var i;
  for (i = 0; i < SMS_KEY_WORD.length; i++) {

    index = str.indexOf(SMS_KEY_WORD[i]);
    if (index >= 0) {
      return "短信内容中包含黑名单词语[" + SMS_KEY_WORD[i] + "]";
    }
  }
  return "OK";
}
var mobiles = "";// 所选手机号
/**
 * 短信统计
 */
$("#smsTotal").linkbutton({
  iconCls : "icon-total",
  plain:true,
  onClick : function() {
    var hourseOwnerIds = tree.getCodes();
    var contactsIds = contactstree.getCodes();
    myparams.hourseOwnerIds = hourseOwnerIds.join(",");
    myparams.contactsIds = contactsIds.join(",");
    cont = myparams.contactsIds.split(",");
    smscontent.z.ajax("sms/stat_smscontent.do", myparams, function(data) {
      if (cont != null) {
        data = data.concat(cont);
      }
      mobiles = data.join(",");
      $("#mobiles").val(data.join(","));
      calWho(mobiles);
    });
  }
});
/* 计算手机个数 */
function countPhone(id) {
  var arrData = new Array();
  var obj = document.getElementById(id);
  var mob = $.trim(obj.value);
  if (mob.length < 9) {
    return;
  }
  arrData = mob.match(/\d{11}/g);
  if (arrData == "" || arrData == null) {
    return;
  }
}
/* 获取运营商类型 */
function getMobileType(val) {
  var type = 0;
  if (pattern_mobile_yd.test(val)) {
    type = 1;
  }
  if (pattern_mobile_lt.test(val)) {
    type = 2;
  }
  if (pattern_mobile_dx.test(val)) {
    type = 3;
  }
  return type;
}

/* 手机号码分类 */
function calWho(mob) {
  var arrData = new Array();
  arrData = mob.match(/\d{11}/g);
  if (arrData.length > 0) {
    var tx = 0, lt = 0, yd = 0;
    for (var i = 0; i < arrData.length; i++) {
      var type = getMobileType(arrData[i]);
      if (type == 3) {
        tx++;
      }
      if (type == 2) {
        lt++;
      }
      if (type == 1) {
        yd++;
      }
    }
    showResultNum(yd, lt, tx);
  }
}
/* 各运营商计费条数计算 */
function showResultNum(mobileNum, UnionNum, TelconNum) {
  $("#cmbs").val(mobileNum);
  $("#cuns").val(UnionNum);
  $("#cts").val(TelconNum);
  var count = Math.ceil($("#counter_sum").text() / 70);
  if (mobileNum > 0) {
    $("#cmbt").val(count);
  } else {
    $("#cmbt").val(0);
  }
  if (UnionNum > 0) {
    $("#cunt").val(count);
  } else {
    $("#cunt").val(0);
  }
  if (TelconNum > 0) {
    $("#ctt").val(count);
  } else {
    $("#ctt").val(0);
  }
  calAllSum();
}

function calAllSum() {
  var cmbs = $("#cmbs").val();
  var cmbt = $("#cmbt").val();
  var cuns = $("#cuns").val();
  var cunt = $("#cunt").val();
  var cts = $("#cts").val();
  var ctt = $("#ctt").val();
  var sum = $("#cmbt").val();
  $("#cmba").val(cmbs * cmbt);
  $("#cuna").val(cuns * cunt);
  $("#cta").val(cts * ctt);
  var sum = cmbt;
  if (sum == 0) {
    sum = cunt;
  }
  if (sum == 0) {
    sum = ctt;
  }
  $("#sumt").val(sum);
  $("#sums").val(parseInt(cmbs) + parseInt(cuns) + parseInt(cts));
  $("#suma").val(cmbs * cmbt + cuns * cunt + cts * ctt);
}
// 祝福签名判断
function checkSignature(title) {
  var Letters = "【】";
  var i;
  var c;
  for (i = 0; i < Letters.length; i++) {
    c = Letters.charAt(i);
    if (title.indexOf(c) > -1)
      return true;
  }
  return false;
}
/* 计算公告文本内容普显示内容条数 */
function countChar(id, size) {
  if (checkSignature($.trim($("#"+id).val()))) {// 内容无签名容
    $("#mark").text();// 签名字数
  } else {// 内容有签名
    $("#mark").text(size);// 签名字数
  }
  var mark = $("#mark").text();// 签名字数
  $("#counter_input").html($("#" + id + "").val().length);
  $("#counter_sum").html(eval(eval(mark) + eval($("#counter_input").text())));
  $("#counter_follow").html(500 - eval($("#counter_sum").text()));

  if (eval($("#counter_sum").text()) > 500) {
    $("#" + id + "").attr('value', document.getElementById(id).value.substring(0, 500 - eval(mark)));
    $("#counter_input").html($("#" + id + "").val().length);
    $("#counter_sum").html(eval(eval(mark) + eval($("#counter_input").text())));
    $("#counter_follow").html(500 - eval($("#counter_sum").text()));
  }

}