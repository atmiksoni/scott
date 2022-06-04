package com.test;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import javax.imageio.ImageIO;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.flyme.common.util.context.PathUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.map.CriterionMap;

import sun.misc.BASE64Encoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class WordUtil {

	private Configuration configuration = null;  
	
	public WordUtil(){
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");  
	}
	
	//dataMap 要填入模本的数据文件  
	public void createDoc(Map<String,Object> dataMap,String fileName,Integer type) {  
	    //设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，  
	    //这里我们的模板是放在template包下面  
		System.out.println(WordUtil.class.getClassLoader().getResource("/template/"));
	    configuration.setClassForTemplateLoading(WordUtil.class, "/template/");  
	    Template t=null;
	    try {  
	        //test.ftl为要装载的模板  
	    	if(type == 1){
	    		t = configuration.getTemplate("newcheckorder.ftl");//
	    	}else if(type == 2){
	    		t = configuration.getTemplate("workorderdemo3.ftl");         //w
	    	}else if(type == 3){
	    		t = configuration.getTemplate("workorderdemoEnglish.ftl");      //w
	    	}else if(type == 4){
	    		t = configuration.getTemplate("formalcheckorderEnglish.ftl");//
	    	}else if(type==5){
	    		t = configuration.getTemplate("ordere.ftl");
	    	}else if(type==6){
	    		t = configuration.getTemplate("ordereeng.ftl");
	    	}else if(type==7){
	    		t = configuration.getTemplate("englishtotaldata.ftl");//英文 统计报表
	    	}else if(type==8){
	    		t = configuration.getTemplate("totaldata.ftl"); //中文统计报表
	    	}
	    	 //test.ftl为要装载的模板  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    //输出文档路径及名称  
	    File outFile = new File(fileName);  
	    Writer out = null;  
	    FileOutputStream fos=null;  
	    try {  
	        fos = new FileOutputStream(outFile);  
	        OutputStreamWriter oWriter = new OutputStreamWriter(fos,"UTF-8");  
	        //这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。  
	        //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));  
	         out = new BufferedWriter(oWriter);   
	    } catch (FileNotFoundException | UnsupportedEncodingException e1) {  
	        e1.printStackTrace();  
	    }  
	       
	    try {  
	        t.process(dataMap, out);  
	        out.close();  
	        fos.close();  
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
	 public CriterionMap getImageWidthAndHeight(String images){
		  CriterionMap map=new CriterionMap();
		 File picture = new File(images);
	        BufferedImage sourceImg;
			try {
				sourceImg = ImageIO.read(new FileInputStream(picture));
				 System.out.println(String.format("%.1f",picture.length()/1024.0));// 源图大小
			        System.out.println(sourceImg.getWidth()); // 源图宽度
			        System.out.println(sourceImg.getHeight()); // 源图高度
			      
			        map.put("width", sourceImg.getWidth()+"px");
			        map.put("height", sourceImg.getHeight()+"px");
			        
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
	       return map;
	 }
}
