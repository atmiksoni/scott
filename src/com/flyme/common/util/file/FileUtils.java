package com.flyme.common.util.file;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.context.PathUtils;
import com.flyme.core.mybatis.alias.PagerInfo;

/**
 * @description:(文件操作辅助类)
 * @author：flyme @data：2013-8-26 下午01:47:58 @version：v 1.0
 */
public class FileUtils {
	/**
	 * 遍历文件夹中文件
	 * 
	 * @param filepath
	 * @return 返回file［］数组
	 */
	public static File[] getFileList(String filepath) {
		File d = null;
		File list[] = null;
		try {
			d = new File(filepath);
			if (d.exists()) {
				list = d.listFiles();/* 取得代表目录中所有文件的File对象数组 */
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return list;
	}

	/**
	 * 分页遍历文件夹中文件
	 * 
	 * @param filepath
	 * @return 返回file［］数组
	 */
	public static List<File> getFileList(String filepath, PagerInfo pagerInfo) {
		File d = null;
		List<File> file = new ArrayList<File>();
		try {
			d = new File(filepath);
			if (d.exists()) {
				// File list[] = d.listFiles();/* 取得代表目录中所有文件的File对象数组 */
				// Integer allCounts = list.length;
				//// Integer pageSize = pagerInfo.getRows();
				// Integer curPageNO = pagerInfo.getCurPageNO();
				/*
				 * //Integer offset = pagerInfo.getOffset(); for (int i =
				 * offset; i < pageSize * curPageNO; i++) { if (i < list.length)
				 * { file.add(list[i]); } }
				 */
				// PagerInfo pa = new PagerInfo(allCounts, curPageNO, pageSize);
				// ContextUtils.getRequest().setAttribute(Global.PAGERSTR,
				// pa.getPageBar());

			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return file;
	}

	/**
	 * 根据路径创建文件
	 */
	public static File getFile(String path) {
		return new File(path);
	}

	/**
	 * 获取文件名称[不含后缀名]
	 * 
	 * @param
	 * @return String
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex).replaceAll("\\s*", "");
	}

	/**
	 * 创建单层路径文件夹
	 */
	public static void makDir(File file) {
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * 创建多层路径文件夹
	 */
	public static void makDirs(File file) {
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 创建多层路径文件夹
	 */
	public static void makDirs(String path) {
		makDirs(getFile(path));
	}

	/**
	 * 根据路径删除指定的目录或文件，无论存在与否
	 * 
	 * @param sPath
	 *            要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false。
	 */
	public static boolean DeleteFolder(String filePath) {
		Boolean flag = false;
		File file = getFile(filePath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return delFile(filePath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(filePath);
			}
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {
		Boolean flag;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = delFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 */
	public static boolean delFile(String filePath) {
		Boolean flag = false;
		if (ObjectUtils.isNotEmpty(filePath)) {
			File file = getFile(filePath);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除单个文件
	 */
	public static boolean removeFile(String filePath) {
		Boolean flag = false;
		if (ObjectUtils.isNotEmpty(filePath)) {
			filePath = PathUtils.getRealPath(filePath);
			File file = getFile(filePath);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				file.delete();
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 创建单层路径文件夹
	 */
	public static void makDir(String path) {
		makDir(getFile(path));
	}

	public static boolean createFile(File file) throws IOException {
		if (!file.exists()) {
			makeDir(file.getParentFile());
		}
		return file.createNewFile();
	}

	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = getFile(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 */
	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}

	public static void main(String args[]) {
		String filePath = "C:/temp/a/b/c/d/e/f/g.txt";
		String filePath2 = "C:/temp/";
		File file = new File(filePath);
		try {

			createFile(file);
			DeleteFolder(filePath2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtend(String filename) {
		return getExtend(filename, "");
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtend(String filename, String defExt) {
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				return (filename.substring(i + 1)).toLowerCase();
			}
		}
		return defExt.toLowerCase();
	}

	public static FileInputStream getFileInputStream(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileInputStream;
	}

	public static String FormetFileSize(long fileS) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 判断文件是否为图片<br>
	 */
	public static boolean isPicture(String filaName) {
		// 文件名称为空的场合
		if (ObjectUtils.isEmpty(filaName)) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		String tmpName = getExtend(filaName);
		// 声明图片后缀名数组
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" }, { "gif", "2" }, { "jfif", "3" }, { "jpe", "4" }, { "jpeg", "5" }, { "jpg", "6" }, { "png", "7" }, { "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (imgeArray[i][0].equals(tmpName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 文件转二进制
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] fileToBetyArray(File file) {
		FileInputStream fileInputStream = null;
		byte[] bFile = null;
		try {
			bFile = new byte[(int) file.length()];
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStream.close();
				bFile.clone();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bFile;

	}

	public static FileInputStream getFileInputStream(String path) {
		FileInputStream fileInputStream = null;
		fileInputStream = getFileInputStream(getFile(path));
		return fileInputStream;
	}
}
