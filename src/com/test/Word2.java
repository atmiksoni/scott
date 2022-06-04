package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flyme.common.util.context.PathUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import sun.misc.BASE64Encoder;

public class Word2 {

	private Configuration configuration = null;

	public Word2() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}

	public void createDoc() {

		// 要填入模本的数据文件
		Map dataMap = new HashMap();
		getData(dataMap);

		// 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		// ftl文件存放路径
		configuration.setClassForTemplateLoading(this.getClass(), "/com/ftl");

		Template t = null;
		try {
			// test.ftl为要装载的模板
			t = configuration.getTemplate("checkordertest2.ftl");
			t.setEncoding("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 输出文档路径及名称
		File outFile = new File("C:\\Users\\Administrator\\Desktop\\checkordertest2.doc");
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			t.process(dataMap, out);
			out.close();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 图片处理
	 * @param imgFile
	 * @return
	 */
	 public String getImageStr(String imgFile){
        InputStream in=null;
        byte[] data=null;
        try {
            in=new FileInputStream(imgFile);
            data=new byte[in.available()];
            in.read(data);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder=new BASE64Encoder();
        return encoder.encode(data);
    }
	/**
	 * 
	 * 注意dataMap里存放的数据Key值要与模板中的参数相对应
	 * @param dataMap
	 */
	private void getData(Map dataMap) {
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
        dataMap.put("img", getImageStr(path+"img/Hydrangeas.jpg"));
	}

	public static void main(String[] args) {
		System.out.println(PathUtils.getRealPath());
		/*new Word2().createDoc();
		 WordToPdf d = new WordToPdf();
	        d.wordToPDF("C:\\Users\\Administrator\\Desktop\\checkordertest2.doc", "C:\\Users\\Administrator\\Desktop\\checkordertest2.pdf");*/
	}

}
