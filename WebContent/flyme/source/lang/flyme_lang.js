/**
 *国际化
 */
var template = function(b, a) {
    return template[typeof a === "object" ? "render": "compile"].apply(template, arguments)
}; (function(a, c) {
    a.version = "2.0.1";
    a.openTag = "{{";
    a.closeTag = "}}";
    a.isEscape = true;
    a.isCompress = false;
    a.parser = null;
    a.render = function(j, h) {
        var g = f(j);
        if (g === undefined) {
            return d({
                id: j,
                name: "Render Error",
                message: "No Template"
            })
        }
        return g(h)
    };
    a.compile = function(o, l) {
        var n = arguments;
        var h = n[2];
        var k = "anonymous";
        if (typeof l !== "string") {
            h = n[1];
            l = n[0];
            o = k
        }
        try {
            var g = b(l, h)
        } catch(m) {
            m.id = o || l;
            m.name = "Syntax Error";
            return d(m)
        }
        function j(p) {
            try {
                return new g(p) + ""
            } catch(q) {
                if (!h) {
                    return a.compile(o, l, true)(p)
                }
                q.id = o || l;
                q.name = "Render Error";
                q.source = l;
                return d(q)
            }
        }
        j.prototype = g.prototype;
        j.toString = function() {
            return g.toString()
        };
        if (o !== k) {
            e[o] = j
        }
        return j
    };
    a.helper = function(g, h) {
        a.prototype[g] = h
    };
    a.onerror = function(h) {
        var g = "[template]:\n" + h.id + "\n\n[name]:\n" + h.name;
        if (h.message) {
            g += "\n\n[message]:\n" + h.message
        }
        if (h.line) {
            g += "\n\n[line]:\n" + h.line;
            g += "\n\n[source]:\n" + h.source.split(/\n/)[h.line - 1].replace(/^[\s\t]+/, "")
        }
        if (h.temp) {
            g += "\n\n[temp]:\n" + h.temp
        }
        if (c.console) {
            console.error(g)
        }
    };
    var e = {};
    var f = function(k) {
        var g = e[k];
        if (g === undefined && "document" in c) {
            var h = document.getElementById(k);
            if (h) {
                var j = h.value || h.innerHTML;
                return a.compile(k, j.replace(/^\s*|\s*$/g, ""))
            }
        } else {
            if (e.hasOwnProperty(k)) {
                return g
            }
        }
    };
    var d = function(h) {
        a.onerror(h);
        function g() {
            return g + ""
        }
        g.toString = function() {
            return "{Template Error}"
        };
        return g
    };
    var b = (function() {
        a.prototype = {
            $render: a.render,
            $escape: function(q) {
                return typeof q === "string" ? q.replace(/&(?![\w#]+;)|[<>"']/g,
                function(r) {
                    return {
                        "<": "&#60;",
                        ">": "&#62;",
                        '"': "&#34;",
                        "'": "&#39;",
                        "&": "&#38;"
                    } [r]
                }) : q
            },
            $string: function(q) {
                if (typeof q === "string" || typeof q === "number") {
                    return q
                } else {
                    if (typeof q === "function") {
                        return q()
                    } else {
                        return ""
                    }
                }
            }
        };
        var o = Array.prototype.forEach ||
        function(u, r) {
            var q = this.length >>> 0;
            for (var t = 0; t < q; t++) {
                if (t in this) {
                    u.call(r, this[t], t, this)
                }
            }
        };
        var j = function(r, q) {
            o.call(r, q)
        };
        var m = "break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined";
        var l = /\/\*(?:.|\n)*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|'[^']*'|"[^"]*"|[\s\t\n]*\.[\s\t\n]*[$\w\.]+/g;
        var n = /[^\w$]+/g;
        var g = new RegExp(["\\b" + m.replace(/,/g, "\\b|\\b") + "\\b"].join("|"), "g");
        var h = /\b\d[^,]*/g;
        var k = /^,+|,+$/g;
        var p = function(q) {
            q = q.replace(l, "").replace(n, ",").replace(g, "").replace(h, "").replace(k, "");
            q = q ? q.split(/,+/) : [];
            return q
        };
        return function(H, J) {
            var F = a.openTag;
            var A = a.closeTag;
            var v = a.parser;
            var r = H;
            var u = "";
            var C = 1;
            var G = {
                $data: true,
                $helpers: true,
                $out: true,
                $line: true
            };
            var I = a.prototype;
            var E = {};
            var y = "var $helpers=this," + (J ? "$line=0,": "");
            var M = "".trim;
            var O = M ? ["$out='';", "$out+=", ";", "$out"] : ["$out=[];", "$out.push(", ");", "$out.join('')"];
            var x = M ? "if(content!==undefined){$out+=content;return content}": "$out.push(content);";
            var w = "function(content){" + x + "}";
            var q = "function(id,data){if(data===undefined){data=$data}var content=$helpers.$render(id,data);" + x + "}";
            j(r.split(F),
            function(S, R) {
                S = S.split(A);
                var Q = S[0];
                var P = S[1];
                if (S.length === 1) {
                    u += B(Q)
                } else {
                    u += K(Q);
                    if (P) {
                        u += B(P)
                    }
                }
            });
            r = u;
            if (J) {
                r = "try{" + r + "}catch(e){e.line=$line;throw e}"
            }
            r = "'use strict';" + y + O[0] + r + "return new String(" + O[3] + ")";
            try {
                var N = new Function("$data", r);
                N.prototype = E;
                return N
            } catch(L) {
                L.temp = "function anonymous($data) {" + r + "}";
                throw L
            }
            function B(P) {
                C += P.split(/\n/).length - 1;
                if (a.isCompress) {
                    P = P.replace(/[\n\r\t\s]+/g, " ")
                }
                P = P.replace(/('|\\)/g, "\\$1").replace(/\r/g, "\\r").replace(/\n/g, "\\n");
                P = O[1] + "'" + P + "'" + O[2];
                return P + "\n"
            }
            function K(R) {
                var S = C;
                if (v) {
                    R = v(R)
                } else {
                    if (J) {
                        R = R.replace(/\n/g,
                        function() {
                            C++;
                            return "$line=" + C + ";"
                        })
                    }
                }
                if (R.indexOf("=") === 0) {
                    var Q = R.indexOf("==") !== 0;
                    R = R.replace(/^=*|[\s;]*$/g, "");
                    if (Q && a.isEscape) {
                        var P = R.replace(/\s*\([^\)]+\)/, "");
                        if (!I.hasOwnProperty(P) && !/^(include|print)$/.test(P)) {
                            R = "$escape($string(" + R + "))"
                        }
                    } else {
                        R = "$string(" + R + ")"
                    }
                    R = O[1] + R + O[2]
                }
                if (J) {
                    R = "$line=" + S + ";" + R
                }
                D(R);
                return R + "\n"
            }
            function D(P) {
                P = p(P);
                j(P,
                function(Q) {
                    if (!G.hasOwnProperty(Q)) {
                        t(Q);
                        G[Q] = true
                    }
                })
            }
            function t(P) {
                var Q;
                if (P === "print") {
                    Q = w
                } else {
                    if (P === "include") {
                        E["$render"] = I["$render"];
                        Q = q
                    } else {
                        Q = "$data." + P;
                        if (I.hasOwnProperty(P)) {
                            E[P] = I[P];
                            if (P.indexOf("$") === 0) {
                                Q = "$helpers." + P
                            } else {
                                Q = Q + "===undefined?$helpers." + P + ":" + Q
                            }
                        }
                    }
                }
                y += P + "=" + Q + ","
            }
        }
    })()
})(template, this);
if (typeof define === "function") {
    define(function(b, a, c) {
        c.exports = template
    })
} else {
    if (typeof exports !== "undefined") {
        module.exports = template
    }
} (function() {
    var c = window.Flyme,
    a = {};
    a.Supported = "en,zh-cn,zh-tw";
    a.DefaultLanguage = "zh-cn";
    a.Map = {};
    a.getCurrentLang = function() {
        var d = c.Cookie.get("_ZVING_LANGUAGE");
        if (d) {
            return d
        }
        d = navigator.language ? navigator.language: navigator.browserLanguage;
        d = d ? d: navigator.systemLanguage;
        if (d) {
            d = d.replace("_", "-").toLowerCase();
            if (d.startsWith("en-")) {
                d = "en"
            }
            if (d.startsWith("ar-")) {
                d = "ar"
            }
            if (d.startsWith("de-")) {
                d = "de"
            }
            if (d.startsWith("es-")) {
                d = "es"
            }
            if (d.startsWith("fr-")) {
                d = "fr"
            }
            if (d.startsWith("pt-")) {
                d = "pt"
            }
            if (d.startsWith("ru-")) {
                d = "ru"
            }
            if (d.startsWith("ja-")) {
                d = "ja"
            }
            if (d.startsWith("ko-")) {
                d = "ko"
            }
            if (d.startsWith("it-")) {
                d = "it"
            }
            if (d.startsWith("zh-")) {
                if (d != "zh-cn") {
                    d = "zh-tw"
                } else {
                    d = "zh-cn"
                }
            }
            return d
        }
        return a.DefaultLanguage
    };
    a.onLangButtonClick = function(f) {
        var e = new Dialog({
            title: a.get("LangDialog.Title"),
            url: c.CONTEXTPATH + "framework/resources/langEditDialog.zhtml?ID=" + f,
            width: 710,
            height: 200,
            onOk: function() {
                c.Node.setValue(f + "_I18N", $DW.getLangString());
                c.Node.setValue(f, $DW.getCurrentLangValue());
                $D.close()
            }
        });
        e.show()
    };
    a.setCurrentLang = function(g, e) {
        var f = c.Cookie.get("_ZVING_LANGUAGE");
        if (f == g) {
            return g
        }
        var d = new DataCollection();
        d.add("Language", "en");
        Server.sendRequest("Application.changeLanguage", d,
        function(h) {
            if (e) {
                window.location.reload()
            }
        })
    };
    var b = a.getCurrentLang();
    if (("," + a.Supported + ",").indexOf("," + b + ",") < 0) {
        c.Console.log("Flyme Frmaework can't support language:" + b);
        b = a.DefaultLanguage
    }
    a.get = function(d) {
        return a.Map[b][d]
    };
    c.Lang = a;
    c.importCss("resources/" + b + ".css")
})();
Flyme.Lang.Map["zh-cn"] = {
    "Common.Invalid": "无效",
    "Common.Error": "错误",
    "Common.Wrong": "不正确",
    "Common.OK": "确定",
    "Common.Cancel": "取消",
    "Common.Close": "关闭",
    "Dialog.BlockedMessage": "发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!",
    "Dialog.SystemMessage": "系统提示",
    "Dialog.Confirm": "信息确认",
    "Dialog.WaitMessage": "请等待",
    "Verify.NotNull": "不能为空",
    "Verify.MustChoose": "必须选择",
    "Verify.NotRight": "不是正确的",
    "Verify.MustBe": "必须是",
    "Verify.MustGreaterThan": "必须大于",
    "Verify.MustLessThan": "必须小于",
    "Verify.MustGreaterThan": "必须大于",
    "Verify.MustEqualOrGreaterThan": "必须大于或等于",
    "Verify.MustEqualOrLessThan": "必须小于或等于",
    "Verify.MustBeRight": "必须是正确的",
    "Verify.char": "个字符",
    "Verify.Number": "数字",
    "Verify.Int": "整数",
    "Verify.Time": "时间",
    "Verify.DateWrong ": "日期格式错误，",
    "Verify.Date": "日期",
    "Verify.DateTime": "日期及时间",
    "Verify.Email": "电子邮箱",
    "Verify.Zip": "邮政编码",
    "Verify.FixedTelNumber": "固定电话号码",
    "Verify.TelNumber": "电话号码",
    "Verify.MobileNumber": "手机号码",
    "Verify.IDNumber": "身份证号码",
    "Verify.RuleError": "校验规则错误，",
    "Verify.BackOfLength": "Length后面必须是",
    "Verify.InputValue": "输入的值",
    "Verify.Length": "长度",
    "Verify.NotAnOption": "不是可选项",
    "Verify.HasError": "还有未正确填写的项，请参照提示修改!",
    "Verify.NoYear": "没有年!",
    "Verify.NoMonth": "没有月!",
    "Verify.NoDay": "没有日!",
    "Verify.TabPageHasError": "页签内有未正确填写的项!",
    "Verify.HiddenElemHasError": "还有未正确填写的项!",
    "DataGrid.SelectCol": "选择要导出的列",
    "DataGrid.Loading": "正在加载......",
    "DataGrid.FirstPage": "第一页",
    "DataGrid.PrevPage": "上一页",
    "DataGrid.NextPage": "下一页",
    "DataGrid.LastPage": "最末页",
    "DataGrid.GotoThe": "转到第",
    "DataGrid.Goto": "跳转",
    "DataGrid.Total0": "共 0 条记录，",
    "DataGrid.10Records": "每页 10 条，",
    "DataGrid.CopyToClipboard": "复制文本",
    "DataGrid.SelectedRowToExcel": "导出选中行成Excel",
    "DataGrid.ToExcel": "导出本页成Excel",
    "DataGrid.AllToExcel": "导出全部成Excel",
    "DataGrid.MustDataGrid": "DataGrid.edit的参数必须是一个DataGrid对象",
    "DataGrid.MustSelected": "请先选择一条记录!",
    "DataGrid.NoChange": "数据未被修改过!",
    "DataGrid.UpdateSuccess": "修改成功!",
    "DataGrid.SelectAllColumn": "选择所有列",
    "DateTime.MonthNames": "1月,2月,3月,4月,5月,6月,7月,8月,9月,10月,11月,12月",
    "DateTime.WeekNames": "日,一,二,三,四,五,六",
    "DateTime.Now": "现在",
    "DateTime.Time": "时间",
    "DateTime.Today": "今日",
    "DateTime.Year": "年",
    "DateTime.Month": "月",
    "Progress.HasCanceled": "任务己取消",
    "Progress.HasCancelling": "正在取消任务...",
    "MsgPop.Countdown": "?秒后关闭",
    "LangDialog.Title": "多语言字段编辑器",
    "Uploader.Browse": "浏览...",
    "Uploader.Start": "开始",
    "Uploader.End": "结束",
    "Uploader.Action": "动作",
    "Uploader.Node": "节点",
    "Uploader.ExistsLonelyNode": "存在孤立节点!",
    "Uploader.ExistsBranchCannotEnd": "存在不能结束的流程分支!",
    "Uploader.MaxFileSize": "允许的文件大小",
    "Uploader.SupportedExts": "允许的文件类型",
    "Uploader.OversizeError": "错误:文件超过允许的大小!",
    "Uploader.ProcessMessage": "文件,共",
    "Uploader.NoFlashPlugin": "<br/>检测到您的Adobe Flash Player版本过低<br/>建议您在线安装最新版本的Flash Player<a href='http://www.adobe.com/go/getflash/' target='_blank'>在线安装</a>",
    "Special.ModifyContainer": "修改内容块边框",
    "Special.ModifyLayout": "修改布局块",
    "Special.AddLayout": "添加布局块",
    "Special.ModifyContent": "修改内容块",
    "Special.EditContent": "编辑内容",
    "Special.PageSetting": "页面属性",
    "Special.Save2Scheme": "保存为方案"
};
Flyme.Lang.Map["zh-tw"] = {
    "Common.Invalid": "无效",
    "Common.Error": "错误",
    "Common.Wrong": "不正確",
    "Common.OK": "確定",
    "Common.Cancel": "取消",
    "Common.Close": "關閉",
    "Dialog.BlockedMessage": "发现弹出窗口被阻止，請更改浏览器设置，以便正常使用本功能!",
    "Dialog.SystemMessage": "系统提示",
    "Dialog.Confirm": "信息確认",
    "Dialog.WaitMessage": "請等待",
    "Verify.NotNull": "不能為空",
    "Verify.MustChoose": "必須選擇",
    "Verify.NotRight": "不是正確的",
    "Verify.MustBe": "必須是",
    "Verify.MustGreaterThan": "必須大於",
    "Verify.MustLessThan": "必須小於",
    "Verify.MustEqualOrGreaterThan": "必須大於或等於",
    "Verify.MustEqualOrLessThan": "必須小於或等於",
    "Verify.MustBeRight": "必須是正確的",
    "Verify.char": "个字符",
    "Verify.Number": "數字",
    "Verify.Int": "整數",
    "Verify.Time": "时间",
    "Verify.DateWrong ": "日期格式错误，",
    "Verify.Date": "日期",
    "Verify.DateTime": "日期及时间",
    "Verify.Email": "電子郵箱",
    "Verify.Zip": "郵政编碼",
    "Verify.FixedTelNumber": "固定電话號碼",
    "Verify.TelNumber": "電话號碼",
    "Verify.MobileNumber": "手机號碼",
    "Verify.IDNumber": "身份證號碼",
    "Verify.RuleError": "校验规则錯誤，",
    "Verify.BackOfLength": "Length後面必須是",
    "Verify.InputValue": "輸入的值",
    "Verify.Length": "長度",
    "Verify.NotAnOption": "不是可選項",
    "Verify.HasError": "還有未正確填寫的項，請參照提示修改!",
    "Verify.NoYear": "没有年!",
    "Verify.NoMonth": "没有月!",
    "Verify.NoDay": "没有日!",
    "Verify.TabPageHasError": "頁签內有未正確填寫的項!",
    "Verify.HiddenElemHasError": "還有未正確填寫的項!",
    "DataGrid.SelectCol": "選擇要導出的列",
    "DataGrid.Loading": "正在加载......",
    "DataGrid.FirstPage": "第一頁",
    "DataGrid.PrevPage": "上一頁",
    "DataGrid.NextPage": "下一頁",
    "DataGrid.LastPage": "最末頁",
    "DataGrid.GotoThe": "轉到第",
    "DataGrid.Goto": "跳轉",
    "DataGrid.Total0": "共 0 條記錄，",
    "DataGrid.10Records": "每頁 10 條，",
    "DataGrid.CopyToClipboard": "複製文本",
    "DataGrid.SelectedRowToExcel": "導出选中行成Excel",
    "DataGrid.ToExcel": "導出本頁成Excel",
    "DataGrid.AllToExcel": "導出全部成Excel",
    "DataGrid.MustDataGrid": "DataGrid.edit的參數必須是一个DataGrid對象",
    "DataGrid.MustSelected": "請先選擇一條記錄!",
    "DataGrid.NoChange": "數据未被修改過!",
    "DataGrid.UpdateSuccess": "修改成功!",
    "DataGrid.SelectAllColumn": "選擇所有列",
    "DateTime.MonthNames": "1月,2月,3月,4月,5月,6月,7月,8月,9月,10月,11月,12月",
    "DateTime.WeekNames": "日,一,二,三,四,五,六",
    "DateTime.Now": "現在",
    "DateTime.Time": "時間",
    "DateTime.Today": "今日",
    "DateTime.Year": "年",
    "DateTime.Month": "月",
    "Progress.HasCanceled": "任務己取消",
    "Progress.HasCancelling": "正在取消任務...",
    "MsgPop.Countdown": "?秒后關閉",
    "LangDialog.Title": "多語言欄位編輯器",
    "Uploader.Browse": "瀏覽...",
    "Uploader.Start": "開始",
    "Uploader.End": "結束",
    "Uploader.Action": "動作",
    "Uploader.Node": "節點",
    "Uploader.ExistsLonelyNode": "存在孤立節點!",
    "Uploader.ExistsBranchCannotEnd": "存在不能結束的流程分支!",
    "Uploader.MaxFileSize": "允許的文檔大小",
    "Uploader.SupportedExts": "允許的文檔類型",
    "Uploader.OversizeError": "錯誤:文檔超過允許的大小!",
    "Uploader.ProcessMessage": "文件,共",
    "Uploader.NoFlashPlugin": "<br/>检测到您的Adobe Flash Player版本過低<br/>建議您在線安裝最新版本的Flash Player<a href='http://www.adobe.com/go/getflash/' target='_blank'>在線安裝</a>",
    "Special.ModifyContainer": "修改內容塊邊框",
    "Special.ModifyLayout": "修改佈局塊",
    "Special.AddLayout": "添加佈局塊",
    "Special.ModifyContent": "修改內容塊",
    "Special.EditContent": "編輯內容",
    "Special.PageSetting": "頁面屬性",
    "Special.Save2Scheme": "保存為方案"
};
Flyme.Lang.Map.en = {
    "Common.Invalid": "Invalid",
    "Common.Error": "Error",
    "Common.Wrong": "Wrong",
    "Common.OK": "OK",
    "Common.Cancel": "Cancel",
    "Common.Close": "Close",
    "Dialog.BlockedMessage": "Dialog is blocked,please check you browser setting!",
    "Dialog.SystemMessage": "System Message",
    "Dialog.Confirm": "Confirm",
    "Dialog.WaitMessage": "Wait Please",
    "Verify.NotNull": " can't be empty",
    "Verify.MustChoose": "Must choose ",
    "Verify.NotRight": " not a right",
    "Verify.MustBe": " must be",
    "Verify.MustGreaterThan": " must greater than",
    "Verify.MustLessThan": " must less than",
    "Verify.MustEqualOrGreaterThan": " equal or greater than ",
    "Verify.MustEqualLessThan": "  equal or less than ",
    "Verify.MustBeRight": " must be right",
    "Verify.char": " char",
    "Verify.Number": " number",
    "Verify.Int": " integer",
    "Verify.Time": " time",
    "Verify.DateWrong ": "Date format wrong,",
    "Verify.Date": " date",
    "Verify.DateTime": " date&time",
    "Verify.Email": " email",
    "Verify.Zip": " zip code",
    "Verify.FixedTelNumber": " fixed tel No.",
    "Verify.TelNumber": " tel No.",
    "Verify.MobileNumber": " mobile tel No.",
    "Verify.IDNumber": " ID No.",
    "Verify.RuleError": "Validation rule error,",
    "Verify.BackOfLength": "length's value must be",
    "Verify.InputValue": "Input value",
    "Verify.Length": "'s length",
    "Verify.NotAnOption": " not an option",
    "Verify.HasError": "Some items are not completed correctly!",
    "Verify.NoYear": "no year!",
    "Verify.NoMonth": "no month!",
    "Verify.NoDay": "no day!",
    "Verify.TabPageHasError": "Some items are not completed correctly in the tab-page !",
    "Verify.HiddenElemHasError": "Some items are not completed correctly!",
    "DataGrid.SelectCol": "Select the columns you want to export",
    "DataGrid.Loading": "Loading......",
    "DataGrid.FirstPage": "FirstPage",
    "DataGrid.PrevPage": "PrevPage",
    "DataGrid.NextPage": "NextPage",
    "DataGrid.LastPage": "LastPage",
    "DataGrid.GotoThe": "goto the",
    "DataGrid.Goto": "GO",
    "DataGrid.Total0": "Total 0 Records,",
    "DataGrid.10Records": "10 Records per page，",
    "DataGrid.CopyToClipboard": "Copy to clipboard",
    "DataGrid.SelectedRowToExcel": "Export the selected rows to Excel",
    "DataGrid.ToExcel": "Export current page to Excel",
    "DataGrid.AllToExcel": "Export all records to Excel",
    "DataGrid.MustDataGrid": "DataGrid.edit parameter must be a DataGrid object",
    "DataGrid.MustSelected": "Please select a record!",
    "DataGrid.NoChange": "Data has not been modified!",
    "DataGrid.UpdateSuccess": "Successfully modified!",
    "DataGrid.SelectAllColumn": "Select All Column",
    "DateTime.MonthNames": "Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sept,Oct,Nov,Dec",
    "DateTime.WeekNames": "Sun,Mon,Tue,Wed,Thu,Fri,Sat",
    "DateTime.Now": "Now",
    "DateTime.Time": "Time",
    "DateTime.Today": "Today",
    "DateTime.Year": " Year ",
    "DateTime.Month": "Month",
    "Progress.HasCanceled": "Task has been canceled",
    "Progress.HasCancelling": "Cancelling task...",
    "MsgPop.Countdown": "Closed after ? seconds",
    "LangDialog.Title": "i18n Field Editor",
    "Uploader.Browse": "Browse...",
    "Uploader.Start": "Start",
    "Uploader.End": "End",
    "Uploader.Action": "Action",
    "Uploader.Node": "Node",
    "Uploader.ExistsLonelyNode": "Exists lonely node!",
    "Uploader.ExistsBranchCannotEnd": "Exists flow branch which cann't be end!",
    "Uploader.MaxFileSize": "Max file size",
    "Uploader.SupportedExts": "Supported file extensions",
    "Uploader.OversizeError": "Error:Over max file size!",
    "Uploader.ProcessMessage": "file,total",
    "Uploader.NoFlashPlugin": "<br/>Detect your version of Adobe Flash Player is too low,<br/>we recommend that you install the latest version of the online Flash Player.<a href='http://www.adobe.com/go/getflash/' target='_blank'>Installation Online</a>",
    "Special.ModifyContainer": "Modify Container",
    "Special.ModifyLayout": "Modify Layout Table",
    "Special.AddLayout": "Add Layout Table",
    "Special.ModifyContent": "Modify Content Options",
    "Special.EditContent": "Edit Content",
    "Special.PageSetting": "Page Setting",
    "Special.Save2Scheme": "Save to Scheme"
};