package com.flyme.common.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

public class MyExcel {
	private HSSFWorkbook wb = new HSSFWorkbook();
	private Sheet sheet;
	
	public MyExcel() {
		this.sheet = wb.createSheet();
	}
	
	public HSSFWorkbook getWb() {
		return wb;
	}
	
	public Sheet getSheet() {
		return sheet;
	}
	
	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}
	
	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
	public void setColumnWidth(int columnSize, int width) {
		for (int i = 0; i < columnSize; i++) {
			sheet.setColumnWidth(i, 4000);// 设置标题单元格宽度
		}
	}
	
}
