package com.flyme.common.util.html;

import java.util.regex.Matcher; 
import java.util.regex.Pattern;

public class HTMLSpirit {
	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		return htmlStr.trim(); // 返回文本字符串
	}
	
	public static String toHtml(String title,String content) {
		String result="<!DOCTYPE html><html><head><meta charset=\"utf-8\"><base href=\"http://tcedu.weiruanmeng.com\" /><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\"><title>"+
				title+"</title><style>*{max-width: 100% !important; word-break: break-all; word-wrap: break-word;}body{width: 100% !important; margin: 0px !important; padding: 0px !important; background: #fff;font-family: \"Helvetica Neue\",\"微软雅黑\", Helvetica, Arial, sans-serif;}#half_top{width: 94%; border-bottom: 0.0425rem solid #d8d8d8; margin: 10px auto 0px auto; padding: 10px 0px;}#half_top #half_title{font-size: 1.5em; text-align: center;}#half_subtitle{color: #999; padding-top: 10px; font-size:0.8em;}#half_subtitle .half_sub{padding-right: 10px;}#half_content img{max-width: 100% !important; display: block; height: auto !important;}p{-webkit-margin-before: 0.5em;-webkit-margin-after: 0.5em;}#half_content{padding: 0 3% 10px 3%;}#half_content{font-size:0.9em !important; line-height: 1.6em !important; color: #666 !important;}</style></head><body><div id=\"half_content\">"+content+"</div></body></html>";
		return result; 
	}
	
	public static void main(String[] args) {
		System.out.println(delHTMLTag("<p style=\"margin-top: 0px; margin-bottom: 28px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; line-height: 1.75em; font-family: Arial, 宋体; color: rgb(51, 51, 51); white-space: normal; background-color: rgb(255, 255, 255);\"><span style=\"font-size: small;\"><img src=\"https://imgsa.baidu.com/news/q%3D100/sign=cecad9da51afa40f3ac6cadd9b65038c/1c950a7b02087bf4b7eb3901f9d3572c10dfcfd7.jpg\"/>1月3日，中共中央总书记、国家主席、中央军委主席习近平视察中部战区陆军某师。这是习近平登上99A坦克，详细了解装备战技性能。（图片来自：新华网）</span></p><p style=\"margin-top: 0px; margin-bottom: 28px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; line-height: 1.75em; font-family: Arial, 宋体; color: rgb(51, 51, 51); white-space: normal; background-color: rgb(255, 255, 255);\">　　新年伊始，燕赵大地，生机盎然。3日上午10时30分许，习近平来到中部战区陆军某师。他首先察看了部队武器装备。数十台套数字化装备整齐列阵，威风凛凛，习近平边走边看，不时驻足询问。在地面突击系统装备前，习近平饶有兴致地登上99A坦克。这是我国自主研制的新一代主战坦克，有“陆战之王”美誉。在火力打击系统装备前，习近平登上红箭－10导弹发射车，详细了解装备战技性能。习近平强调，要抓住科技创新这个牛鼻子，把部队科技含量充分释放出来，把科技优势转化为能力优势、作战优势。</p><p style=\"margin-top: 0px; margin-bottom: 28px; padding: 0px; font-variant-numeric: normal; font-variant-east-asian: normal; font-stretch: normal; line-height: 1.75em; font-family: Arial, 宋体; color: rgb(51, 51, 51); white-space: normal; background-color: rgb(255, 255, 255);\">　　随后，习近平来到侦察情报营训练场。该营侦察2连是侦察英雄杨子荣生前所在连，被中央军委授予“英雄侦察连”荣誉称号。习近平仔细观看狙击手构筑工事和伪装训练，询问数字化单兵作战系统情况。看到侦察兵生龙活虎，训练有素，习近平很高兴。</p>"));
	}
}
