package com.flyme.common.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("deprecation")
public class ExcelUtils {

	/**
	 * 生成文件工作薄
	 */
	public static Sheet createSheet(String fileName, String sheetName) {
		Workbook wb = new HSSFWorkbook();
		FileOutputStream fileOut = null;
		Sheet sheet = null;

		try {
			fileOut = new FileOutputStream(fileName);
			sheet = wb.createSheet(sheetName);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheet;
	}

	/**
	 * 设置excel列标题
	 */
	public static void createTitle(String[] titles,Sheet sheet, int rowNo) {
		Row row = sheet.createRow(rowNo);
		int i = 0;
		for (String title : titles) {
			createBoldCell(title, row, i);
			i++;
		}
	}
	
	/**
	 * 设置excel列标题
	 */
	public static void createTitle(String[] titles,Sheet sheet, int rowNo,int fontSize) {
		Row row = sheet.createRow(rowNo);
		int i = 0;
		for (String title : titles) {
			createBoldCell(title, row, i,fontSize);
			i++;
		}
	}

	/**
	 * 设置excel 日期 楼号字体样式
	 */
	public static void createSpecilTitle(String[] titles, Sheet sheet, int rowNo) {
		Row row = sheet.createRow(rowNo);
		int i = 0;
		for (String title : titles) {
			createSpecialBoldCell(title, row, i);
			i++;
		}
	}

	public static void saveSheet(String fileName, Sheet sheet) {
		FileOutputStream fileOut = null;

		try {
			fileOut = new FileOutputStream(fileName);
			sheet.getWorkbook().write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Excel 文件要存放的位置 */
	public static String outputFile = "excel_demo.xls";

	/**
	 * 标题列表
	 */
	private static String[] HEAD_LIST = { "序号", "名字", "年龄", "备注" };

	private static String[] VALUE_LIST = { "01", "感觉", "20", "1987-05-07", "........." };

	/**
	 * 字段列表
	 */
	private static String[] FIELD_LIST = { "index", "name", "age", "content" };

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// ------------------------------------------------------------
		List<String[]> list = new ArrayList<String[]>();
		list.add(VALUE_LIST);
		list.add(VALUE_LIST);
		list.add(VALUE_LIST);
		// createExcel(outputFile, HEAD_LIST, list);

		// ------------------------------------------------------------
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", "001");
		map.put("name", "张三");
		map.put("age", "22");
		map.put("content", "大家好");
		dataList.add(map);
		dataList.add(map);
		dataList.add(map);

		createExcel(getHomePath() + "/aa.xls", HEAD_LIST, FIELD_LIST, dataList, "测试");

		// -------------------------------------------------------------
		// readExcel(null);
		// --------------------------------------------------------------
		// new ExcelUtil().testReadExcel();
	}

	/**
	 * 创建通用EXCEL头部
	 * 
	 * @param headString
	 *            头部显示的字符
	 * @param colSum
	 *            该报表的列数
	 */
	public static void createNormalHead(String headString, int colSum, HSSFWorkbook wb, HSSFSheet sheet) {

		HSSFRow row = sheet.createRow(0);

		// 设置第一行
		HSSFCell cell = row.createCell(0);
		row.setHeight((short) 400);

		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(headString));

		// 指定合并区域
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) colSum));
		HSSFCellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		HSSFFont font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 300);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}

	/**
	 * 创建标题行，指定了第几行和文字对齐方式
	 */
	public static void createNormalHead(String headString, int colSum, int rowNum, short align, short textSize, Workbook wb, Sheet sheet) {

		Row row = sheet.createRow(rowNum);

		// 设置第一行
		Cell cell = row.createCell(0);
		row.setHeight((short) 400);

		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(headString));

		// 指定合并区域
		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, colSum));
		CellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(align); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		Font font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) textSize);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}

	/**
	 * 创建通用EXCEL头部
	 * 
	 * @param headString
	 *            头部显示的字符
	 * @param colSum
	 *            该报表的列数
	 */
	public static void createNormalHead(String headString, int colSum, Workbook wb, Sheet sheet) {

		Row row = sheet.createRow(0);

		// 设置第一行
		Cell cell = row.createCell(0);
		row.setHeight((short) 400);

		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(headString));

		// 指定合并区域
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colSum));
		CellStyle cellStyle = wb.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行
		
		//cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		//cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		//cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		//cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 

		// 设置单元格字体
		Font font = wb.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 300);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 输出表
	 */
	public static void export(Workbook wb, String fileName, HttpServletResponse response) {
		try {
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".xls", "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out;
			out = response.getOutputStream();
			wb.write(out);
			out.flush();
			out.close();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getHomePath() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		return fsv.getHomeDirectory().toString();
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, String[] headList, String[] fieldList, List<Map<String, Object>> dataList, String title) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		createNormalHead(title, 18, workbook, sheet);
		HSSFRow row = sheet.createRow(1);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 2);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList[i])));
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		// System.out.println("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, String[] headList, String[] fieldList, List<Map<String, Object>> dataList, String title, HttpServletResponse response) throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		createNormalHead(title, 18, workbook, sheet);
		HSSFRow row = sheet.createRow(1);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================
		if (dataList != null && dataList.size() > 0) {
			for (int n = 0; n < dataList.size(); n++) {
				// 在索引1的位置创建行（最顶端的行）
				HSSFRow row_value = sheet.createRow(n + 2);
				Map<String, Object> dataMap = dataList.get(n);
				// ===============================================================
				for (int i = 0; i < fieldList.length; i++) {

					// 在索引0的位置创建单元格（左上端）
					HSSFCell cell = row_value.createCell(i);
					// 定义单元格为字符串类型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					// 在单元格中输入一些内容
					cell.setCellValue(objToString(dataMap.get(fieldList[i])));
				}
				// ===============================================================
			}
		}
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(excel_name, "UTF-8"));
		response.setContentType("application/msexcel;charset=UTF-8");
		// 新建一输出文件流
		OutputStream out = response.getOutputStream();
		// 把相应的Excel 工作簿存盘
		workbook.write(out);
		out.flush();
		// 操作结束，关闭文件
		out.close();
		response.flushBuffer();

		// System.out.println("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, List<String> headList, List<String> fieldList, List<Map<String, Object>> dataList) throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		// System.out.println("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws HSSFWorkbook
	 */
	public static HSSFWorkbook createExcel(List<String> headList, List<String> fieldList, List<Map<String, Object>> dataList) throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
			}
			// ===============================================================
		}
		return workbook;
	}

	private static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			if (obj instanceof String) {
				return (String) obj;
			} else if (obj instanceof Date) {
				return null;// DateUtil.dateToString((Date)
							// obj,DateUtil.DATESTYLE_SHORT_EX);
			} else {
				return obj.toString();
			}
		}
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题部分
	 * @param valueList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void bulidExcel(String excel_name, String[] headList, List<String[]> valueList) throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================

		for (int n = 0; n < valueList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			String[] valueArray = valueList.get(n);
			// ===============================================================
			for (int i = 0; i < valueArray.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(valueArray[i]);
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		// System.out.println("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excel_name) throws Exception {
		// 结果集
		List<String[]> list = new ArrayList<String[]>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream(excel_name));

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				String[] arrayString = new String[col];
				for (int i = 0; i < col; i++) {
					HSSFCell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString[i] = "";
					} else if (cell.getCellType() == 0) {
						// arrayString[i] = new
						// Double(cell.getNumericCellValue()).toString();
						if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date d = cell.getDateCellValue();
								// DateFormat formater = new
								// SimpleDateFormat("yyyy-MM-dd");
								DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								arrayString[i] = formater.format(d);
							} else {
								arrayString[i] = new BigDecimal(cell.getNumericCellValue()).longValue() + "";
							}
						}
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString[i] = cell.getStringCellValue().trim();
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcelByList(String excel_name) throws Exception {
		// 结果集
		List<List<Object>> list = new ArrayList<List<Object>>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream(excel_name));

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				List<Object> arrayString = new ArrayList<Object>();
				for (int i = 0; i < col; i++) {
					HSSFCell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					} else if (cell.getCellType() == 0) {
						arrayString.add(new Double(cell.getNumericCellValue()).toString());
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString.add(cell.getStringCellValue().trim());
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcelByInputStream(InputStream inputstream) throws Exception {
		// 结果集
		List<List<Object>> list = new ArrayList<List<Object>>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(inputstream);

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		Sheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数

		// //System.out.println("excel行数：
		// "+hssfsheet.getPhysicalNumberOfRows());
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			Row hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				List<Object> arrayString = new ArrayList<Object>();
				for (int i = 0; i < col; i++) {
					Cell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					} else if (cell.getCellType() == 0) {
						arrayString.add(new Double(cell.getNumericCellValue()).toString());
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString.add(cell.getStringCellValue().trim());
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

	/**
	 * 导入 excel
	 * 
	 * @param file
	 *            : Excel文件
	 * @param pojoClass
	 *            : 对应的导入对象 (每行记录)
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Collection importExcel(File file, Class pojoClass) {
		try {
			// 将传入的File构造为FileInputStream;
			FileInputStream in = new FileInputStream(file);
			return importExcelByIs(in, pojoClass);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 导入 excel
	 * 
	 * @param inputstream
	 *            : 文件输入流
	 * @param pojoClass
	 *            : 对应的导入对象 (每行记录)
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Collection importExcelByIs(InputStream inputstream, Class pojoClass) {
		Collection dist = new ArrayList<Object>();
		try {
			// 得到目标目标类的所有的字段列表
			Field filed[] = pojoClass.getDeclaredFields();
			// 将所有标有Annotation的字段，也就是允许导入数据的字段,放入到一个map中
			Map<String, Method> fieldSetMap = new HashMap<String, Method>();
			Map<String, Method> fieldSetConvertMap = new HashMap<String, Method>();
			// 循环读取所有字段
			for (int i = 0; i < filed.length; i++) {
				Field f = filed[i];
				// 得到单个字段上的Annotation
				Excel excel = f.getAnnotation(Excel.class);
				// 如果标识了Annotationd的话
				if (excel != null) {
					// 构造设置了Annotation的字段的Setter方法
					String fieldname = f.getName();
					String setMethodName = "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
					// 构造调用的method，
					Method setMethod = pojoClass.getMethod(setMethodName, new Class[] { f.getType() });
					// 将这个method以Annotaion的名字为key来存入。
					// 对于重名将导致 覆盖 失败，对于此处的限制需要
					fieldSetMap.put(excel.exportName(), setMethod);
					if (excel.importConvertSign() == 1) {
						// ----------------------------------------------------------------
						// update-begin--Author:Quainty Date:20130524
						// for：[8]excel导出时间问题
						// 用get/setXxxxConvert方法名的话，
						// 由于直接使用了数据库绑定的Entity对象，注入会有冲突
						StringBuffer setConvertMethodName = new StringBuffer("convertSet");
						setConvertMethodName.append(fieldname.substring(0, 1).toUpperCase());
						setConvertMethodName.append(fieldname.substring(1));
						// update-end--Author:Quainty Date:20130524
						// for：[8]excel导出时间问题
						// ----------------------------------------------------------------
						Method getConvertMethod = pojoClass.getMethod(setConvertMethodName.toString(), new Class[] { String.class });
						fieldSetConvertMap.put(excel.exportName(), getConvertMethod);
					}
				}
			}
			// 将传入的File构造为FileInputStream;
			// // 得到工作表
			Workbook book = WorkbookFactory.create(inputstream);
			// // 得到第一页
			Sheet sheet = book.getSheetAt(0);
			// // 得到第一面的所有行
			Iterator<Row> row = sheet.rowIterator();
			// 得到第一行，也就是标题行
			Row title = row.next();
			// 得到第一行的所有列
			Iterator<Cell> cellTitle = title.cellIterator();
			// 将标题的文字内容放入到一个map中。
			Map titlemap = new HashMap();
			// 从标题第一列开始
			int i = 0;
			// 循环标题所有的列
			while (cellTitle.hasNext()) {
				Cell cell = cellTitle.next();
				String value = cell.getStringCellValue();
				titlemap.put(i, value);
				i = i + 1;
			}
			// 用来格式化日期的DateFormat
			// SimpleDateFormat sf;
			while (row.hasNext()) {
				// 标题下的第一行
				Row rown = row.next();
				// 行的所有列
				Iterator<Cell> cellbody = rown.cellIterator();
				// 得到传入类的实例
				Object tObject = pojoClass.newInstance();
				int k = 0;
				// 遍历一行的列
				while (cellbody.hasNext()) {
					Cell cell = cellbody.next();
					// 这里得到此列的对应的标题
					String titleString = (String) titlemap.get(k);
					// 如果这一列的标题和类中的某一列的Annotation相同，那么则调用此类的的set方法，进行设值
					if (fieldSetMap.containsKey(titleString)) {
						Method setMethod = (Method) fieldSetMap.get(titleString);
						// 得到setter方法的参数
						Type[] ts = setMethod.getGenericParameterTypes();
						// 只要一个参数
						String xclass = ts[0].toString();
						// 判断参数类型
						if (Cell.CELL_TYPE_STRING == cell.getCellType() && fieldSetConvertMap.containsKey(titleString)) {
							// 目前只支持从String转换
							fieldSetConvertMap.get(titleString).invoke(tObject, cell.getStringCellValue());
						} else {
							if (xclass.equals("class java.lang.String")) {
								// 先设置Cell的类型，然后就可以把纯数字作为String类型读进来了：
								cell.setCellType(Cell.CELL_TYPE_STRING);
								setMethod.invoke(tObject, cell.getStringCellValue());
							} else if (xclass.equals("class java.util.Date")) {
								// update-start--Author:Quainty Date:20130523
								// for：日期类型数据导入不对(顺便扩大支持了Excel的数据类型)
								Date cellDate = null;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									// 日期格式
									cellDate = cell.getDateCellValue();
								} else { // 全认为是 Cell.CELL_TYPE_STRING: 如果不是
											// yyyy-mm-dd hh:mm:ss 的格式就不对(wait
											// to do:有局限性)
									cellDate = stringToDate(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, cellDate);
								// // update-start--Author:lihuan Date:20130423
								// for：导入bug修复直接将导出的Excel导入出现的bug的修复
								// //
								// --------------------------------------------------------------------------------------------
								// String cellValue = cell.getStringCellValue();
								// Date theDate = stringToDate(cellValue);
								// setMethod.invoke(tObject, theDate);
								// // update-end--Author:lihuan Date:20130423
								// for：导入bug修复直接将导出的Excel导入出现的bug的修复
								// //
								// --------------------------------------------------------------------------------------------
							} else if (xclass.equals("class java.lang.Boolean")) {
								boolean valBool;
								if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
									valBool = cell.getBooleanCellValue();
								} else {// 全认为是 Cell.CELL_TYPE_STRING
									valBool = cell.getStringCellValue().equalsIgnoreCase("true") || (!cell.getStringCellValue().equals("0"));
								}
								setMethod.invoke(tObject, valBool);
							} else if (xclass.equals("class java.lang.Integer")) {
								Integer valInt;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valInt = (new Double(cell.getNumericCellValue())).intValue();
								} else {// 全认为是 Cell.CELL_TYPE_STRING
									valInt = new Integer(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valInt);
							} else if (xclass.equals("class java.lang.Long")) {
								Long valLong;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valLong = (new Double(cell.getNumericCellValue())).longValue();
								} else {// 全认为是 Cell.CELL_TYPE_STRING
									valLong = new Long(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valLong);
							} else if (xclass.equals("class java.math.BigDecimal")) {
								BigDecimal valDecimal;
								if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
									valDecimal = new BigDecimal(cell.getNumericCellValue());
								} else {// 全认为是 Cell.CELL_TYPE_STRING
									valDecimal = new BigDecimal(cell.getStringCellValue());
								}
								setMethod.invoke(tObject, valDecimal);
								// //
								// ----------------------------------------------------------------
								// // update-begin--Author:sky Date:20130422
								// for：取值类型调整cell.getNumberCellValue-->>getStringCellValue
								// setMethod.invoke(tObject, new
								// BigDecimal(cell.getStringCellValue()));
								// // update-end--Author:sky Date:20130422
								// for：取值类型调整
								// //
								// ----------------------------------------------------------------
								// update-end--Author:Quainty Date:20130523
								// for：日期类型数据导入不对(顺便扩大支持了Excel的数据类型)
							}
						}
					}
					// 下一列
					k = k + 1;
				}
				dist.add(tObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return dist;
	}

	// update-begin--Author:Quainty Date:20130523
	// for：日期类型数据导入不对(顺便扩大支持了Excel的数据类型)
	// --------------------------------------------------------------------------------------------
	// update-begin--Author:lihuan Date:20130423 for：直接将导出的Excel导入出现的bug的修复
	/**
	 * 字符串转换为Date类型数据（限定格式 YYYY-MM-DD hh:mm:ss）或（YYYY/MM/DD hh:mm:ss）
	 * 
	 * @param cellValue
	 *            : 字符串类型的日期数据
	 * @return
	 */
	private static Date stringToDate(String cellValue) {
		if (cellValue.length() > 19)
			cellValue = cellValue.substring(0, 19);
		Calendar calendar = Calendar.getInstance();
		String[] dateStr = cellValue.split(" ");
		String[] dateInfo = dateStr[0].split("-");
		if (dateInfo.length != 3) {
			dateInfo = dateStr[0].split("/"); // 让 yyyy/mm/dd 的格式也支持
		}
		if (dateInfo.length == 3) {
			int year = Integer.parseInt(dateInfo[0]);
			int month = Integer.parseInt(dateInfo[1]) - 1; // 0~11
			int day = Integer.parseInt(dateInfo[2]);
			calendar.set(year, month, day);
		} else {
			return null; // 格式不正确
		}
		if (dateStr.length > 1) {// 有时间（限定格式 hh:mm:ss）
			String[] timeStr = dateStr[1].split(":");
			if (timeStr.length == 3) {
				int hour = Integer.parseInt(timeStr[0]);
				int minute = Integer.parseInt(timeStr[1]);
				int second = Integer.parseInt(timeStr[2]);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, second);
			} else {
				return null; // 格式不正确
			}
		}
		return calendar.getTime();
	}

	/**
	 * 批量建立简单单元格
	 * 
	 * @param row
	 * @param i
	 * @param objects
	 */
	public static void createCells(Row row, int i, Object... objects) {
		for (Object object : objects) {
			if (null == object) {
				object = "";
			}
			createCell(object.toString(), row, i);
			i++;
		}
	}

	/**
	 * 批量建立简单单元格
	 * 
	 * @param row
	 * @param i
	 * @param objects
	 */
	public static void createCells(Row row, int i, HSSFCellStyle cellStyle, Object... objects) {
		for (Object object : objects) {
			if (null == object) {
				object = "";
			}
			createCell(object.toString(), row, i, cellStyle);
			i++;
		}
	}

	/**
	 * 批量建立简单单元格
	 * 
	 * @param row
	 * @param i
	 * @param objects
	 */
	public static void createCellsBylist(Row row, int i, HSSFCellStyle cellStyle, List<Object> list) {
		for (Object object : list) {
			if (null == object) {
				object = "";
			}
			createCell(object.toString(), row, i, cellStyle);
			i++;
		}
	}

	/**
	 * 批量建立粗体单元格
	 * 
	 * @param row
	 * @param i
	 * @param objects
	 */
	public static void createBoldCellsByList(Row row, int i, short size, List<Object> objects) {
		for (Object object : objects) {
			if (null == object) {
				object = "";
			}
			createBoldCell(object.toString(), row, i, size);
			i++;
		}
	}

	public static void createBoldCells(Row row, int i, short size, Object... objects) {
		for (Object object : objects) {
			if (null == object) {
				object = "";
			}
			createBoldCell(object.toString(), row, i, size);
			i++;
		}
	}

	/***
	 * * 批量建立相同规格单元格 单元格为横向连续，有相同的列数和行数
	 * 
	 * @param row行
	 * @param i
	 *            其实单元格位置
	 * @param size
	 *            字体大小
	 * @param rows
	 *            单元格行数
	 * @param cols
	 *            单元格列数
	 * @param objects
	 */
	public static void createSameCellsByList(Row row, int i, short size, int rows, int cols, List<Object> objects) {
		for (Object object : objects) {
			if (null == object) {
				object = "";
			}
			createBoldCell(object.toString(), row, i, size);
			row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum() + rows - 1, i, i + cols - 1));
			i = i + cols;
		}
	}

	public static void createSameCells(Row row, int i, short size, int rows, int cols, Object... objects) {
		for (Object object : objects) {
			if (null == object) {
				object = "";
			}
			createBoldCell(object.toString(), row, i, size);
			row.getSheet().addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum() + rows - 1, i, i + cols - 1));
			i = i + cols;
		}
	}

	/**
	 * 创建默认单元格，上下左右对齐 value 单元格内容 row 行 size 创建单元格的位置，如果是第一列，i=0
	 */
	public static Cell createCell(String value, Row row, int i) {
		Cell cell = row.createCell(i);
		CellStyle s = row.getSheet().getWorkbook().createCellStyle();
		
		s.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		s.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		s.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		s.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框 
		
		s.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		s.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cell.setCellStyle(s);
		cell.setCellValue(value);
		return cell;
	}

	/**
	 * 创建默认单元格，上下左右对齐 value 单元格内容 row 行 size 创建单元格的位置，如果是第一列，i=0
	 */
	public static Cell createSpecialCell(String value, Row row, int i) {
		Cell cell = row.createCell(i);
		CellStyle s = row.getSheet().getWorkbook().createCellStyle();
		s.setAlignment(CellStyle.ALIGN_LEFT); // 指定单元格居中对齐
		s.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cell.setCellStyle(s);
		cell.setCellValue(value);
		return cell;
	}

	/**
	 * 创建默认单元格
	 * 
	 * @value 单元格值
	 * @row 行
	 * @i 列
	 */
	public static Cell createCell(String value, Row row, int i, HSSFCellStyle style) {
		Cell cell = row.createCell(i);
		style.setAlignment(CellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cell.setCellStyle(style);
		cell.setCellValue(value);
		if(value != null && value.length()>3 && value.substring(0,3).equals("url")){
			XSSFWorkbook workbook = new XSSFWorkbook();
			cell.setCellValue("URL Link");  
	        XSSFHyperlink link = (XSSFHyperlink)workbook.getCreationHelper().createHyperlink(Hyperlink.LINK_URL);
	        link.setAddress(value.substring(0,3));  
	        cell.setHyperlink((XSSFHyperlink) link);
		}
		return cell;
	}

	/**
	 * 创建右对齐的单元格
	 *
	 * @value 单元格值
	 * @row 行
	 * @i 列
	 */
	public static Cell createCellAlignRight(String value, Row row, int i, HSSFCellStyle style) {
		Cell cell = row.createCell(i);
		style.setAlignment(CellStyle.ALIGN_RIGHT); // 指定单元格右对齐
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cell.setCellStyle(style);
		cell.setCellValue(value);
		return cell;
	}

	/**
	 * 创建单元格，上下左右对齐，字体加粗 value 单元格内容 row 行 size 创建单元格的位置，如果是第一列，i=0
	 */
	public static Cell createBoldCell(String value, Row row, int i) {
		Cell cell = createCell(value, row, i);
		Font font = row.getSheet().getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cell.getCellStyle().setFont(font);
		return cell;
	}
	
	/**
	 * 创建单元格，上下左右对齐，字体加粗 value 单元格内容 row 行 size 创建单元格的位置，如果是第一列，i=0
	 */
	public static Cell createBoldCell(String value, Row row, int i,int fontSize) {
		Cell cell = createCell(value, row, i);
		Font font = row.getSheet().getWorkbook().createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)fontSize);
		cell.getCellStyle().setFont(font);
		return cell;
	}

	/**
	 * 创建单元格，上下左右对齐，字体加粗 value 单元格内容 row 行 size 创建单元格的位置，如果是第一列，i=0
	 */
	public static Cell createSpecialBoldCell(String value, Row row, int i) {
		Cell cell = createSpecialCell(value, row, i);
		Font font = row.getSheet().getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cell.getCellStyle().setFont(font);
		return cell;
	}

	/**
	 * 创建单元格，上下左右对齐，字体加粗，大小自定义 value 单元格内容 row 行 i 创建单元格的位置，如果是第一列，i=0 size 字体大小
	 */
	public static Cell createBoldCell(String value, Row row, int i, short size) {
		Cell cell = createCell(value, row, i);
		Font font = row.getSheet().getWorkbook().createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeight(size);
		cell.getCellStyle().setFont(font);
		return cell;
	}

	/**
	 * 统一调整列宽
	 * 
	 * @param size
	 * @param cloumnNums
	 * @param sheet
	 */
	public static void setColumnWidth(int size, int cloumnNums, Sheet sheet) {
		for (int i = 0; i < cloumnNums; i++) {
			sheet.setColumnWidth(i, size);
		}
	}
}