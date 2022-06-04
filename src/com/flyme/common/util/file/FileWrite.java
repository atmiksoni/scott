package com.flyme.common.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.flyme.common.util.context.PathUtils;

/**
 * @author Hongten
 * @time 2011-12-12 2011
 */
public class FileWrite {
	public static void main(String[] args) {
		FileWrite myFile = new FileWrite();
		try {
			for (int i = 0; i < 5; i++) {
				myFile.creatTxtFile("test");
				myFile.writeTxtFile("显示的是追加的信息" + i);
				String str = myFile.readDate();
				System.out.println("*********\n" + str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String filenameTemp;
	
	/**
	 * 创建文件
	 */
	public boolean creatTxtFile(String name) throws IOException {
		boolean flag = false;
		String fileFolder = PathUtils.getRealPath("log");// 文件的服务器存放路径
		filenameTemp = fileFolder + name + ".txt";
		File filename = new File(filenameTemp);
		if (!filename.exists()) {
			filename.createNewFile();
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 写文件
	 * 
	 * @param newStr
	 *            新内容
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public boolean writeTxtFile(String newStr) throws IOException {
		// 先读取原有文件内容，然后进行写入操作
		boolean flag = false;
		String filein = newStr + "\r\n";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		OutputStreamWriter ow = null;
		PrintWriter pw = null;
		try {
			// 文件路径
			File file = new File(filenameTemp);
			// 将文件读入输入流
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();
			
			// 保存该文件原有的内容
			for (int j = 1; (br.readLine()) != null; j++) {
				// buf = buf.append(temp);
				// System.getProperty("line.separator")
				// 行与行之间的分隔符 相当于“\n”
				// buf = buf.append(System.getProperty("line.separator"));
			}
			buf.append(filein);
			ow = new OutputStreamWriter(new FileOutputStream(file), "GBK");
			BufferedWriter writer = new BufferedWriter(ow);
			writer.write(buf.toString().toCharArray());
			writer.close();
			flag = true;
		} catch (IOException e1) {
			// TODO 自动生成 catch 块
			throw e1;
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (ow != null) {
				ow.close();
			}
			if (br != null) {
				br.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (fis != null) {
				fis.close();
			}
		}
		return flag;
	}
	
	/**
	 * 读取数据
	 */
	@SuppressWarnings("resource")
	public void readData1() {
		try {
			FileReader read = new FileReader(filenameTemp);
			BufferedReader br = new BufferedReader(read);
			String row;
			while ((row = br.readLine()) != null) {
				System.out.println(row);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("resource")
	public String readDate() {
		// 定义一个待返回的空字符串
		String strs = "";
		try {
			FileReader read = new FileReader(new File(filenameTemp));
			StringBuffer sb = new StringBuffer();
			char ch[] = new char[1024];
			int d = read.read(ch);
			while (d != -1) {
				String str = new String(ch, 0, d);
				sb.append(str);
				d = read.read(ch);
			}
			System.out.print(sb.toString());
			String a = sb.toString().replaceAll("@@@@@", ",");
			strs = a.substring(0, a.length() - 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strs;
	}
}