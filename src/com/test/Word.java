package com.test;
//package com;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//
//public class Word {
//
//	private Configuration configuration = null;
//
//	public Word() {
//		configuration = new Configuration();
//		configuration.setDefaultEncoding("utf-8");
//	}
//
//	public void createDoc() {
//
//		// Ҫ����ģ���������ļ�
//		Map dataMap = new HashMap();
//		getData(dataMap);
//
//		// ����ģ��װ�÷�����·��,FreeMarker֧�ֶ���ģ��װ�ط�����������servlet��classpath�����ݿ�װ�أ�
//		// ftl�ļ����·��
//		configuration.setClassForTemplateLoading(this.getClass(), "/com/ftl");
//
//		Template t = null;
//		try {
//			// test.ftlΪҪװ�ص�ģ��
//			t = configuration.getTemplate("QQ.ftl");
//			t.setEncoding("utf-8");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// ����ĵ�·��������
//		File outFile = new File("F:/2013/test.doc");
//		Writer out = null;
//		try {
//			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//
//		try {
//			t.process(dataMap, out);
//			out.close();
//		} catch (TemplateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * 
//	 * ע��dataMap���ŵ�����KeyֵҪ��ģ���еĲ������Ӧ
//	 * @param dataMap
//	 */
//	private void getData(Map dataMap) {
//		dataMap.put("title", "Test");
//		dataMap.put("nian", "����һ��");
//		dataMap.put("danweiming", "����");
//		List lists = new ArrayList();
//
//		WordBean w1 = new WordBean();
//		w1.setPaixu("1");
//		w1.setBiaoduan("���һ");
//		WordBean w2 = new WordBean();
//		w2.setPaixu("2");
//		w2.setBiaoduan("��ζ�");
//
//		lists.add(w1);
//		lists.add(w2);
//
//		dataMap.put("wordBeans", lists);
//
//		dataMap.put("paiming", "1");
//
//		dataMap.put("biaoduan", "���һ");
//
//	}
//
//	public static void main(String[] args) {
//		new Word().createDoc();
//	}
//
//}
