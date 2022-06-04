package com.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public class Word3 {
	public static void main(String[] args) {
		

		
		WordUtil mdoc = new WordUtil();  
		// checkorderService.getalllList();
		Map<String, Object> dataMap = new HashMap<String, Object>();  
        dataMap.put("title", "java做word导出功能");  
        
        dataMap.put("checkOrderNo", "123456");
		dataMap.put("engineerName", "张三");
		dataMap.put("maintainDate", "2018-11-28");
		dataMap.put("equipmentTypeName", "测试电梯");
		dataMap.put("companyName", "张三");
		dataMap.put("equipmentNo", "132");
		dataMap.put("equipmentAddress", "中国");
		dataMap.put("manufacturer", "中国制造");
		dataMap.put("factoryDate", "2018-11-28");
		dataMap.put("isoNormal", "GB/13256-2008");
		dataMap.put("capacity", "1L");
		dataMap.put("checkResultCode", "C");
		List<String> checkResults = new ArrayList<String>();
		
		WordBean w1 = new WordBean();
		w1.setCheckResultCode("A");
		WordBean w2 = new WordBean();
		w1.setCheckResultCode("B");

		checkResults.add("A");
		checkResults.add("B");

		dataMap.put("checkResults", checkResults);
		  //插入图片
        System.out.println(Word2.class.getResource("/").getPath());
        String path = Word2.class.getResource("/").getPath();
        dataMap.put("img", mdoc.getImageStr("E:\\eclipse\\scott\\WebContent\\upload\\engineer\\"+"10C15192327C4A2F8340B05FFEBC23D0.jpg"));
        //[{[{checkResultCode:c,checkResultName:维修要求}],img,checkItemName}]
        mdoc.createDoc(dataMap, "C:\\Users\\Administrator\\Desktop\\checkordertest2.doc",1);  
        
        //model.addAttribute("message","导出word成功！！");  

        //return "HelloWorld";
	}
}
