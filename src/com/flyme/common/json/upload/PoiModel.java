package com.flyme.common.json.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.flyme.common.util.ObjectUtils;
import com.flyme.core.mybatis.alias.PagerInfo;

public class PoiModel extends UploadFile {
	private String[] cellField;/* 上传文件对应的实体字段 */
	private List<Object> rowDates = ObjectUtils.getArrayList();/* 实体对象数据集合 */
	private Class<?> cls;
	private PagerInfo pagerInfo;
	private int errorFlag = 0;// 回馈标识
	private int offset = 1;
	private String errorLines;// 回馈错误行信息
	
	public PoiModel(HttpServletRequest request) {
		this.multipartRequest = (MultipartHttpServletRequest) request;
	}
	
	public PoiModel(HttpServletRequest request, Object t) {
		super(request, t);
		this.object = t;
	}
	
	public PoiModel(HttpServletRequest request, Class<?> cls) {
		super(request, cls);
		this.cls = cls;
		this.object = ObjectUtils.getObject(cls);
	}
	
	public PoiModel(Class<?> cls) {
		this.cls = cls;
		this.object = ObjectUtils.getObject(cls);
	}
	
	public PoiModel(HttpServletRequest request, Class<?> cls, PagerInfo pagerInfo) {
		this.cls = cls;
		this.object = ObjectUtils.getObject(cls);
		this.multipartRequest = (MultipartHttpServletRequest) request;
		this.pagerInfo = pagerInfo;
	}
	
	public String[] getCellField() {
		return cellField;
	}
	
	public void setCellField(String[] cellField) {
		this.cellField = cellField;
	}
	
	public List<Object> getRowDates() {
		return rowDates;
	}
	
	public void setRowDates(List<Object> rowDates) {
		this.rowDates = rowDates;
	}
	
	public Class<?> getCls() {
		return cls;
	}
	
	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
	
	public PagerInfo getPagerInfo() {
		return pagerInfo;
	}
	
	public void setPagerInfo(PagerInfo pagerInfo) {
		this.pagerInfo = pagerInfo;
	}
	
	public String getErrorLines() {
		return errorLines;
	}
	
	public void setErrorLines(String errorLines) {
		this.errorLines = errorLines;
	}
	
	public int getErrorFlag() {
		return errorFlag;
	}
	
	public void setErrorFlag(int errorFlag) {
		this.errorFlag = errorFlag;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}
