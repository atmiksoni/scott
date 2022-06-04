package com.flyme.core.groovy;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.flyme.common.util.ObjectUtils;

/**
 * 公式计算
 */
public class GroovyParse {
	
	/**
	 * 公式解析计算
	 */
	public static Object formulaParse(String formula, Map<String, Object> map) {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("context-groovy.xml");
		GroovyScriptEngine scriptEngine = context.getBean(GroovyScriptEngine.class);
		Object value = scriptEngine.executeObject(formula, map);
		return value;
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = ObjectUtils.getHashMap();
		map.put("用量", 955);
		map.put("单价", 0.58);
		map.put("公摊金额", 101);
		map.put("已入住户数", 69);
		map.put("未入住户数", 0);
		map.put("已装修户数", 69);
		map.put("已入住", true);
		map.put("已装修", true);
		map.put("楼层号", 3);
		map.put("累计用量", 1000);
		map.put("最大楼层号", 26);
		map.put("楼层房间数", 19);
		map.put("已入住", true);
		map.put("高层户数", 31);
		map.put("高层未入住户数", 0);
		map.put("抄表周期内装修户数", 3);
		map.put("租赁户数", 0);
		map.put("公摊总数", 0);
		map.put("未入住户数", 0);
		map.put("租赁", false);
		map.put("高层", true);
		map.put("装修日期", "2015-04-12");
		map.put("开始日期", "2015-03-01");
		map.put("结束日期", "2015-03-31");
		// String content = "if(累计用量<2160){" + "if((累计用量+用量)>2160){" +
		// "未超出用量=(2160-累计用量);" + "超出用量=(用量-未超出用量);" +
		// "return 超出用量*0.61+未超出用量*0.56" + "}else{return 用量*0.56;" + "}" + "}" +
		// "else if(累计用量>=2160 && 累计用量<3120){" + "if((累计用量+用量)>3120){" +
		// "未超出用量=(3120-累计用量);" + "超出用量=(用量-未超出用量);" +
		// "return (超出用量*0.86)+(未超出用量*0.61)" + "}else{return 用量*0.61;" + "}" +
		// "}" + "else if(累计用量>3120){" + "return 用量*0.86;" + "}";
		// String cc =
		// "金额=((用量*单价)-3*高层户数+1.5*高层未入住户数)/(4*租赁户数+公摊总数-0.5*未入住户数);" +
		// "if(租赁){" + " return 金额*5;" + "};" + "if(高层&&已入住){" + "return 金额+3" +
		// "};" + "if(高层&&!已入住){" + "return 0.5*金额+1.5" + "};" + "if(已入住){" +
		// "return 金额;" + "};" + "if(!已入住){" + "return 金额*0.5;" + "};";
		String dd = "附加费=0;";
		
		dd += "if(楼层号>15){";
		
		dd += " 附加费=20;}";
		
		dd += "else{";
		
		dd += "附加费=15;";
		
		dd += "};";
		
		dd += "总附加费=抄表周期内装修户数* 附加费;";
		
		dd += "金额=((用量*单价-总附加费)-3*高层户数+2.5*高层未入住户数)/(4*租赁户数+已装修户数-0.5*未入住户数);";
		
		dd += "if(装修日期>=抄表开始日期&&装修日期<=抄表结束日期)";
		
		dd += "{";
		
		dd += "加收=附加费";
		
		dd += "}else";
		
		dd += "{";
		
		dd += "加收=0";
		
		dd += "};";
		
		dd += "if(!已装修)";
		
		dd += "{";
		
		dd += "return 0;";
		
		dd += "};";
		
		dd += "if(租赁)";
		
		dd += "{";
		
		dd += "return 金额*5+加收;";
		
		dd += "};";
		
		dd += "if(高层&&已入住)";
		
		dd += "{";
		
		dd += "return 金额+3+加收;";
		
		dd += "};";
		
		dd += "if(高层&&!已入住)";
		
		dd += "{";
		
		dd += "return 0.5*金额+1.5+加收;";
		
		dd += "};";
		
		dd += "if(已入住)";
		
		dd += "{";
		
		dd += " return 金额+加收;";
		
		dd += "};";
		
		dd += "if(!已入住)";
		
		dd += "{";
		
		dd += " return 金额*0.5+加收;";
		
		dd += "};";
		
		Object object = GroovyParse.formulaParse(dd, map);
		
		System.out.print(object);
	}
}
